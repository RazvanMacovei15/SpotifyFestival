package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.DAO.Interfaces.GenericDAO;
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

public class ArtistDAORepo extends DBGenericRepository<Integer, Artist> implements GenericDAO<Artist> {
    //DB specific attributes
    String tableName = "Artists";
    String[] columns = {"artist_id", "name", "spotify_id"};
    String[] updateColumns = {"name", "spotify_id"};
    private final String readQuery = "SELECT * FROM " + tableName;

    public String getReadQuery() {
        return readQuery;
    }

    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE artist_id = ?";
    int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR};
    int[] updateTypes = {Types.VARCHAR, Types.VARCHAR};

    //Singleton Creation
    private static ArtistDAORepo instance;

    private ArtistDAORepo() {
    }

    public static ArtistDAORepo getInstance() {
        if (instance == null) {
            instance = new ArtistDAORepo();
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
        try (Connection connection = DBUtils.connect("festivalDB")) {
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

        if (id == null) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not delete from " + tableName +
                            " because the provided id is null.");
            return -1; // or throw an exception, depending on your error handling strategy
        }
        // Build the SQL delete query

        try (Connection connection = DBUtils.connect("festivalDB")) {
            // Prepare and execute the delete query
            PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
            pstmt.setInt(1, id);
            super.delete(id);
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
        ArtistDAORepo artistRepo = ArtistDAORepo.getInstance();
        artistRepo.readAllObjectsFromTable();
        ObservableList<Genre> genres = null;
        Artist artist = new Artist(5, "Metallica", genres, "some_another");
        artistRepo.insertObjectInDB(artist);
        artistRepo.list();
        System.out.println(artistRepo.getItem(5).getName());
        artistRepo.updateObjectInDB(artist);
        System.out.println(artistRepo.getItem(5).getSpotify_id());
        System.out.println(artistRepo.readItemAttributeFromDB("spotify_id", Types.VARCHAR, 8));
    }
}
