package com.example.spotifyfestival;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

//import static com.example.spotifyfestival.HelperMethods.checkStatusCode;

public class mainController {

    HelperMethods helperMethods = new HelperMethods();

    public void loginWithSpotify() throws Exception {
//        HelperMethods.switchScene(event, "awaitConfirmation.fxml");


//        SpotifyAPI.main();
        helperMethods.createLoginSimulation();
        helperMethods.simulateConfirmation();


    }

    public void onAwaitingConfirmationScene(ActionEvent event) throws Exception {
        HelperMethods.switchScene(event, "awaitConfirmation.fxml");
    }

    public void onGetBackButtonClicked(ActionEvent event) throws IOException {
        HelperMethods.switchScene(event, "afterLoginScreen.fxml");
    }

    public void onTopListsButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "topLists.fxml");
    }
    public void onTopArtistsButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "TopArtists.fxml");
    }
    public void onTopTracksButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "TopTracks.fxml");
    }
    public void onTopGenresButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "TopGenres.fxml");
    }

    public void onGetFestivalSuggestionButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "getFestivalSuggestions.fxml");
    }

    public void onGenerateSuggestionList(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "placeholderForFuture.fxml");
    }
     public void onLogOffButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "NotLoggedIn.fxml");
    }
    public void onBackToLoginClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "mainScreen.fxml");
    }
}
