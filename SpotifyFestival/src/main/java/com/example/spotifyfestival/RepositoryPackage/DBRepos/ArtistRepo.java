package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;

public class ArtistRepo extends DBGenericRepository<Integer, Artist> {
    private static ArtistRepo instance;

    private ArtistRepo(){}

    public static ArtistRepo getInstance(){
        if(instance == null){
            instance = new ArtistRepo();

        }
        return instance;
    }
}
