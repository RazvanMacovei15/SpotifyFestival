package com.example.spotifyfestival.database.services;

import com.example.spotifyfestival.database.entities.pojo.Festival;
import com.example.spotifyfestival.generics.CRUDInterface;
import com.example.spotifyfestival.database.entities.pojo.DuplicateEntityException;
import com.example.spotifyfestival.database.dao.FestivalDAO;
import javafx.collections.ObservableList;

public class FestivalDAOService implements CRUDInterface<Festival> {
    protected FestivalDAO festivalDAO;

    public FestivalDAOService() {
        festivalDAO = FestivalDAO.getInstance();
    }

    public FestivalDAO getFestivalDAO() {
        return festivalDAO;
    }

    @Override
    public void add(Festival item) throws DuplicateEntityException {
        festivalDAO.insertObjectInDB(item);
    }

    @Override
    public void update(Festival newItem) {
        festivalDAO.updateObjectInDB(newItem);
    }

    @Override
    public void delete(int id) {
        festivalDAO.deleteObjectByIDInDB(id);
    }

    @Override
    public Festival getItem(int id) {
        if(festivalDAO.getItemByID(id).isPresent()){
            festivalDAO.getItemByID(id).get();
        }
        return null;
    }

    @Override
    public void list() {
        festivalDAO.list();
    }

    public ObservableList<Festival> getFestivalList(){
        return festivalDAO.getFestivalList();
    }
}
