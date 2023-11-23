package com.example.spotifyfestival.UserData.Repos.DBRepos;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DBGenericRepository;

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
