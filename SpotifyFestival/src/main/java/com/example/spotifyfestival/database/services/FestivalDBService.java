package com.example.spotifyfestival.database.services;

import com.example.spotifyfestival.database.dao.EntireDB_Repo;

public class FestivalDBService {
    protected EntireDB_Repo dbRepo;

    public FestivalDBService() {
//        dbRepo = new EntireDB_Repo();
//        dbRepo.initialize();
    }

    public EntireDB_Repo getDbRepo() {
        return dbRepo;
    }
}