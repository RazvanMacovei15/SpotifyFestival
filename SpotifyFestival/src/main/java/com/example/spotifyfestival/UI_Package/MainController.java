
package com.example.spotifyfestival.UI_Package;

import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class MainController {


    @FXML
    private Button button;
    @FXML
    private Label label;

    @FXML
    private void handleLoginButtonClick(ActionEvent event){
        SpotifyAuthFlowService.getInstance().openLogin();
    }

    public void onAwaitingConfirmationScene(ActionEvent event) throws Exception {
        AppSwitchScenesMethods.switchScene(event, "awaitConfirmation.fxml");
    }

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "afterLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    public void onTopListsButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "topLists.fxml");
    }
    public void onTopArtistsButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "TopArtists.fxml");
    }
    public void onTopTracksButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "TopTracks.fxml");
    }
    public void onTopGenresButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "TopGenres.fxml");
    }

    public void onGetFestivalSuggestionButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "getFestivalSuggestions.fxml");
    }


    public void onLogOffButtonClicked(ActionEvent actionEvent) throws IOException {
        AppSwitchScenesMethods.switchScene(actionEvent, "NotLoggedIn.fxml");
    }
    public void onBackToLoginClicked(ActionEvent actionEvent) {
        try {
            AppSwitchScenesMethods.switchScene(actionEvent, "MainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }

    public void onSearchButtonClicked(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "chooseTheSearchParameters.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }



}

