package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Domain.ArtistGenre;
import com.example.spotifyfestival.UserData.Repos.ArtistGenreRepo;
import com.example.spotifyfestival.UserData.Repos.ArtistRepo;

public interface ArtistGenreDAOInterface {
    ArtistGenre create(ArtistGenre artistGenre);
    ArtistGenre getById(int id);
    ArtistGenreRepo getAllArtistGenres();
    ArtistGenre update(ArtistGenre artistGenre);
    void delete(int id);
}
