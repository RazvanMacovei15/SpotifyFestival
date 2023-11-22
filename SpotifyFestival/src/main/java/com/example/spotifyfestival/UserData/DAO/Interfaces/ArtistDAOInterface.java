package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Repos.DBRepos.ArtistRepo;

public interface ArtistDAOInterface {

    Artist create(Artist artist);
    Artist getById(int id);
    ArtistRepo getAllArtists();
    Artist update(Artist artist);
    void delete(int id);
}
