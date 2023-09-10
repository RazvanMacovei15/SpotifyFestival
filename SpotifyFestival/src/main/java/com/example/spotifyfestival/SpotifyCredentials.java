package com.example.spotifyfestival;

public class SpotifyCredentials {

    public static String getClientId() {
        return CLIENT_ID;
    }

    public static String getClientSecret() {
        return CLIENT_SECRET;
    }

    public static String getRedirectUri() {
        return REDIRECT_URI;
    }

    public static String getSCOPE() {
        return SCOPE;
    }

    public static String getSTATE() {
        return STATE;
    }

    private static String CLIENT_ID = "40f0faeac8b043ee99f7bd42e134f97c";
    private static String CLIENT_SECRET = "9713d372e12e4c699accf979bd406435";
    private static String REDIRECT_URI = "http://localhost:8888/callback";
    private static  String SCOPE = "user-top-read";
    private static String STATE = null;


}
