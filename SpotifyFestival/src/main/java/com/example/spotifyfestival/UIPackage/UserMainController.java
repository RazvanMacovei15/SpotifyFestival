package com.example.spotifyfestival.UIPackage;

import com.example.spotifyfestival.UIPackage.HelperClasses.AppSwitchScenesMethods;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UserMainController {

    private final String imageURL = "/com/example/spotifyfestival/PNGs/coperta_2.jpeg";
    @FXML
    GridPane mainGridPane;

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
        Helper.mouseHoverUpOnButton(festivals);
        Helper.mouseHoverUpOnButton(topLists);

    }


    public void onTopListsButtonClicked() {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
    }

    public void onGetFestivalSuggestionButtonClicked() {
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/ConcertCanvas/CanvasScene.fxml");
    }
}