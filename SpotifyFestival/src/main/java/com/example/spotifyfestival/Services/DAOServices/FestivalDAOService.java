package com.example.spotifyfestival.Services.DAOServices;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Festival;
import com.example.spotifyfestival.GenericsPackage.CRUDInterface;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.FestivalDAO;
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
