package com.example.spotifyfestival.UIPackage;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.NewFeatures.SpotifyAPIJsonParser;
import com.example.spotifyfestival.NewFeatures.SpotifyResponseService;
import com.example.spotifyfestival.UIPackage.HelperClasses.AppSwitchScenesMethods;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.http.HttpResponse;
import java.util.List;

public class AdminMainController {
    private final String imageURL = "/com/example/spotifyfestival/PNGs/coperta_3.jpeg";
    @FXML
    GridPane mainGridPane;
    @FXML
    Button admin;
    @FXML
    Button festivals;
    @FXML
    Button topLists;
    @FXML
    VBox vBox;
    @FXML
    ImageView imageView;

    public void initialize() {

        Helper.loadCover(imageView, imageURL);
        Helper.mouseHoverUpOnButton(admin);
        Helper.mouseHoverUpOnButton(festivals);
        Helper.mouseHoverUpOnButton(topLists);

    }

    public void handleDBButton() {
        //for testing purposes
        String accessToken = SpotifyAuthFlowService.getInstance().getAccessToken();
        SpotifyResponseService spotifyResponseService = new SpotifyResponseService(accessToken);
        SpotifyAPIJsonParser spotifyAPIJsonParser = new SpotifyAPIJsonParser();
        HttpResponse<String> response = spotifyResponseService.getTopArtists(10, "short_term", 0);
        List<Artist> topArtists = spotifyAPIJsonParser.getTopArtists(response);
        for (Artist artist : topArtists) {
            System.out.println(artist);
        }
        System.out.println("Access token: " + accessToken);
        long startTime = SpotifyAuthFlowService.getInstance().getStartTime();
        long endTime = System.currentTimeMillis();
        System.out.println("App started " + ((endTime - startTime) / 1000) + " seconds ago");
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/MainDatabaseScene.fxml");
    }

    public void onTopListsButtonClicked() {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
    }

    public void onGetFestivalSuggestionButtonClicked() {
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/ConcertCanvas/CanvasScene.fxml");
    }
}