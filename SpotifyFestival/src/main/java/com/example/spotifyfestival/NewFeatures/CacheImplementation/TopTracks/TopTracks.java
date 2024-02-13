package com.example.spotifyfestival.NewFeatures.CacheImplementation.TopTracks;

import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Track;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.CacheFileRepo;
import com.example.spotifyfestival.NewFeatures.SpotifyAPIJsonParser;
import com.example.spotifyfestival.NewFeatures.SpotifyResponseService;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TopTracks extends CacheFileRepo<String, Track> {
    private int limit;
    private String timeRange;
    private int offset;

    public TopTracks(String filename) {
        super(filename);
        this.limit = 50;
        this.offset = 0;
    }

    public void initializeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            SpotifyResponseService spotifyResponseService = new SpotifyResponseService(SpotifyAuthFlowService.getInstance().getAccessToken());
            SpotifyAPIJsonParser spotifyAPIJsonParser = new SpotifyAPIJsonParser();
            ObservableList<Track> tracks;
            switch (filename) {
                case "long_term_tracks.txt":
                    timeRange = "long_term";
                    tracks = spotifyAPIJsonParser.getTopTracks(spotifyResponseService.getTopTracks(limit, timeRange, offset));
                    for (Track track : tracks) {
                        writer.write(track.toString());
                        writer.newLine();
                    }
                    break;
                case "medium_term_tracks.txt":
                    timeRange = "medium_term";
                    tracks = spotifyAPIJsonParser.getTopTracks(spotifyResponseService.getTopTracks(limit, timeRange, offset));
                    for (Track track : tracks) {
                        writer.write(track.toString());
                        writer.newLine();
                    }
                    break;
                case "short_term_tracks.txt":
                    timeRange = "short_term";
                    tracks = spotifyAPIJsonParser.getTopTracks(spotifyResponseService.getTopTracks(limit, timeRange, offset));
                    for (Track track : tracks) {
                        writer.write(track.toString());
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Iterable<Track> tracks = super.getAll();
            for (Track track : tracks) {
                writer.write(track.toString());
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
        if (!file.exists()) {
            try {
                file.createNewFile();
                new Thread(this::initializeFile).start();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            //if file is empty, initialize data
            if (file.length() == 0) {
                new Thread(this::initializeFile).start();
            }
        }

        try (BufferedReader r = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = r.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length != 6) {
                    throw new IllegalStateException("this format is not allowed!!");
                }
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1];
                String[] artists = parts[2].split("\\|");
                List<Artist> artistList = new ArrayList<>();
                if (artists[0].equals("null")) {
                    artistList = null;
                } else {
                    for (int i = 0; i < artists.length; i++) {
                        artistList.add(new Artist(i, artists[i], null));
                    }
                }
                String spotifyId = parts[3].trim();
                String spotifyLink = parts[4].trim();
                String imageUrl = parts[5].trim();
                System.out.println("WIP!");
                Track track = new Track(id, name, spotifyId, spotifyLink, imageUrl, artistList);
                super.add(spotifyId, track);
            }
        } catch (IOException | DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void listFile() {
        try (BufferedReader r = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
