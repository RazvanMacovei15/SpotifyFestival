// Import statements for necessary packages and classes
package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.GenericsPackage.MapValueSorter;
import com.example.spotifyfestival.UIPackage.AppSwitchScenesMethods;
import com.example.spotifyfestival.API_Packages.APIServices.SpotifyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


// Controller class for the TopGenres FXML file
public class TopGenresController {

    // FXML annotations to inject UI elements
    @FXML
    private ListView<String> listView;

    // Initialize method, automatically triggered when the scene is shown
    @FXML
    public void initialize() throws JsonProcessingException {
        // Automatically trigger the "4 weeks" button when the scene is shown
        on4WeeksButtonClicked();
    }

    // Event handler for the "Get Back" button
    public void onGetBackButtonClicked() {
        try {
            // Switch scene to adminMainScreen.fxml
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminMainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    // Event handler for the "All Time" button
    public void onAllTimeButtonClicked() throws JsonProcessingException {
        // Delegate to the common method with the specified time range
        onTimeRangeButtonClicked("all time");
    }

    // Event handler for the "6 Months" button
    public void on6MonthsButtonClicked() throws JsonProcessingException {
        // Delegate to the common method with the specified time range
        onTimeRangeButtonClicked("6 months");
    }

    // Event handler for the "4 Weeks" button
    public void on4WeeksButtonClicked() throws JsonProcessingException {
        // Delegate to the common method with the specified time range
        onTimeRangeButtonClicked("4 weeks");
    }

    // Common method for handling time range button clicks
    public void onTimeRangeButtonClicked(String timeRange) throws JsonProcessingException {
        // Get user's top artists based on the specified time range
        HttpResponse<String> response;
        switch (timeRange) {
            case "all time":
                response = SpotifyService.getUserTopArtists();
                break;
            case "6 months":
                response = SpotifyService.getUserTopArtistsOver6Months();
                break;
            case "4 weeks":
                response = SpotifyService.getUserTopArtistsOver4Weeks();
                break;
            default:
                // Handle the case when an unsupported time range is provided
                return;
        }
        // Extract relevant information from the API response
        String jsonResponse = response.body();
        ObservableList<Artist> allArtists = SpotifyService.extractArtists(jsonResponse);
        Map<Genre, Integer> genreCount = getGenreCountFromResponse(allArtists);
        // Prepare data for UI display
        ObservableList<Genre> genres = FXCollections.observableArrayList();
        ObservableList<String> genresCount = FXCollections.observableArrayList();
        ObservableList<String> genreNames = FXCollections.observableArrayList();

        for (Map.Entry<Genre, Integer> entry : genreCount.entrySet()) {
            genres.add(entry.getKey());
        }
        for (Map.Entry<Genre, Integer> entry : genreCount.entrySet()) {
            genresCount.add(String.valueOf(entry.getValue()));
        }
        for (Genre genre : genres) {
            String name = genre.getName();
            genreNames.add(name);
        }

        // Set the data in the ListView UI elements
        listView.setItems(genreNames);
//        countListView.setItems(genresCount);
    }

    // Method to count the occurrences of each genre in the list of artists
    public Map<Genre, Integer> getGenreCountFromResponse(ObservableList<Artist> artists) {
        HashMap<Genre, Integer> genreCount = new HashMap<>();
        for (Artist artist : artists) {
            ObservableList<Genre> genres = (ObservableList<Genre>) artist.getGenres();
            for (int i = 0; i < genres.size(); i++) {
                Genre genre = genres.get(i);
                genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
            }
        }

        // Sort and return the genre count map
        Map<Genre, Integer> sortedGenreMap = MapValueSorter.sortByValuesDescendingWithAlphabetical(genreCount);
//        for (Map.Entry<Genre, Integer> entry : sortedGenreMap.entrySet()) {
//            System.out.println(entry.getKey() + " is found " + entry.getValue() + " times in your listening history!");
//        }
        return sortedGenreMap;
    }
}
