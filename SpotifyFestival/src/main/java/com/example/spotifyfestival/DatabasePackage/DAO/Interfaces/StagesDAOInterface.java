package com.example.spotifyfestival.DatabasePackage.DAO.Interfaces;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.FestivalStage;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalStageRepo;


public interface StagesDAOInterface {
    FestivalStage create(FestivalStage stage);
    FestivalStage getById(int id);
    FestivalStageRepo getAllStages();
    FestivalStage update(FestivalStage stage);
    void delete(int id);
}
