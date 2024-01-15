package com.example.spotifyfestival.API_Packages.SpotifyAPI;

import com.example.spotifyfestival.API_Packages.API_URLS.Artists_API_URLS;
import com.example.spotifyfestival.API_Packages.API_URLS.SearchAPI;
import com.example.spotifyfestival.API_Packages.API_URLS.Tracks_API_URLS;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class SpotifyService {

    public SpotifyService() {

    }

    public static HttpResponse<String> getHttpResponse(String accessToken, String apiUrl) {

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

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

    public static Artist createArtistFromSearchResultForConcertRetrieval(String json, int id) {
        // Create an empty ObservableList to store the attribute values
        ObservableList<String> attributeValues = FXCollections.observableArrayList();
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
                System.out.println(name);
                spotifyID = itemObject.getString("id");
                System.out.println(spotifyID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Artist(id, name, spotifyID);
    }

    //method to retrieve a list of all the user top artists
    //based on the timeline in HttpResponse
    public static List<Artist> getTopArtists(String jsonResponse){
        List<Artist> artists = new ArrayList<>();
        int id = 0;
        String name  = null;
        ObservableList<Genre> genres = FXCollections.observableArrayList();
        String spotifyID = null;
        String spotifyLink = null;
        String imageURL = null;
        int popularity = 0;

        try {
            // Parse the JSON response
            JSONObject responseObject = new JSONObject(jsonResponse);
            int limit = responseObject.getInt("limit");

            JSONArray itemsArray = responseObject.getJSONArray("items");

            // Iterate through the items and extract the specified attribute
            for (int i = 1; i < limit + 1; i++) {

                JSONObject itemObject = itemsArray.getJSONObject(i-1);

                name = itemObject.getString("name");
                JSONArray genresArray = itemObject.getJSONArray("genres");

                for(int j = 1 ; j < genresArray.length() + 1; j++){

                    int genreId = j;
                    String genreName = (String) genresArray.get(j-1);
                    Genre genre = new Genre(genreId, genreName);

                    genres.add(genre);
                }
                spotifyID = itemObject.getString("id");
                spotifyLink = itemObject.getString("uri");

                JSONArray urlList = itemObject.getJSONArray("images");
                imageURL = urlList.getJSONObject(2).getString("url");

                popularity = itemObject.getInt("popularity");
                Artist artist = new Artist(i, name, spotifyID, genres, imageURL, spotifyLink, popularity);
                System.out.println(artist);
                artists.add(artist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return artists;
    }

    public void openURL2(String url) {
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

    public String getEmailResponse(String accessToken) throws IOException, InterruptedException {
        String apiUrl = "https://api.spotify.com/v1/me";
        // Create HttpRequest
        HttpRequest emailRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(apiUrl))
                .headers("Authorization", "Bearer " + accessToken)
                .build();
        // Send the request and get the response
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> emailResponse = httpClient.send(emailRequest, HttpResponse.BodyHandlers.ofString());
        return emailResponse.body();
    }

    //TOP ARTISTS RETRIEVAL METHODS
    public static HttpResponse<String> getUserTopArtists() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            return getHttpResponse(token, Artists_API_URLS.getUserTopArtistsAllTimeURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse<String> getUserTopArtistsOver6Months() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            return getHttpResponse(token, Artists_API_URLS.getUserTopArtists6MonthsURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HttpResponse<String> getUserTopArtistsOver4Weeks() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            return getHttpResponse(token, Artists_API_URLS.getUserTopArtistsOver4WeeksURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //TOP TRACKS RETRIEVAL METHODS
    public static HttpResponse<String> getUserTopTracksOfAllTime() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            return getHttpResponse(token, Tracks_API_URLS.getUserTopTracksAllTimeURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static HttpResponse<String> getUserTopTracksOver6Months() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            return getHttpResponse(token, Tracks_API_URLS.getUserTopTracks6MonthsURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static HttpResponse<String> getUserTopTracksOver4Weeks() {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            return getHttpResponse(token, Tracks_API_URLS.getUserTopTracksOver4WeeksURI());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //from tracks api response
    public  ObservableList<String> extractArtistNamesFromTracks(String jsonResponse) {
        ObservableList<String> ol = FXCollections.observableArrayList();
        String allArtistsOfASong = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray allTheTracks = jsonObject.getJSONArray("items");

            for(int i = 0; i < allTheTracks.length(); i++){

                JSONObject objectTracks = allTheTracks.getJSONObject(i);
                JSONArray trackObjects = objectTracks.getJSONArray("artists");

                StringBuilder sb = new StringBuilder();
                sb.append("performed by: ");
                for(int j = 0; j < trackObjects.length(); j++){
                    JSONObject artistObject = trackObjects.getJSONObject(j);
                    String individualArtists = artistObject.getString("name");
                    sb.append(individualArtists);
                    // Check if it's not the last item in the loop before adding a comma
                    if (j < trackObjects.length()-1) {
                        sb.append(", "); // Add a comma and space as a separator
                    }
                }

                allArtistsOfASong = sb.toString();

                ol.add(allArtistsOfASong);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ol;
    }

    //search by artist name api
    public static HttpResponse<String> getArtistSpotifyDetails(String artistName) {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            return getHttpResponse(token, SearchAPI.searchForArtist(artistName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //extract spotify id from search api
    public String extractSpotifyID(String jsonResponse){
        String id = null;
        try{
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray artistsArray = jsonObject.getJSONArray("artists");
            for(int i=0; i<artistsArray.length(); i++){
                JSONObject artistObject = artistsArray.getJSONObject(i);
                id = artistObject.getString("id");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

}
