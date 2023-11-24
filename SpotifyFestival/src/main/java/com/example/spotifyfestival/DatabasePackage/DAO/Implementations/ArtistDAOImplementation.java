package com.example.spotifyfestival.DatabasePackage.DAO.Implementations;

import com.example.spotifyfestival.DatabasePackage.DAO.Interfaces.ArtistDAOInterface;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DB;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistDAOImplementation implements ArtistDAOInterface {
    String tableName = "Artists";
    String[] columns = {"artist_id", "name", "spotify_id"};
    String[] updateColumns = {"name", "spotify_id"};
    String readQuery = "SELECT * FROM " + tableName;
    String deleteQuery = "DELETE FROM " + tableName + " WHERE artist_id = ?";
    int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR};
    protected static ObservableList<Artist> artistsList = FXCollections.observableArrayList();
    protected static ArtistRepo artistRepo = ArtistRepo.getInstance();
    public static ObservableList<Artist> getArtistsList() {
        return artistsList;
    }
    public static void setArtistsList(ObservableList<Artist> artistsList) {
        ArtistDAOImplementation.artistsList = artistsList;
    }
    public static ArtistRepo getArtistRepo() {
        return artistRepo;
    }
    public static void setArtistRepo(ArtistRepo artistRepo) {
        ArtistDAOImplementation.artistRepo = artistRepo;
    }
    public ArtistDAOImplementation() {
        artistRepo = getAllArtists();
    }
    @Override
    public void insertArtistInDB(int artist_id, String name, ObservableList<Genre> genres, String spotify_id) {
        //update DB
        int id = (int) CRUDHelper.create(
                tableName,
                columns,
                new Object[]{artist_id, name, spotify_id},
                types
        );
        artistsList.add(new Artist(
                artist_id,
                name,
                genres,
                spotify_id
        ));
    }

    @Override
    public Optional<Artist> getById(int id) {
        for (Artist artist : artistsList) {
            if (artist.getId().equals(id))
                return Optional.of(artist);
        }
        return Optional.empty();
    }

    @Override
    public ArtistRepo getAllArtists() {

        try (Connection connection = DB.connect("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(readQuery);
            ResultSet rs = statement.executeQuery();
            artistsList.clear();
            while (rs.next()) {
                int artist_id = rs.getInt("artist_id");
                String name = rs.getString("name");
                String spotify_id = rs.getString("spotify_id");
                ObservableList<Genre> genres = null;

                Artist artist = new Artist(artist_id, name, genres, spotify_id);

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
                    LocalDateTime.now() + ": Could not load Artists from database ");
            artistsList.clear();
        }
        return artistRepo;
    }

    @Override
    public Object readArtistAttribute(String attributeField, String indexID, int index) {

        Object thing = CRUDHelper.read(tableName, attributeField, Types.VARCHAR, indexID, Types.INTEGER, index);
        return thing;
    }

    @Override
    public void update(Artist newArtist) {
        //update DB
        long rows = CRUDHelper.update(
                tableName,
                updateColumns,
                new Object[]{newArtist.getName(), newArtist.getSpotify_id()},
                new int[]{Types.VARCHAR, Types.VARCHAR},
                "artist_id",
                Types.INTEGER,
                newArtist.getId()
        );
        if (rows == 0)
            throw new IllegalStateException("Artist to update with id " + newArtist.getId() + "doesn't exist in the database!");
        //update cache
        Optional<Artist> optionalArtist = getById(newArtist.getId());
        optionalArtist.ifPresentOrElse((oldArtist) -> {
            artistsList.remove(oldArtist);
            artistsList.add(newArtist);
        }, () -> {
            throw new IllegalStateException("Artist to update with id " + newArtist.getId() + "doesn't exist in the database!");
        });
    }

    @Override
    public int delete(Integer id) {
        if (id == null) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not delete from " + tableName +
                            " because the provided id is null.");
            return -1; // or throw an exception, depending on your error handling strategy
        }
        // Build the SQL delete query
        try (Connection conn = DB.connect("festivalDB")) {
            // Prepare and execute the delete query
            PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            // Log an error if the operation fails
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not delete from " + tableName +
                            " by id " + id + " because " + e.getCause());
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

        ArtistRepo artistRepo = ArtistRepo.getInstance();
        ArtistDAOImplementation artistDAOImplementation = new ArtistDAOImplementation();
        System.out.println(artistDAOImplementation.getById(23));
        artistRepo.list();

        System.out.println();

        ArtistGenreRepo artistGenreRepo = ArtistGenreRepo.getInstance();
        ArtistGenreDAOImplementation artistGenreDAOImplementation = new ArtistGenreDAOImplementation();
        artistGenreDAOImplementation.populateArtistsWithGenres(artistRepo, genreRepo, artistGenreRepo);
        artistGenreRepo.list();

        System.out.println();

        FestivalDAOImplementation festivalDAOImplementation = new FestivalDAOImplementation();
        FestivalRepo festivalRepo = festivalDAOImplementation.getAllFestivals();
        festivalRepo.list();

        System.out.println();

        FestivalStageDAOImplementation festivalStageDAOImplementation = new FestivalStageDAOImplementation();
        FestivalStageRepo festivalStageRepo = festivalStageDAOImplementation.getAllStages();
        festivalStageRepo.list();

        System.out.println();

        for (int i = 1; i < festivalStageRepo.getSize() + 1; i++) {
            System.out.println(festivalStageRepo.getItem(i).getVenue());
        }

        System.out.println();

        ConcertDAOImplementation concertDAOImplementation = new ConcertDAOImplementation();
        ConcertRepo concertRepo = concertDAOImplementation.getAllConcerts();
        concertRepo.list();
        System.out.println(concertRepo.getItem(5).getDescription());

        int id = 21;
        String name = "Eminem";
        ObservableList<Genre> genres = null;
        String spotify_id = "not yet determined";
        Artist newArtist = new Artist(id, name, genres, spotify_id);
//
//        artistDAOImplementation.insertArtistInDB(id, name, genres, spotify_id);

//        artistDAOImplementation.update(newArtist);
//        artistDAOImplementation.delete(21);
        int idToDelete = 20; // Replace this with the actual ID you want to delete
//        int rowsAffected = artistDAOImplementation.delete(idToDelete);

        Object freshArtist = artistDAOImplementation.readArtistAttribute( "name", "artist_id", idToDelete);
        System.out.println(freshArtist);
    }
}
