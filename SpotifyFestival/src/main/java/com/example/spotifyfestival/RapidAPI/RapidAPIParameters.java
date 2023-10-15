package com.example.spotifyfestival.RapidAPI;

import java.time.LocalDate;

public class RapidAPIParameters {

    private LocalDate startDate;
    private LocalDate endDate;
    private String city;

    public RapidAPIParameters(LocalDate startDate, LocalDate endDate, String city) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
