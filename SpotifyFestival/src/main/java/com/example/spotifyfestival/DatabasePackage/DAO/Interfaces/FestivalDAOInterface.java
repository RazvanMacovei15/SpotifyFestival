package com.example.spotifyfestival.DatabasePackage.DAO.Interfaces;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Festival;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalDAO;

public interface FestivalDAOInterface {
    Festival create(Festival festival);
    Festival getById(int id);
    FestivalDAO getAllFestivals();
    Festival update(Festival festival);
    void delete(int id);
}
