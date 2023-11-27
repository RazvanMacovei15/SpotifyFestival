package com.example.spotifyfestival.DatabasePackage.DAO.Implementations;

import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Concert;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.DatabasePackage.DAO.Interfaces.ConcertDAOInterface;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.FestivalStage;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistDAO;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalStageDAO;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.VenueDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcertDAO implements ConcertDAOInterface {
    @Override
    public Concert create(Concert concert) {
        return null;
    }

    @Override
    public Concert getById(int id) {
        return null;
    }

    @Override
    public com.example.spotifyfestival.RepositoryPackage.DBRepos.ConcertDAO getAllConcerts() {
        com.example.spotifyfestival.RepositoryPackage.DBRepos.ConcertDAO concertRepo = com.example.spotifyfestival.RepositoryPackage.DBRepos.ConcertDAO.getInstance();

        String tableName = "Concerts";

        ObservableList<Concert> concerts = FXCollections.observableArrayList();

        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DBUtils.getConnection("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            concerts.clear();
            while (rs.next()) {
                int concert_id = rs.getInt("concert_id");
                String description = rs.getString("description");
                String start_date = rs.getString("start_date");
                String start_time = rs.getString("start_time");
                int venue_id = rs.getInt("venue_id");
                int artist_id = rs.getInt("artist_id");
                int stage_id = rs.getInt("stage_id");

                VenueDAO venueRepo = VenueDAO.getInstance();
                Venue venue = venueRepo.getItem(venue_id);

                ArtistDAO artistRepo = ArtistDAO.getInstance();
                Artist artist = artistRepo.getItem(artist_id);

                FestivalStageDAO festivalStageRepo = FestivalStageDAO.getInstance();
                FestivalStage festivalStage = festivalStageRepo.getItem(stage_id);

                Concert concert = new Concert(concert_id, description, artist, venue, start_date, start_time);

                concertRepo.add(concert_id, concert);
                concerts.add(concert);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            concerts.clear();
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
        return concertRepo;
    }

    @Override
    public Concert update(Concert concert) {
        return null;
    }

    @Override
    public void delete(int id) {

    }


}
