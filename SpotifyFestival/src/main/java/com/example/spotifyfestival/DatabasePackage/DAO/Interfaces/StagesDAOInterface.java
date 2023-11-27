package com.example.spotifyfestival.DatabasePackage.DAO.Interfaces;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.FestivalStage;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalStageDAO;


public interface StagesDAOInterface {
    FestivalStage create(FestivalStage stage);
    FestivalStage getById(int id);
    FestivalStageDAO getAllStages();
    FestivalStage update(FestivalStage stage);
    void delete(int id);
}
