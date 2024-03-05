package com.example.spotifyfestival.ui;

import com.example.spotifyfestival.api.spotifyapi.SpotifyAuthFlowService;

import com.example.spotifyfestival.ui.helper.classes.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MainPageController {
    private final String imageURL = "/com/example/spotifyfestival/PNGs/coperta_2.jpeg";

    @FXML
    public ImageView imageView;
    @FXML
    public Label label;
    public void initialize(){
        Helper.loadCover(imageView, imageURL);

    }

    @FXML
    private void handleLoginButtonClick() {
        SpotifyAuthFlowService.getInstance().login();
        label.setText("STATUS: LOGGING IN PROGRESS...");
    }
}
