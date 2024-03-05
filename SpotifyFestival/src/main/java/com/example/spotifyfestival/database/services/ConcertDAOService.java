package com.example.spotifyfestival.database.services;

import com.example.spotifyfestival.database.entities.pojo.Concert;
import com.example.spotifyfestival.generics.CRUDInterface;
import com.example.spotifyfestival.database.entities.pojo.DuplicateEntityException;
import com.example.spotifyfestival.database.dao.ConcertDAO;
import javafx.collections.ObservableList;

public class ConcertDAOService implements CRUDInterface<Concert> {
    protected ConcertDAO concertDAO;

    public ConcertDAO getConcertDAO() {
        return concertDAO;
    }

    public ConcertDAOService() {
        concertDAO = ConcertDAO.getInstance();
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
