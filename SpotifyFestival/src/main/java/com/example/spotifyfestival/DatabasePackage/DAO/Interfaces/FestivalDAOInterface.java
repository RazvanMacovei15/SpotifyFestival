package com.example.spotifyfestival.DatabasePackage.DAO.Interfaces;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Festival;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalRepo;

public interface FestivalDAOInterface {
    Festival create(Festival festival);
    Festival getById(int id);
    FestivalRepo getAllFestivals();
    Festival update(Festival festival);
    void delete(int id);
}
