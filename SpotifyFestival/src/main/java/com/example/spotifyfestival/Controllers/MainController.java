package com.example.spotifyfestival.Controllers;

import com.example.spotifyfestival.helperObsLis.AccessTokenObserver;
import com.example.spotifyfestival.APPHelperMethods;
import com.example.spotifyfestival.SpotifyAPI.SpotifyAuthFlowService;
import javafx.application.Platform;
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

            SpotifyAuthFlowService authFlowService = SpotifyAuthFlowService.getInstance();
            AccessTokenObserver accessTokenObserver = new AccessTokenObserver();
            authFlowService.start();
            authFlowService.addObserver(accessTokenObserver);

            try {
                authFlowService.join();

                while(!authFlowService.bool){
                    Thread.sleep(100);
                }

                try {
                    APPHelperMethods.switchScene(event, "afterLoginScreen.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }

    public void onAwaitingConfirmationScene(ActionEvent event) throws Exception {
        APPHelperMethods.switchScene(event, "awaitConfirmation.fxml");
    }

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            APPHelperMethods.switchScene(event, "afterLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    public void onTopListsButtonClicked(ActionEvent actionEvent) throws IOException {
        APPHelperMethods.switchScene(actionEvent, "topLists.fxml");
    }
    public void onTopArtistsButtonClicked(ActionEvent actionEvent) throws IOException {
        APPHelperMethods.switchScene(actionEvent, "TopArtists.fxml");
    }
    public void onTopTracksButtonClicked(ActionEvent actionEvent) throws IOException {
        APPHelperMethods.switchScene(actionEvent, "TopTracks.fxml");
    }
    public void onTopGenresButtonClicked(ActionEvent actionEvent) throws IOException {
        APPHelperMethods.switchScene(actionEvent, "TopGenres.fxml");
    }

    public void onGetFestivalSuggestionButtonClicked(ActionEvent actionEvent) throws IOException {
        APPHelperMethods.switchScene(actionEvent, "getFestivalSuggestions.fxml");
    }

    public void onGenerateSuggestionList(ActionEvent actionEvent) throws IOException {
        APPHelperMethods.switchScene(actionEvent, "placeholderForFuture.fxml");
    }
     public void onLogOffButtonClicked(ActionEvent actionEvent) throws IOException {
        APPHelperMethods.switchScene(actionEvent, "NotLoggedIn.fxml");
    }
    public void onBackToLoginClicked(ActionEvent actionEvent) {
        try {
            APPHelperMethods.switchScene(actionEvent, "mainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }


}
