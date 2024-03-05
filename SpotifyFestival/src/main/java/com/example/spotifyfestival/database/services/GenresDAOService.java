package com.example.spotifyfestival.database.services;

import com.example.spotifyfestival.database.entities.pojo.Genre;
import com.example.spotifyfestival.generics.CRUDInterface;
import com.example.spotifyfestival.database.entities.pojo.DuplicateEntityException;
import com.example.spotifyfestival.database.dao.GenreDAO;
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
