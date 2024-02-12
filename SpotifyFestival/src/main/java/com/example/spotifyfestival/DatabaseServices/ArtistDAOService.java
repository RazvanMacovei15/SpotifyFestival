package com.example.spotifyfestival.DatabaseServices;

import com.example.spotifyfestival.DatabasePackage.DAO.ArtistDAO;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.GenericsPackage.CRUDInterface;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;
import javafx.collections.ObservableList;

public class ArtistDAOService implements CRUDInterface<Artist> {
    protected ArtistDAO artistDAO;

    public ArtistDAO getArtistDAO() {
        return artistDAO;
    }

    public ArtistDAOService() {
        artistDAO = ArtistDAO.getInstance();
    }

    @Override
    public void add(Artist item) throws DuplicateEntityException {
            artistDAO.insertObjectInDB(item);
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
