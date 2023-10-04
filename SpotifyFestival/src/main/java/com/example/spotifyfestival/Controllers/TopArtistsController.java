package com.example.spotifyfestival.Controllers;

import com.example.spotifyfestival.API_URLS.Artists_API_URLS;
import com.example.spotifyfestival.APPHelperMethods;
import com.example.spotifyfestival.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.SpotifyAPI.SpotifyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;

public class TopArtistsController {

    @FXML
    private ListView<String> listView;

    @FXML
    private Button allTimeButton;

    @FXML
    private Button sixMonthsButton;

    @FXML
    private Button fourWeeksButton;

    @FXML
    public void initialize() throws JsonProcessingException {
        // Automatically trigger the "All Time" button when the scene is shown
        onAllTimeButtonClicked();
    }

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            APPHelperMethods.switchScene(event, "afterLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    public static HttpResponse getUserTopArtists() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, Artists_API_URLS.getUserTopArtistsAllTimeURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static HttpResponse getUserTopArtistsOver6Months() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, Artists_API_URLS.getUserTopArtists6MonthsURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static HttpResponse getUserTopArtistsOver4Weeks() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, Artists_API_URLS.getUserTopArtistsOver4WeeksURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void onAllTimeButtonClicked() throws JsonProcessingException {
        HttpResponse response = getUserTopArtists();

        String jsonResponse = response.body().toString();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNames = extractAttribute(jsonResponse, "name");
        ObservableList<String> artistID = extractAttribute(jsonResponse, "id");

        printNamesAndIDs(artistNames, artistID);

        listView.setItems(artistNames);
    }

    public void on6MonthsButtonClicked() throws JsonProcessingException {
        HttpResponse response = getUserTopArtistsOver6Months();

        String jsonResponse = response.body().toString();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNames = extractAttribute(jsonResponse, "name");
        ObservableList<String> artistID = extractAttribute(jsonResponse, "id");

        printNamesAndIDs(artistNames, artistID);

        listView.setItems(artistNames);
    }
    public void on4WeeksButtonClicked() throws JsonProcessingException {
        HttpResponse response = getUserTopArtistsOver4Weeks();

        String jsonResponse = response.body().toString();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNames = extractAttribute(jsonResponse, "name");
        ObservableList<String> artistID = extractAttribute(jsonResponse, "id");

        printNamesAndIDs(artistNames, artistID);

        listView.setItems(artistNames);
    }

    public static void printNamesAndIDs(ObservableList<String> artistNames, ObservableList<String> artistID) {
        if (artistNames.size() != artistID.size()) {
            System.out.println("Error: The sizes of artistNames and artistID lists do not match.");
            return;
        }

        for (int i = 0; i < artistNames.size(); i++) {
            String name = artistNames.get(i);
            String id = artistID.get(i);
            System.out.println("Name: " + name);
            System.out.println("ID: " + id);
        }
    }

    public static ObservableList<String> extractAttribute(String jsonResponse, String attributeName) {
        // Create an empty ObservableList to store the attribute values
        ObservableList<String> attributeValues = FXCollections.observableArrayList();

        try {
            // Parse the JSON response
            JSONObject responseJson = new JSONObject(jsonResponse);
            JSONArray itemsArray = responseJson.getJSONArray("items");

            // Iterate through the items and extract the specified attribute
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);
                String attributeValue = itemObject.getString(attributeName);
                attributeValues.add(attributeValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return attributeValues;
    }



}
