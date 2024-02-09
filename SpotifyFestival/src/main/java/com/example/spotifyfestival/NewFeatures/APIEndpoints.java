package com.example.spotifyfestival.NewFeatures;

import java.util.List;

public class APIEndpoints {

    private APIEndpoints() {
    }

    //Endpoints

    //ARTISTS
    public static final String TOP_ARTISTS = "https://api.spotify.com/v1/me/top/artists";
    public static String generateTopArtistsURL(int limit, String timeRange, int offset) {
        return TOP_ARTISTS +"?time_range=" + timeRange +"&limit=" + limit+"&offset=" + offset;

    }
    private static final String ARTIST_BY_ID = "https://api.spotify.com/v1/artists/";
    public static String generateArtistURL(String artistId) {
        return ARTIST_BY_ID + artistId;
    }
    public static String generateMultipleArtistsURL(List<String> artistIds) {
        StringBuilder url = new StringBuilder(ARTIST_BY_ID);
        for (String artistId : artistIds) {
            url.append(artistId).append(",");
        }
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }
    //TRACKS
    private static final String TOP_TRACKS = "https://api.spotify.com/v1/me/top/tracks";
    public static String generateTopTracksURL(int limit, String timeRange, int offset) {
        return TOP_TRACKS +"?time_range=" + timeRange +"&limit=" + limit+"&offset=" + offset;
    }
    private static final String TRACK_BY_ID = "https://api.spotify.com/v1/tracks/";
    public static String generateTrackURL(String trackId) {
        return TRACK_BY_ID + trackId;
    }
    public static String generateMultipleTracksURL(List<String> trackIds) {
        StringBuilder url = new StringBuilder(TRACK_BY_ID);
        for (String trackId : trackIds) {
            url.append(trackId).append(",");
        }
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }

    //USER
    public static final String USER_PROFILE = "https://api.spotify.com/v1/me";

    //SEARCH
    private static final String SEARCH = "https://api.spotify.com/v1/search?q=";
    public static String search(String name, String type, String market, int limit, int offset) {
        String formattedString = formatSearchString(name);
        return SEARCH + formattedString + "&type=" + type + "&market=" + market + "&limit=" + limit + "&offset=" + offset;
    }
    public static String formatSearchString(String str) {
        return str.replace(" ", "+");
    }
}
