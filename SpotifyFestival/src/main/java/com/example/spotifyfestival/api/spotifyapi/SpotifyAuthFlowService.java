package com.example.spotifyfestival.api.spotifyapi;

import com.example.spotifyfestival.database.entities.pojo.User;
import com.example.spotifyfestival.database.dao.UserDAO;
import com.example.spotifyfestival.newfeatures.SpotifyResponseService;
import com.example.spotifyfestival.ui.helper.classes.AppSwitchScenesMethods;
import com.example.spotifyfestival.database.entities.pojo.UserManager;
import javafx.application.Platform;
import org.json.JSONObject;
import spark.Spark;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpotifyAuthFlowService {
    private final String userMainScreen = "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/userMainScreen.fxml";
    private final String adminMainScreen = "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminMainScreen.fxml";
    private SpotifyAPPCredentials spotifyAPPCredentials = null;
    private String accessTokenResponse = null;
    private String accessToken = null;
    private String myState = null;
    private SpotifyResponseService spotifyResponseService;
    private String refreshToken = null;
    private String originalInput = null;
    private volatile boolean bool = false;

    private long startTime = 0;

    private static SpotifyAuthFlowService instance;

    public static SpotifyAuthFlowService getInstance() {
        if (instance == null) {
            instance = new SpotifyAuthFlowService();
        }
        return instance;
    }

    private SpotifyAuthFlowService() {
        spotifyAPPCredentials = SpotifyAPPCredentials.getInstance();
        originalInput = spotifyAPPCredentials.getClientId() + ":" + spotifyAPPCredentials.getClientSecret();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void run() {
        // Start the server
        startTime = System.currentTimeMillis();
        Spark.port(8888);
        login();
        defineCallbackPath();

        // Schedule a task to check and regenerate the access token every hour
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::checkAndRegenerateToken, 10, 10, TimeUnit.SECONDS);
    }

    public void login() {
        // Generate a random state
        myState = getAlphaNumericString(16);
        String loginURL = generateLoginURL(myState);
        // Open the login URL in the default web browser
        openURL(loginURL);

        Spark.get("/login", (request, response) -> {
            response.redirect(loginURL);
            return null;
        });
    }

    private void checkAndRegenerateToken() {
        // Check if an hour has passed since the last time the access token was generated
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        long oneHourInMillis = 60 * 60 * 1000; // 1 hour in milliseconds
        long tenSecondsInMillis = 10 * 1000; // 10 seconds in milliseconds
        if (elapsedTime >= oneHourInMillis) {
            // Regenerate the access token
            generateNewAccessToken();
            System.out.println("Access token regenerated");

            // Update the start time for the next hour
            startTime = System.currentTimeMillis();
        }
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
        // Encode the scope, state and redirect URI
        String encodedScope = null;
        encodedScope = URLEncoder.encode(spotifyAPPCredentials.getUserTopReadScope(), StandardCharsets.UTF_8);
        String encodedState = null;
        encodedState = URLEncoder.encode(STATE, StandardCharsets.UTF_8);
        String encodedRedirectUri = null;
        encodedRedirectUri = URLEncoder.encode(spotifyAPPCredentials.getRedirectUri(), StandardCharsets.UTF_8);

        // Generate the login URL
        return "https://accounts.spotify.com/authorize?" +
                "response_type=code" +
                "&client_id=" + spotifyAPPCredentials.getClientId() +
                "&scope=" + encodedScope +
                "&redirect_uri=" + encodedRedirectUri +
                "&state=" + encodedState;
    }

    public void openURL(String url) {
        // Open the URL in the default web browser
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();// Get the desktop object
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                try {
                    java.net.URI uri = new java.net.URI(url);// Create a URI object
                    desktop.browse(uri);// Browse the URL
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void defineCallbackPath() {
        // Define the callback path
        Spark.get("/callback", (request, response) -> {
            String code = request.queryParams("code");//get the code
            String receivedState = request.queryParams("state");//get the state
            //if statement here to check if state is the same
            if (myState.equals(receivedState)) {
                //request parameters
                String requestBody = "grant_type=authorization_code&code="
                        + code
                        + "&redirect_uri="
                        + spotifyAPPCredentials.getRedirectUri();
                //generate access token
                generateAccessToken(requestBody);
            }
            //check if user is admin or not
            checkUserAuthorization();
            bool = true;//set bool to true

            return HtmlCONSTANTS.HTML_PAGE;
        });
    }

    private void checkUserAuthorization() {
        //get user email
        String email = getUserEmail();
        //check if user is in the database
        UserDAO userDAO = UserDAO.getInstance();
        Iterable<User> users = userDAO.getAll();
        //check if user is admin
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                if (user.getRole().equals("admin")) {
                    UserManager.setAdmin(true);
                    Platform.runLater(() -> {
                        AppSwitchScenesMethods.switchScene(adminMainScreen);
                    });
                } else {
                    UserManager.setAdmin(false);
                    Platform.runLater(() -> {
                        AppSwitchScenesMethods.switchScene(userMainScreen);
                    });
                }
            }
        }
    }

    private void generateAccessToken(String requestBody) {
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
            accessTokenResponse = httpResponse.body();
            if (statusCode == 200) {
                accessToken = getAccessToken(accessTokenResponse);
            } else {
                // handle error responses here
                System.err.println("Error: " + statusCode);
            }
//
        } catch (IOException | InterruptedException exception) {
            // Handle exceptions
            exception.printStackTrace();
            // Return an error response
        }
    }

    //get new access token
    public void generateNewAccessToken() {
        refreshToken = getRefreshToken(accessTokenResponse);
        HttpResponse<String> refresh = spotifyResponseService.getNewAccessToken(refreshToken, originalInput);
        accessToken = getAccessToken(refresh.body());
    }

    //get user email from JSON response
    public String getUserEmail() {
        spotifyResponseService = new SpotifyResponseService(accessToken);
        String userInfoJson = spotifyResponseService.getUserProfile().body();
        JSONObject jsonObject = new JSONObject(userInfoJson);
        return jsonObject.getString("email");
    }

    //get access token from JSON response
    public String getAccessToken(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getString("access_token");
    }

    //get refresh token from JSON response
    public String getRefreshToken(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        return jsonObject.getString("refresh_token");
    }

    public long getStartTime() {
        return startTime;
    }
}