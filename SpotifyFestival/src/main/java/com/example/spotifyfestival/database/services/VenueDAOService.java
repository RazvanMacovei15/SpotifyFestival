package com.example.spotifyfestival.database.services;

import com.example.spotifyfestival.database.entities.pojo.Venue;
import com.example.spotifyfestival.generics.CRUDInterface;
import com.example.spotifyfestival.database.entities.pojo.DuplicateEntityException;
import com.example.spotifyfestival.database.dao.VenueDAO;
import javafx.collections.ObservableList;

public class VenueDAOService implements CRUDInterface<Venue> {
    protected VenueDAO venueDAO;

    public VenueDAOService() {
        venueDAO = VenueDAO.getInstance();
    }
    public VenueDAO getVenueDAO() {
        return venueDAO;
    }

    @Override
    public void add(Venue item) throws DuplicateEntityException {
        venueDAO.insertObjectInDB(item);
    }

    @Override
    public void update(Venue newItem) {
        venueDAO.updateObjectInDB(newItem);
    }

    @Override
    public void delete(int id) {
        venueDAO.deleteObjectByIDInDB(id);
    }

    @Override
    public Venue getItem(int id) {
        if(venueDAO.getItemByID(id).isPresent()){
            venueDAO.getItemByID(id).get();
        }
        return null;
    }

    @Override
    public void list() {
        venueDAO.list();
    }

    public ObservableList<Venue> getVenuesList(){
        return venueDAO.getVenuesList();
    }
}
