package com.example.spotifyfestival.UserData.DAO.Implementations;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.DAO.Interfaces.VenueDAOInterface;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DB;
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

public class VenueDAOImplementation implements VenueDAOInterface {
    @Override
    public Venue create(Venue venue) {
        return null;
    }

    @Override
    public Venue getById(int id) {
        return null;
    }

    @Override
    public VenueRepo getAllVenues() {

        VenueRepo venueRepo = VenueRepo.getInstance();

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
                    venueRepo.add(String.valueOf(venue_id), venue);
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
        return venueRepo;
    }

    @Override
    public Venue update(Venue venue) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    public void populateVenueRepo(String tableName,VenueRepo venueRepo){

        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DB.connect("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int venue_id = rs.getInt("venue_id");
                String city = rs.getString("city");
                String name = rs.getString("name");
                String address = rs.getString("address");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");

                Venue venue = new Venue(city,name,address, String.valueOf(latitude), String.valueOf(longitude));


                try {
                    venueRepo.add(String.valueOf(venue_id), venue);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");

        }

    }

    public void generateVenueRepo(){
        VenueDAOImplementation venueDAOImplementation = new VenueDAOImplementation();
        VenueRepo venueRepo = VenueRepo.getInstance();
        String tableName = "Venues";

        venueDAOImplementation.populateVenueRepo(tableName, venueRepo);
    }


    public static void main(String[] args) {
        VenueDAOImplementation venueDAOImplementation = new VenueDAOImplementation();
        VenueRepo venueRepo = VenueRepo.getInstance();
        String tableName = "Venues";

        venueDAOImplementation.populateVenueRepo(tableName, venueRepo);
        venueRepo.list();
    }
}
