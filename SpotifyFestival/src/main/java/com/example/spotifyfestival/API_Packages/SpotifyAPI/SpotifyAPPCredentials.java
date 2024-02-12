package com.example.spotifyfestival.API_Packages.SpotifyAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class SpotifyAPPCredentials {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String userTopReadScope;
    private String userReadPlaybackPosition;
    private String userReadRecentlyPlayed;
    private final String stringToEncode;

    private SpotifyAPPCredentials(String filePath) {
        readCredentialsFromFile(filePath);
        this.stringToEncode = clientId + ":" + clientSecret;
    }

    private static SpotifyAPPCredentials instance;

    // Public method to access the single instance
    public static SpotifyAPPCredentials getInstance() {
        if(instance == null){
            instance = new SpotifyAPPCredentials("/Users/razvanmc15/Desktop/SpotifyAppStuff/spotifyCredentials.txt");
        }
        return instance;
    }

    private void setCredential(String key, String value) {
        switch (key) {
            case "clientId":
                this.clientId = value;
                break;
            case "clientSecret":
                this.clientSecret = value;
                break;
            case "redirectUri":
                this.redirectUri = value;
                break;
            case "userTopReadScope":
                this.userTopReadScope = value;
                break;
            case "userReadPlaybackPosition":
                this.userReadPlaybackPosition = value;
                break;
            case "userReadRecentlyPlayed":
                this.userReadRecentlyPlayed = value;
                break;
            default:
                // Handle unknown key or ignore
                break;
        }
    }

    private void readCredentialsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    setCredential(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getUserTopReadScope() {
        return userTopReadScope;
    }

    public String getUserReadPlaybackPosition() {
        return userReadPlaybackPosition;
    }

    public String getUserReadRecentlyPlayed() {
        return userReadRecentlyPlayed;
    }

    public String getStringToEncode() {
        return stringToEncode;
    }
}
