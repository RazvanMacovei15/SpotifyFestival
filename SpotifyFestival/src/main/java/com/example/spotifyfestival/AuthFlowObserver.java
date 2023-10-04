package com.example.spotifyfestival;

public interface AuthFlowObserver {
    void onAuthFlowCompleted(String accessToken);
}