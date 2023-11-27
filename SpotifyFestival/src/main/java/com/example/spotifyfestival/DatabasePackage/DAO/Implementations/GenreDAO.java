package com.example.spotifyfestival.DatabasePackage.DAO.Implementations;

import com.example.spotifyfestival.DatabasePackage.DAO.Interfaces.GenresDAOInterface;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DBUtils;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenreDAO implements GenresDAOInterface {
    @Override
    public Genre create(Genre genre) {
        return null;
    }

    @Override
    public Genre getById(int id) {
        return null;
    }

    @Override
    public com.example.spotifyfestival.RepositoryPackage.DBRepos.GenreDAO getAllGenres() {
        com.example.spotifyfestival.RepositoryPackage.DBRepos.GenreDAO genreRepo = com.example.spotifyfestival.RepositoryPackage.DBRepos.GenreDAO.getInstance();

        String tableName = "Genres";

        ObservableList<Genre> genres = FXCollections.observableArrayList();

        String query = "SELECT * FROM " + tableName;
        try (Connection connection = DBUtils.connect("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            genres.clear();
            while (rs.next()) {
                int genre_id = rs.getInt("genre_id");
                String name = rs.getString("name");

                Genre genre = new Genre(genre_id, name);

                genreRepo.add(genre_id, genre);
                genres.add(genre);
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Persons from database ");
            genres.clear();
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
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
        GenreDAO genreDAOImplementation = new GenreDAO();
        com.example.spotifyfestival.RepositoryPackage.DBRepos.GenreDAO genreRepo = com.example.spotifyfestival.RepositoryPackage.DBRepos.GenreDAO.getInstance();
        String tableName = "Genres";

    }
}
