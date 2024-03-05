package com.example.spotifyfestival.database.entities.pojo;

import java.util.List;
import java.util.Objects;

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
    private FestivalStage festivalStage;
    protected int venueId;
    protected int artistIdValue;
    protected int stageId;

    public Concert(int id, String description, List<Artist> listOfArtists, Venue venue, String startOfTheConcert, String time, FestivalStage festivalStage) {
        super(id);
        this.description = description;
        this.listOfArtists = listOfArtists;
        this.venue = venue;
        this.startOfTheConcert = startOfTheConcert;
        this.time = time;
        this.festivalStage = festivalStage;
    }

    public String listOfArtistToString(List<Artist> artists) {
        StringBuilder sb = new StringBuilder();
//        sb.append("Artists: ");
        if (artists.size() == 1) {
            sb.append(artists.get(0).getName());
        } else if (artists.size() == 2) {

            sb.append(artists.get(0).getName());
            sb.append(" & ");
            sb.append(artists.get(1).getName());

        } else {
            for (int i = 0; i < artists.size() - 1; i++) {
                Artist artistToAdd = artists.get(i);
                sb.append(artistToAdd.getName());
                sb.append(" & ");
            }
            sb.append(artists.get(artists.size()).getName());
        }
        return sb.toString();
    }

    public FestivalStage getFestivalStage() {
        return festivalStage;
    }

    public void setFestivalStage(FestivalStage festivalStage) {
        this.festivalStage = festivalStage;
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

    public int getVenueId() {
        return venueId = venue.getId();
    }

    public int getArtistIdValue() {
        return artistIdValue = artist.getId();
    }

    public int getStageId() {
        return stageId = festivalStage.getId();
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Concert concert = (Concert) o;
        return Objects.equals(id, concert.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}