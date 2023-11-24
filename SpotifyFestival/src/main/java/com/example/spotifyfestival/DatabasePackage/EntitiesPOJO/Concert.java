package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import java.util.List;

public class Concert extends Entity {
    private String description;
    private List<Artist> listOfArtists;
    private Artist artist;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    private Venue venue;
    private String startOfTheConcert;
    private String time;

    public Concert(int id, String description, List<Artist> listOfArtists, Venue venue, String startOfTheConcert, String time) {
        super(id);
        this.description = description;
        this.listOfArtists = listOfArtists;
        this.venue = venue;
        this.startOfTheConcert = startOfTheConcert;
        this.time = time;
    }

    public Concert(int id, String description, Artist artist, Venue venue, String startOfTheConcert, String time) {
        super(id);
        this.description = description;
        this.artist = artist;
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

    @Override
    public String toString() {
        return "Concert{" +
                "description='" + description + '\'' +
                ", listOfArtists=" + listOfArtists +
                ", artist=" + artist +
                ", venue=" + venue +
                ", startOfTheConcert='" + startOfTheConcert + '\'' +
                ", time='" + time + '\'' +
                ", id=" + id +
                '}';
    }
}