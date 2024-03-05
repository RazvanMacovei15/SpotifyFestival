package com.example.spotifyfestival.ui.database.controllers;

import com.example.spotifyfestival.database.entities.pojo.FestivalStage;
import com.example.spotifyfestival.database.entities.pojo.DuplicateEntityException;
import com.example.spotifyfestival.database.dao.FestivalStageDAO;
import com.example.spotifyfestival.database.services.FestivalStageDAOService;
import com.example.spotifyfestival.ui.helper.classes.Helper;
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

public class StagesController {
    private FestivalStageDAOService service;
    @FXML
    protected TableView<FestivalStage> festivalStageTableView;
    @FXML
    protected TableColumn idColumn;
    @FXML
    protected TableColumn nameColumn;
    @FXML
    protected TableColumn venueIdColumn;

    ObservableList<FestivalStage> stageObservableList;

    public void initialize() {
        service = new FestivalStageDAOService();
        stageObservableList = FXCollections.observableArrayList();
        stageObservableList = service.getStageList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        venueIdColumn.setCellValueFactory(new PropertyValueFactory<>("venueId"));

        festivalStageTableView.setItems(stageObservableList);
    }

    private Dialog<FestivalStage> createStageDialog(FestivalStage stage) {
        //create the dialog itself
        Dialog<FestivalStage> dialog = new Dialog<>();
        dialog.setTitle("Add Dialog");
        dialog.setHeaderText("Add a new stage to the database");
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
                if (stage != null) id2 = stage.getId();
                return new FestivalStage(Integer.parseInt(id.getText()), name.getText(), service.getStageDAO().getVenueDAO().getItem(Integer.valueOf(venueId.getText())));
            }
            return null;
        });

        //if a record is supplied, use it to fill in the fields automatically
        if (stage != null) {
            id.setText(String.valueOf(stage.getId()));
            id.setEditable(false);
            name.setText(stage.getName());
            venueId.setText(String.valueOf(stage.getVenue().getId()));
        }

        return dialog;
    }

    public void add(ActionEvent event) {
        Dialog<FestivalStage> addStageDialog = createStageDialog(null);
        Optional<FestivalStage> result = addStageDialog.showAndWait();

        result.ifPresent(stage ->
        {
            try {
                FestivalStage stageToAdd = new FestivalStage(
                        stage.getId(),
                        stage.getName(),
                        stage.getVenue());
                service.add(stageToAdd);
                stageObservableList.add(stageToAdd);
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }
        });

        event.consume();
    }

    public void update(ActionEvent event) {
        if (festivalStageTableView.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Artist editing error");
            alert.setContentText("One artist must be selected when editing");
        } else {
            Dialog<FestivalStage> dialog = createStageDialog((FestivalStage) festivalStageTableView.getSelectionModel().getSelectedItem());
            Optional<FestivalStage> optionalFestivalStage = dialog.showAndWait();
            FestivalStageDAO stageDAO = service.getStageDAO();
            optionalFestivalStage.ifPresent(updatedStage -> {
                stageDAO.updateObjectInDB(updatedStage);

                // Update the artistList to reflect the changes in the TableView
                int index = stageObservableList.indexOf(updatedStage);
                if (index != -1) {
                    stageObservableList.set(index, updatedStage);
                } else {
                    // Handle the case where the artist is not found in the list
                    System.out.println("Error: Festival not found in the list.");
                }
            });
        }
        event.consume();
    }

    public void delete(ActionEvent event) {
        for (FestivalStage stage : festivalStageTableView.getSelectionModel().getSelectedItems()) {
            service.delete(stage.getId());
            stageObservableList.remove(stage);
        }
        event.consume();
    }

    public void list() {
        service.list();
    }

    public void back(){
        Helper.getBackToDBList();
    }
}
