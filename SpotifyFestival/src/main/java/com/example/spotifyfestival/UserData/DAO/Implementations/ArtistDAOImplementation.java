package com.example.spotifyfestival.UserData.DAO.Implementations;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.DAO.Interfaces.ArtistDAOInterface;
import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Domain.Genre;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.CRUDHelper;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DB;
import com.example.spotifyfestival.UserData.Repos.DBRepos.ArtistGenreRepo;
import com.example.spotifyfestival.UserData.Repos.DBRepos.ArtistRepo;
import com.example.spotifyfestival.UserData.Repos.DBRepos.GenreRepo;
import com.example.spotifyfestival.UserData.Repos.DBRepos.VenueRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistDAOImplementation implements ArtistDAOInterface {
    String tableName = "Artists";

    @Override
    public Artist create(Artist artist) {
        return null;
    }

    @Override
    public void createArtist(int artist_id, String name, String spotify_id) {
        ObservableList<Genre> genres = null;
        int id = (int) CRUDHelper.create(
                tableName,
                new String[]{"artist_id", "name", "spotify_id"},
                new Object[]{artist_id, name, spotify_id},
                new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR}
        );
    }

    @Override
    public Artist getById(int id) {
        return null;
    }

    @Override
    public ArtistRepo getAllArtists() {
        ArtistRepo artistRepo = ArtistRepo.getInstance();

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
    public void update(Artist newArtist) {

        String artist_id = "7";
        //udpate database
        int rows = (int) CRUDHelper.update(
                tableName,
                new String[]{"artist_id", "name", "spotify_id"},
                new Object[]{7, newArtist.getName(), newArtist.getSpotify_id()},
                new int[]{Types.INTEGER, Types.VARCHAR, Types.INTEGER},
                artist_id,
                Types.INTEGER,
                newArtist.getId()
        );
        if (rows == 0)
            throw new IllegalStateException("Person to be updated with id " + newArtist.getId() + " didn't exist in database");
    }

    @Override
    public void delete(int id) {

    }

    public void readAllArtists(String tableName, ArtistRepo artistRepo){

        String query = "SELECT * FROM " + tableName;

        try (Connection connection = DB.connect("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int artist_id = rs.getInt("artist_id");
                String name = rs.getString("name");
                String spotify_id = rs.getString("spotify_id");
                ObservableList<Genre> gList = FXCollections.observableArrayList();

                Artist artist = new Artist(name, gList, spotify_id);

                try {
                    artistRepo.add(String.valueOf(artist_id), artist);
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

    public static void main(String[] args) {
        VenueDAOImplementation venueDAOImplementation = new VenueDAOImplementation();
        venueDAOImplementation.generateVenueRepo();
        VenueRepo venueRepo = VenueRepo.getInstance();
        venueRepo.list();

        System.out.println();

        GenreDAOImplementation genreDAOImplementation = new GenreDAOImplementation();
        genreDAOImplementation.generateGenreRepo();
        GenreRepo genreRepo = GenreRepo.getInstance();
        genreRepo.list();

        System.out.println();

        ArtistRepo artistRepo = ArtistRepo.getInstance();
        String tableName = "Artists";
        ArtistDAOImplementation artistDAOImplementation = new ArtistDAOImplementation();
        artistDAOImplementation.readAllArtists(tableName, artistRepo);
        artistRepo.list();

        System.out.println();

        ArtistGenreRepo artistGenreRepo = ArtistGenreRepo.getInstance();
        ArtistGenreDAOImplementation artistGenreDAOImplementation = new ArtistGenreDAOImplementation();

        artistGenreDAOImplementation.populateArtistsWithGenres(artistRepo, genreRepo, artistGenreRepo);

        String name = "lala";
        String spotify_id = "blala";
        ObservableList<Genre> genres = null;

        artistDAOImplementation.createArtist(22, name, spotify_id);
        Artist newArtist = new Artist("blalala", genres, "tralalala");
        artistDAOImplementation.update(newArtist);


    }
}
