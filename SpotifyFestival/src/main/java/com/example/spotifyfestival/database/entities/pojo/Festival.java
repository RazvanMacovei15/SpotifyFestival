package com.example.spotifyfestival.database.entities.pojo;

public class Festival extends Entity {
    protected String name;
    protected Venue venue;

    public Festival(int id, String name, Venue venue) {
        super(id);
        this.name = name;
        this.venue=venue;
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
        return "Festival{" +
                "name='" + name + '\'' +
                ", venue=" + venue +
                ", id=" + id +
                '}';
    }


}
