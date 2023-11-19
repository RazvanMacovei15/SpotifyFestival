package com.example.spotifyfestival.UserData.DAO.Implementations;

import com.example.spotifyfestival.ConcertsAndFestivals.Entity;
import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.DAO.Interfaces.ArtistDAOInterface;
import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DB;
import com.example.spotifyfestival.UserData.Repos.ArtistRepo;
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

public class ArtistDAOImplementation implements ArtistDAOInterface {
    @Override
    public Artist create(Artist artist) {
        return null;
    }

    @Override
    public Artist getById(int id) {
        return null;
    }

    @Override
    public ArtistRepo getAllArtists() {
        ArtistRepo artistRepo = new ArtistRepo();

        String tableName = "Artists";

        ObservableList<Venue> artists = FXCollections.observableArrayList();

        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DB.connect("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            artists.clear();
            while (rs.next()) {
                int artist_id = rs.getInt("artist_id");
                String name = rs.getString("name");
                String spotify_id = rs.getString("spotify_id");

                Artist artist = new Artist(name, spotify_id);

                try {
                    artistRepo.add(String.valueOf(artist_id), artist);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }
            for(int i =0; i< artists.size(); i++){
                System.out.println(artists.get(i).getVenueName());
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            artists.clear();
        }
        return artistRepo;
    }

    @Override
    public Artist update(Artist artist) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    public static void main(String[] args) {
        ArtistDAOImplementation artistDAOImplementation = new ArtistDAOImplementation();
        ArtistRepo artistRepo = artistDAOImplementation.getAllArtists();

        for(int i=1; i<artistRepo.getSize(); i++){
            System.out.println(artistRepo.getItem(String.valueOf(i)).getName());
        }
    }
}