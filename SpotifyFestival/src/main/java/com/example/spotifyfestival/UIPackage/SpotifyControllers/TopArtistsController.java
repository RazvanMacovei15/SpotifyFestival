package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;
import com.example.spotifyfestival.NewFeatures.SpotifyAPIJsonParser;
import com.example.spotifyfestival.NewFeatures.SpotifyResponseService;
import com.example.spotifyfestival.NewFeatures.Utils;
import com.example.spotifyfestival.UIPackage.HelperClasses.AppSwitchScenesMethods;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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