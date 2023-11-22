package com.example.spotifyfestival.UserData.Repos.DBRepos;

import com.example.spotifyfestival.UserData.Domain.Festival;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DBGenericRepository;

public class FestivalRepo extends DBGenericRepository<String, Festival> {
    private static FestivalRepo instance;

    private FestivalRepo(){}

    public static FestivalRepo getInstance(){
        if(instance == null){
            instance = new FestivalRepo();

        }
        return instance;
    }
}
