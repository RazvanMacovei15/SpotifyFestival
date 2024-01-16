package com.example.spotifyfestival.UIPackage.DatabaseControllers;

import com.example.spotifyfestival.UIPackage.HelperClasses.AppSwitchScenesMethods;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
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
    @FXML protected Button backButton;

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

    public void back(){
        Helper.backToMainPageCondition();
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
            AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ArtistsDB.fxml");
        } else if ("ArtistGenre Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 2...");
            AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ArtistsGenresDB.fxml");
        }else if ("Venues Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 3...");
            AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/VenuesDB.fxml");
        }else if ("Concerts Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 4...");
            AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ConcertsDB.fxml");
        } else if ("Genres Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 5...");
            AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/GenresDB.fxml");
        }else if ("Festivals Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 6...");
            AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/FestivalsDB.fxml");
        }else if ("Stages Table".equals(selectedButtonText)) {
            System.out.println("Loading Page 7...");
            AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/StagesDB.fxml");
        }
    }
}
