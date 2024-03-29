package com.example.spotifyfestival.database.dao;

import com.example.spotifyfestival.generics.GenericDAO;
import com.example.spotifyfestival.database.helpers.CRUDHelper;
import com.example.spotifyfestival.database.helpers.DBUtils;
import com.example.spotifyfestival.database.entities.pojo.Venue;
import com.example.spotifyfestival.database.helpers.DBGenericRepository;
import com.example.spotifyfestival.database.entities.pojo.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VenueDAO extends DBGenericRepository<Integer, Venue> implements GenericDAO<Venue> {
    //DB specific attributes
    private static final String location = "festivalDB";
    private final String tableName = "Venues";
    private final String[] columns = {"venue_id", "name", "city", "address", "latitude", "longitude"};
    private final String[] updateColumns = {"name", "city", "address", "latitude", "longitude"};
    private final String readQuery = "SELECT * FROM " + tableName;
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE venue_id = ?";

    private final int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.DOUBLE};
    private final int[] updateTypes = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.DOUBLE};

    public String getReadQuery() {
        return readQuery;
    }

    public String getDeleteQuery() {
        return deleteQuery;
    }
    private static CRUDHelper crudHelper;
    //Singleton Creation
    private static VenueDAO instance;

    private VenueDAO() {}

    public static VenueDAO getInstance() {
        if (instance == null) {
            instance = new VenueDAO();
            crudHelper = new CRUDHelper(location);
            initialize();
        }
        return instance;
    }

    public static void initialize(){
        instance.readAllObjectsFromTable();
    }

    //TableView JavaFX stuff
    ObservableList<Venue> venueList = FXCollections.observableArrayList();

    public ObservableList<Venue> getVenuesList() {
        return venueList;
    }

    @Override
    public void insertObjectInDB(Venue venue) {
        //update DB
        int id = (int) crudHelper.create(
                tableName,
                columns,
                new Object[]{venue.getId(), venue.getVenueName(), venue.getCity(), venue.getStreetAddress(), Double.parseDouble(venue.getLocationLatitude()), Double.parseDouble(venue.getLocationLongitude())},
                types
        );
        //update MemoryRepo
        try {
            super.add(venue.getId(), venue);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getHighestId(){
        List<Integer> keys = instance.getListOfKeys();
        Integer highestValue = keys.stream()
                .max(Integer::compareTo)
                .orElse(null);
        return highestValue;
    }
    @Override
    public Optional<Venue> getItemByID(int id) {
        Iterable<Venue> venues = super.getAll();
        for (Venue venue : venues) {
            if (venue.getId().equals(id))
                return Optional.of(venue);
        }
        return Optional.empty();
    }

    @Override
    public void updateObjectInDB(Venue item) {
//update DB
        long rows = crudHelper.update(
                tableName,
                updateColumns,
                new Object[]{item.getVenueName(), item.getCity(), item.getStreetAddress(), item.getLocationLatitude(), item.getLocationLongitude()},
                updateTypes,
                "venue_id",
                Types.INTEGER,
                item.getId()
        );
        if (rows == 0)
            throw new IllegalStateException("Venue to update with id " + item.getId() + "doesn't exist in the database!");
        super.update(item.getId(), item);
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
            venueList.clear();
            while (rs.next()) {

                Venue venue = new Venue(
                        rs.getInt("venue_id"),
                        rs.getString("city"),
                        rs.getString("name"),
                        rs.getString("address"),
                        String.valueOf(rs.getDouble("latitude")),
                        String.valueOf(rs.getDouble("longitude")));

                instance.add(rs.getInt("venue_id"), venue);
                venueList.add(venue);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Venues from database ");
            venueList.clear();
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