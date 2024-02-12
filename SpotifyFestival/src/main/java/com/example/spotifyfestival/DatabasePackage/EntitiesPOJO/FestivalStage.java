package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import java.util.Objects;

public class FestivalStage extends Entity {

    protected String name;

    protected Venue venue;

    public FestivalStage(int id, String name, Venue venue) {
        super(id);
        this.name = name;
        this.venue = venue;
    }

    public FestivalStage(int id) {
        super(id);
    }

    protected int venueId;

    public int getVenueId() {
        return venueId = venue.getId();
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

    @Override
    public String toString() {
        return "FestivalStage{" +
                "name='" + name + '\'' +
                ", venue=" + venue +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FestivalStage stage)) return false;
        return Objects.equals(id, stage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
