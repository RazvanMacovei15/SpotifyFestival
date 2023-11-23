package com.example.spotifyfestival.UserData.DAO.Implementations;

import com.example.spotifyfestival.UserData.DAO.Interfaces.GenresDAOInterface;
import com.example.spotifyfestival.UserData.Domain.Genre;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.FestivalDatabase.DB.DB;
import com.example.spotifyfestival.UserData.Repos.DBRepos.GenreRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenreDAOImplementation implements GenresDAOInterface {
    @Override
    public Genre create(Genre genre) {
        return null;
    }

    @Override
    public Genre getById(int id) {
        return null;
    }

    @Override
    public GenreRepo getAllGenres() {
        GenreRepo genreRepo = GenreRepo.getInstance();

        String tableName = "Genres";

        ObservableList<Genre> genres = FXCollections.observableArrayList();

        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DB.connect("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            genres.clear();
            while (rs.next()) {
                int genre_id = rs.getInt("genre_id");
                String name = rs.getString("name");

                Genre genre = new Genre(genre_id, name);

                try {
                    genreRepo.add(genre_id, genre);
                    genres.add(genre);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            genres.clear();
        }
        return genreRepo;
    }

    @Override
    public Genre update(Genre genre) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
    public void generateGenreRepo(){
        GenreDAOImplementation genreDAOImplementation = new GenreDAOImplementation();
        GenreRepo genreRepo = GenreRepo.getInstance();
        String tableName = "Genres";

    }
}
