package com.example.spotifyfestival.LabFacultate.Utils;

import com.example.spotifyfestival.API_Packages.API_URLS.SearchAPI;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;

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
}