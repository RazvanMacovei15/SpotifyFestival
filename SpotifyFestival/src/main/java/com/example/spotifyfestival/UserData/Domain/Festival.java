package com.example.spotifyfestival.UserData.Domain;

import com.example.spotifyfestival.ConcertsAndFestivals.Entity;
import com.example.spotifyfestival.ConcertsAndFestivals.Venue;

public class Festival extends Entity {
    protected String name;
    protected Venue venue;

    public Festival(int id, String name, Venue venue) {
        super(id);
        this.name = name;
        this.venue=venue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
