package com.example.spotifyfestival.NewFeatures;

import java.util.List;

public class APIEndpoints {

    public APIEndpoints() {
    }

    //Endpoints

    //ARTISTS
    public final String TOP_ARTISTS = "https://api.spotify.com/v1/me/top/artists";
    public String generateTopArtistsURL(int limit, String timeRange, int offset) {
        return TOP_ARTISTS +"?time_range=" + timeRange +"&limit=" + limit+"&offset=" + offset;

    }
    private final String ARTIST_BY_ID = "https://api.spotify.com/v1/artists/";
    public String generateArtistURL(String artistId) {
        return ARTIST_BY_ID + artistId;
    }
    public String generateMultipleArtistsURL(List<String> artistIds) {
        StringBuilder url = new StringBuilder(ARTIST_BY_ID);
        for (String artistId : artistIds) {
            url.append(artistId).append(",");
        }
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }
    //TRACKS
    private final String TOP_TRACKS = "https://api.spotify.com/v1/me/top/tracks";
    public String generateTopTracksURL(int limit, String timeRange, int offset) {
        return TOP_TRACKS +"?time_range=" + timeRange +"&limit=" + limit+"&offset=" + offset;
    }
    private final String TRACK_BY_ID = "https://api.spotify.com/v1/tracks/";
    public String generateTrackURL(String trackId) {
        return TRACK_BY_ID + trackId;
    }
    public String generateMultipleTracksURL(List<String> trackIds) {
        StringBuilder url = new StringBuilder(TRACK_BY_ID);
        for (String trackId : trackIds) {
            url.append(trackId).append(",");
        }
        url.deleteCharAt(url.length() - 1);
        return url.toString();
    }

    //USER
    public final String USER_PROFILE = "https://api.spotify.com/v1/me";

    //SEARCH
    private final String SEARCH = "https://api.spotify.com/v1/search?q=";
    public String search(String name, String type, String market, int limit, int offset) {
        String formattedString = formatSearchString(name);
        return SEARCH + formattedString + "&type=" + type + "&market=" + market + "&limit=" + limit + "&offset=" + offset;
    }
    private String formatSearchString(String str) {
        return str.replace(" ", "+");
    }
}
