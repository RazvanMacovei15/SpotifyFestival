package com.example.spotifyfestival.UnusedStuffForNow;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

public class ToDoListApp extends Application {

    private ArrayList<String> toDoList = new ArrayList<>();
    private ListView<String> listView = new ListView<>();
    private int currentIndex = 0;


    @Override
    public void start(Stage primaryStage) {
        // Initialize your to-do list (add some items for testing)
        toDoList.add("Task 1");
        toDoList.add("Task 2");
        toDoList.add("Task 3");

        // Set up the JavaFX stage and scene
        primaryStage.setTitle("To-Do List App");
        Scene scene = new Scene(listView, 300, 200);
        primaryStage.setScene(scene);

        // Display the stage
        primaryStage.show();

        // Set up the Timeline to update the list view with a delay
        Timeline timeline = new Timeline();
        for (String item : toDoList) {
            KeyFrame keyFrame = new KeyFrame(
                    Duration.seconds(currentIndex),
                    event -> {
                        listView.getItems().add(item);
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
            currentIndex++;
        }

        // Play the timeline
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
