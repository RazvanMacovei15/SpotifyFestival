package com.example.spotifyfestival.ui.spotify.controllers;

import com.example.spotifyfestival.api.spotifyapi.SpotifyAuthFlowService;
import com.example.spotifyfestival.database.entities.pojo.Artist;
import com.example.spotifyfestival.database.entities.pojo.Entity;
import com.example.spotifyfestival.newfeatures.SpotifyAPIJsonParser;
import com.example.spotifyfestival.newfeatures.SpotifyResponseService;
import com.example.spotifyfestival.newfeatures.Utils;
import com.example.spotifyfestival.ui.helper.classes.AppSwitchScenesMethods;
import com.example.spotifyfestival.ui.helper.classes.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

import java.net.http.HttpResponse;

public class TopArtistsController {
    @FXML
    public ScrollPane scrollPane;
    @FXML
    private ImageView imageView;
    private final String imageURL = "/com/example/spotifyfestival/PNGs/copertaSpotify.png";

    @FXML
    public void initialize() throws JsonProcessingException {
        Helper.loadSpotifyCover(imageView, imageURL);
        // Automatically trigger the "4 weeks" button when the scene is shown
        on4WeeksButtonClicked();
    }

    public void newService(int limit, String timeRange, int offset) {
        ObservableList<Artist> artists = FXCollections.observableArrayList();
        artists = generateObservableListForScrollPane(limit, timeRange, offset);
        ObservableList<Entity> obsArtists = FXCollections.observableArrayList();
        obsArtists.addAll(artists);

        Utils.populateScrollPaneWithArtists(scrollPane, artists);

    }

    private ObservableList<Artist> generateObservableListForScrollPane(int limit, String timeRange, int offset) {
        // Get the access token from the SpotifyAuthFlowService
        SpotifyAuthFlowService auth = SpotifyAuthFlowService.getInstance();
        String accessToken = auth.getAccessToken();
        // Create a new SpotifyResponseService and SpotifyAPIJsonParser
        SpotifyResponseService service = new SpotifyResponseService(accessToken);
        SpotifyAPIJsonParser parser = new SpotifyAPIJsonParser();
        // Get the top tracks from the Spotify API
        HttpResponse<String> topArtists = service.getTopArtists(limit, timeRange, offset);
        ObservableList<Artist> artists = FXCollections.observableArrayList();

        artists.addAll(parser.getTopArtists(topArtists));
        return artists;
    }

    public void onAllTimeButtonClicked() {
        scrollPane.setContent(null);

        new Thread(() -> {
            newService(50, "long_term", 0);
        }).start();
    }

    public void on6MonthsButtonClicked() {
        scrollPane.setContent(null);

        new Thread(() -> {
            newService(50, "medium_term", 0);
        }).start();
    }

    public void on4WeeksButtonClicked() {
        scrollPane.setContent(null);


        new Thread(() -> {
            newService(50, "short_term", 0);

        }).start();

    }

    public void getBackToTopLists() {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
    }
}