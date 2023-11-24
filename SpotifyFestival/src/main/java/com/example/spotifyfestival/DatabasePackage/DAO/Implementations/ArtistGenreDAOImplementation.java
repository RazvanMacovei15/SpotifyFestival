package com.example.spotifyfestival.DatabasePackage.DAO.Implementations;

import com.example.spotifyfestival.DatabasePackage.DAO.Interfaces.ArtistGenreDAOInterface;
import com.example.spotifyfestival.DatabasePackage.DBHelpers.DB;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistGenreRepo;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistRepo;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.GenreRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistGenreDAOImplementation implements ArtistGenreDAOInterface {
    String tableName = "ArtistsGenres";
    ArtistGenreRepo artistGenreRepo = ArtistGenreRepo.getInstance();
    ObservableList<ArtistGenre> artistGenres = FXCollections.observableArrayList();

    public ArtistGenreDAOImplementation() {
        artistGenreRepo = getAllArtistGenres();
    }

    @Override
    public ArtistGenre create(ArtistGenre artistGenre) {
        return null;
    }

    @Override
    public ArtistGenre getById(int id) {
        return null;
    }

    @Override
    public ArtistGenreRepo getAllArtistGenres() {
        String query = "SELECT * FROM " + tableName;

        try (Connection connection = DB.connect("festivalDB")) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            artistGenres.clear();
            while (rs.next()) {
                int artist_genre_id = rs.getInt("artist_genre_id");
                int artist_id = rs.getInt("artist_id");
                int genre_id = rs.getInt("genre_id");

                ArtistGenre artistGenre = new ArtistGenre(artist_genre_id, artist_id, genre_id);

                try {
                    artistGenreRepo.add(artist_genre_id, artistGenre);
                    artistGenres.add(artistGenre);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Artists Genres from database ");
            artistGenres.clear();
        }
        return artistGenreRepo;
    }

    @Override
    public ArtistGenre update(ArtistGenre artistGenre) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
    public void populateArtistsWithGenres(ArtistRepo artistRepo, GenreRepo genreRepo, ArtistGenreRepo artistGenreRepo) {
        artistRepo = ArtistRepo.getInstance();
        genreRepo = GenreRepo.getInstance();
        artistGenreRepo = ArtistGenreRepo.getInstance();

        for (int i = 1; i < artistGenreRepo.getSize() + 1; i++)
        {
            ArtistGenre artistGenre = artistGenreRepo.getItem(i);
            int artist_id = artistGenre.getArtist_id();
            int genre_id = artistGenre.getGenre_id();
            Artist artist = artistRepo.getItem(artist_id);
            Genre genre = genreRepo.getItem(genre_id);

            try {
                artist.addGenre(genre);
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void populateArtistAtIDWithGenres(int id, ArtistRepo artistRepo, GenreRepo genreRepo) {

        Artist artist = artistRepo.getItem(id);

        for (int i = 1; i < artistGenres.size() + 1; i++) {
            if (artistGenres.get(i).getArtist_id() == id) {
                int genre_id = artistGenres.get(i).getGenre_id();
                Genre genre = genreRepo.getItem(genre_id);
                try {
                    artist.addGenre(genre);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}