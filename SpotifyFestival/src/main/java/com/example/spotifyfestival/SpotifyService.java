package com.example.spotifyfestival;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpotifyService {

//    SpotifyAuthFlowService spotifyAuthFlowService = new SpotifyAuthFlowService() ;
//
//    public void setSpotifyAuthFlowService(SpotifyAuthFlowService spotifyAuthFlowService){
//        this.spotifyAuthFlowService = spotifyAuthFlowService;
//    }

    public HttpResponse getUserTopTracks(String accessToken) throws IOException, InterruptedException {

        String API_URL = API_URLS.getUserTopTracks();

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }

    public HttpResponse getUserTopArtists(String accessToken)  {

//        String accessToken = accessTokenResponse.getAccessToken();

        String API_URL = API_URLS.getUserTopArtists();

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
