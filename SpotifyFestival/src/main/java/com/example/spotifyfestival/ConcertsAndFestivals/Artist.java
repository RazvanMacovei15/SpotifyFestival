package com.example.spotifyfestival.ConcertsAndFestivals;

public class Artist {

    private String name;

    private String spotifyID;

    public Artist(String name, String spotifyID) {
        this.name = name;
        this.spotifyID = spotifyID;
    }

    public Artist(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpotifyID() {
        return spotifyID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }
}