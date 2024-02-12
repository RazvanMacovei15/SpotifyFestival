package com.example.spotifyfestival.UIPackage.DatabaseControllers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.GenericsPackage.GenericObservableList;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.GenreDAO;
import com.example.spotifyfestival.DatabaseServices.GenresDAOService;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
import javafx.beans.binding.Bindings;
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

public class GenresController extends GenericObservableList<Genre> {
    private GenresDAOService genresDAOService;
    @FXML
    protected TableView<Genre> genreTableView;
    @FXML
    protected TableColumn idColumn;
    @FXML
    protected TableColumn nameColumn;

    ObservableList<Genre> genreList;
    public void initialize(){
        genresDAOService = new GenresDAOService();
//        genreList = FXCollections.observableArrayList();
//        genreList = genresDAOService.getGenresList();
        super.observableList = genresDAOService.getGenresList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        genreTableView.setItems(super.observableList);
    }

    private Dialog<Genre> createArtistDialog(Genre genre) {
        //create the dialog itself
        Dialog<Genre> dialog = new Dialog<>();
        dialog.setTitle("Add Dialog");
        dialog.setHeaderText("Add a new genre to the database");
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
        grid.add(new Label("ID:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("NAME:"), 0, 1);
        grid.add(name, 1, 1);
        dialog.getDialogPane().setContent(grid);

        //disable the OK button if the fields haven't been filled in
        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
                Bindings.createBooleanBinding(() -> id.getText().trim().isEmpty(), id.textProperty())
                        .or(Bindings.createBooleanBinding(() -> name.getText().trim().isEmpty(), name.textProperty())
                                ));

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
                if (genre != null) id2 = genre.getId();
                return new Genre(Integer.parseInt(id.getText()), name.getText());
            }
            return null;
        });

        //if a record is supplied, use it to fill in the fields automatically
        if (genre != null) {
            id.setText(String.valueOf(genre.getId()));
            id.setEditable(false);
            name.setText(genre.getName());
        }

        return dialog;
    }

    public void add(ActionEvent event) {
        Dialog<Genre> addArtistDialog = createArtistDialog(null);
        Optional<Genre> result = addArtistDialog.showAndWait();

        result.ifPresent(genre ->
        {
            try {
                Genre genreToAdd = new Genre(
                        genre.getId(),
                        genre.getName());
                genresDAOService.add(genreToAdd);
                super.add(genreToAdd);
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }
        });

        event.consume();
    }

    public void update(ActionEvent event) {
        if (genreTableView.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Artist editing error");
            alert.setContentText("One artist must be selected when editing");
        } else {
            Dialog<Genre> dialog = createArtistDialog((Genre) genreTableView.getSelectionModel().getSelectedItem());
            Optional<Genre> optionalGenre = dialog.showAndWait();
            GenreDAO genreDAO = genresDAOService.getGenreDAO();
            if(optionalGenre.isPresent()){
                super.update(optionalGenre.get());
                genreDAO.updateObjectInDB(optionalGenre.get());
            }else{
                // Handle the case where the artist is not found in the list
                System.out.println("Error: Genre not found in the list.");
            }
        }
        event.consume();
    }

    public void delete(ActionEvent event){
        for (Genre genre : genreTableView.getSelectionModel().getSelectedItems()) {
            genresDAOService.delete(genre.getId());
            super.delete(genre.getId());
        }
        event.consume();
    }

    public void list(){
        genresDAOService.list();
    }

    public void back(){
        Helper.getBackToDBList();
    }
}
