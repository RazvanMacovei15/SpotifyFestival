package com.example.spotifyfestival.database.dao;

import com.example.spotifyfestival.generics.GenericDAO;
import com.example.spotifyfestival.database.helpers.CRUDHelper;
import com.example.spotifyfestival.database.helpers.DBUtils;
import com.example.spotifyfestival.database.EntitiesPOJO.*;
import com.example.spotifyfestival.database.helpers.DBGenericRepository;
import com.example.spotifyfestival.database.entities.pojo.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcertDAO extends DBGenericRepository<Integer, Concert> implements GenericDAO<Concert> {
    //DB specific attributes
    private static final String location = "festivalDB";
    private final String tableName = "Concerts";
    private final String[] columns = {"concert_id", "description", "start_date", "start_time", "venue_id", "artist_id", "stage_id"};
    private final String[] updateColumns = {"description", "start_date", "start_time", "venue_id", "artist_id", "stage_id"};
    private final String readQuery = "SELECT * FROM " + tableName;
    private final String deleteQuery = "DELETE FROM " + tableName + " WHERE concert_id = ?";
    private final int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER};
    private final int[] updateTypes = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER};
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

    public ArtistDAO getArtistDAO() {
        return artistDAO;
    }

    public FestivalStageDAO getFestivalStageDAO() {
        return festivalStageDAO;
    }

    private static ArtistDAO artistDAO;
    private static FestivalStageDAO festivalStageDAO;
    private static FestivalDAO festivalDAO;
    private static CRUDHelper crudHelper;
    //Singleton creation
    private static ConcertDAO instance;
    private ConcertDAO(){

    }

    public ObservableList<Concert> getAllConcertsForAStage(FestivalStage stage){
        Iterable<Concert> concerts = instance.getAll();
        ObservableList<Concert> concertsForTheStage = FXCollections.observableArrayList();
        for(Concert concert : concerts){
            if(concert.getFestivalStage().getId() == stage.getId()){
                concertsForTheStage.add(concert);
            }
        }
        return concertsForTheStage;
    }

    public static ConcertDAO getInstance(){
        if(instance == null){
            instance = new ConcertDAO();
            crudHelper = new CRUDHelper(location);
            venueDAO = VenueDAO.getInstance();
            artistDAO = ArtistDAO.getInstance();
            festivalStageDAO = FestivalStageDAO.getInstance();
            festivalDAO = FestivalDAO.getInstance();
            initialize();

        }
        return instance;
    }
    public Integer getHighestId(){
        List<Integer> keys = instance.getListOfKeys();
        Integer highestValue = keys.stream()
                .max(Integer::compareTo)
                .orElse(null);
        return highestValue;
    }

    public static void initialize(){
        instance.readAllObjectsFromTable();
    }
    //TableView JavaFX stuff
    ObservableList<Concert> concertList = FXCollections.observableArrayList();
    public ObservableList<Concert> getConcertList() {
        return concertList;
    }

    @Override
    public void insertObjectInDB(Concert item) {
        //update DB
        int id = (int) crudHelper.create(
                tableName,
                columns,
                new Object[]{item.getId(), item.getDescription(), item.getStartOfTheConcert(), item.getTime(), item.getVenueId(), item.getArtistIdValue(), item.getFestivalStage().getId()},
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
    public Optional<Concert> getItemByID(int id) {
        Iterable<Concert> concerts = super.getAll();
        for (Concert concert : concerts) {
            if (concert.getId().equals(id))
                return Optional.of(concert);
        }
        return Optional.empty();
    }

    @Override
    public void updateObjectInDB(Concert item) {
        //update DB
        long rows = crudHelper.update(
                tableName,
                updateColumns,
                new Object[]{item.getDescription(), item.getStartOfTheConcert(), item.getTime(), item.getVenueId(), item.getArtistIdValue(), item.getStageId()},
                updateTypes,
                "concert_id",
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
            concertList.clear();
            while (rs.next()) {

                Venue venue = venueDAO.getItem(rs.getInt("venue_id"));
                Artist artist = artistDAO.getItem(rs.getInt("artist_id"));
                FestivalStage stage = festivalStageDAO.getItem(rs.getInt("stage_id"));
                List<Artist> list = new ArrayList<>();
                list.add(artist);
                //remember to add ConcertArtist class in case there are multiple artists at a concert

                Concert concert = new Concert(
                        rs.getInt("concert_id"),
                        rs.getString("description"),
                        list,
                        venue,
                        rs.getString("start_date"),
                        rs.getString("start_time"),
                        stage);

                concert.setArtist(artist);

                venue.addConcertToList(concert);

                concertList.add(concert);
                instance.add(rs.getInt("concert_id"), concert);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Festivals from database ");
            concertList.clear();
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
