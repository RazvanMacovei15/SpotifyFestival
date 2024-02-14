package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.Cache;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.TopArtists.TopArtists;
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
    private Cache cache;
    private boolean loadFromCache;
    private ObservableList<Entity> obsArtists;
    private String timeRange;
    private String filename;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    private ImageView imageView;
    private final String imageURL = "/com/example/spotifyfestival/PNGs/copertaSpotify.png";

    @FXML
    public void initialize() throws JsonProcessingException {
        Helper.loadSpotifyCover(imageView, imageURL);
        cache = Cache.getInstance();
        // Automatically trigger the "4 weeks" button when the scene is shown
        on4WeeksButtonClicked();
    }

    public void newService(int limit, String timeRange, int offset) {
        if (loadFromCache) {
            Iterable<Entity> artists = cache.getCache(filename);
            ObservableList<Artist> obsArtists = FXCollections.observableArrayList();
            ObservableList<Artist> obsArtistsTwo = FXCollections.observableArrayList();
            for(Entity artist : artists){
                obsArtists.add((Artist) artist);
            }
            List<Entity> myList = obsArtists.stream()
                            .sorted(Comparator.comparing(Entity::getId))
                                    .collect(Collectors.toList());

            for(Entity artist : myList){
                obsArtistsTwo.add((Artist) artist);
            }

            Utils.populateScrollPaneWithArtists(scrollPane, obsArtistsTwo);
        } else {
            ObservableList<Artist> artists = FXCollections.observableArrayList();
            artists = generateObservableListForScrollPane(limit, timeRange, offset);
            ObservableList<Entity> obsArtists = FXCollections.observableArrayList();
            obsArtists.addAll(artists);
            saveToCache(timeRange, obsArtists);
            Utils.populateScrollPaneWithArtists(scrollPane, artists);
        }
    }

    private void resetLoad() {
        loadFromCache = false;
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

    private void saveToCache(String timeRange, ObservableList<Entity> artists) {
        switch (timeRange) {
            case "long_term":
                cache.initializeFileCache("long_term_artists.txt", artists);
                break;
            case "medium_term":
                cache.initializeFileCache("medium_term_artists.txt", artists);
                break;
            case "short_term":
                cache.initializeFileCache("short_term_artists.txt", artists);
                break;
        }
    }

    public void onAllTimeButtonClicked() {
        scrollPane.setContent(null);
        filename = "long_term_artists.txt";
        resetLoad();
        timeRange = "long_term";
        loadFromCache = cache.checkIfCacheExists("long_term_artists.txt");
        new Thread(() -> {
            newService(50, "long_term", 0);
        }).start();
    }

    public void on6MonthsButtonClicked() {
        scrollPane.setContent(null);
        filename = "medium_term_artists.txt";
        resetLoad();
        timeRange = "medium_term";
        loadFromCache = cache.checkIfCacheExists("medium_term_artists.txt");
        new Thread(() -> {
            newService(50, "medium_term", 0);
        }).start();
    }

    public void on4WeeksButtonClicked() {
        scrollPane.setContent(null);
        filename = "short_term_artists.txt";
        resetLoad();
        timeRange = "short_term";
        loadFromCache = cache.checkIfCacheExists("short_term_artists.txt");

        new Thread(() -> {
            newService(50, "short_term", 0);

        }).start();

    }

    public void getBackToTopLists() {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
    }
}