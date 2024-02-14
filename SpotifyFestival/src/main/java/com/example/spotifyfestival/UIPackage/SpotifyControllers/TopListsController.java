package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.UIPackage.HelperClasses.AppSwitchScenesMethods;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class TopListsController {
    private final String imageURL = "/com/example/spotifyfestival/PNGs/copertaSpotify.png";
    @FXML
    ImageView imageView;
    @FXML
    Button artists;
    @FXML
    Button tracks;
    @FXML
    Button genres;

    public void initialize(){
        Helper.loadSpotifyCover(imageView, imageURL);
        Helper.mouseHoverUpOnButton(artists);
        Helper.mouseHoverUpOnButton(tracks);
        Helper.mouseHoverUpOnButton(genres);

    }
    public void onTopArtistsButtonClicked() {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopArtists.fxml");
    }

    public void onTopTracksButtonClicked() {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopTracks.fxml");
    }

    public void onTopGenresButtonClicked() {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopGenres.fxml");
    }
    public void onGetBackButtonClicked() {
        Helper.backToMainPageCondition();
    }

}
