package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.UserData.Domain.FestivalStage;
import com.example.spotifyfestival.UserData.Repos.DBRepos.FestivalStageRepo;


public interface StagesDAOInterface {
    FestivalStage create(FestivalStage stage);
    FestivalStage getById(int id);
    FestivalStageRepo getAllStages();
    FestivalStage update(FestivalStage stage);
    void delete(int id);
}
