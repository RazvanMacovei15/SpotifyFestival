package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyService;
import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
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

    protected SpotifyService service;

    public TopTracksController() {
        this.service = new SpotifyService();
    }

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

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminMainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }



    public void onAllTimeButtonClicked(){
        HttpResponse<String> response = SpotifyService.getUserTopTracksOfAllTime();

        assert response != null;
        String jsonResponse = response.body();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNamesFromTracks = service.extractArtistNamesFromTracks(jsonResponse);
        ObservableList<String> trackNames = extractAttribute(jsonResponse, "name");

        ObservableList<String> concat = listOfNamesAndSongs(artistNamesFromTracks, trackNames);
        listView.setItems(concat);
    }

    public void on6MonthsButtonClicked() {
        HttpResponse<String> response = SpotifyService.getUserTopTracksOver6Months();

        assert response != null;
        String jsonResponse = response.body();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNamesFromTracks = service.extractArtistNamesFromTracks(jsonResponse);
        ObservableList<String> trackNames = extractAttribute(jsonResponse, "name");

        ObservableList<String> concat = listOfNamesAndSongs(artistNamesFromTracks, trackNames);
        listView.setItems(concat);
    }
    public void on4WeeksButtonClicked() {
        HttpResponse<String> response = SpotifyService.getUserTopTracksOver4Weeks();

        assert response != null;
        String jsonResponse = response.body();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNamesFromTracks = service.extractArtistNamesFromTracks(jsonResponse);
        ObservableList<String> trackNames = extractAttribute(jsonResponse, "name");

        ObservableList<String> concat = listOfNamesAndSongs(artistNamesFromTracks, trackNames);
        listView.setItems(concat);
    }

}
