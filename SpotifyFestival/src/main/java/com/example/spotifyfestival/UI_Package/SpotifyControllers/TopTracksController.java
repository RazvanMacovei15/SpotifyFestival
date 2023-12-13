package com.example.spotifyfestival.UI_Package.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.API_URLS.Tracks_API_URLS;
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

public class TopTracksController {


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


    public void getBackToTopLists(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
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
    public static void printNamesAndSongs(ObservableList<String> artist, ObservableList<String> songs) {
        if (artist.size() != songs.size()) {
            System.out.println("Error: The sizes of artistNames and artistID lists do not match.");
            return;
        }

        for (int i = 0; i < artist.size(); i++) {
            String name = artist.get(i);
            String id = songs.get(i);
            System.out.println("Name: " + name);
            System.out.println("ID: " + id);
        }
    }
    public static ObservableList<String> listOfNamesAndSongs(ObservableList<String> artist, ObservableList<String> songs) {
        ObservableList<String> stringList = FXCollections.observableArrayList();

        if (artist.size() != songs.size()) {
            System.out.println("Error: The sizes of artistNames and artistID lists do not match.");
        }

        for (int i = 0; i < artist.size(); i++) {
            String name = artist.get(i);
            String id = songs.get(i);
            stringList.add(id + ", " + name);
        }
        return stringList;
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



    public static ObservableList<String> extractArtistNamesFromTracks(String jsonResponse) {
        ObservableList<String> ol = FXCollections.observableArrayList();
        String allArtistsOfASong = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray allTheTracks = jsonObject.getJSONArray("items");

            for(int i = 0; i < allTheTracks.length(); i++){

                JSONObject objectTracks = allTheTracks.getJSONObject(i);
                JSONArray trackObjects = objectTracks.getJSONArray("artists");

                StringBuilder sb = new StringBuilder();
                sb.append("performed by: ");
                for(int j = 0; j < trackObjects.length(); j++){
                    JSONObject artistObject = trackObjects.getJSONObject(j);
                    String individualArtists = artistObject.getString("name");
                    sb.append(individualArtists);
                    // Check if it's not the last item in the loop before adding a comma
                    if (j < trackObjects.length()-1) {
                        sb.append(", "); // Add a comma and space as a separator
                    }
                }

                allArtistsOfASong = sb.toString();

                ol.add(allArtistsOfASong);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ol;
    }

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    public static HttpResponse<String> getUserTopTracksOfAllTime() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, Tracks_API_URLS.getUserTopTracksAllTimeURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static HttpResponse<String> getUserTopTracksOver6Months() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, Tracks_API_URLS.getUserTopTracks6MonthsURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static HttpResponse<String> getUserTopTracksOver4Weeks() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, Tracks_API_URLS.getUserTopTracksOver4WeeksURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onAllTimeButtonClicked() throws JsonProcessingException {
        HttpResponse<String> response = getUserTopTracksOfAllTime();

        String jsonResponse = response.body().toString();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNamesFromTracks = extractArtistNamesFromTracks(jsonResponse);
        ObservableList<String> trackNames = extractAttribute(jsonResponse, "name");

        ObservableList<String> concat = listOfNamesAndSongs(artistNamesFromTracks, trackNames);
        listView.setItems(concat);
    }

    public void on6MonthsButtonClicked() throws JsonProcessingException {
        HttpResponse<String> response = getUserTopTracksOver6Months();

        String jsonResponse = response.body().toString();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNamesFromTracks = extractArtistNamesFromTracks(jsonResponse);
        ObservableList<String> trackNames = extractAttribute(jsonResponse, "name");

        ObservableList<String> concat = listOfNamesAndSongs(artistNamesFromTracks, trackNames);
        listView.setItems(concat);
    }
    public void on4WeeksButtonClicked() throws JsonProcessingException {
        HttpResponse<String> response = getUserTopTracksOver4Weeks();

        String jsonResponse = response.body().toString();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNamesFromTracks = extractArtistNamesFromTracks(jsonResponse);
        ObservableList<String> trackNames = extractAttribute(jsonResponse, "name");

        ObservableList<String> concat = listOfNamesAndSongs(artistNamesFromTracks, trackNames);
        listView.setItems(concat);
    }

}
