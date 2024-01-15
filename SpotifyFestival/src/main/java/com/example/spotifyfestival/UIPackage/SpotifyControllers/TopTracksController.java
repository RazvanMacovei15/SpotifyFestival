package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Track;
import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

public class TopTracksController {


    @FXML
    private ListView<String> listView;

    @FXML
    private Button allTimeButton;

    @FXML
    private Button sixMonthsButton;

    @FXML
    private Button fourWeeksButton;

    protected SpotifyService service;

    @FXML
    protected ScrollPane scrollPane;

    public TopTracksController() {
        this.service = new SpotifyService();
    }

    @FXML
    public void initialize() throws JsonProcessingException {
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

    public void getBackToTopLists(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }

    public static void printNamesAndIDs(ObservableList<String> artistNames, ObservableList<String> artistID) {
        if (artistNames.size() != artistID.size()) {
            System.out.println("Error: The sizes of artistNames and artistID lists do not match.");
            return;
        }

        for (int i = 0; i < artistNames.size(); i++) {
            String name = artistNames.get(i);
            String id = artistID.get(i);
            System.out.println("Name: " + name);
            System.out.println("ID: " + id);
        }
    }
    public static void printNamesAndSongs(ObservableList<String> artist, ObservableList<String> songs) {
        if (artist.size() != songs.size()) {
            System.out.println("Error: The sizes of artistNames and artistID lists do not match.");
            return;
        }

        for (int i = 0; i < artist.size(); i++) {
            String name = artist.get(i);
            String id = songs.get(i);
            System.out.println("Name: " + name);
            System.out.println("ID: " + id);
        }
    }
    public static ObservableList<String> listOfNamesAndSongs(ObservableList<String> artist, ObservableList<String> songs) {
        ObservableList<String> stringList = FXCollections.observableArrayList();

        if (artist.size() != songs.size()) {
            System.out.println("Error: The sizes of artistNames and artistID lists do not match.");
        }

        for (int i = 0; i < artist.size(); i++) {
            String name = artist.get(i);
            String id = songs.get(i);
            stringList.add(id + ", " + name);
        }
        return stringList;
    }

    public static ObservableList<String> extractAttribute(String jsonResponse, String attributeName) {
        // Create an empty ObservableList to store the attribute values
        ObservableList<String> attributeValues = FXCollections.observableArrayList();

        try {
            // Parse the JSON response
            JSONObject responseJson = new JSONObject(jsonResponse);
            JSONArray itemsArray = responseJson.getJSONArray("items");

            // Iterate through the items and extract the specified attribute
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);
                String attributeValue = itemObject.getString(attributeName);
                attributeValues.add(attributeValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return attributeValues;
    }

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminMainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }



    public void onAllTimeButtonClicked(){
        HttpResponse<String> response = SpotifyService.getUserTopTracksOfAllTime();

        assert response != null;
        String jsonResponse = response.body();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNamesFromTracks = service.extractArtistNamesFromTracks(jsonResponse);
        ObservableList<String> trackNames = extractAttribute(jsonResponse, "name");

        ObservableList<String> concat = listOfNamesAndSongs(artistNamesFromTracks, trackNames);
//        listView.setItems(concat);
        something(scrollPane, jsonResponse);
    }

    public void on6MonthsButtonClicked() {
        HttpResponse<String> response = SpotifyService.getUserTopTracksOver6Months();

        assert response != null;
        String jsonResponse = response.body();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNamesFromTracks = service.extractArtistNamesFromTracks(jsonResponse);
        ObservableList<String> trackNames = extractAttribute(jsonResponse, "name");

        ObservableList<String> concat = listOfNamesAndSongs(artistNamesFromTracks, trackNames);
//        listView.setItems(concat);
        something(scrollPane, jsonResponse);
    }
    public void on4WeeksButtonClicked() {
        HttpResponse<String> response = SpotifyService.getUserTopTracksOver4Weeks();

        assert response != null;
        String jsonResponse = response.body();
        // Call the extractAttribute method to get the artist attributes
        ObservableList<String> artistNamesFromTracks = service.extractArtistNamesFromTracks(jsonResponse);
        ObservableList<String> trackNames = extractAttribute(jsonResponse, "name");

        ObservableList<String> concat = listOfNamesAndSongs(artistNamesFromTracks, trackNames);
//        listView.setItems(concat);
        something(scrollPane, jsonResponse);

    }

}
