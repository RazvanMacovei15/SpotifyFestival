package com.example.spotifyfestival.NewFeatures;

import com.example.spotifyfestival.API_Packages.APIServices.SpotifyService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Track;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class Utils {
    public static void populateScrollPaneWithTracks(ScrollPane scrollPane, ObservableList<Track> topTracks){
        GridPane gridPane = new GridPane();

        Platform.runLater(()->{
            scrollPane.setContent(gridPane);

            gridPane.setGridLinesVisible(false);
            gridPane.setAlignment(Pos.CENTER);

            // Ensure scroll bars are displayed only if needed
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        });

        int row = 0;
        int col = 0;
        for (Track track : topTracks) {

            String url = track.getImageURL();

            // Load an image
            Image originalImage = new Image(url);

            // Create an ImageView with the image
            ImageView imageView = new ImageView(originalImage);

            // Set the desired width and height to scale down the image
            double scaledWidth = 50;
            double scaledHeight = 50;

            // Set the fitWidth and fitHeight properties to scale the image
            imageView.setFitWidth(scaledWidth);
            imageView.setFitHeight(scaledHeight);

            Text textID = new Text(track.getId().toString() + ".");
            Text textName = new Text(track.toString());

            HBox hBox = new HBox();

            textID.setWrappingWidth(50);
            textName.setWrappingWidth(200);
            hBox.minWidth(300);
            hBox.minHeight(100);
            imageView.setOnMouseClicked(event -> {
                openURL(track.getSpotifyLink());
            });

            textID.setTextAlignment(TextAlignment.CENTER); // Center-align the text
            textName.setTextAlignment(TextAlignment.CENTER); // Center-align the text

            hBox.getChildren().addAll(textID, imageView, textName);
            hBox.setAlignment(Pos.CENTER);
            int finalRow = row;
            Platform.runLater(()->{
                gridPane.add(hBox, col, finalRow);
                gridPane.getRowConstraints().add(new RowConstraints(70));
            });
            row++;
        }
    }

    public static void populateScrollPaneWithArtists(ScrollPane scrollPane, ObservableList<Artist> artists) {
        GridPane gridPane = new GridPane();
        Platform.runLater(()->{
            scrollPane.setContent(gridPane);

            gridPane.setGridLinesVisible(false);
            gridPane.setAlignment(Pos.CENTER);

            // Ensure scroll bars are displayed only if needed
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        });

        for (int i = 0; i < 3; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(100));
        }

        int row = 0;
        int col = 0;
        for (Artist artist : artists) {

            String url = artist.getImageUrl();

            // Load an image
            Image originalImage = new Image(url);

            // Create an ImageView with the image
            ImageView imageView = new ImageView(originalImage);

            // Set the desired width and height to scale down the image
            double scaledWidth = 50;
            double scaledHeight = 50;

            // Set the fitWidth and fitHeight properties to scale the image
            imageView.setFitWidth(scaledWidth);
            imageView.setFitHeight(scaledHeight);

            Text text = new Text(artist.getId() + ". " + artist.getName());

            VBox vBox = new VBox();

            text.setWrappingWidth(100);
            vBox.minWidth(100);
            vBox.minHeight(100);
            imageView.setOnMouseClicked(event -> {
                openURL(artist.getSpotifyLink());
            });

            vBox.getChildren().add(imageView);
            text.setTextAlignment(TextAlignment.CENTER); // Center-align the text

            vBox.getChildren().add(text);
            vBox.setAlignment(Pos.CENTER);
            int finalCol = col;
            int finalRow = row;
            Platform.runLater(() -> {
                gridPane.add(vBox, finalCol, finalRow);
            });

            col++;

            int tempFinalCol = col;

            if (col == 3) {
                col = 0;
                gridPane.getRowConstraints().add(new RowConstraints(100));
                row++;
            }
        }

    }

    public static void openURL(String url) {
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

}
