package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.FestivalStage;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;
import com.example.spotifyfestival.GenericsPackage.MemoryRepository;

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
