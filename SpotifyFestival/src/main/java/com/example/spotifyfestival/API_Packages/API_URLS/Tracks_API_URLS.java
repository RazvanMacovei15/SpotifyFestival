package com.example.spotifyfestival.API_Packages.API_URLS;

public class Tracks_API_URLS {
    //access user's top tracks
    public static String getUserTopTracksAllTimeURI() {
        return userTopTracksAllTimeURI;
    }
    private static final String userTopTracksAllTimeURI = "https://api.spotify.com/v1/me/top/tracks?time_range=long_term&limit=50";

    public static String getUserTopTracks6MonthsURI() {
        return userTopTracks6MonthsURI;
    }
    private static final String userTopTracks6MonthsURI = "https://api.spotify.com/v1/me/top/tracks?time_range=medium_term&limit=50";

    public static String getUserTopTracksOver4WeeksURI() {
        return userTopTracksOver4WeeksURI;
    }
    private static final String userTopTracksOver4WeeksURI = "https://api.spotify.com/v1/me/top/tracks?time_range=short_term&limit=50";
}

