package com.example.spotifyfestival.UserData.DAO.Implementations;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.DAO.Interfaces.FestivalDAOInterface;
import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Domain.Festival;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DB;
import com.example.spotifyfestival.UserData.Repos.ArtistRepo;
import com.example.spotifyfestival.UserData.Repos.FestivalRepo;
import com.example.spotifyfestival.UserData.Repos.VenueRepo;
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
        FestivalRepo festivalRepo = new FestivalRepo();

        String tableName = "Festivals";

        ObservableList<Venue> festivals = FXCollections.observableArrayList();

        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DB.connect("festivalDB")) {
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
                for(int i=1;i<=venueRepo.getSize();i++){
                    if(String.valueOf(venue_id).equals(venueRepo.getKey())){
                        venue = venueRepo.getItem(String.valueOf(i));
                    }
                }

                Festival festival = new Festival(name, venue);

                try {
                    festivalRepo.add(String.valueOf(festival_id), festival);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }
            for(int i =0; i< festivals.size(); i++){
                System.out.println(festivals.get(i).getVenueName());
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

    public static void main(String[] args) {
        FestivalDAOImplementation festivalDAOImplementation = new FestivalDAOImplementation();
        FestivalRepo festivalRepo = festivalDAOImplementation.getAllFestivals();
        for(int i=1; i < festivalRepo.getSize(); i++){
            System.out.println(festivalRepo.getItem(String.valueOf(i)).getVenue().getStreetAddress());
        }
    }
}
