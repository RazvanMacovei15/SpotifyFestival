package com.example.spotifyfestival.Services.DAOServices;

import com.example.spotifyfestival.DatabasePackage.DAO.EntireDB_Repo;

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