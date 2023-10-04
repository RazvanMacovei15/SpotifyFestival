package com.example.spotifyfestival.helperObsLis;

public interface AuthFlowObserver {
    void onAuthFlowCompleted(String accessToken);

    void getTopArtists(String accessToken);

}