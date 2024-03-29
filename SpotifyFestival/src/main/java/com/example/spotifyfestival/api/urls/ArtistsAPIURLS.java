package com.example.spotifyfestival.api.urls;

public class ArtistsAPIURLS {

    //access user's top artists
    public static String getUserTopArtistsAllTimeURI() {
        return userTopArtistsAllTimeURI;
    }

    private static final String userTopArtistsAllTimeURI = "https://api.spotify.com/v1/me/top/artists?time_range=long_term&limit=50";


    public static String getUserTopArtists6MonthsURI() {
        return userTopArtists6MonthsURI;
    }

    private static final String userTopArtists6MonthsURI = "https://api.spotify.com/v1/me/top/artists?time_range=medium_term&limit=50";

    public static String getUserTopArtistsOver4WeeksURI() {
        return userTopArtistsOver4WeeksURI;
    }

    private static final String userTopArtistsOver4WeeksURI = "https://api.spotify.com/v1/me/top/artists?time_range=short_term&limit=50";

}
