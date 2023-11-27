package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.GenericsPackage.GenericDAO;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistDAO extends DBGenericRepository<Integer, Artist> implements GenericDAO<Artist> {

    //DB specific attributes
    private final String tableName = "Artists";
    private final String[] columns = {"artist_id", "name", "spotify_id"};
    private final String[] updateColumns = {"name", "spotify_id"};
    private final String readQuery = "SELECT * FROM " + tableName;
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE artist_id = ?";

    private final int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR};
    private final int[] updateTypes = {Types.VARCHAR, Types.VARCHAR};
    public String getReadQuery()
    {
        return readQuery;
    }
    public String getDeleteQuery()
    {
        return deleteQuery;
    }
    //Singleton Creation
    private static ArtistDAO instance;

    private ArtistDAO() {
    }

    public static ArtistDAO getInstance() {
        if (instance == null) {
            instance = new ArtistDAO();
        }
        return instance;
    }

    //TableView JavaFX stuff
    ObservableList<Artist> artistList = FXCollections.observableArrayList();

    public ObservableList<Artist> getArtistList() {
        return artistList;
    }

    //DB related methods
    public void readAllObjectsFromTable() {
        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DBUtils.getConnection("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            artistList.clear();
            while (rs.next()) {
                ObservableList<Genre> genres = null;
                Artist artist = new Artist(
                        rs.getInt("artist_id"),
                        rs.getString("name"),
                        genres,
                        rs.getString("spotify_id"));
                artistList.add(artist);
                instance.add(rs.getInt("artist_id"), artist);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            artistList.clear();
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertObjectInDB(Artist artist) {
        //update DB
        int id = (int) CRUDHelper.create(
                tableName,
                columns,
                new Object[]{artist.getId(), artist.getName(), artist.getSpotify_id()},
                types
        );
        //update cache
        artistList.add(artist);

        //update MemoryRepo
        try {
            super.add(artist.getId(), artist);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Artist> getItemByID(int id) {
        Iterable<Artist> artists = super.getAll();
        for (Artist artist : artists) {
            if (artist.getId().equals(id))
                return Optional.of(artist);
        }
        return Optional.empty();
    }

    @Override
    public void updateObjectInDB(Artist newArtist) {
        //update DB
        long rows = CRUDHelper.update(
                tableName,
                updateColumns,
                new Object[]{newArtist.getName(), newArtist.getSpotify_id()},
                updateTypes,
                "artist_id",
                Types.INTEGER,
                newArtist.getId()
        );
        if (rows == 0)
            throw new IllegalStateException("Artist to update with id " + newArtist.getId() + "doesn't exist in the database!");
        //update cache
        Optional<Artist> optionalArtist = getItemByID(newArtist.getId());
        optionalArtist.ifPresentOrElse((oldArtist) -> {
            artistList.remove(oldArtist);
            artistList.add(newArtist);
        }, () -> {
            throw new IllegalStateException("Artist to update with id " + newArtist.getId() + "doesn't exist in the database!");
        });
        super.update(newArtist.getId(), newArtist);
    }

    @Override
    public int deleteObjectByIDInDB(Integer id) {
        return CRUDHelper.delete(
                tableName,
                id,
                deleteQuery);
    }
    @Override
    public Object readItemAttributeFromDB(String fieldName, int fieldDataType, Object index) {
        return CRUDHelper.read(tableName,
                fieldName,
                fieldDataType,
                columns[0],
                types[0],
                index);
    }
    public static void main(String[] args) {
//        ArtistDAO artistRepo = ArtistDAO.getInstance();
//        artistRepo.readAllObjectsFromTable();
//        ObservableList<Genre> genres = null;
//        Artist artist = new Artist(5, "Metallica", genres, "some_another");
//        artistRepo.insertObjectInDB(artist);
//        artistRepo.list();
//        System.out.println(artistRepo.getItem(5).getName());
//        artistRepo.updateObjectInDB(artist);
//        System.out.println(artistRepo.getItem(5).getSpotify_id());
//        System.out.println(artistRepo.readItemAttributeFromDB("spotify_id", Types.VARCHAR, 8));
//        artistRepo.deleteObjectByIDInDB(5);
        VenueDAO venueDAO = VenueDAO.getInstance();
        venueDAO.readAllObjectsFromTable();
        venueDAO.list();

        FestivalDAO festivalDAO = FestivalDAO.getInstance();
        festivalDAO.readAllObjectsFromTable();
        festivalDAO.list();
    }
}