package com.example.spotifyfestival.Services.UniServices;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.*;
import com.example.spotifyfestival.GenericsPackage.RepoInterface;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;

import java.sql.SQLException;

public class Service {
    private RepoInterface<Integer, Artist> artistRepo;
    private RepoInterface<Integer, ArtistGenre> artistGenreRepo;
    private RepoInterface<Integer, Genre> genreRepo;
    private RepoInterface<Integer, Venue> venueRepo;
    private RepoInterface<Integer, Festival> festivalRepo;
    private RepoInterface<Integer, FestivalStage> stageRepo;
    private RepoInterface<Integer, Concert> concertRepo;
    private RepoInterface<Integer, User> userRepo;

    public Service(RepoInterface<Integer, Artist> artistRepo, RepoInterface<Integer, ArtistGenre> artistGenreRepo, RepoInterface<Integer, Genre> genreRepo, RepoInterface<Integer, Venue> venueRepo, RepoInterface<Integer, Festival> festivalRepo, RepoInterface<Integer, FestivalStage> stageRepo, RepoInterface<Integer, Concert> concertRepo, RepoInterface<Integer, User> userRepo) {
        this.artistRepo = artistRepo;
        this.artistGenreRepo = artistGenreRepo;
        this.genreRepo = genreRepo;
        this.venueRepo = venueRepo;
        this.festivalRepo = festivalRepo;
        this.stageRepo = stageRepo;
        this.concertRepo = concertRepo;
        this.userRepo = userRepo;
    }

    //Artists
    public void addArtist(Artist a) throws DuplicateEntityException, SQLException {
        artistRepo.add(a.getId(), a);
    }

    public void updateArtist(Artist a){
        artistRepo.update(a.getId(), a);
    }

    public void deleteArtist(int id){
        artistRepo.delete(id);
    }

    public void listArtists(){
        artistRepo.list();
    }


}
