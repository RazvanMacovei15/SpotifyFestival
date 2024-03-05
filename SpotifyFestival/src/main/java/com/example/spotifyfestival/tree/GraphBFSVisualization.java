package com.example.spotifyfestival.tree;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GraphBFSVisualization extends Application {

    private Canvas canvas;
    private GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(400, 400);
        gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("Graph BFS Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Perform BFS in a background task
        Task<Void> bfsTask = createBFSTask();
        new Thread(bfsTask).start();
    }

    private Task<Void> createBFSTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                // Your BFS algorithm logic goes here
                // For demonstration, let's just update UI in a loop
                for (int i = 0; i < 10; i++) {
                    final int index = i;
                    Platform.runLater(() -> {
                        // Update UI (e.g., highlight nodes or edges)
                        updateUI(index);
                    });

                    try {
                        // Simulate some work being done in the background
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
    }

    private void updateUI(int index) {
        // Implement your UI update logic here
        // For demonstration, let's just draw a circle on the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;
        double radius = 20;
        gc.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        gc.fillText("Step: " + index, 10, 20);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
