package com.example.spotifyfestival;

public class API_URLS {

    //access user's top artists
    public static String getUserTopArtists() {
        return userTopArtists;
    }

    private static final String userTopArtists = "https://api.spotify.com/v1/me/top/artists?limit=5";

    //access user's top tracks
    public static String getUserTopTracks() {
        return userTopTracks;
    }
    private static final String userTopTracks = "https://api.spotify.com/v1/me/top/tracks?limit=5";
}
