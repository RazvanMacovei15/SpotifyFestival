package com.example.spotifyfestival.Controllers;

import com.example.spotifyfestival.APPHelperMethods;
import com.example.spotifyfestival.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.SpotifyAPI.SpotifyService;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.http.HttpResponse;

public class TopTracksController {
    public void onGetBackButtonClicked(ActionEvent event){
        try {
            APPHelperMethods.switchScene(event, "afterLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    public void onAllTimeTopTracksButtonClicked(){
        SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
        String token = spotifyAuthFlowService.getAccessToken();

        SpotifyService spotifyService = new SpotifyService();
        HttpResponse response = spotifyService.getUserTopTracksAllTime(token);
        System.out.println(response.body());
    }

}
