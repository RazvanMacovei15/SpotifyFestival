package com.example.spotifyfestival.UserData.DAO.Implementations;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.DAO.Interfaces.FestivalDAOInterface;
import com.example.spotifyfestival.UserData.Domain.Festival;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DB;
import com.example.spotifyfestival.UserData.Repos.DBRepos.FestivalRepo;
import com.example.spotifyfestival.UserData.Repos.DBRepos.VenueRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FestivalDAOImplementation implements FestivalDAOInterface {
    @Override
    public Festival create(Festival festival) {
        return null;
    }

    @Override
    public Festival getById(int id) {
        return null;
    }

    @Override
    public FestivalRepo getAllFestivals() {
        FestivalRepo festivalRepo = FestivalRepo.getInstance();

        String tableName = "Festivals";

        ObservableList<Festival> festivals = FXCollections.observableArrayList();

        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DB.connect("FestivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            festivals.clear();
            while (rs.next()) {
                int festival_id = rs.getInt("festival_id");
                String name = rs.getString("name");
                int venue_id = rs.getInt("venue_id");

                VenueDAOImplementation venueDAOImplementation = new VenueDAOImplementation();

                VenueRepo venueRepo = venueDAOImplementation.getAllVenues();
                Venue venue = null;

//                // Iterate through the map and check if the key is equal to keyToCheck
//                for (Map.Entry<String, Venue> entry : venueRepo.getAll().entrySet()) {
//                    String key = entry.getKey();
//                    Venue value = entry.getValue();
//
//                    if (key.equals(String.valueOf(venue_id))) {
//                        // Key found, do something with the corresponding value (Venue)
//                        System.out.println("Key found: " + key + ", Value: " + value);
//                        venue = value;
////                        System.out.println(venue.getVenueName());
//                        break;  // Assuming you want to stop searching after finding the key
//                    }
//                }

                Festival festival = new Festival(name, venue);
                festivals.add(festival);

                try {
                    festivalRepo.add(String.valueOf(festival_id), festival);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }
            for(int i =0; i< festivals.size(); i++){
                System.out.println(festivals.get(i).toString());
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            festivals.clear();
        }
        return festivalRepo;
    }

    @Override
    public Festival update(Festival festival) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
