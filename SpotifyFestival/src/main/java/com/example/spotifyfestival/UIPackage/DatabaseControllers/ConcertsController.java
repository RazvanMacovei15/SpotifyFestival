package com.example.spotifyfestival.UIPackage.DatabaseControllers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Concert;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.ConcertDAO;
import com.example.spotifyfestival.Services.DAOServices.ConcertDAOService;
import com.example.spotifyfestival.UIPackage.HelperClasses.AppSwitchScenesMethods;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public class ConcertsController {
    private ConcertDAOService service;
    @FXML
    protected TableView<Concert> concertTableView;
    @FXML
    protected TableColumn idColumn;
    @FXML
    protected TableColumn descriptionColumn;
    @FXML
    protected TableColumn startDateColumn;
    @FXML
    protected TableColumn startTimeColumn;
    @FXML
    protected TableColumn venueIdColumn;
    @FXML
    protected TableColumn artistIdColumn;
    @FXML
    protected TableColumn stageIdColumn;
    ObservableList<Concert> concertObservableList;

    public void initialize() {
        service = new ConcertDAOService();

        concertObservableList = FXCollections.observableArrayList();
        concertObservableList = service.getConcertsList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startOfTheConcert"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        venueIdColumn.setCellValueFactory(new PropertyValueFactory<>("venueId"));
        artistIdColumn.setCellValueFactory(new PropertyValueFactory<>("artistIdValue"));
        stageIdColumn.setCellValueFactory(new PropertyValueFactory<>("stageId"));

        concertTableView.setItems(concertObservableList);
    }

    private Dialog<Concert> createConcertDialog(Concert concert) {
        //create the dialog itself
        Dialog<Concert> dialog = new Dialog<>();
        dialog.setTitle("Add Dialog");
        dialog.setHeaderText("Add a new venue to the database");
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
        TextField description = new TextField();
        description.setPromptText("DESCRIPTION");
        TextField startDate = new TextField();
        startDate.setPromptText("START DATE");
        TextField startTime = new TextField();
        startTime.setPromptText("START TIME");
        TextField venueId = new TextField();
        venueId.setPromptText("VENUE ID");
        TextField artistId = new TextField();
        artistId.setPromptText("ARTIST ID");
        TextField stageId = new TextField();
        stageId.setPromptText("STAGE ID");
        grid.add(new Label("ID:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("DESCRIPTION:"), 0, 1);
        grid.add(description, 1, 1);
        grid.add(new Label("START DATE:"), 0, 2);
        grid.add(startDate, 1, 2);
        grid.add(new Label("START TIME:"), 0, 3);
        grid.add(startTime, 1, 3);
        grid.add(new Label("VENUE ID:"), 0, 4);
        grid.add(venueId, 1, 4);
        grid.add(new Label("ARTIST ID:"), 0, 5);
        grid.add(artistId, 1, 5);
        grid.add(new Label("STAGE ID:"), 0, 6);
        grid.add(stageId, 1, 6);
        dialog.getDialogPane().setContent(grid);

        //disable the OK button if the fields haven't been filled in
        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
                Bindings.createBooleanBinding(() -> id.getText().trim().isEmpty(), id.textProperty())
                        .or(Bindings.createBooleanBinding(() -> description.getText().trim().isEmpty(), description.textProperty())
                                .or(Bindings.createBooleanBinding(() -> startDate.getText().trim().isEmpty(), startDate.textProperty())
                                        .or(Bindings.createBooleanBinding(() -> startTime.getText().trim().isEmpty(), startTime.textProperty())
                                                .or(Bindings.createBooleanBinding(() -> venueId.getText().trim().isEmpty(), venueId.textProperty())
                                                        .or(Bindings.createBooleanBinding(() -> artistId.getText().trim().isEmpty(), artistId.textProperty()))
                                                        .or(Bindings.createBooleanBinding(() -> stageId.getText().trim().isEmpty(), stageId.textProperty()))
                                                )))));

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
                if (concert != null) id2 = concert.getId();
                List<Artist> artists = new ArrayList<>();
                artists.add( service.getConcertDAO().getArtistDAO().getItem(Integer.valueOf(artistId.getText())));
                return new Concert(Integer.parseInt(id.getText()), description.getText(), artists, service.getConcertDAO().getVenueDAO().getItem(Integer.valueOf(venueId.getText())), startDate.getText(), startTime.getText(), service.getConcertDAO().getFestivalStageDAO().getItem(Integer.valueOf(stageId.getText())));
            }
            return null;
        });

        //if a record is supplied, use it to fill in the fields automatically
        if (concert != null) {
            id.setText(String.valueOf(concert.getId()));
            id.setEditable(false);
            description.setText(concert.getDescription());
            startDate.setText(concert.getStartOfTheConcert());
            startTime.setText(concert.getTime());
            venueId.setText(String.valueOf(concert.getVenueId()));
            artistId.setText(String.valueOf(concert.getArtist().getId()));
            stageId.setText(String.valueOf(concert.getFestivalStage().getId()));
        }

        return dialog;
    }

    public void add(ActionEvent event) {
        Dialog<Concert> concertDialog = createConcertDialog(null);
        Optional<Concert> result = concertDialog.showAndWait();

        result.ifPresent(concert ->
        {
            try {
                List<Artist> artists = new ArrayList<>();
                artists.add(service.getConcertDAO().getArtistDAO().getItem(concert.getArtistIdValue()));
                Concert concertToAdd = new Concert(concert.getId(),
                        concert.getDescription(),
                        artists,
                        service.getConcertDAO().getVenueDAO().getItem(concert.getVenueId()),
                        concert.getStartOfTheConcert(),
                        concert.getTime(),
                        service.getConcertDAO().getFestivalStageDAO().getItem(concert.getStageId())
                );
                service.add(concertToAdd);
                concertObservableList.add(concertToAdd);
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }
        });

        event.consume();
    }

    public void update(ActionEvent event) {
        if (concertTableView.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Artist editing error");
            alert.setContentText("One artist must be selected when editing");
        } else {
            Dialog<Concert> dialog = createConcertDialog((Concert) concertTableView.getSelectionModel().getSelectedItem());
            Optional<Concert> optionalConcert = dialog.showAndWait();
            ConcertDAO concertDAO = service.getConcertDAO();
            optionalConcert.ifPresent(updatedConcert -> {
                concertDAO.updateObjectInDB(updatedConcert);

                // Update the artistList to reflect the changes in the TableView
                int index = concertObservableList.indexOf(updatedConcert);
                if (index != -1) {
                    concertObservableList.set(index, updatedConcert);
                } else {
                    // Handle the case where the artist is not found in the list
                    System.out.println("Error: Venue not found in the list.");
                }
            });
        }
        event.consume();
    }

    public void delete(ActionEvent event) {
        for (Concert concert : concertTableView.getSelectionModel().getSelectedItems()) {
            service.delete(concert.getId());
            concertObservableList.remove(concert);
        }
        event.consume();
    }

    public void list() {
        service.list();
    }

    public void back(){
        Helper.backToMainPageCondition();
    }
}
