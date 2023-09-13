package com.example.spotifyfestival;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HelperMethods {
    public static void switchScene(ActionEvent event, String sceneFXML) throws IOException {

        FXMLLoader loader = new FXMLLoader(HelperMethods.class.getResource(sceneFXML));
        Parent sceneRoot = loader.load();

        double sceneWidth = 360; // Set your desired width
        double sceneHeight = 720; // Set your desired height
        Scene scene = new Scene(sceneRoot, sceneWidth, sceneHeight);

        // Get the Stage
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public String getAlphaNumericString(int n)
    {
        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    public String generateLoginURL(String STATE) throws UnsupportedEncodingException {

        String encodedScope = URLEncoder.encode(SpotifyAPPCredentials.getUserTopReadScope(), StandardCharsets.UTF_8.toString());
        String encodedState = URLEncoder.encode(STATE, StandardCharsets.UTF_8.toString());

        return "https://accounts.spotify.com/authorize?" +
                "response_type=code" +
                "&client_id=" + SpotifyAPPCredentials.getClientId() +
                "&scope=" + encodedScope +
                "&redirect_uri=" + URLEncoder.encode(SpotifyAPPCredentials.getRedirectUri(), StandardCharsets.UTF_8.toString()) +
                "&state=" + encodedState;
    }

    public void openURL2(String url) {
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                try {
                    java.net.URI uri = new java.net.URI(url);
                    desktop.browse(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
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
//            System.out.println("Error code: " + spotifyAPI.getStatusCode1());
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
