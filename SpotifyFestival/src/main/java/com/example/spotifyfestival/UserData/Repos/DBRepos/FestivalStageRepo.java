package com.example.spotifyfestival.UserData.Repos.DBRepos;

import com.example.spotifyfestival.UserData.Domain.FestivalStage;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DBGenericRepository;

public class FestivalStageRepo extends DBGenericRepository<Integer, FestivalStage> {

    private static FestivalStageRepo instance;

    private FestivalStageRepo() {
        // Private constructor to prevent instantiation outside of this class
    }

    public static FestivalStageRepo getInstance() {
        if (instance == null) {
            instance = new FestivalStageRepo();
        }
        return instance;
    }

}
