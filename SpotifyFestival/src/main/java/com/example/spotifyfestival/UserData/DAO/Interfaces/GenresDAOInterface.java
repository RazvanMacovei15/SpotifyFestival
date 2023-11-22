package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.UserData.Domain.Genre;
import com.example.spotifyfestival.UserData.Repos.DBRepos.GenreRepo;

public interface GenresDAOInterface {
    Genre create(Genre genre);
    Genre getById(int id);
    GenreRepo getAllGenres();
    Genre update(Genre genre);
    void delete(int id);
}
