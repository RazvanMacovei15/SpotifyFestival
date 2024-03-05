package com.example.spotifyfestival.database.services;

import com.example.spotifyfestival.database.dao.ArtistDAO;
import com.example.spotifyfestival.database.entities.pojo.Artist;
import com.example.spotifyfestival.generics.CRUDInterface;
import com.example.spotifyfestival.database.entities.pojo.DuplicateEntityException;
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
