package com.example.spotifyfestival.UIPackage;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class MainPageController {

    @FXML
    public ImageView image;
    public void initialize(){
    }
    @FXML
    private void handleLoginButtonClick() {
        SpotifyAuthFlowService.getInstance().login();
    }
}
