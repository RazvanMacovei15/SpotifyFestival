package com.example.spotifyfestival;

import com.fasterxml.jackson.databind.ObjectMapper;

import spark.Spark;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class SpotifyAPI {

    private final static String CLIENT_ID = "40f0faeac8b043ee99f7bd42e134f97c";
    private final static String CLIENT_SECRET = "9713d372e12e4c699accf979bd406435";
    private final static String REDIRECT_URI = "http://localhost:8888/callback";
    private static final String SCOPE = "user-top-read";
    private static String STATE = null;

    private static String responseBody= null;

    private static String accessToken;
    private static String tokenType;
    private static int expiresIn;
    private static String refreshToken;
    private static String scope;

    private static String refreshedToken;

    private static String refreshedTokenType;

    private static int refreshedTokenExpiresIn;

    private static String refreshedTokenScope;

    public int getStatusCode1() {
        return statusCode1;
    }

    public static int statusCode1;

    public static void mainSpot() throws Exception {
        STATE = HelperMethods.getAlphaNumericString(16);
        String loginURL = HelperMethods.loginURL(STATE);
        HelperMethods.openURL2(loginURL);

        Spark.port(8888);

        Spark.get("/login", (request, response) -> {

            response.redirect(loginURL);

            return null;
        });

        Spark.get("/callback", (request, response) -> {
            String code = request.queryParams("code");
            String state = request.queryParams("state");

            //if statement here to check if state is the same

            //request parameters
            String requestBody = "grant_type=authorization_code&code=" + code + "&redirect_uri=" + REDIRECT_URI;

            String originalInput = CLIENT_ID + ":" + CLIENT_SECRET;

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

//            statusCode1 = httpResponse.statusCode();

            System.out.println("Response Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);

            try {
                // Create an ObjectMapper instance
                ObjectMapper mapper = new ObjectMapper();

                // Deserialize the JSON response into an AccessTokenResponse object
                AccessTokenResponse accessTokenResponse = mapper.readValue(responseBody, AccessTokenResponse.class);

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
            } catch (Exception e) {
                e.printStackTrace();
            }

            HttpClient httpClient = HttpClient.newHttpClient();


            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://accounts.spotify.com/api/token"))
                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(originalInput.getBytes()))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("grant_type=refresh_token&refresh_token=" + refreshToken))
                    .build();

            HttpResponse<String> refreshResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            String refreshResponseBody = refreshResponse.body();
//            System.out.println("refresh: " + refreshResponseBody);

            try {
                // Create an ObjectMapper instance
                ObjectMapper refreshMapper = new ObjectMapper();

                // Deserialize the JSON response into an AccessTokenResponse object
                RefreshAccessTokenResponse refreshAccessTokenResponse = refreshMapper.readValue(refreshResponseBody, RefreshAccessTokenResponse.class);
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
            } catch (Exception e) {
                e.printStackTrace();
            }

            //access user's top artists and tracks
            String apiUrl = "https://api.spotify.com/v1/me/top/tracks?limit=20";

            // Create an HttpClient
            HttpClient httpClient2 = HttpClient.newBuilder().build();

            // Create an HttpRequest with the necessary headers
            HttpRequest request2 = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Authorization", "Bearer " + accessToken)
                    .GET()
                    .build();

            HttpResponse<String> response2 = httpClient2.send(request2, HttpResponse.BodyHandlers.ofString());
            System.out.println(response2.body());
            System.out.println(response2.statusCode());

            return HtmlCONSTANTS.HTML_PAGE;
        });
    }

    public static void main(String[] args) {
        // Entry point of your SpotifyAPI application
        try {
            SpotifyAPI spotifyAPI = new SpotifyAPI();

            // Initialize and run your SpotifyAPI logic here
            mainSpot();

            int n = spotifyAPI.getStatusCode1();
            System.out.println(n);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
