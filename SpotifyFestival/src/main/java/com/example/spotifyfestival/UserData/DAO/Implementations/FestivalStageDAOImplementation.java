package com.example.spotifyfestival.UserData.DAO.Implementations;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.DAO.Interfaces.StagesDAOInterface;
import com.example.spotifyfestival.UserData.Domain.Festival;
import com.example.spotifyfestival.UserData.Domain.FestivalStage;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DB;
import com.example.spotifyfestival.UserData.Repos.FestivalRepo;
import com.example.spotifyfestival.UserData.Repos.FestivalStageRepo;
import com.example.spotifyfestival.UserData.Repos.VenueRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FestivalStageDAOImplementation implements StagesDAOInterface {
    @Override
    public FestivalStage create(FestivalStage stage) {
        return null;
    }

    @Override
    public FestivalStage getById(int id) {
        return null;
    }

    @Override
    public FestivalStageRepo getAllStages() {
        FestivalStageRepo stageRepo =FestivalStageRepo.getInstance();

        String tableName = "Stages";

        ObservableList<FestivalStage> festivalStages = FXCollections.observableArrayList();

        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DB.connect("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            festivalStages.clear();
            while (rs.next()) {
                int stage_id = rs.getInt("stage_id");
                String name = rs.getString("name");
                int venue_id = rs.getInt("venue_id");

                VenueDAOImplementation venueDAOImplementation = new VenueDAOImplementation();

                VenueRepo venueRepo = venueDAOImplementation.getAllVenues();

                Venue venue = null;

                // Iterate through the map and check if the key is equal to keyToCheck
                for (Map.Entry<String, Venue> entry : venueRepo.getAll().entrySet()) {
                    String key = entry.getKey();
                    Venue value = entry.getValue();

                    if (key.equals(String.valueOf(venue_id))) {
                        // Key found, do something with the corresponding value (Venue)
                        System.out.println("Key found: " + key + ", Value: " + value);
                        venue = value;
                        System.out.println(venue.getVenueName());
                        break;  // Assuming you want to stop searching after finding the key
                    }
                }

                FestivalStage festivalStage = new FestivalStage(name, venue);

                try {
                    stageRepo.add(String.valueOf(stage_id), festivalStage);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }
            for(int i =0; i< festivalStages.size(); i++){
                System.out.println(festivalStages.get(i).getVenue().getVenueName());
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            festivalStages.clear();
        }
        return stageRepo;
    }

    @Override
    public FestivalStage update(FestivalStage stage) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    public static void main(String[] args) {
        FestivalStageDAOImplementation festivalStageDAOImplementation = new FestivalStageDAOImplementation();
        FestivalStageRepo festivalStageRepo = festivalStageDAOImplementation.getAllStages();

    }
}
