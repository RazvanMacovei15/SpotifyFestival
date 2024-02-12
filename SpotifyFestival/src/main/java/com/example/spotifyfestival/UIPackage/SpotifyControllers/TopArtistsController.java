package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.NewFeatures.SpotifyAPIJsonParser;
import com.example.spotifyfestival.NewFeatures.SpotifyResponseService;
import com.example.spotifyfestival.NewFeatures.Utils;
import com.example.spotifyfestival.UIPackage.HelperClasses.AppSwitchScenesMethods;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final String imageURL ="/com/example/spotifyfestival/PNGs/copertaSpotify.png";

    @FXML
    public void initialize() throws JsonProcessingException {
        Helper.loadSpotifyCover(imageView, imageURL);

        // Automatically trigger the "4 weeks" button when the scene is shown
        on4WeeksButtonClicked();
    }

    public void onAllTimeButtonClicked() {
        scrollPane.setContent(null);
        new Thread(()->{
            newService(50, "long_term", 0);
        }).start();
    }

    public void on6MonthsButtonClicked() {
        scrollPane.setContent(null);
        new Thread(()->{
            newService(50, "medium_term", 0);
        }).start();
    }

    public void on4WeeksButtonClicked() {
        scrollPane.setContent(null);
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
        // Get the top tracks from the Spotify API
        HttpResponse<String> topArtists = service.getTopArtists(limit, timeRange, offset);
        if(topArtists == null){
            return;
        }
        ObservableList<Artist> artists = parser.getTopArtists(topArtists);
        // Populate the scroll pane with the top tracks
        Utils.populateScrollPaneWithArtists(scrollPane, artists);
    }

    public void getBackToTopLists() {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
    }
}