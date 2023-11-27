package com.example.spotifyfestival.DatabasePackage.DAO.Implementations;

import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.DatabasePackage.DAO.Interfaces.StagesDAOInterface;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.FestivalStage;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.VenueDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FestivalStageDAO implements StagesDAOInterface {
    @Override
    public FestivalStage create(FestivalStage stage) {
        return null;
    }

    @Override
    public FestivalStage getById(int id) {
        return null;
    }

    @Override
    public com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalStageDAO getAllStages() {
        com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalStageDAO stageRepo = com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalStageDAO.getInstance();

        String tableName = "Stages";

        ObservableList<FestivalStage> festivalStages = FXCollections.observableArrayList();

        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DBUtils.getConnection("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            festivalStages.clear();
            while (rs.next()) {
                int stage_id = rs.getInt("stage_id");
                String name = rs.getString("name");
                int venue_id = rs.getInt("venue_id");

                VenueDAO venueRepo = VenueDAO.getInstance();
                Venue venue = venueRepo.getItem(venue_id);

                FestivalStage festivalStage = new FestivalStage(stage_id, name, venue);

                stageRepo.add(stage_id, festivalStage);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            festivalStages.clear();
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
        return stageRepo;
    }

    @Override
    public FestivalStage update(FestivalStage stage) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    public static void main(String[] args) {
        FestivalStageDAO festivalStageDAOImplementation = new FestivalStageDAO();
        com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalStageDAO festivalStageRepo = festivalStageDAOImplementation.getAllStages();

    }
}
