package com.example.spotifyfestival.DatabasePackage.DAO.Interfaces;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Concert;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.ConcertRepo;

public interface ConcertDAOInterface {
    Concert create(Concert concert);
    Concert getById(int id);
    ConcertRepo getAllConcerts();
    Concert update(Concert concert);
    void delete(int id);
}
