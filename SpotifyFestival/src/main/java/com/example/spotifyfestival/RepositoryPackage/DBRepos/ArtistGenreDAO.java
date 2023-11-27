package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.DAO.Interfaces.GenericDAO;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
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

public class ArtistGenreDAO extends DBGenericRepository<Integer, ArtistGenre> implements GenericDAO<ArtistGenre> {
    //DB specific attributes
    private final String tableName = "ArtistsGenres";
    private final String[] columns = {"artist_genre_id", "artist_id", "genre_id"};
    private final String[] updateColumns = {"artist_id", "genre_id"};
    private final String readQuery = "SELECT * FROM " + tableName;
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE artist_genre_id = ?";

    private final int[] types = {Types.INTEGER, Types.INTEGER, Types.INTEGER};
    private final int[] updateTypes = {Types.INTEGER, Types.INTEGER};
    public String getReadQuery()
    {
        return readQuery;
    }
    public String getDeleteQuery()
    {
        return deleteQuery;
    }

    //Singleton Creation
    private static ArtistGenreDAO instance;
    private ArtistGenreDAO(){}
    public static ArtistGenreDAO getInstance(){
        if(instance == null){
            instance = new ArtistGenreDAO();

        }
        return instance;
    }
    //TableView JavaFX stuff
    ObservableList<ArtistGenre> artistGenreList = FXCollections.observableArrayList();
    public ObservableList<ArtistGenre> getArtistGenreList() {
        return artistGenreList;
    }
    @Override
    public void insertObjectInDB(ArtistGenre artistGenre) {
        //update DB
        int id = (int) CRUDHelper.create(
                tableName,
                columns,
                new Object[]{artistGenre.getArtist_id(), artistGenre.getGenre_id()},
                types
        );
        //update cache
        artistGenreList.add(artistGenre);
        //update MemoryRepo
        try {
            super.add(artistGenre.getId(), artistGenre);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ArtistGenre> getItemByID(int id) {
        Iterable<ArtistGenre> artistGenres = super.getAll();
        for (ArtistGenre artistGenre : artistGenres) {
            if (artistGenre.getId().equals(id))
                return Optional.of(artistGenre);
        }
        return Optional.empty();
    }

    @Override
    public void updateObjectInDB(ArtistGenre newArtistGenre) {
        //update DB
        long rows = CRUDHelper.update(
                tableName,
                updateColumns,
                new Object[]{newArtistGenre.getArtist_id(), newArtistGenre.getGenre_id()},
                updateTypes,
                "artist_id",
                Types.INTEGER,
                newArtistGenre.getId()
        );
        if (rows == 0)
            throw new IllegalStateException("Artist to update with id " + newArtistGenre.getId() + "doesn't exist in the database!");
        //update cache
        Optional<ArtistGenre> optionalArtistGenre = getItemByID(newArtistGenre.getId());
        optionalArtistGenre.ifPresentOrElse((oldGenre) -> {
            artistGenreList.remove(oldGenre);
            artistGenreList.add(newArtistGenre);
        }, () -> {
            throw new IllegalStateException("Artist to update with id " + newArtistGenre.getId() + "doesn't exist in the database!");
        });
        super.update(newArtistGenre.getId(), newArtistGenre);
    }

    @Override
    public int deleteObjectByIDInDB(Integer id) {
        return CRUDHelper.delete(
                tableName,
                id,
                deleteQuery);
    }

    @Override
    public void readAllObjectsFromTable() {
        try (Connection connection = DBUtils.getConnection("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(readQuery);
            ResultSet rs = statement.executeQuery();
            artistGenreList.clear();
            while (rs.next()) {

                ArtistGenre artistGenre = new ArtistGenre(
                        rs.getInt("artist_genre_id"),
                        rs.getInt("artist_id"),
                        rs.getInt("genre_id"));
                artistGenreList.add(artistGenre);
                instance.add(rs.getInt("artist_genre_id"), artistGenre);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            artistGenreList.clear();
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
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
}