package com.example.spotifyfestival.UserData.Repos;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
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

    private VenueRepo(){}

    public static VenueRepo getInstance(){
        if(instance == null){
            instance = new VenueRepo();

        }
        return instance;
    }
    public static VenueRepo readVenuesFromDB(){

        String tableName = "venues";

        ObservableList<Venue> venues = FXCollections.observableArrayList();

        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DB.connect("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            venues.clear();
            while (rs.next()) {
                int venue_id = rs.getInt("venue_id");
                String city = rs.getString("city");
                String name = rs.getString("name");
                String address = rs.getString("address");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");

                Venue venue = new Venue(city,name,address, String.valueOf(latitude), String.valueOf(longitude));
                venues.add(venue);

                try {
                    instance.add(String.valueOf(venue_id), venue);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }
            for(int i =0; i< venues.size(); i++){
                System.out.println(venues.get(i).getVenueName());
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            venues.clear();
        }
        return instance;
    }

}

