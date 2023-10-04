package com.example.spotifyfestival.helperObsLis;


import com.example.spotifyfestival.MainController;
import com.example.spotifyfestival.SpotifyService;
import com.example.spotifyfestival.helperObsLis.AuthFlowObserver;
import javafx.event.Event;
import javafx.event.EventTarget;

import java.net.http.HttpResponse;

public class AccessTokenObserver implements AuthFlowObserver {
    @Override
    public void onAuthFlowCompleted(String accessToken) {
        System.out.println("Access Token Received: " + accessToken);

        SpotifyService spotifyService = new SpotifyService();
        HttpResponse response = spotifyService.getUserTopArtists(accessToken);
        System.out.println(response.body());
    }

    @Override
    public void getTopArtists(String accessToken){
        SpotifyService spotifyService = new SpotifyService();
        HttpResponse response = spotifyService.getUserTopArtists(accessToken);
        System.out.println(response.body());
    }

//    private MainController javaFXController;

//    public AccessTokenObserver(MainController javaFXController) {
//        this.javaFXController = javaFXController;
//    }
//
//    @Override
//    public void onAuthFlowCompleted(String accessToken) {
//        // Notify JavaFX controller by firing the custom event
//        AccessTokenEvent event = new AccessTokenEvent(accessToken);
//        Event.fireEvent((EventTarget) javaFXController, event);
//    }
}