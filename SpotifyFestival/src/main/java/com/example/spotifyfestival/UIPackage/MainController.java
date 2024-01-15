package com.example.spotifyfestival.UIPackage;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;

public class MainController {
    @FXML
    protected GridPane mainGridPane;

    public void initialize() {

    }

    public void somethingToDoLater() {
        SpotifyAuthFlowService.getInstance().login();

        //File
        File file = new File("SpotifyLogo.png");

        // Load an image
        Image originalImage = new Image(file.toURI().toString());

        // Create an ImageView with the image
        ImageView imageView = new ImageView(originalImage);

        mainGridPane.add(imageView, 0, 0);
    }

    public void handleDBButton() throws IOException {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/MainDatabaseScene.fxml");
    }

    public void onGetBackButtonClicked() {
        try {
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminMainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    public void onTopListsButtonClicked() {
        try {
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onTopArtistsButtonClicked() {
        try {
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopArtists.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onTopTracksButtonClicked() {
        try {
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopTracks.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onTopGenresButtonClicked() {
        try {
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopGenres.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onGetFestivalSuggestionButtonClicked() {
        try {
            AppSwitchScenesMethods.switchSceneTwoForDatabase("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/ConcertCanvas/CanvasScene.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onBackToLoginClicked() {
        try {
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/MainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }
}