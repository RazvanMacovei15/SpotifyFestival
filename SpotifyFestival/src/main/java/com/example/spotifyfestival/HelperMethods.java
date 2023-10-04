package com.example.spotifyfestival;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelperMethods {



    SpotifyAPPCredentials spotifyAPPCredentials = new SpotifyAPPCredentials();
    public static void switchScene(ActionEvent event, String sceneFXML) throws IOException {

        FXMLLoader loader = new FXMLLoader(HelperMethods.class.getResource(sceneFXML));
        Parent sceneRoot = loader.load();

        double sceneWidth = 360; // Set your desired width
        double sceneHeight = 720; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight);
        Stage stage = App.getPrimaryStage();
        // Get the Stage
        stage.setScene(scene);
        stage.show();
    }

//    public static void switchSceneToken(AccessTokenEvent event, String sceneFXML) throws IOException {
//
//        FXMLLoader loader = new FXMLLoader(HelperMethods.class.getResource(sceneFXML));
//        Parent sceneRoot = loader.load();
//
//        double sceneWidth = 360; // Set your desired width
//        double sceneHeight = 720; // Set your desired height
//        Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight);
//        Stage stage = App.getPrimaryStage();
//        // Get the Stage
//        stage.setScene(scene);
//        stage.show();
//    }

    public void createLoginSimulation() throws Exception {

        Stage stage = App.getPrimaryStage();

        int statusCode = 200;

        // Check the login status code
        boolean isValidLogin;
        isValidLogin = statusCode == 200;

        if (isValidLogin) {
            // If the login is successful, transition to the awaiting confirmation scene
            try {
                Parent root = FXMLLoader.load(getClass().getResource("awaitConfirmation.fxml"));
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        } else {
            // Display an error message for invalid login
//            System.out.println("Error code: " + spotifyAuthFlowService.getStatusCode1());
            showErrorDialog();
        }
    }

    public void simulateConfirmation() {

        Stage stage = App.getPrimaryStage();
        // Simulate a confirmation process with a delay
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulated confirmation delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Run on the JavaFX application thread to update the UI
            javafx.application.Platform.runLater(() -> {
                // Transition to the desired scene after confirmation
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("afterLoginScreen.fxml"));
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();
    }

    public void showErrorDialog() {
        // Display an error dialog for invalid login
//        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
//        alert.setTitle("Login Error");
//        alert.showAndWait();
        System.out.println("Error!!");
    }

}
