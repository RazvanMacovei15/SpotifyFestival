package com.example.spotifyfestival.helperObsLis;


import com.example.spotifyfestival.APPHelperMethods;
import javafx.event.ActionEvent;

import java.io.IOException;

public class AccessTokenObserver implements AuthFlowObserver {

    public void onBackToLoginClicked(ActionEvent actionEvent) {
        try {
            APPHelperMethods.switchScene(actionEvent, "mainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }
    public AccessTokenObserver(){
    }

    @Override
    public void onAuthFlowCompleted(String accessToken) {

        System.out.println("Access Token Received: " + accessToken);

    }

    @Override
    public void getTopArtists(String accessToken){

//        SpotifyService spotifyService = new SpotifyService();
//        HttpResponse response = spotifyService.getUserTopArtists(accessToken);
//        System.out.println(response.body());

    }
}