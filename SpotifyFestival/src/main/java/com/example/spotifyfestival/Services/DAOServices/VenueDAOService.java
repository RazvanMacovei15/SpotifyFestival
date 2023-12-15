package com.example.spotifyfestival.Services.DAOServices;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.GenericsPackage.CRUDInterface;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.VenueDAO;
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
