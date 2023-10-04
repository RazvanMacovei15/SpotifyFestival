package com.example.spotifyfestival;

import com.example.spotifyfestival.helperObsLis.AccessTokenObserver;

import java.net.http.HttpResponse;

public class MainApp{

    public static void main(String[] args) {
        SpotifyAuthFlowService authFlowService = SpotifyAuthFlowService.getInstance();
        AccessTokenObserver accessTokenObserver = new AccessTokenObserver();
        authFlowService.addObserver(accessTokenObserver);
        authFlowService.apiCall();
    }
}
