package com.example.spotifyfestival.ConcertsAndFestivals;

import java.util.Date;

public class Concert {
    private String locationName;
    private double locationLatitude;
    private double locationLongitude;
    private Date startOfTheConcert;
    private Date endOfTheConcert;

    public static void main(String[] args) {
        String jsonResponse = JSONConstant.getConstant();
        System.out.println(jsonResponse);
    }
}
