package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.Repos.DBRepos.VenueRepo;

public interface VenueDAOInterface {
    Venue create(Venue venue);
    Venue getById(int id);
    VenueRepo getAllVenues();
    Venue update(Venue venue);
    void delete(int id);
}
