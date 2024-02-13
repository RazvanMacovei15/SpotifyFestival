package com.example.spotifyfestival.NewFeatures.CacheImplementation;

import java.io.*;

public class CacheReset {
    private long timeCreated;
    private Cache cache;

    public CacheReset(Cache cache) {
        this.cache = cache;
        this.timeCreated = readTimeCreatedFromFile();

    }

    public void resetCache(){
        cache.getLongTermArtists().resetFile();
        cache.getMediumTermArtists().resetFile();
        cache.getShortTermArtists().resetFile();
        cache.getLongTermTracks().resetFile();
        cache.getMediumTermTracks().resetFile();
        cache.getShortTermTracks().resetFile();
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

    public void checkHoursPassed(){
        long timeNow = System.currentTimeMillis();
        long hoursPassed = (timeNow - timeCreated) / 3600000;
        long secondsPassed = (timeNow - timeCreated) / 1000;
        System.out.println("Hours passed: " + hoursPassed);
        System.out.println("Seconds passed: " + secondsPassed);
        if(hoursPassed >= 24){
            System.out.println("Cache reset in progress...");
            resetCache();
            saveTimeCreatedToFile();
        }
    }

}
