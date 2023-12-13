package com.example.spotifyfestival.DatabasePackage.DAO;

import com.example.spotifyfestival.GenericsPackage.GenericDAO;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.CRUDHelper;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.FestivalStage;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBGenericRepository;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FestivalStageDAO extends DBGenericRepository<Integer, FestivalStage> implements GenericDAO<FestivalStage> {
    //DB specific attributes
    private static final String LOCATION = "festivalDB";
    private final String tableName = "Stages";
    private final String[] columns = {"stage_id", "name", "venue_id"};
    private final String[] updateColumns = {"name", "venue_id"};
    private final String readQuery = "SELECT * FROM " + tableName;
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE stage_id = ?";
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
    private static CRUDHelper crudHelper;
    //Singleton Creation
    private static FestivalStageDAO instance;

    private FestivalStageDAO() {
        // Private constructor to prevent instantiation outside of this class
    }
    public static FestivalStageDAO getInstance() {
        if (instance == null) {
            instance = new FestivalStageDAO();
            crudHelper = new CRUDHelper(LOCATION);
            venueDAO = VenueDAO.getInstance();
            initialize();
        }
        return instance;
    }

    public VenueDAO getVenueDAO() {
        return venueDAO;
    }

    public static void initialize(){
        instance.readAllObjectsFromTable();
    }
    //TableView JavaFX stuff
    ObservableList<FestivalStage> festivalStages = FXCollections.observableArrayList();
    public ObservableList<FestivalStage> getFestivalStages() {
        return festivalStages;
    }

    //
    private static VenueDAO venueDAO;
    @Override
    public void insertObjectInDB(FestivalStage item) {
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
    public Optional<FestivalStage> getItemByID(int id) {
        Iterable<FestivalStage> stages = super.getAll();
        for (FestivalStage stage : stages) {
            if (stage.getId().equals(id))
                return Optional.of(stage);
        }
        return Optional.empty();
    }

    @Override
    public void updateObjectInDB(FestivalStage item) {
        //update DB
        long rows = crudHelper.update(
                tableName,
                updateColumns,
                new Object[]{item.getName(), item.getVenue().getId()},
                updateTypes,
                "stage_id",
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
            festivalStages.clear();
            while (rs.next()) {

                int venue_id = rs.getInt("venue_id");
                Venue venue = venueDAO.getItem(venue_id);

                FestivalStage festivalStage = new FestivalStage(
                        rs.getInt("stage_id"),
                        rs.getString("name"),
                        venue);

                festivalStages.add(festivalStage);
                instance.add(rs.getInt("stage_id"), festivalStage);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Stages from database ");
            festivalStages.clear();
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
