package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Festival;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;

public class FestivalRepo extends DBGenericRepository<Integer, Festival> {
    private static FestivalRepo instance;

    private FestivalRepo(){}

    public static FestivalRepo getInstance(){
        if(instance == null){
            instance = new FestivalRepo();

        }
        return instance;
    }
}
