
package com.example.spotifyfestival.SpotifyAPI;


import com.example.spotifyfestival.APPHelperMethods;
import com.example.spotifyfestival.JSONObjects.AccessTokenResponse;
import com.example.spotifyfestival.JSONObjects.JsonUtils;
import com.example.spotifyfestival.JSONObjects.RefreshAccessTokenResponse;
import com.example.spotifyfestival.helperObsLis.AuthFlowObserver;
import com.example.spotifyfestival.helperObsLis.HtmlCONSTANTS;
import javafx.application.Platform;
import spark.Spark;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class SpotifyAuthFlowService {

    public void run() {
        Spark.port(8888);
        defineCallbackPath();
    }

    SpotifyAPPCredentials spotifyAPPCredentials = SpotifyAPPCredentials.getInstance();


    private String responseBody = null;

    private String accessToken;

    private String STATE;

    private final List<AuthFlowObserver> observers = new ArrayList<>();

    public void addObserver(AuthFlowObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(AuthFlowObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(String accessToken) {
        for (AuthFlowObserver observer : observers) {

            observer.onAuthFlowCompleted(accessToken);

        }
    }

    private static SpotifyAuthFlowService instance;

    private SpotifyAuthFlowService() {
    }

    public static SpotifyAuthFlowService getInstance() {
        if (instance == null) {
            instance = new SpotifyAuthFlowService();
        }
        return instance;
    }

    public String getAccessToken() {
        return accessToken;
    }

    private String refreshToken;

    public AccessTokenResponse accessTokenResponse;

    public RefreshAccessTokenResponse refreshAccessTokenResponse;

    String originalInput = spotifyAPPCredentials.getClientId() + ":" + spotifyAPPCredentials.getClientSecret();

    private AccessTokenResponse deserializeAccessTokenResponse(String json) {
        return JsonUtils.deserializeJson(json, AccessTokenResponse.class);
    }

    private RefreshAccessTokenResponse deserializeRefreshAccessTokenResponse(String json) {
        return JsonUtils.deserializeJson(json, RefreshAccessTokenResponse.class);
    }

    public String getAlphaNumericString(int n) {
        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    public String generateLoginURL(String STATE) {
        String encodedScope = null;
        encodedScope = URLEncoder.encode(spotifyAPPCredentials.getUserTopReadScope(), StandardCharsets.UTF_8);
        String encodedState = null;
        encodedState = URLEncoder.encode(STATE, StandardCharsets.UTF_8);

        return "https://accounts.spotify.com/authorize?" +
                "response_type=code" +
                "&client_id=" + spotifyAPPCredentials.getClientId() +
                "&scope=" + encodedScope +
                "&redirect_uri=" + URLEncoder.encode(spotifyAPPCredentials.getRedirectUri(), StandardCharsets.UTF_8) +
                "&state=" + encodedState;
    }

    public void openURL2(String url) {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                try {
                    java.net.URI uri = new java.net.URI(url);
                    desktop.browse(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

//        Runtime runtime = Runtime.getRuntime();
//        String[] args = {"osascript", "-e", "open location \"" + url + "\""};
//        try {
//            Process process = runtime.exec(args);
//        } catch (IOException e) {
//            // do what you want with this
//        }
    }

    public void openLogin() {
        STATE = getAlphaNumericString(16);
        String loginURL = generateLoginURL(STATE);
        openURL2(loginURL);
    }

    public volatile boolean bool = false;

    public void defineCallbackPath() {

        Spark.get("/callback", (request, response) -> {

            String code = request.queryParams("code");
            String state = request.queryParams("state");

            //if statement here to check if state is the same
            if (STATE.equals(state)) {
                //request parameters
                String requestBody = "grant_type=authorization_code&code=" + code + "&redirect_uri="
                        + spotifyAPPCredentials.getRedirectUri();

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
                    int statusCode = httpResponse.statusCode();
                    responseBody = httpResponse.body();

                    if (statusCode == 200) {
                        accessTokenResponse = deserializeAccessTokenResponse(responseBody);
                        if (accessTokenResponse != null) {
                            accessToken = accessTokenResponse.getAccessToken();
                            notifyObservers(accessToken); // Notify observers when API call is completed
                        } else {
                            System.out.println("Something went wrong with ACCESS TOKEN RESPONSE!");
                        }

                    } else {
                        // handle error responses here
                        System.err.println("Error: " + statusCode);

                    }

                } catch (IOException | InterruptedException exception) {
                    // Handle exceptions
                    exception.printStackTrace();
                    // Return an error response
                }
            }

            System.out.println("bool is true");
            Platform.runLater(() -> {
                try {
                    APPHelperMethods.switchSceneTwo("afterLoginScreen.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            bool = true;
            return HtmlCONSTANTS.HTML_PAGE;
        });
    }
    public String refreshTheToken(AccessTokenResponse accessTokenResponse) {

        refreshToken = accessTokenResponse.getRefreshToken();

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
        String refreshResponseBody = refreshResponse.body();
        int refreshStatusCode = refreshResponse.statusCode();

        if (refreshStatusCode == 200) {

            // handle a successful refresh response here
            refreshAccessTokenResponse = deserializeRefreshAccessTokenResponse(refreshResponseBody);
            if (refreshAccessTokenResponse != null) {
                refreshToken = refreshAccessTokenResponse.getRefreshedAccessToken();
                notifyObservers(refreshToken);
            } else {
                System.out.println("Something went wrong with ACCESS TOKEN RESPONSE!");
            }
        } else {
            // Handle error responses here
            System.err.println("Error: " + refreshStatusCode);
            // Handle error response, log, or return an error message
        }
        accessToken = refreshAccessTokenResponse.getRefreshedAccessToken();
        return accessToken;
    }
}


