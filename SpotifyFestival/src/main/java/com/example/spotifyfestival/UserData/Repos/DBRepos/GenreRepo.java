package com.example.spotifyfestival.UserData.Repos.DBRepos;

import com.example.spotifyfestival.UserData.Domain.Genre;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DBGenericRepository;

public class GenreRepo extends DBGenericRepository<String, Genre> {
    private static GenreRepo instance;

    private GenreRepo(){}

    public static GenreRepo getInstance(){
        if(instance == null){
            instance = new GenreRepo();

        }
        return instance;
    }
}
