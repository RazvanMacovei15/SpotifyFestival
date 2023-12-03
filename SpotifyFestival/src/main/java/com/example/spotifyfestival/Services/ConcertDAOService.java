package com.example.spotifyfestival.Services;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Concert;
import com.example.spotifyfestival.GenericsPackage.CRUDInterface;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.ConcertDAO;
import javafx.collections.ObservableList;

public class ConcertDAOService implements CRUDInterface<Concert> {
    protected ConcertDAO concertDAO;

    public ConcertDAO getConcertDAO() {
        return concertDAO;
    }

    public void initialize(){
        concertDAO.initialize();
    }

    public ConcertDAOService() {
        concertDAO = ConcertDAO.getInstance();
        initialize();
    }

    @Override
    public void add(Concert item) throws DuplicateEntityException {
        concertDAO.insertObjectInDB(item);
    }

    @Override
    public void update(Concert newItem) {
        concertDAO.updateObjectInDB(newItem);
    }

    @Override
    public void delete(int id) {
        concertDAO.deleteObjectByIDInDB(id);
    }

    @Override
    public Concert getItem(int id) {
        if(concertDAO.getItemByID(id).isPresent()){
            return concertDAO.getItemByID(id).get();
        }
        return null;
    }

    @Override
    public void list() {
        concertDAO.list();
    }

    public ObservableList<Concert> getConcertsList(){
        return concertDAO.getConcertList();
    }
}
