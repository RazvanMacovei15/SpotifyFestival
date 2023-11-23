package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.ConcertsAndFestivals.Concert;
import com.example.spotifyfestival.UserData.Repos.DBRepos.ConcertRepo;

public interface ConcertDAOInterface {
    Concert create(Concert concert);
    Concert getById(int id);
    ConcertRepo getAllConcerts();
    Concert update(Concert concert);
    void delete(int id);
}
