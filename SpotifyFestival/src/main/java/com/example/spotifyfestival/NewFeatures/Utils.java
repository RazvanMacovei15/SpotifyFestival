package com.example.spotifyfestival.NewFeatures;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Track;
import com.example.spotifyfestival.GenericsPackage.MapValueSorter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            // Load an image
            try {
                String url = "/com/example/spotifyfestival/PNGs/coperta_1.jpeg";
                String imageUrl = track.getImageURL();

                URI uri = new URI(imageUrl);
                Image originalImage = new Image(imageUrl);
                // Create an ImageView with the image
                ImageView imageView = new ImageView(originalImage);

                // Set the desired width and height to scale down the image
                double scaledWidth = 50;
                double scaledHeight = 50;
                // Set the fitWidth and fitHeight properties to scale the image
                imageView.setFitWidth(scaledWidth);
                imageView.setFitHeight(scaledHeight);
                Text textID = new Text(track.getId().toString() + ".");
                Text textName = new Text(track.toStringForUI());
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
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void populateScrollPaneWithArtists(ScrollPane scrollPane, ObservableList<Artist> artists) {
        GridPane gridPane = new GridPane();
        Platform.runLater(()->{
            gridPane.setGridLinesVisible(false);
            gridPane.setAlignment(Pos.CENTER);
            scrollPane.setContent(gridPane);
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

    public static Map<Genre, Integer> getGenreCountFromResponse(ObservableList<Artist> artists) {
        HashMap<Genre, Integer> genreCount = new HashMap<>();
        for (Artist artist : artists) {
            ObservableList<Genre> genres = (ObservableList<Genre>) artist.getGenres();
            for (int i = 0; i < genres.size(); i++) {
                Genre genre = genres.get(i);
                genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
            }
        }
        // Sort and return the genre count map
        Map<Genre, Integer> sortedGenreMap = MapValueSorter.sortByValuesDescendingWithAlphabetical(genreCount);
        return sortedGenreMap;
    }

    public static Map<Genre, Integer> getTopMostGenresListened(String timeRange) {

        SpotifyAuthFlowService auth = SpotifyAuthFlowService.getInstance();
        String accessToken = auth.getAccessToken();
        SpotifyResponseService service = new SpotifyResponseService(accessToken);
        SpotifyAPIJsonParser parser = new SpotifyAPIJsonParser();

        HttpResponse<String> response = service.getTopArtists(50, timeRange, 0);
        // Extract relevant information from the API response
        ObservableList<Artist> allArtists = parser.getTopArtists(response);
        Map<Genre, Integer> genreCount = Utils.getGenreCountFromResponse(allArtists);

        // Create a new map to store the highest three values
        Map<Genre, Integer> topMostGenres = new HashMap<>();

        int count = 0;
        int maxCount = 3;

        // Iterate through the highest three values and save corresponding elements
        int previousValue = 0;
        for (Map.Entry<Genre, Integer> entry : genreCount.entrySet()) {
            while (count < maxCount) {
                Genre key = entry.getKey();
                Integer value = entry.getValue();
                if (count == 0) {
                    topMostGenres.put(key, value);
                    count++;
                    previousValue = value;
                    break;
                } else {
                    if (!value.equals(previousValue)) {
                        topMostGenres.put(key, value);
                        previousValue = value;
                        break;
                    }
                }
                count++;
            }
        }
        return topMostGenres;
    }

    public static List<Genre> returnArtistGenresFromSpotifyID(String id, String accessToken) {
        SpotifyResponseService spotifyResponseService = new SpotifyResponseService(accessToken);
        HttpResponse<String> artistByIdResponse = spotifyResponseService.getArtistById(id);

        List<Genre> genres = new ArrayList<>();
        try {
            // Parse the JSON response
            JSONObject responseJson = new JSONObject(artistByIdResponse.body());
            JSONArray genresArray = responseJson.optJSONArray("genres");
            if(genresArray.length() == 0){
                return genres;
            }
            // Iterate through the items and extract the specified attribute
            for (int i = 0; i < genresArray.length(); i++) {
                String name = (String) genresArray.get(i);
                Genre genre = new Genre(i, name);
                genres.add(genre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genres;
    }

    public static String getArtistByNameHttpResponse(String name, String accessToken) {
        SpotifyResponseService responseService = new SpotifyResponseService(accessToken);
        HttpResponse<String> searchResponse = responseService.search(name, "artist", "RO", 1,0);
        return searchResponse.body();
    }

    public static Artist createArtistFromSearchResultForConcertRetrieval(String json, int id) {
        // Create an empty ObservableList to store the attribute values
        String name = null;
        String spotifyID = null;
        try {
            // Parse the JSON response
            JSONObject responseJson = new JSONObject(json);
            JSONObject obj = responseJson.getJSONObject("artists");
            JSONArray itemsArray = obj.getJSONArray("items");

            // Iterate through the items and extract the specified attribute
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);
                name = itemObject.getString("name");
                spotifyID = itemObject.getString("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Artist(id, name, spotifyID);
    }

}
