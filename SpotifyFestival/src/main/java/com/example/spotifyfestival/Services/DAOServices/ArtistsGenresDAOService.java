package com.example.spotifyfestival.Services.DAOServices;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
import com.example.spotifyfestival.GenericsPackage.CRUDInterface;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.ArtistGenreDAO;
import javafx.collections.ObservableList;

public class ArtistsGenresDAOService implements CRUDInterface<ArtistGenre> {
    protected ArtistGenreDAO artistGenreDAO;

    public ArtistGenreDAO getArtistGenreDAO() {
        return artistGenreDAO;
    }

    public ArtistsGenresDAOService() {
        artistGenreDAO = ArtistGenreDAO.getInstance();
    }


    @Override
    public void add(ArtistGenre item) throws DuplicateEntityException {
        artistGenreDAO.insertObjectInDB(item);
    }

    @Override
    public void update(ArtistGenre newItem) {
        artistGenreDAO.updateObjectInDB(newItem);
    }

    @Override
    public void delete(int id) {
        artistGenreDAO.deleteObjectByIDInDB(id);
    }

    @Override
    public ArtistGenre getItem(int id) {
        if(artistGenreDAO.getItemByID(id).isPresent()){
            return artistGenreDAO.getItemByID(id).get();
        }
        return null;
    }

    @Override
    public void list() {
        artistGenreDAO.list();
    }

    public ObservableList<ArtistGenre> getArtistsGenresList(){
        return artistGenreDAO.getArtistGenreList();
    }
}
