package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Domain.Genre;
import com.example.spotifyfestival.UserData.Repos.DBRepos.ArtistRepo;
import javafx.collections.ObservableList;

import java.util.Optional;

public interface ArtistDAOInterface {
    ArtistRepo getAllArtists();
    Artist create(Artist newArtist);
    void insertArtistInDB(int artist_id, String name, ObservableList<Genre> genres, String spotify_id);
    Optional<Artist> getById(int id);
    void update(Artist artist);
    int delete(Integer id);
}