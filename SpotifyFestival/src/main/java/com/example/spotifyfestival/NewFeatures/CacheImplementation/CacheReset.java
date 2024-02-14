package com.example.spotifyfestival.NewFeatures.CacheImplementation;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CacheReset implements Runnable{

    private Cache cache;

    public CacheReset(Cache cache) {
        this.cache = cache;
        checkHoursPassed();
    }

    public void resetCache(){
        System.out.println("Cache reset in progress...");
        if(cache.checkIfCacheExists("long_term_artists.txt")){
            cache.getLongTermArtists().resetFile();
            cache.setLongTermArtistsExists(false);
            System.out.println("Long term artists cache reset!");
        }

        if(cache.checkIfCacheExists("medium_term_artists.txt"))
        {
            cache.getMediumTermArtists().resetFile();
            cache.setMediumTermArtistsExists(false);
            System.out.println("Medium term artists cache reset!");
        }
        if(cache.checkIfCacheExists("short_term_artists.txt"))
        {
            cache.getShortTermArtists().resetFile();
            cache.setShortTermArtistsExists(false);
            System.out.println("Short term artists cache reset!");
        }
        if(cache.checkIfCacheExists("long_term_tracks.txt"))
        {
            cache.getLongTermTracks().resetFile();
            cache.setLongTermTracksExists(false);
        }
        if(cache.checkIfCacheExists("medium_term_tracks.txt"))
        {
            cache.getMediumTermTracks().resetFile();
            cache.setMediumTermTracksExists(false);
        }
        if(cache.checkIfCacheExists("short_term_tracks.txt"))
        {
            cache.getShortTermTracks().resetFile();
            cache.setShortTermTracksExists(false);
        }
        cache.saveTimeCreated(0);

        System.out.println("Cache reset completed!");
        cache.setCacheStatusFile();
        System.out.println("Cache status file updated!");
    }

    public void checkHoursPassed(){
        long timeCreated = cache.getTimeCreated();
        long timeNow = System.currentTimeMillis();
        long hoursPassed = (timeNow - timeCreated) / 3600000;
        long minutesPassed = (timeNow - timeCreated) / 60000;
        long secondsPassed = (timeNow - timeCreated) / 1000;
        System.out.println("Hours passed: " + hoursPassed);
        System.out.println("Minutes passed: " + minutesPassed);
        System.out.println("Seconds passed: " + secondsPassed);
        if(minutesPassed >= 1){
            resetCache();
        }
    }

    public void checkReset(){
        System.out.println("Cache reset started to check if 10 minutes passed...");
        // Schedule a task to check and regenerate the access token every hour
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
        scheduler.scheduleAtFixedRate(this::checkHoursPassed, 60, 5, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        checkReset();
    }
}
