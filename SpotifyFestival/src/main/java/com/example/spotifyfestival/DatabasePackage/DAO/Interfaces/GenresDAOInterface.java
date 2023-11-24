package com.example.spotifyfestival.DatabasePackage.DAO.Interfaces;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.GenreRepo;

public interface GenresDAOInterface {
    Genre create(Genre genre);
    Genre getById(int id);
    GenreRepo getAllGenres();
    Genre update(Genre genre);
    void delete(int id);
}
