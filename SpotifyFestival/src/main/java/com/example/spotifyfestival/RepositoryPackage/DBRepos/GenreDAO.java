package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.GenericsPackage.GenericDAO;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenreDAO extends DBGenericRepository<Integer, Genre> implements GenericDAO<Genre> {
    //DB specific attributes
    private final String location = "festivalDB";
    private final String tableName = "Genres";
    private final String[] columns = {"genre_id", "name"};
    private final String[] updateColumns = {"name"};
    private final String readQuery = "SELECT * FROM " + tableName;
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE genre_id = ?";

    private final int[] types = {Types.INTEGER, Types.VARCHAR};
    private final int[] updateTypes = {Types.VARCHAR};
    public String getReadQuery()
    {
        return readQuery;
    }
    public String getDeleteQuery()
    {
        return deleteQuery;
    }
    public CRUDHelper crudHelper;
    //Singleton creation
    private static GenreDAO instance;
    private GenreDAO(){
        crudHelper = new CRUDHelper(location);
    }
    public static GenreDAO getInstance(){
        if(instance == null){
            instance = new GenreDAO();
        }
        return instance;
    }

    public void initialize(){
        instance.readAllObjectsFromTable();
    }

    //TableView JavaFX stuff
    ObservableList<Genre> genreList = FXCollections.observableArrayList();

    public ObservableList<Genre> getGenresList() {
        return genreList;
    }


    @Override
    public void insertObjectInDB(Genre genre) {
        //update DB
        int id = (int) crudHelper.create(
                tableName,
                columns,
                new Object[]{genre.getId(), genre.getName()},
                types
        );
        //update cache
        genreList.add(genre);
        //update MemoryRepo
        try {
            super.add(genre.getId(), genre);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Genre> getItemByID(int id) {
        Iterable<Genre> genres = super.getAll();
        for (Genre genre : genres) {
            if (genre.getId().equals(id))
                return Optional.of(genre);
        }
        return Optional.empty();
    }

    @Override
    public void updateObjectInDB(Genre newGenre) {
        //update DB
        long rows = crudHelper.update(
                tableName,
                updateColumns,
                new Object[]{newGenre.getName()},
                updateTypes,
                "genre_id",
                Types.INTEGER,
                newGenre.getId()
        );
        if (rows == 0)
            throw new IllegalStateException("Genres to update with id " + newGenre.getId() + "doesn't exist in the database!");
        //update cache
        Optional<Genre> optionalGenre = getItemByID(newGenre.getId());
        optionalGenre.ifPresentOrElse((oldGenre) -> {
            genreList.remove(oldGenre);
            genreList.add(newGenre);
        }, () -> {
            throw new IllegalStateException("Genres to update with id " + newGenre.getId() + "doesn't exist in the database!");
        });
        super.update(newGenre.getId(), newGenre);
    }

    @Override
    public int deleteObjectByIDInDB(Integer id) {
        return crudHelper.delete(
                tableName,
                id,
                deleteQuery);
    }

    @Override
    public void readAllObjectsFromTable() {
        try (Connection connection = DBUtils.getConnection("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(readQuery);
            ResultSet rs = statement.executeQuery();
            genreList.clear();
            while (rs.next()) {

                Genre genre = new Genre(
                        rs.getInt("genre_id"),
                        rs.getString("name"));
                genreList.add(genre);
                instance.add(rs.getInt("genre_id"), genre);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Genres from database ");
            genreList.clear();
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object readItemAttributeFromDB(String fieldName, int fieldDataType, Object index) {
        return crudHelper.read(tableName,
                fieldName,
                fieldDataType,
                columns[0],
                types[0],
                index);
    }
}
