package com.example.spotifyfestival;

import java.util.concurrent.CompletableFuture;

public class MainApp{

    public static void main(String[] args) {
        SpotifyAuthFlowService authFlowService = SpotifyAuthFlowService.getInstance();
        AccessTokenObserver accessTokenObserver = new AccessTokenObserver();
        authFlowService.addObserver(accessTokenObserver);
        authFlowService.apiCall();
    }
}
