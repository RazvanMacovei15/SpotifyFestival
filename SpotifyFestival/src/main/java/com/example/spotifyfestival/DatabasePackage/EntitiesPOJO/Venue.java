package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import java.util.List;

public class Venue extends Entity {
    private String city;
    private String venueName;
    private String streetAddress;
    private String locationLatitude;
    private String locationLongitude;
    private List<Concert> listOfAllConcertsAtThatVenue;


    public Venue(Integer id, String city, String venueName, String streetAddress, String locationLatitude, String locationLongitude) {
        super(id);
        this.city = city;
        this.venueName = venueName;
        this.streetAddress = streetAddress;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }



    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public List<Concert> getListOfAllConcertsAtThatVenue() {
        return listOfAllConcertsAtThatVenue;
    }

    public void setListOfAllConcertsAtThatVenue(List<Concert> listOfAllConcertsAtThatVenue) {
        this.listOfAllConcertsAtThatVenue = listOfAllConcertsAtThatVenue;
    }

    @Override
    public String toString() {
        return "Venue{" +
                "city='" + city + '\'' +
                ", venueName='" + venueName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", locationLatitude='" + locationLatitude + '\'' +
                ", locationLongitude='" + locationLongitude + '\'' +
                ", listOfAllConcertsAtThatVenue=" + listOfAllConcertsAtThatVenue +
                ", id=" + id +
                '}';
    }
}