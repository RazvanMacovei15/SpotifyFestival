package com.example.spotifyfestival;
import com.example.spotifyfestival.HelperMethods;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class App extends Application {

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) throws Exception {

        launch();

    }
}