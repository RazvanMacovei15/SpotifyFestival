package com.example.spotifyfestival.NewFeatures;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;

public class SpotifyResponseService {
    private final String accessToken;

    private final APIEndpoints apiEndpoints;

    public SpotifyResponseService(String accessToken) {
        this.accessToken = accessToken;
        this.apiEndpoints = new APIEndpoints();
    }

    public HttpResponse<String> getNewAccessToken(String refreshToken, String originalInput ) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://accounts.spotify.com/api/token"))
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(originalInput.getBytes()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=refresh_token&refresh_token=" + refreshToken))
                .build();
        HttpResponse<String> refreshResponse = null;
        try {
            refreshResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return refreshResponse;
    }

    public HttpResponse<String> getHttpResponse(String apiUrl) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(response.statusCode() == 503){
            System.out.println("ERROR 503 --> Service Unavailable");
            return null;
        }
        return response;
    }

    public HttpResponse<String> getTopArtists(int limit, String timeRange, int offset) {
        String apiUrl = apiEndpoints.generateTopArtistsURL(limit, timeRange, offset);
        return getHttpResponse(apiUrl);
    }

    public HttpResponse<String> getArtistById(String artistId) {
        String apiUrl = apiEndpoints.generateArtistURL(artistId);
        return getHttpResponse(apiUrl);
    }

    public HttpResponse<String> getMultipleArtists(List<String> artistIds) {
        String apiUrl = apiEndpoints.generateMultipleArtistsURL(artistIds);
        return getHttpResponse(apiUrl);
    }

    public HttpResponse<String> getTopTracks(int limit, String timeRange, int offset) {
        String apiUrl = apiEndpoints.generateTopTracksURL(limit, timeRange, offset);
        return getHttpResponse(apiUrl);
    }

    public HttpResponse<String> getTrackById(String trackId) {
        String apiUrl = apiEndpoints.generateTrackURL(trackId);
        return getHttpResponse(apiUrl);
    }

    public HttpResponse<String> getMultipleTracks(List<String> trackIds) {
        String apiUrl = apiEndpoints.generateMultipleTracksURL(trackIds);
        return getHttpResponse(apiUrl);
    }

    public HttpResponse<String> getUserProfile() {
        String apiUrl = apiEndpoints.USER_PROFILE;
        return getHttpResponse(apiUrl);
    }

    public HttpResponse<String> search(String name, String type, String market, int limit, int offset) {
        String apiUrl = apiEndpoints.search(name, type, market, limit, offset);
        return getHttpResponse(apiUrl);
    }
}
