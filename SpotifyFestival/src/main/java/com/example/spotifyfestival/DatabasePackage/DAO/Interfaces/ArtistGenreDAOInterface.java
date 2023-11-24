package com.example.spotifyfestival.DatabasePackage.DAO.Interfaces;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistGenreRepo;

public interface ArtistGenreDAOInterface {
    ArtistGenre create(ArtistGenre artistGenre);
    ArtistGenre getById(int id);
    ArtistGenreRepo getAllArtistGenres();
    ArtistGenre update(ArtistGenre artistGenre);
    void delete(int id);
}
