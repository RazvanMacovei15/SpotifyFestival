package com.example.spotifyfestival.UI_Package.DatabaseControllers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistDAO;
import com.example.spotifyfestival.Services.FestivalDBService;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.function.UnaryOperator;

public class ArtistTableController {
    private FestivalDBService festivalDBService;
    @FXML
    protected TableView artistsTable;
    @FXML
    protected TableColumn idColumn;
    @FXML
    protected TableColumn nameColumn;
    @FXML
    protected TableColumn spotify_ID_column;
    public void initialize(){
        festivalDBService = new FestivalDBService();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        spotify_ID_column.setCellValueFactory(new PropertyValueFactory<>("spotify_id"));

        artistsTable.setItems(festivalDBService.getDbRepo().getArtistDAO().getArtistList());
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
            spotify_id.setText(artist.getSpotify_id());
        }

        return dialog;
    }

    public void addArtist(ActionEvent event) {
        Dialog<Artist> addArtistDialog = createArtistDialog(null);
        Optional<Artist> result = addArtistDialog.showAndWait();
        ArtistDAO artistDAO = ArtistDAO.getInstance();
        result.ifPresent(artist ->
                festivalDBService.getDbRepo().getArtistDAO().insertObjectInDB(new Artist(artist.getId(),
                        artist.getName(),
                        artist.getSpotify_id()
                )));

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
            Optional<Artist> optionalPerson = dialog.showAndWait();
            ArtistDAO artistDAO = festivalDBService.getDbRepo().getArtistDAO();
            optionalPerson.ifPresent(artistDAO::updateObjectInDB);
        }
        event.consume();
    }
}