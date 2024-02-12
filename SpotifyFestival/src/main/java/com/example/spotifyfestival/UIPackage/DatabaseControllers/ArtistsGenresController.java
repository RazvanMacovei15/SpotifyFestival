package com.example.spotifyfestival.UIPackage.DatabaseControllers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
import com.example.spotifyfestival.GenericsPackage.GenericObservableList;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.ArtistGenreDAO;
import com.example.spotifyfestival.DatabaseServices.ArtistsGenresDAOService;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
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

public class ArtistsGenresController extends GenericObservableList<ArtistGenre> {
    private ArtistsGenresDAOService service;

    @FXML protected TableView<ArtistGenre> artistGenreTableView;
    @FXML protected TableColumn idColumn;
    @FXML protected TableColumn artistIdColumn;
    @FXML protected TableColumn genreIdColumn;

    ObservableList<ArtistGenre> artistGenreObservableList;

    public void initialize(){
        service = new ArtistsGenresDAOService();
        artistGenreObservableList = FXCollections.observableArrayList();
        artistGenreObservableList = service.getArtistsGenresList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        artistIdColumn.setCellValueFactory(new PropertyValueFactory<>("artist_id"));
        genreIdColumn.setCellValueFactory(new PropertyValueFactory<>("genre_id"));

        artistGenreTableView.setItems(artistGenreObservableList);
    }

    private Dialog<ArtistGenre> createArtistDialog(ArtistGenre artistGenre) {
        //create the dialog itself
        Dialog<ArtistGenre> dialog = new Dialog<>();
        dialog.setTitle("Add Dialog");
        dialog.setHeaderText("Add a new person to the database");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Stage dialogWindow = (Stage) dialog.getDialogPane().getScene().getWindow();

        //create the form for the user to fill in
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField id = new TextField();
        id.setPromptText("ID");
        TextField artistID = new TextField();
        artistID.setPromptText("ARTIST ID");
        TextField genreID = new TextField();
        genreID.setPromptText("GENRE ID");
        grid.add(new Label("ID:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("ARTIST ID:"), 0, 1);
        grid.add(artistID, 1, 1);
        grid.add(new Label("GENRE ID:"), 0, 2);
        grid.add(genreID, 1, 2);
        dialog.getDialogPane().setContent(grid);

        //disable the OK button if the fields haven't been filled in
        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
                Bindings.createBooleanBinding(() -> id.getText().trim().isEmpty(), id.textProperty())
                        .or(Bindings.createBooleanBinding(() -> artistID.getText().trim().isEmpty(), artistID.textProperty())
                                .or(Bindings.createBooleanBinding(() -> genreID.getText().trim().isEmpty(), genreID.textProperty())
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
                if (artistGenre != null) id2 = artistGenre.getId();
                return new ArtistGenre(Integer.parseInt(id.getText()), Integer.parseInt(artistID.getText()), Integer.parseInt(genreID.getText()));
            }
            return null;
        });

        //if a record is supplied, use it to fill in the fields automatically
        if (artistGenre != null) {
            id.setText(String.valueOf(artistGenre.getId()));
            id.setEditable(false);
            artistID.setText(String.valueOf(artistGenre.getArtist_id()));
            genreID.setText(String.valueOf(artistGenre.getGenre_id()));
        }

        return dialog;
    }

    public void add(ActionEvent event) {
        Dialog<ArtistGenre> addArtistDialog = createArtistDialog(null);
        Optional<ArtistGenre> result = addArtistDialog.showAndWait();

        result.ifPresent(artistGenre ->
        {
            try {
                ArtistGenre artistGenreToADD = new ArtistGenre(artistGenre.getId(),
                        artistGenre.getArtist_id(),
                        artistGenre.getGenre_id()
                );
                service.add(artistGenreToADD);
                artistGenreObservableList.add(artistGenreToADD);
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }
        });

        event.consume();
    }

    public void update(ActionEvent event) {
        if (artistGenreTableView.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Artist editing error");
            alert.setContentText("One artist must be selected when editing");
        } else {
            Dialog<ArtistGenre> dialog = createArtistDialog((ArtistGenre) artistGenreTableView.getSelectionModel().getSelectedItem());
            Optional<ArtistGenre> optionalArtist = dialog.showAndWait();
            ArtistGenreDAO artistGenreDAO = service.getArtistGenreDAO();
            optionalArtist.ifPresent(updatedArtist -> {
                artistGenreDAO.updateObjectInDB(updatedArtist);

                // Update the artistList to reflect the changes in the TableView
                int index = artistGenreObservableList.indexOf(updatedArtist);
                if (index != -1) {
                    artistGenreObservableList.set(index, updatedArtist);
                } else {
                    // Handle the case where the artist is not found in the list
                    System.out.println("Error: Artist not found in the list.");
                }
            });
        }
        event.consume();
    }
    public void delete(ActionEvent event){
        for (ArtistGenre artistGenre : artistGenreTableView.getSelectionModel().getSelectedItems()) {
            service.delete(artistGenre.getId());
            artistGenreObservableList.remove(artistGenre);
        }
        event.consume();
    }

    public void print(){
        service.list();
    }


    public void back(){
        Helper.getBackToDBList();
    }

}
