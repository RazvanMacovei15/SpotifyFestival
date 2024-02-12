package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.Objects;

public class Artist extends Entity implements Serializable {

    protected String name;

    protected ObservableList<Genre> genres ;
    protected String spotifyId;

    protected String imageUrl;

    protected String spotifyLink;
    protected int popularity;


    public Artist(int id) {
        super(id);
    }

    public Artist(int id, String name, ObservableList<Genre> genres, String spotify_id) {
        super(id);
        this.name = name;
        this.genres = genres;
        this.spotifyId = spotify_id;
    }

    public Artist(int id, String name, String spotify_id) {
        super(id);
        this.name = name;
        this.spotifyId = spotify_id;
    }

    public Artist(int id, String name,  String spotifyId, ObservableList<Genre> genres, String imageUrl, String spotifyLink, int popularity) {
        super(id);
        this.name = name;
        this.genres = genres;
        this.spotifyId = spotifyId;
        this.imageUrl = imageUrl;
        this.spotifyLink = spotifyLink;
        this.popularity = popularity;
    }

    public ObservableList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ObservableList<Genre> genres) {
        this.genres = genres;
    }

    public void addGenre(Genre genre) throws DuplicateEntityException{
        if(genres == null){
            genres = FXCollections.observableArrayList();
            genres.add(genre);
        }else{
            for(Genre genre1 : genres){
                if(genre1.getId().equals(genre.getId()))
                    throw new DuplicateEntityException("Genre already exists!");
            }
            genres.add(genre);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSpotifyLink() {
        return spotifyLink;
    }

    public void setSpotifyLink(String spotifyLink) {
        this.spotifyLink = spotifyLink;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + spotifyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(name, artist.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}