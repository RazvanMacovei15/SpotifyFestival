package com.example.spotifyfestival;

public class SpotifyAPPCredentials {

    public static String getClientId() {
        return CLIENT_ID;
    }

    public static String getClientSecret() {
        return CLIENT_SECRET;
    }

    public static String getRedirectUri() {
        return REDIRECT_URI;
    }

    public static String getUserTopReadScope() {
        return USER_TOP_READ_SCOPE;
    }

    public String getSTATE() {
        return STATE;
    }

    private static String CLIENT_ID = "40f0faeac8b043ee99f7bd42e134f97c";
    private static String CLIENT_SECRET = "9713d372e12e4c699accf979bd406435";

    public static String originalInput = CLIENT_ID + ":" + CLIENT_SECRET;

    public static String getOriginalInput() {
        return originalInput;
    }

    public void setOriginalInput(String originalInput) {
        SpotifyAPPCredentials.originalInput = originalInput;
    }
    private static String REDIRECT_URI = "http://localhost:8888/callback";
    private static String USER_TOP_READ_SCOPE = "user-top-read";
    private  String USER_READ_PLAYBACK_POSITION = "user-read-playback-position";
    public String getUserReadPlaybackPosition() {
        return USER_READ_PLAYBACK_POSITION;
    }
    public void setUserReadPlaybackPosition(String userReadPlaybackPosition) {
        USER_READ_PLAYBACK_POSITION = userReadPlaybackPosition;
    }
    private  String USER_READ_RECENTLY_PLAYED = "user-read-recently-played";
    public String getUserReadRecentlyPlayed() {
        return USER_READ_RECENTLY_PLAYED;
    }
    public void setUserReadRecentlyPlayed(String userReadRecentlyPlayed) {
        USER_READ_RECENTLY_PLAYED = userReadRecentlyPlayed;
    }
    private String STATE = null;

}
