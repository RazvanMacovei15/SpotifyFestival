package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.Cache;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.CacheFileRepo;
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

public class TopArtistsController {
    private Cache cache;
    private boolean loadFromCache;
    private ObservableList<Entity> obsArtists;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    private ImageView imageView;
    private final String imageURL ="/com/example/spotifyfestival/PNGs/copertaSpotify.png";

    @FXML
    public void initialize() throws JsonProcessingException {
        Helper.loadSpotifyCover(imageView, imageURL);
        cache = Cache.getInstance();
        // Automatically trigger the "4 weeks" button when the scene is shown
        on4WeeksButtonClicked();
    }

    public void newService(int limit, String timeRange, int offset) {
        if(loadFromCache){
            //load from cache

        }else{
            //load from api and save to cache
            ObservableList<Artist> artists = getObsList(limit, timeRange, offset);
            // Populate the scroll pane with the top tracks
            Utils.populateScrollPaneWithArtists(scrollPane, artists);
            // Save the top tracks to the cache
            saveToCache(timeRange, obsArtists);
        }
    }

    private void getListFromCache(String filename){
        CacheFileRepo<String, Entity> cacheFileRepo = new TopArtists(filename);
    }

    private void resetLoad(){
        loadFromCache = false;
    }

    private ObservableList<Artist> getObsList(int limit, String timeRange, int offset){
        // Get the access token from the SpotifyAuthFlowService
        SpotifyAuthFlowService auth = SpotifyAuthFlowService.getInstance();
        String accessToken = auth.getAccessToken();
        // Create a new SpotifyResponseService and SpotifyAPIJsonParser
        SpotifyResponseService service = new SpotifyResponseService(accessToken);
        SpotifyAPIJsonParser parser = new SpotifyAPIJsonParser();
        // Get the top tracks from the Spotify API
        HttpResponse<String> topArtists = service.getTopArtists(limit, timeRange, offset);
        ObservableList<Artist> entities = FXCollections.observableArrayList();
        entities.addAll(parser.getTopArtists(topArtists));
        return entities;
    }

    private void saveToCache(String timeRange, ObservableList<Entity> artists){
        switch (timeRange){
            case "long_term":
                TopArtists artistsLong = (TopArtists) cache.getLongTermArtists();
                artistsLong.initializeFile(artists);
                break;
            case "medium_term":
                TopArtists artistsTop = (TopArtists) cache.getMediumTermArtists();
                artistsTop.initializeFile(artists);
                break;
            case "short_term":
                TopArtists artistsShort = (TopArtists) cache.getShortTermArtists();
                artistsShort.initializeFile(artists);
                break;
        }
    }

    public void onAllTimeButtonClicked() {
        scrollPane.setContent(null);
        resetLoad();
        loadFromCache = cache.checkIfCacheExists("long_term_artists.txt");
        obsArtists.addAll(getObsList(50, "long_term", 0));

        new Thread(()->{
            newService(50, "long_term", 0);
        }).start();
    }

    public void on6MonthsButtonClicked() {
        scrollPane.setContent(null);
        resetLoad();
        loadFromCache = cache.checkIfCacheExists("medium_term_artists.txt");
        new Thread(()->{
            newService(50, "medium_term", 0);
        }).start();
    }

    public void on4WeeksButtonClicked() {
        scrollPane.setContent(null);
        resetLoad();
        loadFromCache = cache.checkIfCacheExists("short_term_artists.txt");
        new Thread(()->{
            newService(50, "short_term", 0);
        }).start();

    }

    public void getBackToTopLists() {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
    }
}