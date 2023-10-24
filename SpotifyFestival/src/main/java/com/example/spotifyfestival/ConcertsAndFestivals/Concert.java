package com.example.spotifyfestival.ConcertsAndFestivals;

import java.util.Date;
import java.util.List;

public class Concert {
    private String description;
    private String city;
    private String venue;
    private double locationLatitude;
    private double locationLongitude;
    private Date startOfTheConcert;
    private Date endOfTheConcert;
    private List<Artist> listOfArtists;

    public Concert(String description, String city, String venue, double locationLatitude, double locationLongitude, Date startOfTheConcert, Date endOfTheConcert, List<Artist> listOfArtists) {
        this.description = description;
        this.city = city;
        this.venue = venue;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.startOfTheConcert = startOfTheConcert;
        this.endOfTheConcert = endOfTheConcert;
        this.listOfArtists = listOfArtists;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Concert(){
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public double getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public double getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public Date getStartOfTheConcert() {
        return startOfTheConcert;
    }

    public void setStartOfTheConcert(Date startOfTheConcert) {
        this.startOfTheConcert = startOfTheConcert;
    }

    public Date getEndOfTheConcert() {
        return endOfTheConcert;
    }

    public void setEndOfTheConcert(Date endOfTheConcert) {
        this.endOfTheConcert = endOfTheConcert;
    }

    public List<Artist> getListOfArtists() {
        return listOfArtists;
    }

    public void setListOfArtists(List<Artist> listOfArtists) {
        this.listOfArtists = listOfArtists;
    }
}
