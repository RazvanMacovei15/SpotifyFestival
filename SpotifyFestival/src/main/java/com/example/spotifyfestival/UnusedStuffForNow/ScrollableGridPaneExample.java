package com.example.spotifyfestival.UnusedStuffForNow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ScrollableGridPaneExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Scrollable GridPane Example");

        // Create a GridPane with a larger size than the visible window
        GridPane gridPane = createGridPane();

        // Place the GridPane inside a ScrollPane
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 400, 100);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        // Populate your GridPane with elements as needed

        // For example, adding labels to demonstrate the height
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                gridPane.add(new javafx.scene.control.Label("Cell " + i + ", " + j), j, i);
            }
        }

        return gridPane;
    }
}
