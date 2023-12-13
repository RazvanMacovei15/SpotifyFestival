package com.example.spotifyfestival.Services.DAOServices;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.GenericsPackage.CRUDInterface;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.DatabasePackage.DAO.GenreDAO;
import javafx.collections.ObservableList;

public class GenresDAOService implements CRUDInterface<Genre> {
    protected GenreDAO genreDAO;

    public GenreDAO getGenreDAO() {
        return genreDAO;
    }

    public GenresDAOService() {
        genreDAO = GenreDAO.getInstance();
    }

    @Override
    public void add(Genre item) throws DuplicateEntityException {
        genreDAO.insertObjectInDB(item);
    }

    @Override
    public void update(Genre newItem) {
        genreDAO.updateObjectInDB(newItem);
    }

    @Override
    public void delete(int id) {
        genreDAO.deleteObjectByIDInDB(id);
    }

    @Override
    public Genre getItem(int id) {
        if(genreDAO.getItemByID(id).isPresent()){
            return genreDAO.getItemByID(id).get();
        }
        return null;
    }

    @Override
    public void list() {
        genreDAO.list();
    }

    public ObservableList<Genre> getGenresList(){
        return genreDAO.getGenresList();
    }
}
