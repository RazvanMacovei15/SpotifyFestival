package com.example.spotifyfestival.NewFeatures.CacheImplementation;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Track;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.TopArtists.TopArtists;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.TopTracks.TopTracks;

public class Cache {
    private CacheReset cacheReset;
    //Top Artists
    private CacheFileRepo<String, Artist> longTermArtists;
    private CacheFileRepo<String, Artist> mediumTermArtists;
    private CacheFileRepo<String, Artist> shortTermArtists;
    private CacheFileRepo<String, Track> longTermTracks;
    private CacheFileRepo<String, Track> shortTermTracks;
    private CacheFileRepo<String, Track> mediumTermTracks;
    private static Cache instance;

    public static Cache getInstance() {
        if(instance == null){
            instance = new Cache();
        }
        return instance;
    }

    private Cache() {

        longTermArtists = new TopArtists("long_term_artists.txt");
        mediumTermArtists = new TopArtists("medium_term_artists.txt");
        shortTermArtists = new TopArtists("short_term_artists.txt");
        longTermTracks = new TopTracks("long_term_tracks.txt");
        mediumTermTracks = new TopTracks("medium_term_tracks.txt");
        shortTermTracks = new TopTracks("short_term_tracks.txt");


    }
    public void listAll(){
        longTermArtists.list();
        mediumTermArtists.list();
        shortTermArtists.list();
        longTermTracks.list();
        mediumTermTracks.list();
        shortTermTracks.list();
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

    //TODO: Implement cache
    //TODO - every day at 00:00:00, the cache should be cleared
    //TODO - every time a user requests a new list, the cache should be updated
    //TODO - every time a user requests a list, the cache should be checked first
    //TODO - if the cache is empty, the data should be fetched from the API
    //TODO - if the cache is not empty, the data should be fetched from the cache
}
