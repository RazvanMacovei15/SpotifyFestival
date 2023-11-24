package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;

public class ArtistGenreRepo extends DBGenericRepository<Integer, ArtistGenre> {
    private static ArtistGenreRepo instance;

    private ArtistGenreRepo(){}

    public static ArtistGenreRepo getInstance(){
        if(instance == null){
            instance = new ArtistGenreRepo();

        }
        return instance;
    }
}
