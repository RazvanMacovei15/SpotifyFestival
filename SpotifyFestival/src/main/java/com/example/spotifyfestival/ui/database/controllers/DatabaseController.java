package com.example.spotifyfestival.ui.database.controllers;

import com.example.spotifyfestival.ui.helper.classes.AppSwitchScenesMethods;
import com.example.spotifyfestival.ui.helper.classes.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class DatabaseController {
    private String imageURL = "/com/example/spotifyfestival/PNGs/coperta_3.jpeg";

    @FXML
    protected Button artistsButton;
    @FXML
    protected Button venuesButton;
    @FXML
    protected Button concertButton;
    @FXML
    protected Button artistGenreButton;
    @FXML
    protected Button genresButton;
    @FXML
    protected Button festivalsButton;
    @FXML
    protected Button stagesButton;
    @FXML
    protected Button backButton;

    @FXML
    ImageView imageView;

    @FXML
    public void initialize() {
        Helper.loadSpotifyCover(imageView, imageURL);
    }

    public void onArtistButtonClicked() {
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ArtistsDB.fxml");
    }

    public void onGenres(){
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/GenresDB.fxml");

    }
    public void onArtistGenres(){
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ArtistsGenresDB.fxml");

    }
    public void onVenues(){
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/VenuesDB.fxml");
    }
    public void onFestivals(){
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/FestivalsDB.fxml");

    }
    public void onStages(){
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/StagesDB.fxml");

    }
    public void onConcerts(){
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/ConcertsDB.fxml");

    }
    public void back() {
        Helper.backToMainPageCondition();
    }

}
