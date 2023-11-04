package com.example.spotifyfestival.ConcertsAndFestivals;

import com.example.spotifyfestival.UserData.Domain.Artist;

import java.util.List;

public class Concert extends Entity implements Identifiable<String> {
    private String description;
    private List<Artist> listOfArtists;
    private Venue venue;
    private String startOfTheConcert;
    private String time;


    public Concert(String description, List<Artist> listOfArtists, Venue venue, String startOfTheConcert, String time) {
        super();
        this.description = description;
        this.listOfArtists = listOfArtists;
        this.venue = venue;
        this.startOfTheConcert = startOfTheConcert;
        this.time = time;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Venue getVenue() {
        return venue;
    }
    public void setVenue(Venue venue) {
        this.venue = venue;
    }
    public String getStartOfTheConcert() {
        return startOfTheConcert;
    }
    public void setStartOfTheConcert(String startOfTheConcert) {
        this.startOfTheConcert = startOfTheConcert;
    }
    public List<Artist> getListOfArtists() {
        return listOfArtists;
    }
    public void setListOfArtists(List<Artist> listOfArtists) {
        this.listOfArtists = listOfArtists;
    }
}