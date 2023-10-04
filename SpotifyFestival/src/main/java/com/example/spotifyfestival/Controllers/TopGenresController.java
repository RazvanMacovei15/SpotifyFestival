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
import javafx.scene.control.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Iterator;

public class TopGenresController {

    @FXML
    private ListView<String> listView;

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            APPHelperMethods.switchScene(event, "afterLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    public void on4WeeksButtonClicked() throws JsonProcessingException {
        HttpResponse response = getUserTopGenresOver4Weeks();
        String s = null;
        String jsonResponse = response.body().toString();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> genreNamesFromArtists = printGenresFor4Weeks(jsonResponse);
        ObservableList<String> sortedGenres = printGenresFor4Weeks(jsonResponse);

//        printGenresFor4Weeks(jsonResponse);
        listView.setItems(genreNamesFromArtists);

        SortedBag<String> sB = computeSortedBag(genreNamesFromArtists);

        // Create an ObservableList to store the genres
        ObservableList<String> observableList = FXCollections.observableArrayList();

        // Use an iterator to iterate through the sorted bag and add genres to the ObservableList
        Iterator<String> iterator = sB.iterator();
        while (iterator.hasNext()) {
            String genre = iterator.next();
            int count = sB.countOccurrences(genre);
            for (int i = 0; i < count; i++) {
                observableList.add(genre);
            }
        }

        // Now, observableList contains the genres in order
        System.out.println(observableList);

        listView.setItems(observableList);



    }

    public static HttpResponse getUserTopGenresOver4Weeks() {
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

    public ObservableList<String> printGenresFor4Weeks(String jsonResponse){
        ObservableList<String> ol = FXCollections.observableArrayList();
        String s = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray allTheArtists = jsonObject.getJSONArray("items");

            for(int i = 0; i < allTheArtists.length(); i++){

                JSONObject objectTracks = allTheArtists.getJSONObject(i);
                JSONArray artistGenres = objectTracks.getJSONArray("genres");
//                StringBuilder sb = new StringBuilder();
//                sb.append("[");

                for(int j = 0; j < artistGenres.length(); j++){
                     s = artistGenres.get(j).toString();
                    System.out.println(s);
                    ol.add(s);
//                    sb.append(s);
//                    if (j < artistGenres.length()-1) {
//                        sb.append(", "); // Add a comma and space as a separator
                    }
                }
//                sb.append("]");
//                String s = sb.toString();



        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println(ol);

        return ol;
    }

    public SortedBag<String> computeSortedBag(ObservableList<String> ol){
        ObservableList obL = FXCollections.observableArrayList();
        SortedBag<String> sB = new SortedBag<>();

        for(int i = 0; i < ol.size(); i++){
            sB.add(ol.get(i));
        }
        System.out.println(sB);
        return sB;
    }

}
