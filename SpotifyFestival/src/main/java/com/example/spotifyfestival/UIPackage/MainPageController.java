package com.example.spotifyfestival.UIPackage;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class MainPageController {

    @FXML
    public ImageView image;
    @FXML
    public Label label;
    public void initialize(){
        // Load the image from resources
        Image imageS = new Image(getClass().getResource("/com/example/spotifyfestival/PNGs/SpotifyLogo.png").toExternalForm());

        // Set the image to the ImageView
        image.setImage(imageS);
    }
    @FXML
    private void handleLoginButtonClick() {
        SpotifyAuthFlowService.getInstance().login();
        label.setText("STATUS: LOGGING IN PROGRESS...");
    }
}
