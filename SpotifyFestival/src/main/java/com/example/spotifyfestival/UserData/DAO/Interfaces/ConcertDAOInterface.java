package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.ConcertsAndFestivals.Concert;
import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Repos.ArtistRepo;
import com.example.spotifyfestival.UserData.Repos.ConcertRepo;

public interface ConcertDAOInterface {
    Concert create(Concert concert);
    Concert getById(int id);
    ConcertRepo getAllVenues();
    Concert update(Concert concert);
    void delete(int id);
}
