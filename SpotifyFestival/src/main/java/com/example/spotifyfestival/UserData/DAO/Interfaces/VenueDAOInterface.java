package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.Repos.VenueRepo;
import javafx.beans.Observable;
import javafx.collections.ObservableList;

public interface VenueDAOInterface {
    Venue create(Venue venue);
    Venue getById(int id);
    VenueRepo getAllVenues();
    Venue update(Venue venue);
    void delete(int id);
}
