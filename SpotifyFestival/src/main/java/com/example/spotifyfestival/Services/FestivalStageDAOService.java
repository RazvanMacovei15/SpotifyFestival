package com.example.spotifyfestival.Services;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.FestivalStage;
import com.example.spotifyfestival.GenericsPackage.CRUDInterface;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.FestivalStageDAO;
import javafx.collections.ObservableList;

public class FestivalStageDAOService implements CRUDInterface<FestivalStage> {
    protected FestivalStageDAO stageDAO;

    public FestivalStageDAO getStage() {
        return stageDAO;
    }

    private void initialize(){
        stageDAO.initialize();
    }

    public FestivalStageDAOService() {
        stageDAO = FestivalStageDAO.getInstance();
        initialize();
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
