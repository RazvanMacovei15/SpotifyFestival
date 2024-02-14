package com.example.spotifyfestival.NewFeatures.CacheImplementation.TopArtists;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.CacheFileRepo;
import com.example.spotifyfestival.NewFeatures.SpotifyAPIJsonParser;
import com.example.spotifyfestival.NewFeatures.SpotifyResponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class TopArtists extends CacheFileRepo<String, Artist> {
    private int limit;
    private String timeRange;
    private int offset;
    public TopArtists(String filename) {
        super(filename);
        this.limit = 50;
        this.offset = 0;
    }

    private void initializeFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            SpotifyResponseService spotifyResponseService = new SpotifyResponseService(SpotifyAuthFlowService.getInstance().getAccessToken());
            SpotifyAPIJsonParser spotifyAPIJsonParser = new SpotifyAPIJsonParser();
            ObservableList<Artist> artists;
            switch (filename){
                case "long_term_artists.txt":
                    timeRange = "long_term";
                    artists = spotifyAPIJsonParser.getTopArtists(spotifyResponseService.getTopArtists(limit, timeRange,offset));
                    for (Artist artistG : artists) {
                        writer.write(artistG.toString());
                        writer.newLine();
                    }
                    break;
                case "medium_term_artists.txt":
                    timeRange = "medium_term";
                    artists = spotifyAPIJsonParser.getTopArtists(spotifyResponseService.getTopArtists(limit, timeRange,offset));
                    for (Artist artistG : artists) {
                        writer.write(artistG.toString());
                        writer.newLine();
                    }
                    break;
                case "short_term_artists.txt":
                    timeRange = "short_term";
                    artists = spotifyAPIJsonParser.getTopArtists(spotifyResponseService.getTopArtists(limit, timeRange,offset));
                    for (Artist artistG : artists) {
                        writer.write(artistG.toString());
                        writer.newLine();
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            Iterable<Artist> artists = super.getAll();
            for (Artist artistG : artists) {
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
                new Thread(this::initializeFile).start();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }else{
            //if file is empty, initialize data
            if(file.length() == 0){
                new Thread(this::initializeFile).start();
            }
        }

        try(BufferedReader r = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = r.readLine()) != null) {

                String[] parts = line.split("<>");

                if(parts.length != 7){
                    throw new IllegalStateException("this format is not allowed!!");
                }

                int id = Integer.parseInt(parts[0]);
                String name = parts[1].trim();
                String[] genres = parts[3].trim().split("\\|");
                ObservableList<Genre> genresList = FXCollections.observableArrayList();
                if(genres[0].equals("null")){
                    genresList = null;
                }else{
                    for (int i = 0; i < genres.length; i++) {
                        genresList.add(new Genre(i, genres[i].trim()));
                    }
                }
                String spotifyId = parts[2].trim();
                String imageUrl = parts[4].trim();
                String spotifyLink = parts[5].trim();
                int popularity = Integer.parseInt(parts[6].trim());

                Artist a = new Artist(id, name, spotifyId, genresList, imageUrl, spotifyLink, popularity);
                super.add(name, a);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    @Override
    public void resetFile() {
        new Thread(()->{
            System.out.println("resetting file '" + filename + "' on thread: " + Thread.currentThread().getName());
            super.resetFile();
//            initializeFile();
        }).start();

    }
}
