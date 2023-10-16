package com.example.spotifyfestival.graphForConcertsAndFestivals;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
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
