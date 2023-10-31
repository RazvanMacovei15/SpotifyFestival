package com.example.spotifyfestival.Controllers;

import com.example.spotifyfestival.API_URLS.Artists_API_URLS;
import com.example.spotifyfestival.AppSwitchScenesMethods;
import com.example.spotifyfestival.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.SpotifyAPI.SpotifyService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;

public class TopGenresController {

    @FXML
    private ListView<String> listView;

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "afterLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

//    public void on4WeeksButtonClicked() throws JsonProcessingException {
//
//
//        HttpResponse response = getUserTopGenresOver4Weeks();
//        String s2 = null;
//        String jsonResponse = response.body().toString();
//        // Call the extractAttribute method to get the artist attributes
//        ObservableList<String> genreNamesFromArtists = printGenresFor4Weeks(jsonResponse);
//
//        LinkedHashMap<String,Integer> newMap = computeSortedBag(genreNamesFromArtists);
//
//        ObservableList<String> ol = FXCollections.observableArrayList();
//
//        for (Map.Entry<String, Integer> entry : newMap.entrySet()) {
//            String key = entry.getKey();
//            int value = entry.getValue();
//
//            StringBuilder sb = new StringBuilder();
//            sb.append(key);
//            sb.append(", ");
//            sb.append(value);
//            sb.append(";");
//            String concatenatedString = sb.toString();
//
//            ol.add(concatenatedString);
//        }
//
//        System.out.println(ol);
//
//        listView.setItems(ol);
//
//    }

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

//    public ObservableList<String> printGenresFor4Weeks(String jsonResponse){
//        ObservableList<String> ol = FXCollections.observableArrayList();
//        String s = null;
//        SortedBag<String> sB = new SortedBag<>();
//
//        try {
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//            JSONArray allTheArtists = jsonObject.getJSONArray("items");
//
//            for(int i = 0; i < allTheArtists.length(); i++){
//
//                JSONObject objectTracks = allTheArtists.getJSONObject(i);
//                JSONArray artistGenres = objectTracks.getJSONArray("genres");
//
//                for(int j = 0; j < artistGenres.length(); j++){
//                    s = artistGenres.get(j).toString();
//                    sB.add(s);
//                    ol.add(s);
//                    }
//                }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        System.out.println(sB);
////        System.out.println(ol);
//        return ol;
//    }

    public LinkedHashMap<String, Integer> computeSortedBag(ObservableList<String> ol) {
        List<String> genres = List.of("pop", "singer-songwriter pop", "uk pop", "flick hop", "underground rap", "alternative metal", "neo mellow", "pop rock", "post-grunge", "alternative metal", "nu metal", "detroit hip hop", "hip hop", "rap", "moldovan pop", "romanian pop", "israeli pop", "romanian rap", "romanian rock", "danish metal", "danish rock", "melodic power metal", "alternative metal", "groove metal", "nu metal", "dutch metal", "gothic metal", "gothic symphonic metal", "symphonic metal", "alternative pop rock", "modern alternative rock", "modern rock", "hip hop", "pop rap", "rap", "piano rock", "pop");

        Map<String, Integer> genreCountMap = new HashMap<>();

        for (String genre : genres) {
            genreCountMap.put(genre, genreCountMap.getOrDefault(genre, 0) + 1);
        }

        // Convert the map into a list of entries
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(genreCountMap.entrySet());

        // Sort the list of entries first by key (descending order) and then alphabetically
        entryList.sort((entry1, entry2) -> {
            int cmp = entry2.getValue().compareTo(entry1.getValue()); // Sort by count (descending)
            if (cmp == 0) {
                // If counts are the same, sort alphabetically by genre
                return entry1.getKey().compareTo(entry2.getKey());
            }
            return cmp;
        });

        LinkedHashMap<String,Integer>newMap = new LinkedHashMap<>();


        // Print the sorted entries
        for (Map.Entry<String, Integer> entry : entryList) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            newMap.put(entry.getKey(), entry.getValue());
        }
        return newMap;
    }


    public static ObservableList<String> keysToObservableList(Map<String, Integer> map) {
        List<String> keyList = new ArrayList<>(map.keySet());
        ObservableList<String> observableList = FXCollections.observableArrayList(keyList);
        return observableList;
    }

    // Method to print keys of a LinkedHashMap in order
    public static void printKeysInOrder(Map<String, Integer> map) {
        Iterator<String> iterator = map.keySet().iterator();
        Iterator<Integer> it = map.values().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            int value = it.next();
            System.out.println(key + " " + value);
        }
    }



}
