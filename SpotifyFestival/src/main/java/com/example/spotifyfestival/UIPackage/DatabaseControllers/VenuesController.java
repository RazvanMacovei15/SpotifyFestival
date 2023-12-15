package com.example.spotifyfestival.UIPackage.DatabaseControllers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.VenueDAO;
import com.example.spotifyfestival.Services.DAOServices.VenueDAOService;
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

public class VenuesController {
    private VenueDAOService service;
    @FXML protected TableView<Venue> venueTableView;
    @FXML protected TableColumn idColumn;
    @FXML protected TableColumn nameColumn;
    @FXML protected TableColumn cityColumn;
    @FXML protected TableColumn addressColumn;
    @FXML protected TableColumn latitudeColumn;
    @FXML protected TableColumn longitudeColumn;
    ObservableList<Venue> venueObservableList;
    public void initialize(){
        service = new VenueDAOService();

        venueObservableList = FXCollections.observableArrayList();
        venueObservableList = service.getVenuesList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("venueName"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("streetAddress"));
        latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("locationLatitude"));
        longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("locationLongitude"));

        venueTableView.setItems(venueObservableList);
    }

    private Dialog<Venue> createVenueDialog(Venue venue) {
        //create the dialog itself
        Dialog<Venue> dialog = new Dialog<>();
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
        TextField name = new TextField();
        name.setPromptText("NAME");
        TextField city = new TextField();
        city.setPromptText("CITY");
        TextField address = new TextField();
        address.setPromptText("ADDRESS");
        TextField latitude = new TextField();
        latitude.setPromptText("LATITUDE");
        TextField longitude = new TextField();
        longitude.setPromptText("LONGITUDE");
        grid.add(new Label("ID:"), 0, 0);
        grid.add(id, 1, 0);
        grid.add(new Label("NAME:"), 0, 1);
        grid.add(name, 1, 1);
        grid.add(new Label("CITY:"), 0, 2);
        grid.add(city, 1, 2);
        grid.add(new Label("ADDRESS:"), 0, 3);
        grid.add(address, 1, 3);
        grid.add(new Label("LATITUDE:"), 0, 4);
        grid.add(latitude, 1, 4);
        grid.add(new Label("LONGITUDE:"), 0, 5);
        grid.add(longitude, 1, 5);
        dialog.getDialogPane().setContent(grid);

        //disable the OK button if the fields haven't been filled in
        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(
                Bindings.createBooleanBinding(() -> id.getText().trim().isEmpty(), id.textProperty())
                        .or(Bindings.createBooleanBinding(() -> name.getText().trim().isEmpty(), name.textProperty())
                                .or(Bindings.createBooleanBinding(() -> city.getText().trim().isEmpty(), city.textProperty())
                                        .or(Bindings.createBooleanBinding(() -> address.getText().trim().isEmpty(), address.textProperty())
                                                .or(Bindings.createBooleanBinding(()-> latitude.getText().trim().isEmpty(), latitude.textProperty())
                                                        .or(Bindings.createBooleanBinding(()->longitude.getText().trim().isEmpty(), longitude.textProperty()))
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
                if (venue != null) id2 = venue.getId();
                return new Venue(Integer.parseInt(id.getText()), name.getText(), city.getText(), address.getText(), latitude.getText(), longitude.getText());
            }
            return null;
        });

        //if a record is supplied, use it to fill in the fields automatically
        if (venue != null) {
            id.setText(String.valueOf(venue.getId()));
            id.setEditable(false);
            name.setText(venue.getVenueName());
            city.setText(venue.getCity());
            address.setText(venue.getStreetAddress());
            latitude.setText(venue.getLocationLatitude());
            longitude.setText(venue.getLocationLongitude());
        }

        return dialog;
    }

    public void add(ActionEvent event) {
        Dialog<Venue> addVenueDialog = createVenueDialog(null);
        Optional<Venue> result = addVenueDialog.showAndWait();

        result.ifPresent(venue ->
        {
            try {
                Venue venueToAdd = new Venue(venue.getId(),
                        venue.getVenueName(),
                        venue.getCity(),
                        venue.getStreetAddress(),
                        venue.getLocationLatitude(),
                        venue.getLocationLongitude()
                );
                service.add(venueToAdd);
                venueObservableList.add(venueToAdd);
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }
        });

        event.consume();
    }

    public void update(ActionEvent event) {
        if (venueTableView.getSelectionModel().getSelectedItems().size() != 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Artist editing error");
            alert.setContentText("One artist must be selected when editing");
        } else {
            Dialog<Venue> dialog = createVenueDialog((Venue) venueTableView.getSelectionModel().getSelectedItem());
            Optional<Venue> optionalVenue = dialog.showAndWait();
            VenueDAO venueDAO = service.getVenueDAO();
            optionalVenue.ifPresent(updatedVenue -> {
                venueDAO.updateObjectInDB(updatedVenue);

                // Update the artistList to reflect the changes in the TableView
                int index = venueObservableList.indexOf(updatedVenue);
                if (index != -1) {
                    venueObservableList.set(index, updatedVenue);
                } else {
                    // Handle the case where the artist is not found in the list
                    System.out.println("Error: Venue not found in the list.");
                }
            });
        }
        event.consume();
    }
    public void delete(ActionEvent event){
        for (Venue venue : venueTableView.getSelectionModel().getSelectedItems()) {
            service.delete(venue.getId());
            venueObservableList.remove(venue);
        }
        event.consume();
    }

    public void list(){
        service.list();
    }
}
