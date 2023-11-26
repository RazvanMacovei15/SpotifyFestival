package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;
import com.example.spotifyfestival.GenericsPackage.MemoryRepository;

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