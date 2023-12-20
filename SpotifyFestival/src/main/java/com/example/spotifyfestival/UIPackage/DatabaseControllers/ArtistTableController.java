package com.example.spotifyfestival.UIPackage.DatabaseControllers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.GenericsPackage.GenericObservableList;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.ArtistDAO;
import com.example.spotifyfestival.RepositoryPackage.BinFileRepos.ArtistBinaryRepo;
import com.example.spotifyfestival.RepositoryPackage.TextFileRepos.ArtistTextRepo;
import com.example.spotifyfestival.Services.DAOServices.ArtistDAOService;
import com.example.spotifyfestival.Services.UniServices.ArtistFileService;
import com.example.spotifyfestival.UIPackage.Settings;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.UnaryOperator;

public class ArtistTableController extends GenericObservableList<Artist> {
    private ArtistDAOService artistDAOService;
    private ArtistFileService artistFileService;
    @FXML
    protected TableView<Artist> artistsTable;
    @FXML
    protected TableColumn idColumn;
    @FXML
    protected TableColumn nameColumn;
    @FXML
    protected TableColumn spotify_ID_column;
    ObservableList<Artist> artistList;

    public ArtistTableController() {}

    public void initialize(){
        Settings settings = new Settings();

        String artistBinarySetting = settings.getProperty("artistsBinaryRepo");
        String artistTextSetting = settings.getProperty("artistsTextRepo");

        ArtistTextRepo artistTextRepo = new ArtistTextRepo(artistTextSetting);
        ArtistBinaryRepo artistBinaryRepo = new ArtistBinaryRepo(artistBinarySetting);

        artistDAOService = new ArtistDAOService();
        artistFileService = new ArtistFileService(artistTextRepo, artistBinaryRepo);

        artistList = FXCollections.observableArrayList();
        artistList = artistDAOService.getArtistList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        spotify_ID_column.setCellValueFactory(new PropertyValueFactory<>("spotifyId"));

        artistsTable.setItems(artistList);
    }

    private Dialog<Artist> createArtistDialog(Artist artist) {
        //create the dialog itself
        Dialog<Artist> dialog = new Dialog<>();
        dialog.setTitle("Add Dialog");
        dialog.setHeaderText("Add a new person to the database");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Stage dialogWindow = (Stage) dialog.getDialogPane().getScene().getWindow();
//        dialogWindow.getIcons().add(new Image(SQLiteExampleApp.class.getResource("img/EdenCodingIcon.png").toExternalForm()));

        //create the form for the user to fill in
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField id = new TextField();
        id.setPromptText("ID");
        TextField name = new TextField();
        name.setPromptText("NAME");
        TextField spotify_id = new TextField();
        spotify_id.setPromptText("SPOTIFY ID");
        grid.add(new Label("ID:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("NAME:"), 0, 1);
        grid.add(name, 1, 1);
        grid.add(new Label("SPOTIFY ID:"), 0, 2);
        grid.add(spotify_id, 1, 2);
        dialog.getDialogPane().setContent(grid);

        //disable the OK button if the fields haven't been filled in
        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
                Bindings.createBooleanBinding(() -> id.getText().trim().isEmpty(), id.textProperty())
                        .or(Bindings.createBooleanBinding(() -> name.getText().trim().isEmpty(), name.textProperty())
                                .or(Bindings.createBooleanBinding(() -> spotify_id.getText().trim().isEmpty(), spotify_id.textProperty())
                                )));

        //ensure only numeric input (integers) in age text field
        UnaryOperator<TextFormatter.Change> numberValidationFormatter = change -> {
            if (change.getText().matches("\\d+") || change.getText().equals("")) {
                return change; //if change is a number or if a deletion is being made
            } else {
                change.setText(""); //else make no change
                change.setRange(    //don't remove any selected text either.
                        change.getRangeStart(),
                        change.getRangeStart()
                );
                return change;
            }
        };
        id.setTextFormatter(new TextFormatter<Object>(numberValidationFormatter));

        //make sure the dialog returns a Person if it's available
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                int id2 = -1;
                if (artist != null) id2 = artist.getId();
                return new Artist(Integer.parseInt(id.getText()), name.getText(), spotify_id.getText());
            }
            return null;
        });

        //if a record is supplied, use it to fill in the fields automatically
        if (artist != null) {
            id.setText(String.valueOf(artist.getId()));
            id.setEditable(false);
            name.setText(artist.getName());
            spotify_id.setText(artist.getSpotifyId());
        }

        return dialog;
    }

    public void addArtist(ActionEvent event) {
        Dialog<Artist> addArtistDialog = createArtistDialog(null);
        Optional<Artist> result = addArtistDialog.showAndWait();

        result.ifPresent(artist ->
        {
            try {
                Artist artistToADD = new Artist(artist.getId(),
                        artist.getName(),
                        artist.getSpotifyId()
                );
                artistDAOService.add(artistToADD);
                artistFileService.add(artistToADD);
                artistList.add(artistToADD);
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }
        });

        event.consume();
    }

    public void updateArtist(ActionEvent event) {
        if (artistsTable.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Artist editing error");
            alert.setContentText("One artist must be selected when editing");
        } else {
            Dialog<Artist> dialog = createArtistDialog((Artist) artistsTable.getSelectionModel().getSelectedItem());
            Optional<Artist> optionalArtist = dialog.showAndWait();
            ArtistDAO artistDAO = artistDAOService.getArtistDAO();
            optionalArtist.ifPresent(updatedArtist -> {
                artistDAO.updateObjectInDB(updatedArtist);

                // Update the artistList to reflect the changes in the TableView
                int index = artistList.indexOf(updatedArtist);
                if (index != -1) {
                    artistList.set(index, updatedArtist);
                } else {
                    // Handle the case where the artist is not found in the list
                    System.out.println("Error: Artist not found in the list.");
                }
            });
        }
        event.consume();
    }
    public void deleteArtist(ActionEvent event){
        for (Artist artist : artistsTable.getSelectionModel().getSelectedItems()) {
            artistDAOService.delete(artist.getId());
            artistList.remove(artist);
        }
        event.consume();
    }

    public void list(){
        artistDAOService.list();
    }
}