package com.example.spotifyfestival.NewFeatures.CacheImplementation;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Track;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.TopArtists.TopArtists;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.TopTracks.TopTracks;

import java.io.*;

public class Cache {
    //Top Artists
    private CacheFileRepo<String, Artist> longTermArtists;
    private CacheFileRepo<String, Artist> mediumTermArtists;
    private CacheFileRepo<String, Artist> shortTermArtists;
    private CacheFileRepo<String, Track> longTermTracks;
    private CacheFileRepo<String, Track> shortTermTracks;
    private CacheFileRepo<String, Track> mediumTermTracks;
    private static Cache instance;
    private long timeCreated;

    public static Cache getInstance() {
        if(instance == null){
            instance = new Cache();
        }
        return instance;
    }

    private Cache() {
        readTimeCreatedFromFile();
    }
    public void listAll(){
        longTermArtists.listFile();
    }

    public void saveTimeCreatedToFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("timeCreatedCache.txt"))){
            writer.write(String.valueOf(System.currentTimeMillis()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public long readTimeCreatedFromFile(){
        if(!new File("timeCreatedCache.txt").exists()){
            saveTimeCreatedToFile();
        }
        try(BufferedReader reader = new BufferedReader(new FileReader("timeCreatedCache.txt"))){
            return Long.parseLong(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public CacheFileRepo<String, Artist> getLongTermArtists() {
        return longTermArtists;
    }

    public CacheFileRepo<String, Artist> getMediumTermArtists() {
        return mediumTermArtists;
    }

    public CacheFileRepo<String, Artist> getShortTermArtists() {
        return shortTermArtists;
    }

    public CacheFileRepo<String, Track> getLongTermTracks() {
        return longTermTracks;
    }

    public CacheFileRepo<String, Track> getShortTermTracks() {
        return shortTermTracks;
    }

    public CacheFileRepo<String, Track> getMediumTermTracks() {
        return mediumTermTracks;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    //TODO: Implement cache
    //TODO - every day at 00:00:00, the cache should be cleared
    //TODO - every time a user requests a new list, the cache should be updated
    //TODO - every time a user requests a list, the cache should be checked first
    //TODO - if the cache is empty, the data should be fetched from the API
    //TODO - if the cache is not empty, the data should be fetched from the cache
}
