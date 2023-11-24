
package com.example.spotifyfestival.MainPackage;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.UnusedStuffForNow.helperObsLis.AccessTokenObserver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        double sceneWidth = 360; // Set your desired width
        double sceneHeight = 720; // Set your desired height
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.show();
    }

    @Override
    public void init() {
        SpotifyAuthFlowService authFlowService = SpotifyAuthFlowService.getInstance();
        AccessTokenObserver accessTokenObserver = new AccessTokenObserver();
        authFlowService.addObserver(accessTokenObserver);
        authFlowService.run();
    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}