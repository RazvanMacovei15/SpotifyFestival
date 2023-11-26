package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;
import com.example.spotifyfestival.GenericsPackage.MemoryRepository;

public class GenreRepo extends DBGenericRepository<Integer, Genre> {
    private static GenreRepo instance;

    private GenreRepo(){}

    public static GenreRepo getInstance(){
        if(instance == null){
            instance = new GenreRepo();

        }
        return instance;
    }
}
