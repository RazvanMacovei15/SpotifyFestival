package com.example.spotifyfestival.Services;

import com.example.spotifyfestival.RepositoryPackage.DBRepos.EntireDB_Repo;

public class FestivalDBService {
    protected EntireDB_Repo dbRepo;

    public FestivalDBService() {
        dbRepo = new EntireDB_Repo();
        dbRepo.initialize();
    }

    public EntireDB_Repo getDbRepo() {
        return dbRepo;
    }
}