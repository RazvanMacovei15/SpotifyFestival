package com.example.spotifyfestival.main;

import com.example.spotifyfestival.api.spotifyapi.SpotifyAuthFlowService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        App.primaryStage = primaryStage;

        URL fxmlLocation = getClass().getResource("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/MainScreen.fxml");
        assert fxmlLocation != null;
        Parent root = null;
        try {
            root = FXMLLoader.load(fxmlLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double sceneWidth = 300; // Set your desired width
        double sceneHeight = 600; // Set your desired height

        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.show();
    }

    @Override
    public void init() {
        SpotifyAuthFlowService authFlowService = SpotifyAuthFlowService.getInstance();
        authFlowService.run();
    }

    @Override
    public void stop(){
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}