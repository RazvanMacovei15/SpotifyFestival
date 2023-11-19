package com.example.spotifyfestival.UserData.Repos;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DB;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DBGenericRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VenueRepo extends DBGenericRepository<String, Venue> {

    private static VenueRepo instance;

    private VenueRepo() {
        // Private constructor to prevent instantiation outside of this class
    }

    public static VenueRepo getInstance() {
        if (instance == null) {
            instance = new VenueRepo();
        }
        return instance;
    }
    @Override
    public void getAllFromDB() {

        }
    }

