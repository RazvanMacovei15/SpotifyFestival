package com.example.spotifyfestival.SpotifyAPI;

public final class SpotifyAPPCredentials {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;
    private final String userTopReadScope;
    private final String userReadPlaybackPosition;
    private final String userReadRecentlyPlayed;
    private final String stringToEncode;



    public SpotifyAPPCredentials() {
        this.clientId = "40f0faeac8b043ee99f7bd42e134f97c";
        this.clientSecret = "9713d372e12e4c699accf979bd406435";
        this.redirectUri = "http://localhost:8888/callback";
        this.userTopReadScope = "user-top-read";
        this.userReadPlaybackPosition = "user-read-playback-position";
        this.userReadRecentlyPlayed = "user-read-recently-played";
        this.stringToEncode = clientId + ":" + clientSecret;
    }

    // Private inner static class to hold the instance of SpotifyAPPCredentials
    private static class SingletonHelper {
        private static final SpotifyAPPCredentials INSTANCE = new SpotifyAPPCredentials();
    }

    // Public method to access the single instance
    public static SpotifyAPPCredentials getInstance() {
        return SingletonHelper.INSTANCE;
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
