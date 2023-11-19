package com.example.spotifyfestival.UserData.Domain;

import com.example.spotifyfestival.ConcertsAndFestivals.Entity;
import javafx.collections.ObservableList;

import java.util.List;

public class Artist extends Entity{

    private String name;

    protected ObservableList<String> genres;

    protected String spotify_id;

    public Artist(String name) {
        this.name = name;
    }

    public Artist(String name, String spotify_id){
        this.name = name;
        this.spotify_id = spotify_id;

    }
    public Artist(String name, ObservableList<String> genres) {
        this.name = name;
        this.genres = genres;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(ObservableList<String> genres) {
        this.genres = genres;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}