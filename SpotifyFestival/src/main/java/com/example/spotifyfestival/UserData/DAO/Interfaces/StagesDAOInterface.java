package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Domain.FestivalStage;
import com.example.spotifyfestival.UserData.Repos.ArtistRepo;
import com.example.spotifyfestival.UserData.Repos.FestivalStageRepo;


public interface StagesDAOInterface {
    FestivalStage create(FestivalStage stage);
    FestivalStage getById(int id);
    FestivalStageRepo getAllStages();
    FestivalStage update(FestivalStage stage);
    void delete(int id);
}
