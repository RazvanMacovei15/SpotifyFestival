package com.example.spotifyfestival.database.services;

import com.example.spotifyfestival.database.entities.pojo.FestivalStage;
import com.example.spotifyfestival.generics.CRUDInterface;
import com.example.spotifyfestival.database.entities.pojo.DuplicateEntityException;
import com.example.spotifyfestival.database.dao.FestivalStageDAO;
import javafx.collections.ObservableList;

public class FestivalStageDAOService implements CRUDInterface<FestivalStage> {
    protected FestivalStageDAO stageDAO;

    public FestivalStageDAO getStage() {
        return stageDAO;
    }

    public FestivalStageDAOService() {
        stageDAO = FestivalStageDAO.getInstance();
    }

    public FestivalStageDAO getStageDAO() {
        return stageDAO;
    }

    @Override
    public void add(FestivalStage item) throws DuplicateEntityException {
        stageDAO.insertObjectInDB(item);
    }

    @Override
    public void update(FestivalStage newItem) {
        stageDAO.updateObjectInDB(newItem);
    }

    @Override
    public void delete(int id) {
        stageDAO.deleteObjectByIDInDB(id);
    }

    @Override
    public FestivalStage getItem(int id) {
        if(stageDAO.getItemByID(id).isPresent()){
            return stageDAO.getItemByID(id).get();
        }
        return null;
    }

    @Override
    public void list() {
        stageDAO.list();
    }

    public ObservableList<FestivalStage> getStageList(){
        return stageDAO.getFestivalStages();
    }
}
