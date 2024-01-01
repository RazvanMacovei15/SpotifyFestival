package com.example.spotifyfestival.UIPackage;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController {
    @FXML private Button loginButton;
    @FXML private Label label;
    @FXML private Button DBButton;


    @FXML
    private void handleLoginButtonClick(ActionEvent event){
        SpotifyAuthFlowService.getInstance().login();
    }
    public void handleDBButton(ActionEvent event) throws IOException {
        AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/DatabaseScenes/MainDatabaseScene.fxml");
    }

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminMainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    public void onTopListsButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
    }
    public void onTopArtistsButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopArtists.fxml");
    }
    public void onTopTracksButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopTracks.fxml");
    }
    public void onTopGenresButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopGenres.fxml");
    }

    public void onGetFestivalSuggestionButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/getFestivalSuggestions.fxml");
    }

    public void onBackToLoginClicked(ActionEvent actionEvent) {
        try {
            AppSwitchScenesMethods.switchScene(actionEvent, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/MainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }
}