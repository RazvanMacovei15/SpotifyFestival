package com.example.spotifyfestival.UserData.Repos.DBRepos;

import com.example.spotifyfestival.UserData.Domain.ArtistGenre;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DBGenericRepository;

public class ArtistGenreRepo extends DBGenericRepository<String, ArtistGenre> {
    private static ArtistGenreRepo instance;

    private ArtistGenreRepo(){}

    public static ArtistGenreRepo getInstance(){
        if(instance == null){
            instance = new ArtistGenreRepo();

        }
        return instance;
    }
}