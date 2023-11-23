package com.example.spotifyfestival.UserData.Repos.DBRepos;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DBGenericRepository;

public class VenueRepo extends DBGenericRepository<Integer, Venue> {
    private static VenueRepo instance;

    private VenueRepo(){}

    public static VenueRepo getInstance(){
        if(instance == null){
            instance = new VenueRepo();

        }
        return instance;
    }
}

