package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.APIServices.SpotifyService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Track;
import com.example.spotifyfestival.UIPackage.HelperClasses.AppSwitchScenesMethods;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class TopTracksController {
    private final String imageURL = "/com/example/spotifyfestival/PNGs/copertaSpotify.png";

    @FXML
    ImageView imageView;

    protected SpotifyService service;

    @FXML
    protected ScrollPane scrollPane;

    public TopTracksController() {
    }

    @FXML
    public void initialize() {
        Helper.loadSpotifyCover(imageView, imageURL);
        service = new SpotifyService();
        // Automatically trigger the "4 weeks" button when the scene is shown
        on4WeeksButtonClicked();
    }

    public void populateScrollPaneWithTracks(ScrollPane scrollPane, String response) {
        scrollPane.setContent(null);
        GridPane gridPane = new GridPane();

        service = new SpotifyService();
        scrollPane.setContent(gridPane);

        gridPane.setGridLinesVisible(false);
        gridPane.setAlignment(Pos.CENTER);

        // Ensure scroll bars are displayed only if needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        List<Track> tracks = SpotifyService.getTopTracks(response);

        int row = 0;
        int col = 0;
        for (Track track : tracks) {

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
                service.openURL2(track.getSpotifyLink());
            });

            textID.setTextAlignment(TextAlignment.CENTER); // Center-align the text
            textName.setTextAlignment(TextAlignment.CENTER); // Center-align the text

            hBox.getChildren().addAll(textID, imageView, textName);
            hBox.setAlignment(Pos.CENTER);
            gridPane.add(hBox, col, row);
            gridPane.getRowConstraints().add(new RowConstraints(70));
            row++;
        }

    }

    public void getBackToTopLists(ActionEvent event) {
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
    }

    public void onAllTimeButtonClicked() {
        HttpResponse<String> response = SpotifyService.getUserTopTracksOfAllTime();

        assert response != null;
        String jsonResponse = response.body();

        populateScrollPaneWithTracks(scrollPane, jsonResponse);
    }

    public void on6MonthsButtonClicked() {
        HttpResponse<String> response = SpotifyService.getUserTopTracksOver6Months();

        assert response != null;
        String jsonResponse = response.body();

        populateScrollPaneWithTracks(scrollPane, jsonResponse);
    }

    public void on4WeeksButtonClicked() {
        HttpResponse<String> response = SpotifyService.getUserTopTracksOver4Weeks();

        assert response != null;
        String jsonResponse = response.body();

        populateScrollPaneWithTracks(scrollPane, jsonResponse);

    }

}
