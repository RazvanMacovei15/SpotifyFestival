package com.example.spotifyfestival.DatabasePackage.DAO;

import com.example.spotifyfestival.GenericsPackage.GenericDAO;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Festival;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FestivalDAO extends DBGenericRepository<Integer, Festival> implements GenericDAO<Festival> {
    //DB specific attributes
    private static final String LOCATION = "festivalDB";
    private final String tableName = "Festivals";
    private final String[] columns = {"festival_id", "name", "venue_id"};
    private final String[] updateColumns = {"name", "venue_id"};
    private final String readQuery = "SELECT * FROM " + tableName;
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE festival_id = ?";
    private final int[] types = {Types.INTEGER, Types.VARCHAR, Types.INTEGER};
    private final int[] updateTypes = {Types.VARCHAR, Types.INTEGER};
    public String getReadQuery()
    {
        return readQuery;
    }
    public String getDeleteQuery()
    {
        return deleteQuery;
    }
    private static VenueDAO venueDAO;

    public VenueDAO getVenueDAO() {
        return venueDAO;
    }

    private static CRUDHelper crudHelper;

    //Singleton Creation
    private static FestivalDAO instance;
    private FestivalDAO(){

    }
    public static FestivalDAO getInstance(){
        if(instance == null){
            instance = new FestivalDAO();
            crudHelper = new CRUDHelper(LOCATION);
            venueDAO = VenueDAO.getInstance();
            initialize();
        }
        return instance;
    }
    //TableView JavaFX stuff
    ObservableList<Festival> festivalObservableList = FXCollections.observableArrayList();
    public ObservableList<Festival> getFestivalList() {
        return festivalObservableList;
    }

    @Override
    public void insertObjectInDB(Festival item) {
        //update DB
        int id = (int) crudHelper.create(
                tableName,
                columns,
                new Object[]{item.getId(), item.getName(), item.getVenue().getId()},
                types
        );
        //update MemoryRepo
        try {
            super.add(item.getId(), item);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Festival> getItemByID(int id) {
        Iterable<Festival> festivals = super.getAll();
        for (Festival festival : festivals) {
            if (festival.getId().equals(id))
                return Optional.of(festival);
        }
        return Optional.empty();
    }

    @Override
    public void updateObjectInDB(Festival item) {
        //update DB
        long rows = crudHelper.update(
                tableName,
                updateColumns,
                new Object[]{item.getName(), item.getVenue().getId()},
                updateTypes,
                "festival_id",
                Types.INTEGER,
                item.getId()
        );
        if (rows == 0)
            throw new IllegalStateException("Festivals to update with id " + item.getId() + "doesn't exist in the database!");
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
            festivalObservableList.clear();
            while (rs.next()) {

                Venue venue = venueDAO.getItem(rs.getInt("venue_id"));

                Festival festival = new Festival(
                        rs.getInt("festival_id"),
                        rs.getString("name"),
                        venue);

                festivalObservableList.add(festival);
                instance.add(rs.getInt("festival_id"), festival);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Festivals from database ");
            festivalObservableList.clear();
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

    public static void initialize() {
        instance.readAllObjectsFromTable();
    }
}
