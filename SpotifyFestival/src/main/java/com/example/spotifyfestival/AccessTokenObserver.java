package com.example.spotifyfestival;



public class AccessTokenObserver implements AuthFlowObserver {
    @Override
    public void onAuthFlowCompleted(String accessToken) {
        System.out.println("Access Token Received: " + accessToken);
    }
}