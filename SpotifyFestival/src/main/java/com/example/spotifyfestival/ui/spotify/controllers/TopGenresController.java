// Import statements for necessary packages and classes
package com.example.spotifyfestival.ui.spotify.controllers;

import com.example.spotifyfestival.api.spotify.SpotifyAuthFlowService;
import com.example.spotifyfestival.database.entities.pojo.Artist;
import com.example.spotifyfestival.database.entities.pojo.Genre;
import com.example.spotifyfestival.newfeatures.SpotifyAPIJsonParser;
import com.example.spotifyfestival.newfeatures.SpotifyResponseService;
import com.example.spotifyfestival.newfeatures.Utils;
import com.example.spotifyfestival.ui.helper.classes.AppSwitchScenesMethods;
import com.example.spotifyfestival.ui.helper.classes.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

import java.net.http.HttpResponse;
import java.util.Map;


// Controller class for the TopGenres FXML file
public class TopGenresController {
    @FXML
    ImageView imageView;

    private final String imageURL = "/com/example/spotifyfestival/PNGs/copertaSpotify.png";

    // FXML annotations to inject UI elements
    @FXML
    private ListView<String> listView;

    // Initialize method, automatically triggered when the scene is shown
    @FXML
    public void initialize() throws JsonProcessingException {
        Helper.loadSpotifyCover(imageView, imageURL);
        // Automatically trigger the "4 weeks" button when the scene is shown
        on4WeeksButtonClicked();
    }

    // Event handler for the "Get Back" button
    public void onGetBackButtonClicked() {
        // Switch scene to adminMainScreen.fxml
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
    }

    // Event handler for the "All Time" button
    public void onAllTimeButtonClicked(){
        listView.setItems(null);
        new Thread(()->{
            newService(50, "long_term", 0);
        }).start();
    }

    // Event handler for the "6 Months" button
    public void on6MonthsButtonClicked() throws JsonProcessingException {
        listView.setItems(null);
        new Thread(()->{
            newService(50, "medium_term", 0);
        }).start();
    }

    // Event handler for the "4 Weeks" button
    public void on4WeeksButtonClicked() throws JsonProcessingException {
        listView.setItems(null);
        new Thread(()->{
            newService(50, "short_term", 0);
        }).start();

    }

    public void newService(int limit, String timeRange, int offset) {
        // Get the access token from the SpotifyAuthFlowService
        SpotifyAuthFlowService auth = SpotifyAuthFlowService.getInstance();
        String accessToken = auth.getAccessToken();
        // Create a new SpotifyResponseService and SpotifyAPIJsonParser
        SpotifyResponseService service = new SpotifyResponseService(accessToken);
        SpotifyAPIJsonParser parser = new SpotifyAPIJsonParser();

        // Get the top artists from the Spotify API
        HttpResponse<String> topArtists = service.getTopArtists(limit, timeRange, offset);
        ObservableList<Artist> artists = parser.getTopArtists(topArtists);

        Map<Genre, Integer> genreCount = Utils.getGenreCountFromResponse(artists);

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
        Platform.runLater(()->{
            listView.setItems(genreNames);
        });
    }
}
