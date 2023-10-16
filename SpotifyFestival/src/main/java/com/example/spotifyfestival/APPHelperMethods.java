package com.example.spotifyfestival;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class APPHelperMethods {
    public static void switchScene(ActionEvent event, String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(APPHelperMethods.class.getResource(path));

        Parent sceneRoot = loader.load();

        double sceneWidth = 360; // Set your desired width
        double sceneHeight = 720; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }
    public static void switchSceneForCanvas(ActionEvent event, String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(APPHelperMethods.class.getResource(path));

        Parent sceneRoot = loader.load();

        double sceneWidth = 800; // Set your desired width
        double sceneHeight = 800; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth,sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }

    public static void switchSceneTwo(String sceneFXML) throws IOException {

        FXMLLoader loader = new FXMLLoader(APPHelperMethods.class.getResource(sceneFXML));
        Parent sceneRoot = loader.load();

        double sceneWidth = 360; // Set your desired width
        double sceneHeight = 720; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }
}
