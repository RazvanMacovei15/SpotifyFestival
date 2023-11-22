package com.example.spotifyfestival.UserData.DAO.Interfaces;

import com.example.spotifyfestival.UserData.Domain.ArtistGenre;
import com.example.spotifyfestival.UserData.Repos.DBRepos.ArtistGenreRepo;

public interface ArtistGenreDAOInterface {
    ArtistGenre create(ArtistGenre artistGenre);
    ArtistGenre getById(int id);
    ArtistGenreRepo getAllArtistGenres();
    ArtistGenre update(ArtistGenre artistGenre);
    void delete(int id);
}
