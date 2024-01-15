// Import statements for necessary packages and classes
package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.API_URLS.Artists_API_URLS;
import com.example.spotifyfestival.DatabasePackage.DAO.ArtistDAO;
import com.example.spotifyfestival.DatabasePackage.DAO.GenreDAO;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.GenericsPackage.MapValueSorter;
import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;



// Controller class for the TopGenres FXML file
public class TopGenresController {

    // FXML annotations to inject UI elements
    @FXML
    private ListView<String> listView;

    @FXML
    private ListView<String> countListView;

    @FXML
    private Button allTimeButton;

    @FXML
    private Button sixMonthsButton;

    @FXML
    private Button fourWeeksButton;

    // Initialize method, automatically triggered when the scene is shown
    @FXML
    public void initialize() throws JsonProcessingException {
        // Automatically trigger the "4 weeks" button when the scene is shown
        on4WeeksButtonClicked();
    }

    // Event handler for the "Get Back" button
    public void onGetBackButtonClicked(ActionEvent event) {
        try {
            // Switch scene to adminMainScreen.fxml
            AppSwitchScenesMethods.switchScene(event, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminMainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    // Method to get user's top artists from Spotify API
    public static HttpResponse<String> getUserTopArtists() {
        try {
            // Get access token and make API call for all-time top artists
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, Artists_API_URLS.getUserTopArtistsAllTimeURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Event handler for the "All Time" button
    public void onAllTimeButtonClicked() throws JsonProcessingException {
        // Delegate to the common method with the specified time range
        onTimeRangeButtonClicked("all time");
    }

    // Event handler for the "6 Months" button
    public void on6MonthsButtonClicked() throws JsonProcessingException {
        // Delegate to the common method with the specified time range
        onTimeRangeButtonClicked("6 months");
    }

    // Event handler for the "4 Weeks" button
    public void on4WeeksButtonClicked() throws JsonProcessingException {
        // Delegate to the common method with the specified time range
        onTimeRangeButtonClicked("4 weeks");
    }

    // Common method for handling time range button clicks
    public void onTimeRangeButtonClicked(String timeRange) throws JsonProcessingException {
        // Get user's top artists based on the specified time range
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
        // Extract relevant information from the API response
        String jsonResponse = response.body();
        ObservableList<Artist> allArtists = extractArtists(jsonResponse);
        Map<Genre, Integer> genreCount = getGenreCountFromResponse(allArtists);
        // Prepare data for UI display
        ObservableList<Genre> genres = FXCollections.observableArrayList();
        ObservableList<String> genresCount = FXCollections.observableArrayList();
        ObservableList<String> genreNames = FXCollections.observableArrayList();

        for (Map.Entry<Genre, Integer> entry : genreCount.entrySet()) {
            genres.add(entry.getKey());
        }
        for (Map.Entry<Genre, Integer> entry : genreCount.entrySet()) {
            genresCount.add(String.valueOf(entry.getValue()));
        }
        for (Genre genre : genres) {
            String name = genre.getName();
            genreNames.add(name);
        }

        // Set the data in the ListView UI elements
        listView.setItems(genreNames);
        countListView.setItems(genresCount);
    }

    // Method to extract a specific attribute from JSON response
    public static ObservableList<String> extractAttribute(String jsonResponse, String attributeName) {
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

    // Method to extract artists and their genres from JSON response
    public ObservableList<Artist> extractArtists(String jsonResponse) {
        ObservableList<ObservableList<String>> allGenresExtracted = FXCollections.observableArrayList();
        ObservableList<Artist> listOfArtistsInResponse = FXCollections.observableArrayList();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                ObservableList<Genre> artistGenres = FXCollections.observableArrayList();
                JSONObject artistObject = itemsArray.getJSONObject(i);
                JSONArray array = artistObject.getJSONArray("genres");
                for (int j = 0; j < array.length(); j++) {
                    String genreName = array.getString(j);
                    int genreID = 0;
                    GenreDAO genreDAO = GenreDAO.getInstance();
                    if (!genreDAO.checkIfGenreInDB(genreName)) {
                        genreID = genreDAO.returnHighestID() + 1;
                        Genre genreToADD = new Genre(genreID, genreName);
                        genreDAO.insertObjectInDB(genreToADD);
                        artistGenres.add(genreToADD);
                    } else {
                        genreID = genreDAO.getGenreByName(genreName).getId();
                        Genre genre = genreDAO.getItem(genreID);
                        artistGenres.add(genre);
                    }
                }
                String name = artistObject.getString("name");

                String spotifyId = artistObject.getString("id");
                int artistId = 0;
                ArtistDAO artistDAO = ArtistDAO.getInstance();
                if (artistDAO.checkIfArtistInDB(name)) {
                    artistId = artistDAO.getArtistByName(name).getId();
                    Artist artist = artistDAO.getItem(artistId);
                    if(artist.getGenres().isEmpty()){
                        for(Genre genre : artistGenres){
                            artist.addGenre(genre);
                        }
                    }
                    listOfArtistsInResponse.add(artist);

                } else {
                    artistId = artistDAO.getHighestId() + 1;
                    Artist artist = new Artist(artistId, name, artistGenres, spotifyId);
                    artistDAO.insertObjectInDB(artist);
                    listOfArtistsInResponse.add(artist);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfArtistsInResponse;
    }

    // Method to count the occurrences of each genre in the list of artists
    public Map<Genre, Integer> getGenreCountFromResponse(ObservableList<Artist> artists) {
        HashMap<Genre, Integer> genreCount = new HashMap<>();
        for (Artist artist : artists) {
            ObservableList<Genre> genres = (ObservableList<Genre>) artist.getGenres();
            for (int i = 0; i < genres.size(); i++) {
                Genre genre = genres.get(i);
                genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
            }
        }
        // Print the genre count to console
//        System.out.println(genreCount);

        // Sort and return the genre count map
        Map<Genre, Integer> sortedGenreMap = MapValueSorter.sortByValuesDescendingWithAlphabetical(genreCount);
        for (Map.Entry<Genre, Integer> entry : sortedGenreMap.entrySet()) {
//            System.out.println(entry.getKey() + " is found " + entry.getValue() + " times in your listening history!");
        }
        return sortedGenreMap;

    }
}
