package com.example.spotifyfestival.NewFeatures.CacheImplementation;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.TopArtists.TopArtists;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.TopTracks.TopTracks;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Cache {
    private Map<String, CacheFileRepo<String, Entity>> cache;
    //Top Artists
    private final String LONG_TERM_ARTISTS = "long_term_artists.txt";
    private CacheFileRepo<String, Entity> longTermArtists;
    private boolean longTermArtistsExists;
    private final String MEDIUM_TERM_ARTISTS = "medium_term_artists.txt";
    private CacheFileRepo<String, Entity> mediumTermArtists;
    private boolean mediumTermArtistsExists;
    private final String SHORT_TERM_ARTISTS = "short_term_artists.txt";
    private CacheFileRepo<String, Entity> shortTermArtists;
    private boolean shortTermArtistsExists;
    //Top Tracks
    private final String LONG_TERM_TRACKS = "long_term_tracks.txt";
    private CacheFileRepo<String, Entity> longTermTracks;
    private boolean longTermTracksExists;
    private final String SHORT_TERM_TRACKS = "short_term_tracks.txt";
    private CacheFileRepo<String, Entity> shortTermTracks;
    private boolean shortTermTracksExists;
    private final String MEDIUM_TERM_TRACKS = "medium_term_tracks.txt";
    private CacheFileRepo<String, Entity> mediumTermTracks;
    private boolean mediumTermTracksExists;
    private static Cache instance;
    private long timeCreated;

    public static Cache getInstance() {
        if(instance == null){
            instance = new Cache();
        }
        return instance;
    }

    private Cache() {
        cache = new HashMap<>();
        initialize();
    }

    public void initializeFileCache(String filename){
        switch (filename) {
            case LONG_TERM_ARTISTS:
                longTermArtists = new TopArtists(filename);
                break;
            case MEDIUM_TERM_ARTISTS:
                mediumTermArtists = new TopArtists(filename);
                break;
            case SHORT_TERM_ARTISTS:
                shortTermArtists = new TopArtists(filename);
                break;
            case LONG_TERM_TRACKS:
                longTermTracks = new TopTracks(filename);
                break;
            case MEDIUM_TERM_TRACKS:
                mediumTermTracks = new TopTracks(filename);
                break;
            case SHORT_TERM_TRACKS:
                shortTermTracks = new TopTracks(filename);
                break;
            default:
                throw new RuntimeException("Invalid filename");
        }
    }

    private void checkExistenceOfCacheFiles(){
        longTermArtistsExists = checkIfCacheExists(LONG_TERM_ARTISTS);
        mediumTermArtistsExists = checkIfCacheExists(MEDIUM_TERM_ARTISTS);
        shortTermArtistsExists = checkIfCacheExists(SHORT_TERM_ARTISTS);
        longTermTracksExists = checkIfCacheExists(LONG_TERM_TRACKS);
        shortTermTracksExists = checkIfCacheExists(SHORT_TERM_TRACKS);
        mediumTermTracksExists = checkIfCacheExists(MEDIUM_TERM_TRACKS);
    }

    public boolean checkIfCacheExists(String filename){
        return new File(filename).exists();
    }

    private void initialize(){
        if(!new File("cacheStatus.txt").exists()){
            System.out.println("Cache file does not exist");
            System.out.println("Cache file is not yet created. Initializing empty cache...");
            checkExistenceOfCacheFiles();
            saveTimeCreated(0);
            initializeCacheStatusFile();
        }else{
            readStatusFile();
        }
    }

    public void saveTimeCreated(int n){
        if(n == 0){
            timeCreated = System.currentTimeMillis();
        }
        else if(n == 1){
            readTimeCreatedFromFile();
        }
    }

    private void readTimeCreatedFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("cacheStatus.txt"))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=",2);
                if(parts.length == 2){
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    if(key.equals("timeCreated")){
                        timeCreated = Long.parseLong(value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void initializeCacheStatusFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("cacheStatus.txt"))){
            writer.write("timeCreated=" + timeCreated);
            writer.newLine();
            writer.write(LONG_TERM_ARTISTS + "=" + longTermArtistsExists);
            writer.newLine();
            writer.write(MEDIUM_TERM_ARTISTS + "=" + mediumTermArtistsExists);
            writer.newLine();
            writer.write(SHORT_TERM_ARTISTS + "=" + shortTermArtistsExists);
            writer.newLine();
            writer.write(LONG_TERM_TRACKS + "=" + longTermTracksExists);
            writer.newLine();
            writer.write(MEDIUM_TERM_TRACKS + "=" + mediumTermTracksExists);
            writer.newLine();
            writer.write(SHORT_TERM_TRACKS + "=" + shortTermTracksExists);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void readStatusFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("cacheStatus.txt"))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=",2);
                if(parts.length == 2){
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    setCredential(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private void setCredential(String key, String value) {
        switch (key) {
            case "timeCreated":
                this.timeCreated = Long.parseLong(value);
                break;
            case LONG_TERM_ARTISTS:
                this.longTermArtistsExists = Boolean.parseBoolean(value);
                break;
            case MEDIUM_TERM_ARTISTS:
                this.mediumTermArtistsExists = Boolean.parseBoolean(value);
                break;
            case SHORT_TERM_ARTISTS:
                this.shortTermArtistsExists = Boolean.parseBoolean(value);
                break;
            case LONG_TERM_TRACKS:
                this.longTermTracksExists = Boolean.parseBoolean(value);
                break;
            case MEDIUM_TERM_TRACKS:
                this.mediumTermTracksExists = Boolean.parseBoolean(value);
                break;
            case SHORT_TERM_TRACKS:
                this.shortTermTracksExists = Boolean.parseBoolean(value);
                break;
            default:
                // Handle unknown key or ignore
                break;
        }
    }

    public CacheFileRepo<String, Entity> getLongTermArtists() {
        return longTermArtists;
    }

    public CacheFileRepo<String, Entity> getMediumTermArtists() {
        return mediumTermArtists;
    }

    public CacheFileRepo<String, Entity> getShortTermArtists() {
        return shortTermArtists;
    }

    public CacheFileRepo<String, Entity> getLongTermTracks() {
        return longTermTracks;
    }

    public CacheFileRepo<String, Entity> getShortTermTracks() {
        return shortTermTracks;
    }

    public CacheFileRepo<String, Entity> getMediumTermTracks() {
        return mediumTermTracks;
    }

    public void setLongTermArtists(CacheFileRepo<String, Entity> longTermArtists) {
        this.longTermArtists = longTermArtists;
    }

    public void setMediumTermArtists(CacheFileRepo<String, Entity> mediumTermArtists) {
        this.mediumTermArtists = mediumTermArtists;
    }

    public void setShortTermArtists(CacheFileRepo<String, Entity> shortTermArtists) {
        this.shortTermArtists = shortTermArtists;
    }

    public void setLongTermTracks(CacheFileRepo<String, Entity> longTermTracks) {
        this.longTermTracks = longTermTracks;
    }

    public void setShortTermTracks(CacheFileRepo<String, Entity> shortTermTracks) {
        this.shortTermTracks = shortTermTracks;
    }

    public void setMediumTermTracks(CacheFileRepo<String, Entity> mediumTermTracks) {
        this.mediumTermTracks = mediumTermTracks;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public static void main(String[] args) {
        Cache cache = Cache.getInstance();

        System.out.println(cache.getTimeCreated());
    }

    //TODO: Implement cache
    //TODO - every day at 00:00:00, the cache should be cleared
    //TODO - every time a user requests a new list, the cache should be updated
    //TODO - every time a user requests a list, the cache should be checked first
    //TODO - if the cache is empty, the data should be fetched from the API
    //TODO - if the cache is not empty, the data should be fetched from the cache
}
