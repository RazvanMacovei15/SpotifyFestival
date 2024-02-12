package com.example.spotifyfestival.API_Packages.APIServices;

import com.example.spotifyfestival.API_Packages.API_URLS.Artists_API_URLS;
import com.example.spotifyfestival.API_Packages.API_URLS.SearchAPI;
import com.example.spotifyfestival.API_Packages.API_URLS.Tracks_API_URLS;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.DAO.ArtistDAO;
import com.example.spotifyfestival.DatabasePackage.DAO.GenreDAO;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Track;
import com.example.spotifyfestival.GenericsPackage.MapValueSorter;
import com.example.spotifyfestival.NewFeatures.SpotifyResponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class SpotifyService {

    public SpotifyService() {
    }
    //TODO - REVIEW THIS METHOD
    public static Map<Genre, Integer> getGenreCountFromResponse(ObservableList<Artist> artists) {
        HashMap<Genre, Integer> genreCount = new HashMap<>();
        for (Artist artist : artists) {
            ObservableList<Genre> genres = (ObservableList<Genre>) artist.getGenres();
            for (int i = 0; i < genres.size(); i++) {
                Genre genre = genres.get(i);
                genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
            }
        }
        System.out.println("--------------------");
        System.out.println("----> debug genreCount");
        for(Map.Entry<Genre, Integer> entry : genreCount.entrySet()){
            System.out.println(entry.getKey() + " is found " + entry.getValue() + " times in your listening history!");
        }

        // Sort and return the genre count map
        Map<Genre, Integer> sortedGenreMap = MapValueSorter.sortByValuesDescendingWithAlphabetical(genreCount);

        System.out.println("--------------------");
        System.out.println("----> debug sorted count");
        for (Map.Entry<Genre, Integer> entry : sortedGenreMap.entrySet()) {
            System.out.println(entry.getKey() + " is found " + entry.getValue() + " times in your listening history!");
        }
        return sortedGenreMap;
    }
    //TODO - REVIEW THIS METHOD
    public static Map<Genre, Integer> getTopMostGenresListened() {
        SpotifyAuthFlowService auth = SpotifyAuthFlowService.getInstance();
        String accessToken = auth.getAccessToken();
        SpotifyResponseService service = new SpotifyResponseService(accessToken);

        HttpResponse<String> response = service.getTopArtists(50, "long_term", 0);

        // Extract relevant information from the API response
        String jsonResponse = response.body();
        ObservableList<Artist> allArtists = SpotifyService.extractArtists(jsonResponse);
        Map<Genre, Integer> genreCount = getGenreCountFromResponse(allArtists);
        System.out.println(genreCount);
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

        Genre genre = new Genre(55, "dutch pop");
        Genre pop = new Genre(55, "romanian pop");
        Genre rock = new Genre(55, "romanian rock");
        topMostGenres.put(genre, 90);
        topMostGenres.put(pop,65);
//        topMostGenres.put(rock, 70);

        System.out.println(topMostGenres + "spotify service");

        return topMostGenres;
    }
    //TODO - REVIEW THIS METHOD
    public static List<Genre> returnArtistGenresFromSpotifyID(String id, String accessToken) {
        String apiUrl = SearchAPI.searchByIDURL(id);
        // Create HttpRequest
        HttpRequest emailRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(apiUrl))
                .headers("Authorization", "Bearer " + accessToken)
                .build();
        // Send the request and get the response
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(emailRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


        List<Genre> genres = new ArrayList<>();

        try {
            // Parse the JSON response
            JSONObject responseJson = new JSONObject(response.body());


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
    //TODO - REVIEW THIS METHOD
    public static String getArtistByNameHttpResponse(String name, String accessToken) {
        String API_URL = SearchAPI.searchForArtist(name);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return response.body();
    }
    //TODO - REVIEW THIS METHOD
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
    //TODO - REVIEW THIS METHOD
    // Method to extract artists and their genres from JSON response
    public static ObservableList<Artist> extractArtists(String jsonResponse) {

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
                    if (artist.getGenres().isEmpty()) {
                        for (Genre genre : artistGenres) {
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
}
