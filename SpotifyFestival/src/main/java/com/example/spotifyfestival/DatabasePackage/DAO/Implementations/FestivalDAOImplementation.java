package com.example.spotifyfestival.DatabasePackage.DAO.Implementations;

import com.example.spotifyfestival.DatabasePackage.DBHelpers.DB;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.DatabasePackage.DAO.Interfaces.FestivalDAOInterface;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Festival;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalRepo;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.VenueRepo;
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

                VenueRepo venueRepo = VenueRepo.getInstance();
                Venue venue = venueRepo.getItem(venue_id);

                Festival festival = new Festival(festival_id, name, venue);

                try {
                    festivalRepo.add(festival_id, festival);
                    festivals.add(festival);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
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
