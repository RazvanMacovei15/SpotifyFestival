package com.example.spotifyfestival.UIPackage;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.UIPackage.HelperClasses.AppSwitchScenesMethods;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class MainController {
    @FXML
    GridPane mainGridPane;
    @FXML
    Button admin;
    @FXML
    Button festivals;
    @FXML
    Button topLists;
    @FXML
    VBox vBox;

    public void initialize() {

        for(Node node : mainGridPane.getChildren()){
            System.out.println(node.toString());
        }

        for(Node node : vBox.getChildren()){
            System.out.println(node.toString());
        }

        Helper.mouseHoverUpOnButton(admin);
        Helper.mouseHoverUpOnButton(festivals);
        Helper.mouseHoverUpOnButton(topLists);

    }

    public void handleDBButton() {
        try {
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/MainDatabaseScene.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onGetBackButtonClicked() {
        Helper.backToMainPageCondition();
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
}