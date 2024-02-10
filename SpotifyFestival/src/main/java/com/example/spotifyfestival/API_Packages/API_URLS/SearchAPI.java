package com.example.spotifyfestival.API_Packages.API_URLS;

public class SearchAPI {
    public static String searchForArtist(String str) {
        String formattedString = formatSearchString(str);
        return "https://api.spotify.com/v1/search?q=" + formattedString + "&type=artist&market=RO&limit=1&offset=0";
    }
    public static String formatSearchString(String str) {
        return str.replace(" ", "+");
    }

    private String searchID = "https://api.spotify.com/v1/artists/";
    public static String searchByIDURL(String str) {
//        String formattedString = formatSearchString(str);
        return "https://api.spotify.com/v1/artists/" + str;
    }
}
