package com.example.spotifyfestival.NewFeatures;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class SpotifyAPIJsonParser {
    //TODO - parse JSON responses from Spotify API


    // ARTISTS
    public List<Artist> getTopArtists(HttpResponse<String> response) {
        List<Artist> topArtists = new ArrayList<>();
        //TODO
        JSONObject topArtistsJSON = new JSONObject(response.body());
        int limit = topArtistsJSON.optInt("limit");
        int artistIndex = 1;
        int genreIndex = 1;
        JSONArray items = topArtistsJSON.optJSONArray("items");
        for(int i = 0 ; i < limit; i++){
            JSONObject artistObject = items.optJSONObject(i);
            String artistName = artistObject.optString("name");
            String artistSpotifyId = artistObject.optString("id");
            String artistImageUrl = artistObject.optJSONArray("images").optJSONObject(0).optString("url");
            String artistSpotifyLink = artistObject.optString("uri");
            int artistPopularity = artistObject.optInt("popularity");
            ObservableList<Genre> genres = FXCollections.observableArrayList();
            JSONArray genresArray = artistObject.optJSONArray("genres");
            for(int j = 0; j < genresArray.length(); j++){
                genres.add(new Genre(genreIndex, genresArray.optString(j)));
                genreIndex++;
            }

            Artist artist = new Artist(artistIndex, artistName, artistSpotifyId, genres, artistImageUrl, artistSpotifyLink, artistPopularity);
            artistIndex++;
            topArtists.add(artist);
        }
        return topArtists;
    }

    public void getArtistById(HttpResponse<String> response) {
        //TODO
    }

    public void getMultipleArtists(HttpResponse<String> response) {
        //TODO
    }


    // TRACKS
    public void getTopTracks(HttpResponse<String> response) {
        //TODO
    }

    public void getTrackById(HttpResponse<String> response) {
        //TODO
    }

    public void getMultipleTracks(HttpResponse<String> response) {
        //TODO
    }


    // USER
    public void getUserProfile(HttpResponse<String> response) {
        //TODO
    }


    // SEARCH
    public void search(HttpResponse<String> response) {
        //TODO
    }

}
