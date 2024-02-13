package com.example.spotifyfestival.NewFeatures.CacheImplementation.TopArtists;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.FileInterface;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.GenericCacheRepository;
import com.example.spotifyfestival.NewFeatures.SpotifyAPIJsonParser;
import com.example.spotifyfestival.NewFeatures.SpotifyResponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

public class LongTermArtists extends GenericCacheRepository<String, Artist> implements FileInterface<Artist> {
    private final String filename;

    public LongTermArtists(String filename) {
        this.filename = filename;
        readFromFile();
    }

    @Override
    public void writeToFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            SpotifyResponseService service = new SpotifyResponseService(SpotifyAuthFlowService.getInstance().getAccessToken());
            SpotifyAPIJsonParser parser = new SpotifyAPIJsonParser();
            ObservableList<Artist> artists = parser.getTopArtists(service.getTopArtists(50, "long_term", 0));
            Iterable<Artist> iterable = artists;
            Iterator<Artist> it = iterable.iterator();
            while(it.hasNext()){
                Artist artistG = (Artist) it.next();
                writer.write(artistG.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void readFromFile() {
        //if file doesn't exist, create it
        File file = new File(filename);
        if(!file.exists()){
            try {
                file.createNewFile();
                writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        try(BufferedReader r = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = r.readLine()) != null) {

                String[] parts = line.split(",");

                if(parts.length != 7){
                    throw new IllegalStateException("this format is not allowed!!");
                }

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String[] genres = parts[2].split("\\|");
                ObservableList<Genre> genresList = FXCollections.observableArrayList();
                for (int i = 0; i < genres.length; i++) {
                    genresList.add(new Genre(i, genres[i]));
                }
                String spotifyId = parts[3];
                String imageUrl = parts[4];
                String spotifyLink = parts[5];
                int popularity = Integer.parseInt(parts[6]);

                Artist a = new Artist(id, name, spotifyId, genresList, imageUrl, spotifyLink, popularity);
                super.add(name, a);
            }
        } catch (DuplicateEntityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile() {
        File file = new File(filename);
        if(file.exists()){
            file.delete();
        }
    }

    @Override
    public void listFile() {
        try(BufferedReader r = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
