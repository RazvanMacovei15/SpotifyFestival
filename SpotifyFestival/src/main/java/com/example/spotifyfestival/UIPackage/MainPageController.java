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
        Image imageS = new Image(getClass().getResource("/com/example/spotifyfestival/PNGs/forApp.jpeg").toExternalForm());

        // Set the desired width and height to scale down the image
        double scaledWidth = 300;
        double scaledHeight = 120;

        // Set the fitWidth and fitHeight properties to scale the image
        image.setFitWidth(scaledWidth);
        image.setFitHeight(scaledHeight);
        // Set the image to the ImageView
        image.setImage(imageS);
    }
    @FXML
    private void handleLoginButtonClick() {
        SpotifyAuthFlowService.getInstance().login();
        label.setText("STATUS: LOGGING IN PROGRESS...");
    }
}
