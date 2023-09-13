package com.example.spotifyfestival;


import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Spark;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;


public class SpotifyAPI {
    private String responseBody = null;

    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String refreshToken;
    private String scope;

    private String refreshedToken;

    private String refreshedTokenType;

    private int refreshedTokenExpiresIn;

    private static String refreshedTokenScope;

    public int getStatusCode1() {
        return statusCode1;
    }

    public int statusCode1;

    public AccessTokenResponse accessTokenResponse;

    public RefreshAccessTokenResponse refreshAccessTokenResponse;

    private String STATE;

    public void startServerOnPort(int n) {
        Spark.port(n);
    }


    private AccessTokenResponse deserializeAccessTokenResponse(String json) {
        return JsonUtils.deserializeJson(json, AccessTokenResponse.class);
    }

    private void handleAccessTokenResponse(AccessTokenResponse accessTokenResponse) {
        //retrieve the data
        accessToken = accessTokenResponse.getAccessToken();
        tokenType = accessTokenResponse.getTokenType();
        expiresIn = accessTokenResponse.getExpiresIn();
        refreshToken = accessTokenResponse.getRefreshToken();
        scope = accessTokenResponse.getScope();

        // Access the data
        System.out.println();
        System.out.println("Access Token: " + accessToken);
        System.out.println("Token Type: " + tokenType);
        System.out.println("Expires In: " + expiresIn);
        System.out.println("Refresh Token: " + refreshToken);
        System.out.println("Scope: " + scope);
    }

    private RefreshAccessTokenResponse deserializeRefreshAccessTokenResponse(String json) {
        return JsonUtils.deserializeJson(json, RefreshAccessTokenResponse.class);
    }

    private void handleRefreshAccessTokenResponse(RefreshAccessTokenResponse refreshAccessTokenResponse) {
        System.out.println();
        refreshedToken = refreshAccessTokenResponse.getRefreshedAccessToken();
        refreshedTokenType = refreshAccessTokenResponse.getRefreshedTokenType();
        refreshedTokenExpiresIn = refreshAccessTokenResponse.getRefreshedExpiresIn();
        refreshedTokenScope = refreshAccessTokenResponse.getRefreshedScope();

        // Access the data
        System.out.println("refreshedToken: " + refreshedToken);
        System.out.println("refreshedTokenType: " + refreshedTokenType);
        System.out.println("refreshedTokenExpiresIn: " + refreshedTokenExpiresIn);
        System.out.println("refreshedTokenScope: " + refreshedTokenScope);
    }

    public void backendThatNeedsChange() throws UnsupportedEncodingException {

        HelperMethods helperMethods = new HelperMethods();

        startServerOnPort(8888);

        STATE = helperMethods.getAlphaNumericString(16);

        String loginURL = helperMethods.generateLoginURL(STATE);

        helperMethods.openURL2(loginURL);
//
        Spark.get("/login", (request, response) -> {

            response.redirect(loginURL);

            return null;
        });

        Spark.get("/callback", (request, response) -> {
            String code = request.queryParams("code");
            String state = request.queryParams("state");

            //if statement here to check if state is the same
            if (STATE.equals(state)){
                //request parameters
                String requestBody = "grant_type=authorization_code&code=" + code + "&redirect_uri=" + SpotifyAPPCredentials.getRedirectUri();

                String originalInput = SpotifyAPPCredentials.getClientId() + ":" + SpotifyAPPCredentials.getClientSecret();

                try{

                    HttpClient client = HttpClient.newBuilder().build();

                    HttpRequest tokenRequest = HttpRequest.newBuilder()
                            .uri(URI.create("https://accounts.spotify.com/api/token"))
                            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(originalInput.getBytes()))
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();

                    HttpResponse<String> httpResponse = client.send(tokenRequest, HttpResponse.BodyHandlers.ofString());
                    int statusCode = httpResponse.statusCode();
                    responseBody = httpResponse.body();

                    if (statusCode == 200) {
                        AccessTokenResponse accessTokenResponse = deserializeAccessTokenResponse(responseBody);
                        if (accessTokenResponse != null) {
                            handleAccessTokenResponse(accessTokenResponse);
//                            System.out.println(accessTokenResponse.getRefreshToken());
                            accessToken = accessTokenResponse.getAccessToken();
                        } else {
                            System.out.println("Something went wrong with ACCESS TOKEN RESPONSE!");
                        }
                    } else {
                        // handle error responses here
                        System.err.println("Error: " + statusCode);

                    }

                    HttpRequest httpRequest = HttpRequest.newBuilder()
                            .uri(URI.create("https://accounts.spotify.com/api/token"))
                            .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(originalInput.getBytes()))
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .POST(HttpRequest.BodyPublishers.ofString("grant_type=refresh_token&refresh_token=" + refreshToken))
                            .build();

                    HttpResponse<String> refreshResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    String refreshResponseBody = refreshResponse.body();
                    int refreshStatusCode = refreshResponse.statusCode();

                    if (refreshStatusCode == 200) {

                        // handle a successful refresh response here
                        RefreshAccessTokenResponse refreshAccessTokenResponse = deserializeRefreshAccessTokenResponse(refreshResponseBody);
                        if(refreshAccessTokenResponse !=  null){
                            handleRefreshAccessTokenResponse(refreshAccessTokenResponse);
//                            System.out.println(refreshAccessTokenResponse.getRefreshedAccessToken());
                            accessToken = refreshAccessTokenResponse.getRefreshedAccessToken();
                        }else {
                            System.out.println("Something went wrong with ACCESS TOKEN RESPONSE!");
                        }
                    } else {
                        // Handle error responses here
                        System.err.println("Error: " + refreshStatusCode);
                        // Handle error response, log, or return an error message
                    }
                }catch (IOException | InterruptedException exception){
                    // Handle exceptions
                    exception.printStackTrace();
                    // Return an error response
                }
            }


            System.out.println(getUserTopArtists().body());
            System.out.println(getUserTopTracks().body());

            return HtmlCONSTANTS.HTML_PAGE;
        });
    }

    public HttpResponse getUserTopTracks() throws IOException, InterruptedException {

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

    public HttpResponse getUserTopArtists() throws IOException, InterruptedException {

        String API_URL = API_URLS.getUserTopArtists();

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response;
    }

    public void printArtistsButton() throws IOException, InterruptedException {
        System.out.println(getUserTopArtists().body());
    }

    public String printTracksButton() throws IOException, InterruptedException {

        String returnString = getUserTopTracks().body().toString();
        return returnString;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        SpotifyAPI spotifyAPI = new SpotifyAPI();
//
//        spotifyAPI.backendThatNeedsChange();

    }
}

