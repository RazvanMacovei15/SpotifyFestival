package com.example.spotifyfestival.ConcertsAndFestivals;

import java.time.LocalDateTime;
import java.util.List;

public class Concert {
    private String description;
    private List<Artist> listOfArtists;
    private Venue venue;
    private LocalDateTime startOfTheConcert;
    private String time;

    public Concert(String description, List<Artist> listOfArtists, Venue venue, LocalDateTime startOfTheConcert, String time) {
        this.description = description;
        this.listOfArtists = listOfArtists;
        this.venue = venue;
        this.startOfTheConcert = startOfTheConcert;
        this.time = time;
    }

    public Concert(){
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

    public LocalDateTime getStartOfTheConcert() {
        return startOfTheConcert;
    }

    public void setStartOfTheConcert(LocalDateTime startOfTheConcert) {
        this.startOfTheConcert = startOfTheConcert;
    }

    public List<Artist> getListOfArtists() {
        return listOfArtists;
    }

    public void setListOfArtists(List<Artist> listOfArtists) {
        this.listOfArtists = listOfArtists;
    }
}
