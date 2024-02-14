package com.example.spotifyfestival.NewFeatures.CacheImplementation;

import java.io.*;
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

        cache.getLongTermArtists().resetFile();
        cache.getMediumTermArtists().resetFile();
        cache.getShortTermArtists().resetFile();
        cache.getLongTermTracks().resetFile();
        cache.getMediumTermTracks().resetFile();
        cache.getShortTermTracks().resetFile();
    }

    public void checkHoursPassed(){
        long timeCreated = cache.getTimeCreated();
        long timeNow = System.currentTimeMillis();
        long hoursPassed = (timeNow - timeCreated) / 3600000;
        long secondsPassed = (timeNow - timeCreated) / 1000;
        System.out.println("Hours passed: " + hoursPassed);
        System.out.println("Seconds passed: " + secondsPassed);
        if( hoursPassed >= 24){
            resetCache();
            cache.saveTimeCreatedToFile();
            cache.initialize();
        }
    }

    public void checkReset(){
        System.out.println("Cache reset started tho check if 24 hours passed...");
        // Schedule a task to check and regenerate the access token every hour
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::checkHoursPassed, 0, 1, TimeUnit.HOURS);
    }

    @Override
    public void run() {
        checkReset();
    }
}
