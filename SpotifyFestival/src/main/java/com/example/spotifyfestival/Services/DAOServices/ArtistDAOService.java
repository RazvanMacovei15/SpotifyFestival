package com.example.spotifyfestival.Services.DAOServices;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.ValidatorClasses.ArtistValidator;
import com.example.spotifyfestival.GenericsPackage.CRUDInterface;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.ArtistDAO;
import javafx.collections.ObservableList;

public class ArtistDAOService implements CRUDInterface<Artist> {
    protected ArtistDAO artistDAO;
    protected ArtistValidator validator;

    public ArtistDAO getArtistDAO() {
        return artistDAO;
    }

    public ArtistDAOService() {
        artistDAO = ArtistDAO.getInstance();
        validator = new ArtistValidator();
    }

    @Override
    public void add(Artist item) throws DuplicateEntityException {
//        if(validator.validate(item)){
            artistDAO.insertObjectInDB(item);
//        }
//        else{
//            System.out.println("Validation was unsuccessful!");
//        }
    }

    @Override
    public void update(Artist newItem) {
        artistDAO.updateObjectInDB(newItem);
    }

    @Override
    public void delete(int id) {
        artistDAO.deleteObjectByIDInDB(id);
    }

    @Override
    public Artist getItem(int id) {
        if(artistDAO.getItemByID(id).isPresent()){
            return artistDAO.getItemByID(id).get();
        }
       return null;
    }

    @Override
    public void list() {
        artistDAO.list();
    }

    public ObservableList<Artist> getArtistList() {
        return artistDAO.getArtistList();
    }
}
