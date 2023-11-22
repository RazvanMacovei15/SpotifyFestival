package com.example.spotifyfestival.Controllers;

import com.example.spotifyfestival.API_URLS.Artists_API_URLS;
import com.example.spotifyfestival.AppSwitchScenesMethods;
import com.example.spotifyfestival.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.SpotifyAPI.SpotifyService;
import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Domain.Genre;
import com.example.spotifyfestival.UserData.Generics.MapValueSorter;
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
import java.util.HashMap;
import java.util.Map;

public class TopGenresController {

    @FXML
    private ListView<String> listView;

    @FXML
    private ListView<String> countListView;

    @FXML
    private Button allTimeButton;

    @FXML
    private Button sixMonthsButton;

    @FXML
    private Button fourWeeksButton;

    @FXML
    public void initialize() throws JsonProcessingException {
        // Automatically trigger the "4 weeks" button when the scene is shown
//        on4WeeksButtonClicked();
    }

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "afterLoginScreen.fxml");
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

//    public void onAllTimeButtonClicked() throws JsonProcessingException {
//
//        onTimeRangeButtonClicked("all time");
//
//    }
//
//    public void on6MonthsButtonClicked() throws JsonProcessingException {
//
//        onTimeRangeButtonClicked("6 months");
//
//    }
//    public void on4WeeksButtonClicked() throws JsonProcessingException {
//
//        onTimeRangeButtonClicked("4 weeks");
//
//    }

//    public void onTimeRangeButtonClicked(String timeRange) throws JsonProcessingException {
//        HttpResponse response;
//
//        switch (timeRange) {
//            case "all time":
//                response = getUserTopArtists();
//                break;
//            case "6 months":
//                response = getUserTopArtistsOver6Months();
//                break;
//            case "4 weeks":
//                response = getUserTopArtistsOver4Weeks();
//                break;
//            default:
//                // Handle the case when an unsupported time range is provided
//                return;
//        }
//
//        String jsonResponse = response.body().toString();
//        // Call the extractAttribute method to get the artist attributes
////        ObservableList<String> artistNames = extractAttribute(jsonResponse, "name");
//        ObservableList<Artist> allArtists = extractArtists(jsonResponse);
//        Map<String, Integer> genreCount = getGenreCountFromResponse(allArtists);
//
//        ObservableList<String> genres = FXCollections.observableArrayList();
//        ObservableList<String> genresCount = FXCollections.observableArrayList();
//
//        for(Map.Entry<String, Integer> entry : genreCount.entrySet()){
//            genres.add(entry.getKey());
//        }
//        for(Map.Entry<String, Integer> entry : genreCount.entrySet()){
//            genresCount.add(String.valueOf(entry.getValue()));
//        }
//
//        // Set the artist names in your ListView or perform other actions
//        listView.setItems(genres);
//        countListView.setItems(genresCount);
//    }

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

//    public ObservableList<Artist> extractArtists(String jsonResponse){
//
//        ObservableList<ObservableList<String>> allGenresExtracted = FXCollections.observableArrayList();
//        ObservableList<Artist> listOfArtistsInResponse = FXCollections.observableArrayList();
//        try{
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//            JSONArray itemsArray = jsonObject.getJSONArray("items");
//            for(int i=0; i<itemsArray.length(); i++){
//                ObservableList<String> artistGenres = FXCollections.observableArrayList();
//                JSONObject artistObject = itemsArray.getJSONObject(i);
//                JSONArray array = artistObject.getJSONArray("genres");
//                for(int j=0; j<array.length(); j++ ){
//                    String genre = array.getString(j);
//                    artistGenres.add(genre);
//                }
//                String name = artistObject.getString("name");
//                String id = artistObject.getString("id");
//                Artist artist = new Artist(name, artistGenres);
//                artist.setId(id);
//
//                listOfArtistsInResponse.add(artist);
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return listOfArtistsInResponse;
//    }

//    public Map<String, Integer> getGenreCountFromResponse(ObservableList<Artist> artists){
//
//        HashMap<String, Integer> genreCount = new HashMap<>();
//        for(Artist artist : artists){
//            ObservableList<Genre> genres = (ObservableList<Genre>) artist.getGenres();
//            for(int i = 0; i < genres.size(); i++){
//                Genre genre = genres.get(i);
//                genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
//            }
//        }
//
//        System.out.println(genreCount);
//
//        Map<String, Integer> sortedGenreMap = MapValueSorter.sortByValuesDescendingWithAlphabetical(genreCount);
//        for (Map.Entry<String, Integer> entry : sortedGenreMap.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
//        return sortedGenreMap;
//    }

}