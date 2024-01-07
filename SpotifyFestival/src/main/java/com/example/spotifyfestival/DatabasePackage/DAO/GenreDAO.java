package com.example.spotifyfestival.DatabasePackage.DAO;

import com.example.spotifyfestival.GenericsPackage.GenericDAO;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.GenericsPackage.MemoryRepository;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenreDAO extends MemoryRepository<Integer, Genre> implements GenericDAO<Genre>, Serializable {
    //DB specific attributes
    private static final String location = "festivalDB";
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
    public static CRUDHelper crudHelper;
    //Singleton creation
    private static GenreDAO instance;
    private GenreDAO(){}
    public static GenreDAO getInstance(){
        if(instance == null){
            instance = new GenreDAO();
            crudHelper = new CRUDHelper(location);
            initialize();
        }
        return instance;
    }

    public static void initialize(){
        instance.readAllObjectsFromTable();
    }

    //TableView JavaFX stuff
    ObservableList<Genre> genreList = FXCollections.observableArrayList();

    public ObservableList<Genre> getGenresList() {
        return genreList;
    }

    public boolean checkIfGenreInDB(String name){
        Iterable<Genre> genres = instance.getAll();
        for(Genre genre : genres){
            if (genre.getName().equals(name))
                return true;
            }
        return false;
    }

    public Integer returnHighestID(){

        List<Integer> keys = instance.getListOfKeys();
        Integer highestValue = keys.stream()
                .max(Integer::compareTo)
                .orElse(null);
        return highestValue;
    }

    public Genre getGenreByName(String name){
        Iterable<Genre> genres = instance.getAll();
        for(Genre genre : genres){
            if(genre.getName().equals(name))
                return genre;
        }
        return null;
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
        super.update(newGenre.getId(), newGenre);
    }

    @Override
    public int deleteObjectByIDInDB(Integer id) {
        instance.delete(id);
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
                instance.add(rs.getInt("genre_id"), genre);
                genreList.add(genre);
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

    public void readFromDBAndWriteToFile(String filename){
        Iterable<Genre> iterable = instance.getAll();
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            for(Genre g : iterable){
                writer.write(g.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void readFromTXTFileJustToCheckIfItFuckingWorks(String filename){
        try(BufferedReader r = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts.length != 2){
                    throw new IllegalStateException("this format is not allowed!!");
                }

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];

                Genre g = new Genre(id, name);

                System.out.println(g);
            }



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
