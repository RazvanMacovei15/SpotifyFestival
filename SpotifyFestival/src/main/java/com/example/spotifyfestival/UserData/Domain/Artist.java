package com.example.spotifyfestival.UserData.Domain;

import com.example.spotifyfestival.ConcertsAndFestivals.Entity;

import java.util.List;

public class Artist extends Entity{

    private String name;

    protected List<String> genres;

    public Artist(String name, String spotifyID) {
        this.name = name;
    }

    public Artist(String name, List<String> genres, String spotifyID) {
        this.name = name;
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
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
}