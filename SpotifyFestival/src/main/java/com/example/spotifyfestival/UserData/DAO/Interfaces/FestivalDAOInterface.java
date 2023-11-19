package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Domain.Festival;
import com.example.spotifyfestival.UserData.Repos.FestivalRepo;

public interface FestivalDAOInterface {
    Festival create(Festival festival);
    Festival getById(int id);
    FestivalRepo getAllFestivals();
    Festival update(Festival festival);
    void delete(int id);
}
