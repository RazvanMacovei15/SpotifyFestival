package com.example.spotifyfestival;

import javafx.event.ActionEvent;

import javax.security.auth.callback.ConfirmationCallback;
import java.io.IOException;

//import static com.example.spotifyfestival.HelperMethods.checkStatusCode;

public class mainController {



    public void loginWithSpotify(ActionEvent event) throws Exception {

        SpotifyAuthFlowService spotifyAuthFlowService = new SpotifyAuthFlowService();

        HelperMethods helperMethods = new HelperMethods();

        SpotifyService spotifyService = new SpotifyService();

        spotifyAuthFlowService.backendThatNeedsChange();


        helperMethods.createLoginSimulation();

        helperMethods.simulateConfirmation();

        System.out.println(spotifyAuthFlowService.getAccessTokenFromAuth());

//        System.out.println(spotifyService.getUserTopTracks();

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
