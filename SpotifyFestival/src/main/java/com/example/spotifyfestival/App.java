package com.example.spotifyfestival;

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

        this.primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        double sceneWidth = 360; // Set your desired width
        double sceneHeight = 720; // Set your desired height
        primaryStage.setScene(new Scene(root, sceneWidth,sceneHeight));
        primaryStage.show();
    }
    public static void main(String[] args) throws Exception {

        launch();

    }
}