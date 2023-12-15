package com.example.spotifyfestival.UIPackage.DatabaseControllers;

import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

public class DatabaseController {
    @FXML protected RadioButton artistsButton;
    @FXML protected RadioButton venuesButton;
    @FXML protected RadioButton concertButton;
    @FXML protected RadioButton artistGenreButton;
    @FXML protected RadioButton genresButton;
    @FXML protected RadioButton festivalsButton;
    @FXML protected RadioButton stagesButton;

    @FXML
    protected Button loadButton;
    @FXML
    private ToggleGroup tableGroup;

    @FXML
    public void initialize() {
        // Create a ToggleGroup and add the RadioButtons to it
        tableGroup = new ToggleGroup();
        artistGenreButton.setToggleGroup(tableGroup);
        artistsButton.setToggleGroup(tableGroup);
        venuesButton.setToggleGroup(tableGroup);
        concertButton.setToggleGroup(tableGroup);
        genresButton.setToggleGroup(tableGroup);
        festivalsButton.setToggleGroup(tableGroup);
        stagesButton.setToggleGroup(tableGroup);

        // Set a default selected RadioButton (optional)
        artistsButton.setSelected(true);
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
        } else if ("ArtistGenre Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 2...");
            try {
                AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ArtistsGenresDB.fxml");
            } catch (IOException e) {
                e.printStackTrace(); // Add better logging or handling based on your needs
            }
        }else if ("Venues Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 3...");
            try {
                AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/VenuesDB.fxml");
            } catch (IOException e) {
                e.printStackTrace(); // Add better logging or handling based on your needs
            }
        }else if ("Concerts Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 4...");
            try {
                AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ConcertsDB.fxml");
            } catch (IOException e) {
                e.printStackTrace(); // Add better logging or handling based on your needs
            }
        } else if ("Genres Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 5...");
            try {
                AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/GenresDB.fxml");
            } catch (IOException e) {
                e.printStackTrace(); // Add better logging or handling based on your needs
            }
        }else if ("Festivals Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 6...");
            try {
                AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/FestivalsDB.fxml");
            } catch (IOException e) {
                e.printStackTrace(); // Add better logging or handling based on your needs
            }
        }else if ("Stages Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 7...");
            try {
                AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/StagesDB.fxml");
            } catch (IOException e) {
                e.printStackTrace(); // Add better logging or handling based on your needs
            }
        }
    }
}
