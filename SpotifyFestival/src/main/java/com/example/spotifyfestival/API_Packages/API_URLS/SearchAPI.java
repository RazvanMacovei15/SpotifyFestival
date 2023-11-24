package com.example.spotifyfestival.API_Packages.API_URLS;

public class SearchAPI {
    public static String searchForArtist(String str) {
        return "https://api.spotify.com/v1/search?q=" + str + "&type=artist&market=RO&limit=1&offset=0";
    }
}
