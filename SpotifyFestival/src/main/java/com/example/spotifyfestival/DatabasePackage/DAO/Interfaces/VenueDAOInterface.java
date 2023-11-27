package com.example.spotifyfestival.DatabasePackage.DAO.Interfaces;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.VenueDAO;

public interface VenueDAOInterface {
    Venue create(Venue venue);
    Venue getById(int id);
    VenueDAO getAllVenues();
    Venue update(Venue venue);
    void delete(int id);
}
