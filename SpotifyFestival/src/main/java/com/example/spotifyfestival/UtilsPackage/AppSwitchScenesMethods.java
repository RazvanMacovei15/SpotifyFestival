package com.example.spotifyfestival.UtilsPackage;

import com.example.spotifyfestival.MainPackage.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class AppSwitchScenesMethods {
    public static void switchScene(ActionEvent event, String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(AppSwitchScenesMethods.class.getResource(path));

        Parent sceneRoot = loader.load();

        double sceneWidth = 400; // Set your desired width
        double sceneHeight = 600; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }
    public static void switchSceneForCanvas(ActionEvent event, String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(AppSwitchScenesMethods.class.getResource(path));

        Parent sceneRoot = loader.load();

        double sceneWidth = 1000; // Set your desired width
        double sceneHeight = 600; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth,sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }
    public static void switchSceneForDatabase(ActionEvent event, String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(AppSwitchScenesMethods.class.getResource(path));

        Parent sceneRoot = loader.load();

        double sceneWidth = 1000; // Set your desired width
        double sceneHeight = 600; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth,sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }
    public static void switchSceneTwoForDatabase(String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(AppSwitchScenesMethods.class.getResource(path));

        Parent sceneRoot = loader.load();

        double sceneWidth = 1000; // Set your desired width
        double sceneHeight = 600; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth,sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }

    public static void switchSceneTwo(String sceneFXML) throws IOException {

        FXMLLoader loader = new FXMLLoader(AppSwitchScenesMethods.class.getResource(sceneFXML));
        Parent sceneRoot = loader.load();

        double sceneWidth = 400; // Set your desired width
        double sceneHeight = 600; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }
}
