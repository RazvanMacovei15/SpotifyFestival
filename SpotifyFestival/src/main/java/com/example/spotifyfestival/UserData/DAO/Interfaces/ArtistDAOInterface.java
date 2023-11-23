package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Repos.DBRepos.ArtistRepo;

public interface ArtistDAOInterface {
    ArtistRepo getAllArtists();
    Artist create(Artist artist);
    void createArtist(int artist_id, String name, String spotify_id);
    Artist getById(int id);
    void update(Artist artist);
    int delete(int id);
}