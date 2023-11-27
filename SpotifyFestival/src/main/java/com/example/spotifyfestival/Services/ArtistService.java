package com.example.spotifyfestival.Services;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.RepositoryPackage.DBRepos.ArtistDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ArtistService {
    private final ArtistDAO artistDAO;
    private final ObservableList<Artist> artistList;

    public ObservableList<Artist> getArtistList() {
        return artistList;
    }

    public ArtistService(ArtistDAO artistDAO) {
        this.artistDAO = artistDAO;

        this.artistList = FXCollections.observableArrayList();
    }

    public void insertArtist(Artist artist) throws DuplicateEntityException {
        // Perform database insertion
        artistDAO.insertObjectInDB(artist);

        //update the JavaFX repo
        artistList.add(artist);
    }
}
