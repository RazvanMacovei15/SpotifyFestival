package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Concert;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;

public class ConcertRepo extends DBGenericRepository<Integer, Concert> {
    private static ConcertRepo instance;

    private ConcertRepo(){}

    public static ConcertRepo getInstance(){
        if(instance == null){
            instance = new ConcertRepo();

        }
        return instance;
    }
}
