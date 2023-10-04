package com.example.spotifyfestival.helperObsLis;

public class API_URLS {

    //access user's top artists
    public static String getUserTopArtistsURI() {
        return userTopArtistsURI;
    }
    private static final String userTopArtistsURI = "https://api.spotify.com/v1/me/top/artists?limit=20";

    //access user's top tracks
    public static String getUserTopTracksURI() {
        return userTopTracksURI;
    }
    private static final String userTopTracksURI = "https://api.spotify.com/v1/me/top/tracks?limit=20";

    public static String getUserTopTracksOver4WeeksURI() {
        return userTopTracksOver4WeeksURI;
    }
    private static final String userTopTracksOver4WeeksURI = "https://api.spotify.com/v1/me/top/artists?time_range=short_term&limit=20";
}
