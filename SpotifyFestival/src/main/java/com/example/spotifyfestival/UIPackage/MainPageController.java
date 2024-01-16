package com.example.spotifyfestival.UIPackage;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
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
        Helper.loadCover(image);
    }

    @FXML
    private void handleLoginButtonClick() {
        SpotifyAuthFlowService.getInstance().login();
        label.setText("STATUS: LOGGING IN PROGRESS...");
    }
}
