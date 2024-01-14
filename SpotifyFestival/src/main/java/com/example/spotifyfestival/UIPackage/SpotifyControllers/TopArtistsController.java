package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.API_URLS.Artists_API_URLS;
import com.example.spotifyfestival.DatabasePackage.DAO.ArtistDAO;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.List;

public class TopArtistsController {
    protected SpotifyService service;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button allTimeButton;

    @FXML
    private Button sixMonthsButton;

    @FXML
    private Button fourWeeksButton;
    @FXML
    private GridPane mainGridPane;
//    @FXML
//    public GridPane scrollPaneGridPane;
//    @FXML
//    public ScrollPane scrollPane;

    @FXML
    public void initialize() throws JsonProcessingException {
        // Automatically trigger the "4 weeks" button when the scene is shown
//        on4WeeksButtonClicked();
        service = new SpotifyService();
        Platform.runLater(()->{
            something();
        });

    }

    public void something(){

        service = new SpotifyService();


        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);
        gridPane.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(gridPane);


        // Ensure scroll bars are displayed only if needed
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        for(int i = 0;  i<3; i++){
            gridPane.getColumnConstraints().add(new ColumnConstraints(100));
        }

        ArtistDAO artistDAO = ArtistDAO.getInstance();

        SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
        String token = spotifyAuthFlowService.getAccessToken();
        SpotifyService spotifyService = new SpotifyService();
        HttpResponse<String> response =  spotifyService.getHttpResponse(token, Artists_API_URLS.getUserTopArtistsOver4WeeksURI());

        List<Artist> artists = service.getTopArtists(response.body());


        int rows = artistDAO.getSize()/3;
        int row = 0;
        int col = 0;
        for(Artist artist : artists){


            token = spotifyAuthFlowService.getAccessToken();

//            String json = SpotifyService.getArtistByNameHttpResponse(artist.getName(), token);

//            SpotifyService service1 = new SpotifyService();
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

            if(col == 3){
                col = 0;
                gridPane.getRowConstraints().add(new RowConstraints(100));
                row++;
            }
        }
        mainGridPane.add(scrollPane, 0, 3);
    }

    public static HttpResponse<String> getUserTopArtists() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, Artists_API_URLS.getUserTopArtistsAllTimeURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse<String> getUserTopArtistsOver6Months() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, Artists_API_URLS.getUserTopArtists6MonthsURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse<String> getUserTopArtistsOver4Weeks() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, Artists_API_URLS.getUserTopArtistsOver4WeeksURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
                response = getUserTopArtists();
                break;
            case "6 months":
                response = getUserTopArtistsOver6Months();
                break;
            case "4 weeks":
                response = getUserTopArtistsOver4Weeks();
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
        ObservableList<String> artistNames = extractAttribute(jsonResponse, "name");

        // Set the artist names in your ListView or perform other actions
        listView.setItems(artistNames);
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

    public void getBackToTopLists(ActionEvent event) {
        try {
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/TOPLists/TopLists.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }
}