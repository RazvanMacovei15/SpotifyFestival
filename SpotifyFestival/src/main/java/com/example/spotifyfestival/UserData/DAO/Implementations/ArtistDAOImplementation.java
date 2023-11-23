package com.example.spotifyfestival.UserData.DAO.Implementations;

import com.example.spotifyfestival.ConcertsAndFestivals.Venue;
import com.example.spotifyfestival.UserData.DAO.Interfaces.ArtistDAOInterface;
import com.example.spotifyfestival.UserData.DAO.Interfaces.ArtistGenreDAOInterface;
import com.example.spotifyfestival.UserData.DAO.Interfaces.FestivalDAOInterface;
import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Domain.FestivalStage;
import com.example.spotifyfestival.UserData.Domain.Genre;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.CRUDHelper;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DB;
import com.example.spotifyfestival.UserData.Repos.DBRepos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistDAOImplementation implements ArtistDAOInterface {
    String tableName = "Artists";
    String[] columns = {"artist_id", "name", "spotify_id"};
    ObservableList<Artist> artistsList = FXCollections.observableArrayList();


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
            artistsList.clear();
            while (rs.next()) {
                int artist_id = rs.getInt("artist_id");
                String name = rs.getString("name");
                String spotify_id = rs.getString("spotify_id");
                ObservableList<Genre> genres = null;

                Artist artist = new Artist(artist_id, name, genres ,spotify_id);

                try {
                    artistRepo.add(artist_id, artist);
                    artistsList.add(artist);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
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
                columns,
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
    public int delete(int id) {
        String sql = "DELETE FROM " + tableName + " WHERE id = ?";
        try (Connection conn = DB.connect("festivalDB")) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not delete from " + tableName + " by id " + id +
                            " because " + e.getCause());
            return -1;
        }
    }
    public static void main(String[] args) {
        VenueDAOImplementation venueDAOImplementation = new VenueDAOImplementation();
        venueDAOImplementation.generateVenueRepo();
        VenueRepo venueRepo = VenueRepo.getInstance();
        venueRepo.list();

        System.out.println();

        GenreDAOImplementation genreDAOImplementation = new GenreDAOImplementation();

        GenreRepo genreRepo;
        genreRepo = genreDAOImplementation.getAllGenres();
        genreRepo.list();

        System.out.println();

        ArtistRepo artistRepo;
        ArtistDAOImplementation artistDAOImplementation = new ArtistDAOImplementation();
        artistRepo = artistDAOImplementation.getAllArtists();
        artistRepo.list();

        System.out.println();

        ArtistGenreRepo artistGenreRepo = ArtistGenreRepo.getInstance();
        ArtistGenreDAOImplementation artistGenreDAOImplementation = new ArtistGenreDAOImplementation();
        artistGenreDAOImplementation.populateArtistsWithGenres(artistRepo, genreRepo, artistGenreRepo);

        FestivalDAOImplementation festivalDAOImplementation = new FestivalDAOImplementation();
        FestivalRepo festivalRepo = festivalDAOImplementation.getAllFestivals();
        festivalRepo.list();

        System.out.println();

        FestivalStageDAOImplementation festivalStageDAOImplementation = new FestivalStageDAOImplementation();
        FestivalStageRepo festivalStageRepo = festivalStageDAOImplementation.getAllStages();
        festivalStageRepo.list();

        System.out.println();

        for(int i = 1; i < festivalStageRepo.getSize()+1; i++){
            System.out.println(festivalStageRepo.getItem(i).getVenue());
        }

        System.out.println();

        ConcertDAOImplementation concertDAOImplementation = new ConcertDAOImplementation();
        ConcertRepo concertRepo = concertDAOImplementation.getAllConcerts();
        concertRepo.list();
        System.out.println(concertRepo.getItem(5).getDescription());

    }
}
