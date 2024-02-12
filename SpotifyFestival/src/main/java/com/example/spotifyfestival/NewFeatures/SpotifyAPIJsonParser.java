package com.example.spotifyfestival.NewFeatures;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.SpotifyUser;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class SpotifyAPIJsonParser {
    // ARTISTS
    public ObservableList<Artist> getTopArtists(HttpResponse<String> response) {
        if(response == null){
            return null;
        }
        ObservableList<Artist> topArtists = FXCollections.observableArrayList();
        JSONObject topArtistsJSON = new JSONObject(response.body());
        int artistIndex = 1;
        JSONArray items = topArtistsJSON.optJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            JSONObject artistObject = items.optJSONObject(i);
            Artist artist = createArtist(artistObject, artistIndex);
            artistIndex++;
            topArtists.add(artist);
        }
        return topArtists;
    }

    public Artist getArtistById(HttpResponse<String> response) {
        JSONObject artistJSON = new JSONObject(response.body());
        int artistIndex = 1;
        return createArtist(artistJSON, artistIndex);
    }

    private Artist createArtist(JSONObject artistJSON, int index) {
        String artistName = artistJSON.optString("name");
        String artistSpotifyId = artistJSON.optString("id");
        String artistImageUrl = artistJSON.optJSONArray("images").optJSONObject(0).optString("url");
        String artistSpotifyLink = artistJSON.optString("uri");
        int artistPopularity = artistJSON.optInt("popularity");
        ObservableList<Genre> genres = FXCollections.observableArrayList();
        JSONArray genresArray = artistJSON.optJSONArray("genres");
        int genreIndex = 1;
        for (int j = 0; j < genresArray.length(); j++) {
            genres.add(new Genre(genreIndex, genresArray.optString(j)));
            genreIndex++;
        }
        return new Artist(index, artistName, artistSpotifyId, genres, artistImageUrl, artistSpotifyLink, artistPopularity);
    }

    public List<Artist> getMultipleArtists(HttpResponse<String> response) {
        JSONObject multipleArtistsJSON = new JSONObject(response.body());
        JSONArray artistsArray = multipleArtistsJSON.optJSONArray("artists");
        List<Artist> multipleArtists = new ArrayList<>();
        int artistIndex = 1;
        for (int i = 0; i < artistsArray.length(); i++) {
            Artist artist = createArtist(artistsArray.optJSONObject(i), artistIndex);
            artistIndex++;
            multipleArtists.add(artist);
        }
        return multipleArtists;
    }


    // TRACKS
    public ObservableList<Track> getTopTracks(HttpResponse<String> response) {
        ObservableList<Track> topTracks = FXCollections.observableArrayList();
        JSONObject topTracksJSON = new JSONObject(response.body());
        int limit = topTracksJSON.optInt("limit");
        int trackIndex = 1;
        JSONArray items = topTracksJSON.optJSONArray("items");
        for (int i = 0; i < limit; i++) {
            Track newTrack = createTrackObject(items.optJSONObject(i), trackIndex);
            trackIndex++;
            topTracks.add(newTrack);
        }
        return topTracks;
    }

    private Track createTrackObject(JSONObject trackObject, int index) {
        String trackName = trackObject.optString("name");
        String trackSpotifyId = trackObject.optString("id");
        String trackSpotifyLink = trackObject.optString("uri");
        String trackImageUrl = trackObject.optJSONObject("album").optJSONArray("images").optJSONObject(0).optString("url");
        ObservableList<Artist> trackArtists = FXCollections.observableArrayList();
        JSONArray artistsArray = trackObject.optJSONArray("artists");
        int artistIndex = 1;
        for (int j = 0; j < artistsArray.length(); j++) {
            JSONObject artistObject = artistsArray.optJSONObject(j);
            String artistName = artistObject.optString("name");
            String artistSpotifyId = artistObject.optString("id");
            String artistSpotifyLink = artistObject.optString("uri");
            //create partial artist object
            //TODO = create background method to fill in the rest of the artist object
            Artist artist = new Artist(artistIndex, artistName, artistSpotifyId, null, null, artistSpotifyLink, 0);
            trackArtists.add(artist);
            artistIndex++;
        }
        return new Track(index, trackName, trackSpotifyId, trackSpotifyLink, trackImageUrl, trackArtists);
    }

    public Track getTrackById(HttpResponse<String> response) {
        JSONObject trackJSON = new JSONObject(response.body());
        int trackIndex = 1;
        return createTrackObject(trackJSON, trackIndex);
    }

    public ObservableList<Track> getMultipleTracks(HttpResponse<String> response) {
        ObservableList<Track> multipleTracks = FXCollections.observableArrayList();
        JSONObject multipleTracksJSON = new JSONObject(response.body());
        JSONArray tracksArray = multipleTracksJSON.optJSONArray("tracks");
        int trackIndex = 1;
        for (int i = 0; i < tracksArray.length(); i++) {
            JSONObject trackObject = tracksArray.optJSONObject(i);
            Track track = createTrackObject(trackObject, trackIndex);
            trackIndex++;
            multipleTracks.add(track);
        }
        return multipleTracks;
    }


    // USER
    public SpotifyUser getUserProfile(HttpResponse<String> response) {
        JSONObject userProfileJSON = new JSONObject(response.body());
        String country = userProfileJSON.optString("country");
        String displayName = userProfileJSON.optString("display_name");
        String email = userProfileJSON.optString("email");
        String spotifyId = userProfileJSON.optString("id");
        String uri = userProfileJSON.optString("uri");
        int followers = userProfileJSON.optJSONObject("followers").optInt("total");
        return new SpotifyUser(displayName, email, spotifyId, country, uri, followers);
    }


    // SEARCH
    public ObservableList<Object> search(HttpResponse<String> response) {

        JSONObject searchJSON = new JSONObject(response.body());
        if(searchJSON.has("artists")){
            JSONObject artistSearch = searchJSON.optJSONObject("artists");
            int limit = artistSearch.optInt("limit");
            JSONArray items = artistSearch.optJSONArray("items");
            int artistIndex = 1;
            ObservableList<Object> artistsToReturn = FXCollections.observableArrayList();
            Artist artistToReturn = null;
            for(int i = 0; i < limit; i++){
                JSONObject artist = items.optJSONObject(i);
                artistToReturn = createArtist(artist, artistIndex);
                artistsToReturn.add(artistToReturn);
            }
            return artistsToReturn;
        }else if(searchJSON.has("tracks")){
            JSONObject trackSearch = searchJSON.optJSONObject("tracks");
            int limit = trackSearch.optInt("limit");
            JSONArray items = trackSearch.optJSONArray("items");
            int trackIndex = 1;
            ObservableList<Object> tracksToReturn = FXCollections.observableArrayList();
            for(int i = 0; i < limit; i++){
                JSONObject track = items.optJSONObject(i);
                Track newTrack = createTrackObject(track, trackIndex);
                trackIndex++;
                tracksToReturn.add(newTrack);
            }
            return tracksToReturn;
        }else {
            System.out.println("No search results found");
            return null;
        }
    }

}
