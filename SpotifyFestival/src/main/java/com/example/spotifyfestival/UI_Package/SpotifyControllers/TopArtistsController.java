package com.example.spotifyfestival.UI_Package.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.API_URLS.Artists_API_URLS;
import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyService;
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
        // Automatically trigger the "4 weeks" button when the scene is shown
        on4WeeksButtonClicked();
    }

    public static HttpResponse<String> getUserTopArtists() {
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
    public static HttpResponse<String> getUserTopArtistsOver6Months() {
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
    public static HttpResponse<String> getUserTopArtistsOver4Weeks() {
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

        onTimeRangeButtonClicked("all time");

    }

    public void on6MonthsButtonClicked() throws JsonProcessingException {

        onTimeRangeButtonClicked("6 months");

    }
    public void on4WeeksButtonClicked() throws JsonProcessingException {

        onTimeRangeButtonClicked("4 weeks");

    }

    public void onTimeRangeButtonClicked(String timeRange) throws JsonProcessingException {
        HttpResponse<String> response;

        switch (timeRange) {
            case "all time":
                response = getUserTopArtists();
                break;
            case "6 months":
                response = getUserTopArtistsOver6Months();
                break;
            case "4 weeks":
                response = getUserTopArtistsOver4Weeks();
                break;
            default:
                // Handle the case when an unsupported time range is provided
                return;
        }

        String jsonResponse = response.body().toString();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNames = extractAttribute(jsonResponse, "name");
        ObservableList<String> artistID = extractAttribute(jsonResponse, "id");

        // Set the artist names in your ListView or perform other actions
        listView.setItems(artistNames);
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

    public void getBackToTopLists(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }


    //never used methods

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


    public void onGetBackButtonClicked(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

}