package com.example.spotifyfestival.UIPackage;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.Cache;
import com.example.spotifyfestival.UIPackage.HelperClasses.AppSwitchScenesMethods;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
    private ObservableList<Artist> artists;
    private Cache cache;

    public void initialize() {

        Helper.loadCover(imageView, imageURL);
        Helper.mouseHoverUpOnButton(admin);
        Helper.mouseHoverUpOnButton(festivals);
        Helper.mouseHoverUpOnButton(topLists);
        cache = Cache.getInstance();

    }

    public void handleDBButton() {
        cache.listAll();
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/MainDatabaseScene.fxml");
    }

    public void onTopListsButtonClicked() {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
    }

    public void onGetFestivalSuggestionButtonClicked() {
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/ConcertCanvas/CanvasScene.fxml");
    }

    //TODO - implement cache system for TOP LISTS Functionality so it doesn't make a request every time
    //TODO - redesign the Database with everything new I learned so far this year by not braking coding concepts
    //TODO - go back to the concert API Call and simplify the code as I did for the Spotify API
    //TODO - implement multithreading for the "THIS IS THE WAY" functionality so it doesn't freeze the UI
}