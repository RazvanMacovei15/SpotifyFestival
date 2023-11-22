package com.example.spotifyfestival.UserData.Utils;

import com.example.spotifyfestival.API_URLS.SearchAPI;
import com.example.spotifyfestival.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.SpotifyAPI.SpotifyService;
import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Domain.Genre;
import com.example.spotifyfestival.UserData.Generics.MapValueSorter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static HttpResponse<String> getArtistSpotifyDetails(String str) {
        try {
            SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
            String token = spotifyAuthFlowService.getAccessToken();
            SpotifyService spotifyService = new SpotifyService();
            return spotifyService.getHttpResponse(token, SearchAPI.searchForArtist(str));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

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

//    public ObservableList<Artist> extractArtists(String jsonResponse){
//
//        ObservableList<ObservableList<String>> allGenresExtracted = FXCollections.observableArrayList();
//        ObservableList<Artist> listOfArtistsInResponse = FXCollections.observableArrayList();
//        try{
//            JSONObject jsonObject = new JSONObject(jsonResponse);
//            JSONArray itemsArray = jsonObject.getJSONArray("artists");
//            for(int i=0; i<itemsArray.length(); i++){
//                ObservableList<String> artistGenres = FXCollections.observableArrayList();
//                JSONObject artistObject = itemsArray.getJSONObject(i);
//                JSONArray array = artistObject.getJSONArray("genres");
//                for(int j=0; j<array.length(); j++ ){
//                    String genre = array.getString(j);
//                    artistGenres.add(genre);
//                }
//                String name = artistObject.getString("name");
//                String id = artistObject.getString("id");
//                Artist artist = new Artist(name, artistGenres);
//                artist.setId(id);
//
//                listOfArtistsInResponse.add(artist);
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return listOfArtistsInResponse;
//    }

//    public Map<String, Integer> getGenreCountFromResponse(ObservableList<Artist> artists){
//
//        HashMap<String, Integer> genreCount = new HashMap<>();
//        for(Artist artist : artists){
//            ObservableList<Genre> genres = (ObservableList<Genre>) artist.getGenres();
//            for(int i = 0; i < genres.size(); i++){
//                String genre = genres.get(i);
//                genreCount.put(genre, genreCount.getOrDefault(genre, 0) + 1);
//            }
//        }
//
//        System.out.println(genreCount);
//
//        Map<String, Integer> sortedGenreMap = MapValueSorter.sortByValuesDescendingWithAlphabetical(genreCount);
//        for (Map.Entry<String, Integer> entry : sortedGenreMap.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
//        return sortedGenreMap;
//    }

    public static void main(String[] args) {
//        Utils utils = new Utils();
//
//        ObservableList<Artist> artists = utils.extractArtists(JSONConstant.getConstant2());
//
//        GenreRepo genreRepo = new GenreRepo();
//
//        for(Artist artist : artists){
//            System.out.println(artist.getName() + " + id: " + artist.getId() + " genres: " + artist.getGenres());
//            try {
//                genreRepo.add(artist.getId(), artist);
//            } catch (DuplicateEntityException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        genreRepo.list();
//
//        utils.getGenreCountFromResponse(artists);
    }
}