package com.example.spotifyfestival.UIPackage.HelperClasses;

import com.example.spotifyfestival.MainPackage.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class AppSwitchScenesMethods {
    public static void switchScene(String path){

        FXMLLoader loader = new FXMLLoader(AppSwitchScenesMethods.class.getResource(path));

        Parent sceneRoot = null;
        try {
            sceneRoot = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double sceneWidth = 300; // Set your desired width
        double sceneHeight = 600; // Set your desired height

        Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }

    public static void switchSceneDatabase(String path) {

        FXMLLoader loader = new FXMLLoader(AppSwitchScenesMethods.class.getResource(path));

        Parent sceneRoot = null;
        try {
            sceneRoot = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double sceneWidth = 1000; // Set your desired width
        double sceneHeight = 600; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth,sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }
}
