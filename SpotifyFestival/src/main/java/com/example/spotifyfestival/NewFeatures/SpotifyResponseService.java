package com.example.spotifyfestival.NewFeatures;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class SpotifyResponseService {
    private final String accessToken;

    public SpotifyResponseService(String accessToken) {
        this.accessToken = accessToken;
    }

    public HttpResponse<String> getAccessTokenResponse(String code, String originalInput, String redirectUri) {

        String requestBody = "grant_type=authorization_code&code=" + code + "&redirect_uri=" + redirectUri;
        try {
            HttpClient client = HttpClient.newBuilder().build();

            HttpRequest tokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://accounts.spotify.com/api/token"))
                    .header("Authorization",
                            "Basic " + Base64.getEncoder().encodeToString(originalInput.getBytes()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            HttpResponse<String> httpResponse = client.send(tokenRequest, HttpResponse.BodyHandlers.ofString());

            return httpResponse;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse<String> getEmailResponse() {
        String apiUrl = "https://api.spotify.com/v1/me";
        // Create HttpRequest
        HttpRequest emailRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(apiUrl))
                .headers("Authorization", "Bearer " + accessToken)
                .build();
        // Send the request and get the response
        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            return httpClient.send(emailRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse<String> getGenericHttpResponse(String accessToken, String apiUrl) {
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
        return response;
    }
}
