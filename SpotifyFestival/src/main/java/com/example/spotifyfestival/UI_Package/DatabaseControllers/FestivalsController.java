package com.example.spotifyfestival.UI_Package.DatabaseControllers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Festival;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.FestivalDAO;
import com.example.spotifyfestival.Services.DAOServices.FestivalDAOService;
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

public class FestivalsController {
    private FestivalDAOService service;
    @FXML
    protected TableView<Festival> festivalTableView;
    @FXML
    protected TableColumn idColumn;
    @FXML
    protected TableColumn nameColumn;
    @FXML
    protected TableColumn venueIdColumn;

    ObservableList<Festival> festivalObservableList;

    public void initialize() {
        service = new FestivalDAOService();
        festivalObservableList = FXCollections.observableArrayList();
        festivalObservableList = service.getFestivalList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        venueIdColumn.setCellValueFactory(new PropertyValueFactory<>("venueId"));

        festivalTableView.setItems(festivalObservableList);
    }

    private Dialog<Festival> createFestivalDialog(Festival festival) {
        //create the dialog itself
        Dialog<Festival> dialog = new Dialog<>();
        dialog.setTitle("Add Dialog");
        dialog.setHeaderText("Add a new festival to the database");
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
        TextField venueId = new TextField();
        venueId.setPromptText("VENUE ID");
        grid.add(new Label("ID:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("NAME:"), 0, 1);
        grid.add(name, 1, 1);
        grid.add(new Label("VENUE ID:"), 0, 2);
        grid.add(venueId, 1, 2);
        dialog.getDialogPane().setContent(grid);

        //disable the OK button if the fields haven't been filled in
        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
                Bindings.createBooleanBinding(() -> id.getText().trim().isEmpty(), id.textProperty())
                        .or(Bindings.createBooleanBinding(() -> name.getText().trim().isEmpty(), name.textProperty())
                                .or(Bindings.createBooleanBinding(() -> venueId.getText().trim().isEmpty(), venueId.textProperty())
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
                if (festival != null) id2 = festival.getId();
                return new Festival(Integer.parseInt(id.getText()), name.getText(), service.getFestivalDAO().getVenueDAO().getItem(Integer.valueOf(venueId.getText())));
            }
            return null;
        });

        //if a record is supplied, use it to fill in the fields automatically
        if (festival != null) {
            id.setText(String.valueOf(festival.getId()));
            id.setEditable(false);
            name.setText(festival.getName());
            venueId.setText(String.valueOf(festival.getVenue().getId()));
        }

        return dialog;
    }

    public void add(ActionEvent event) {
        Dialog<Festival> addFestivalDialog = createFestivalDialog(null);
        Optional<Festival> result = addFestivalDialog.showAndWait();

        result.ifPresent(fe ->
        {
            try {
                Festival festivalToAdd = new Festival(
                        fe.getId(),
                        fe.getName(),
                        fe.getVenue());
                service.add(festivalToAdd);
                festivalObservableList.add(festivalToAdd);
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }
        });

        event.consume();
    }

    public void update(ActionEvent event) {
        if (festivalTableView.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Artist editing error");
            alert.setContentText("One artist must be selected when editing");
        } else {
            Dialog<Festival> dialog = createFestivalDialog((Festival) festivalTableView.getSelectionModel().getSelectedItem());
            Optional<Festival> optionalFestival = dialog.showAndWait();
            FestivalDAO festivalDAO = service.getFestivalDAO();
            optionalFestival.ifPresent(updatedFestival -> {
                festivalDAO.updateObjectInDB(updatedFestival);

                // Update the artistList to reflect the changes in the TableView
                int index = festivalObservableList.indexOf(updatedFestival);
                if (index != -1) {
                    festivalObservableList.set(index, updatedFestival);
                } else {
                    // Handle the case where the artist is not found in the list
                    System.out.println("Error: Festival not found in the list.");
                }
            });
        }
        event.consume();
    }

    public void delete(ActionEvent event) {
        for (Festival festival : festivalTableView.getSelectionModel().getSelectedItems()) {
            service.delete(festival.getId());
            festivalObservableList.remove(festival);
        }
        event.consume();
    }

    public void list() {
        service.list();
    }
}
