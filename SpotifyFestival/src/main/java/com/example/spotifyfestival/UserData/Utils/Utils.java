package com.example.spotifyfestival.UserData.Utils;

import com.example.spotifyfestival.API_URLS.Artists_API_URLS;
import com.example.spotifyfestival.API_URLS.SearchAPI;
import com.example.spotifyfestival.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.SpotifyAPI.SpotifyService;

import java.net.http.HttpResponse;

public class Utils {
    public static HttpResponse<String> getArtistSpotifyID(String str) {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, SearchAPI.searchForArtist(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

    }
}
