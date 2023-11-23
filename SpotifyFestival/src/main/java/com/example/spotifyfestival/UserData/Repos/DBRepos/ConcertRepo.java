package com.example.spotifyfestival.UserData.Repos.DBRepos;

import com.example.spotifyfestival.ConcertsAndFestivals.Concert;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DBGenericRepository;

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
