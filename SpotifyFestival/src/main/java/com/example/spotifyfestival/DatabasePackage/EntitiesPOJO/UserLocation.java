package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import com.example.spotifyfestival.API_Packages.RapidAPI.RapidAPIConcertsAPI;

import java.net.http.HttpResponse;

public class UserLocation extends Entity{
    protected RapidAPIConcertsAPI api;
    protected double latitude;
    protected double longitude;
    public UserLocation(int id) {
        super(id);
        initialize();
    }

    private void initialize(){
        api = RapidAPIConcertsAPI.getInstance();
        HttpResponse<String> locationResponse = api.handleIpInfoHttpResponse();
        String locationCoordinates = api.getAttribute(locationResponse, "loc");
        getCoordinates(locationCoordinates);
        System.out.println(latitude);
        System.out.println(longitude);
    }

    public void getCoordinates(String str) {
        String[] coordinates = str.split(",");
        if (coordinates.length == 2) {
            latitude = Double.parseDouble(coordinates[0]);
            longitude = Double.parseDouble(coordinates[1]);
        } else {
            System.out.println("You parsed the wrong attribute!");
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
