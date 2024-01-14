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

    public HttpResponse<String> getHttpResponse(String accessToken, String apiUrl) {

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

    public HttpResponse<String> getUserTopTracksAllTime(String accessToken) {

        String API_URL = Tracks_API_URLS.getUserTopTracksAllTimeURI();

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

        return response;
    }

    public HttpResponse<String> getUserTopArtists(String accessToken) {

        String API_URL = Artists_API_URLS.getUserTopArtistsAllTimeURI();

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

        return response.body().toString();
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

    public String getImageURL(String json) {
        // Create an empty ObservableList to store the attribute values
        ObservableList<String> attributeValues = FXCollections.observableArrayList();
        String url = null;
        String spotifyID = null;
        try {
            // Parse the JSON response
            JSONObject responseJson = new JSONObject(json);
            JSONObject obj = responseJson.getJSONObject("artists");
            JSONArray itemsArray = obj.getJSONArray("items");

            // Iterate through the items and extract the specified attribute
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);
                JSONArray urlList = itemObject.getJSONArray("images");
                url = urlList.getJSONObject(2).getString("url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public List<Artist> getTopArtists(String jsonResponse){
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

}
