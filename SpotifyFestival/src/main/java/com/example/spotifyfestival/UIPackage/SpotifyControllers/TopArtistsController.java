package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class TopArtistsController {
    protected SpotifyService service;

    @FXML
    protected Label infoLabel;

    @FXML
    private Button allTimeButton;

    @FXML
    private Button sixMonthsButton;

    @FXML
    private Button fourWeeksButton;
    @FXML
    private GridPane mainGridPane;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public void initialize() throws JsonProcessingException {
        service = new SpotifyService();

        // Automatically trigger the "4 weeks" button when the scene is shown
        on4WeeksButtonClicked();

    }

    public void something(ScrollPane scrollPane, String response) {
        scrollPane.setContent(null);
        GridPane gridPane = new GridPane();

        service = new SpotifyService();
        scrollPane.setContent(gridPane);

        gridPane.setGridLinesVisible(false);
        gridPane.setAlignment(Pos.CENTER);

        // Ensure scroll bars are displayed only if needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        for (int i = 0; i < 3; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(100));
        }

        List<Artist> artists = service.getTopArtists(response);

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
                service.openURL2(artist.getSpotifyLink());
            });

            vBox.getChildren().add(imageView);
            text.setTextAlignment(TextAlignment.CENTER); // Center-align the text

            vBox.getChildren().add(text);
            vBox.setAlignment(Pos.CENTER);
            gridPane.add(vBox, col, row);
            col++;

            if (col == 3) {
                col = 0;
                gridPane.getRowConstraints().add(new RowConstraints(100));
                row++;
            }
        }

    }

    public void onAllTimeButtonClicked() {
        onTimeRangeButtonClicked("all time");
    }

    public void on6MonthsButtonClicked() {
        onTimeRangeButtonClicked("6 months");
    }

    public void on4WeeksButtonClicked() {
        onTimeRangeButtonClicked("4 weeks");
    }

    public void onTimeRangeButtonClicked(String timeRange) {
        HttpResponse<String> response;

        switch (timeRange) {
            case "all time":
                response = SpotifyService.getUserTopArtists();
                break;
            case "6 months":
                response = SpotifyService.getUserTopArtistsOver6Months();
                break;
            case "4 weeks":
                response = SpotifyService.getUserTopArtistsOver4Weeks();
                break;
            default:
                // Handle the case when an unsupported time range is provided
                return;
        }

        assert response != null;
        String jsonResponse = response.body();
//        SpotifyService spotifyService = new SpotifyService();
//        List<Artist>
        // Call the extractAttribute method to get the artist attributes
        something(scrollPane, jsonResponse);
        // Set the artist names in your ListView or perform other actions
//        listView.setItems(artistNames);
    }

    public void getBackToTopLists(ActionEvent event) {
        try {
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }
}