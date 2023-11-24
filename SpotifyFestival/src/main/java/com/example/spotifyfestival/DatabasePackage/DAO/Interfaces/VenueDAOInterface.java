package com.example.spotifyfestival.DatabasePackage.DAO.Interfaces;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.VenueRepo;

public interface VenueDAOInterface {
    Venue create(Venue venue);
    Venue getById(int id);
    VenueRepo getAllVenues();
    Venue update(Venue venue);
    void delete(int id);
}
