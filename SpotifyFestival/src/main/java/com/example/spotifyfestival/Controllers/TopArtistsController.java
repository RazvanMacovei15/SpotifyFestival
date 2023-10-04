package com.example.spotifyfestival.Controllers;

import com.example.spotifyfestival.APPHelperMethods;
import com.example.spotifyfestival.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.SpotifyAPI.SpotifyService;
import com.example.spotifyfestival.helperObsLis.API_URLS;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;

public class TopArtistsController {



    @FXML
    private ListView<String> listView;

    private ObservableList<String> data = FXCollections.observableArrayList();

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            APPHelperMethods.switchScene(event, "afterLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }


    public void onAllTimeButtonClicked() throws JsonProcessingException {
        SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
        String token = spotifyAuthFlowService.getAccessToken();

        SpotifyService spotifyService = new SpotifyService();
        HttpResponse response = spotifyService.getHttpResponse(token, API_URLS.getUserTopArtistsURI());
//        System.out.println(response.body());

        String jsonResponse = response.body().toString();
        // Call the extractNames method to get the artist names
        ObservableList<String> artistNames = extractNames(jsonResponse);

        // Print the artist names
        for (String name : artistNames) {
            System.out.println(name);
        }

        listView.setItems(artistNames);
    }

    public static ObservableList<String> extractNames(String jsonResponse) {
        // Create an empty ObservableList to store the artist names
        ObservableList<String> artistNames = FXCollections.observableArrayList();

        try {
            // Parse the JSON response
            JSONObject responseJson = new JSONObject(jsonResponse);
            JSONArray itemsArray = responseJson.getJSONArray("items");

            // Iterate through the items and extract the names
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject artistObject = itemsArray.getJSONObject(i);
                String artistName = artistObject.getString("name");
                artistNames.add(artistName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return artistNames;
    }



}
