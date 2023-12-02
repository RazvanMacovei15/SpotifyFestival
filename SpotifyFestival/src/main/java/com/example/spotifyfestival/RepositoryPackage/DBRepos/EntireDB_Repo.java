package com.example.spotifyfestival.RepositoryPackage.DBRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EntireDB_Repo{
    protected ArtistDAO artistDAO;
    protected FestivalStageDAO stageDAO;
    protected FestivalDAO festivalDAO;
    protected VenueDAO venueDAO;
    protected ConcertDAO concertDAO;

    public EntireDB_Repo() {
        this.artistDAO = ArtistDAO.getInstance();
        this.stageDAO = FestivalStageDAO.getInstance();
        this.festivalDAO = FestivalDAO.getInstance();
        this.venueDAO = VenueDAO.getInstance();
        this.concertDAO = ConcertDAO.getInstance();
    }

    public void initialize() {
        artistDAO.readAllObjectsFromTable();
        venueDAO.readAllObjectsFromTable();
        festivalDAO.readAllObjectsFromTable();
        stageDAO.readAllObjectsFromTable();
        concertDAO.readAllObjectsFromTable();
    }

    public ArtistDAO getArtistDAO() {
        return artistDAO;
    }

    public void setArtistDAO(ArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

    public FestivalStageDAO getStageDAO() {
        return stageDAO;
    }

    public void setStageDAO(FestivalStageDAO stageDAO) {
        this.stageDAO = stageDAO;
    }

    public FestivalDAO getFestivalDAO() {
        return festivalDAO;
    }

    public void setFestivalDAO(FestivalDAO festivalDAO) {
        this.festivalDAO = festivalDAO;
    }

    public VenueDAO getVenueDAO() {
        return venueDAO;
    }

    public void setVenueDAO(VenueDAO venueDAO) {
        this.venueDAO = venueDAO;
    }

    public ConcertDAO getConcertDAO() {
        return concertDAO;
    }

    public void setConcertDAO(ConcertDAO concertDAO) {
        this.concertDAO = concertDAO;
    }

    public static void main(String[] args) {
        EntireDB_Repo repo = new EntireDB_Repo();
        repo.initialize();
        ArtistDAO artistRepo = repo.getArtistDAO();
        artistRepo.list();
//        repo.getArtistDAO().de
    }
}
