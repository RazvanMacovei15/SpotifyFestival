package com.example.spotifyfestival.UnusedStuffForNow.helperObsLis;

public interface AuthFlowObserver {
    void onAuthFlowCompleted(String accessToken);

    void getTopArtists(String accessToken);

}