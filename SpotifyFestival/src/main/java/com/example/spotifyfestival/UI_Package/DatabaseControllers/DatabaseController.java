package com.example.spotifyfestival.UI_Package.DatabaseControllers;


import com.example.spotifyfestival.Services.FestivalDBService;

import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class DatabaseController {
    private FestivalDBService festivalDBService;
    @FXML
    protected RadioButton artistsButton;
    @FXML
    protected RadioButton venuesButton;
    @FXML
    protected RadioButton concertButton;
    @FXML
    protected RadioButton artistGenreButton;
    @FXML
    protected Button loadButton;
    @FXML
    private ToggleGroup tableGroup;

    public void readFromDatabase() {
        festivalDBService = new FestivalDBService();
        festivalDBService.getDbRepo();
    }

    @FXML
    public void initialize() {
        // Create a ToggleGroup and add the RadioButtons to it
        tableGroup = new ToggleGroup();
        artistGenreButton.setToggleGroup(tableGroup);
        artistsButton.setToggleGroup(tableGroup);
        venuesButton.setToggleGroup(tableGroup);
        concertButton.setToggleGroup(tableGroup);

        // Set a default selected RadioButton (optional)
//        artistsButton.setSelected(true);
    }

    public void loadPage() {
        Toggle selectedToggle = tableGroup.getSelectedToggle();

        if (selectedToggle == null) {
            System.out.println("Please select a radio button.");
            return;
        }

        RadioButton selectedRadioButton = (RadioButton) selectedToggle;
        String selectedButtonText = selectedRadioButton.getText();

        // Depending on the selected radio button, navigate to the corresponding page
        if ("Artists Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 1...");
            try {
                AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ArtistsDB.fxml");
            } catch (IOException e) {
                e.printStackTrace(); // Add better logging or handling based on your needs
            }
            // Add code to navigate to Page 1
        } else if ("ArtistGenre Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 2...");
            try {
                AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ArtistsGenresDB.fxml");
            } catch (IOException e) {
                e.printStackTrace(); // Add better logging or handling based on your needs
            }
            // Add code to navigate to Page 2
        }
    }

    public void whenLoadButtonPressed() throws IOException {
        RadioButton selectedRadioButton = (RadioButton) tableGroup.getSelectedToggle();

        if (selectedRadioButton != null) {
            String selectedEntityType = selectedRadioButton.getText();

            switch (selectedEntityType) {
                case "Artists":
                    loadArtistsScene();
                    break;
                case "Venues":
                    loadVenuesScene();
                    break;
                case "Concerts":
                    loadConcertsScene();
                    break;
                case "ArtistGenres":
                    loadArtistGenresScene();
                    break;
                default:
                    // Handle default case or do nothing
                    break;
            }
        } else {
            // No radio button selected, handle as needed
        }
    }

    public void loadArtistsScene() throws IOException {
        try {
            AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ArtistsDB.fxml");
        } catch (IOException e) {
            e.printStackTrace(); // Add better logging or handling based on your needs
        }
    }

    public void loadVenuesScene() {

    }

    public void loadConcertsScene() {

    }

    public void loadArtistGenresScene(){
        try {
            AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ArtistsGenresDB.fxml");
        } catch (IOException e) {
            e.printStackTrace(); // Add better logging or handling based on your needs
        }
    }


}