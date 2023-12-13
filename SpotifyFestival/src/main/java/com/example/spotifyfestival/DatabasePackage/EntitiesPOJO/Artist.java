package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Objects;

public class Artist extends Entity{

    protected String name;
    protected ObservableList<Genre> genres ;
    protected String spotify_id;

    public Artist(int id, String name, ObservableList<Genre> genres, String spotify_id) {
        super(id);
        this.name = name;
        this.genres = genres;
        this.spotify_id = spotify_id;
    }

    public Artist(int id, String name, String spotify_id) {
        super(id);
        this.name = name;
        this.spotify_id = spotify_id;
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

    public String getSpotify_id() {
        return spotify_id;
    }

    public void setSpotify_id(String spotify_id) {
        this.spotify_id = spotify_id;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", genres=" + genres + '\'' +
                ", spotify_id='" + spotify_id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Artist artist = (Artist) obj;
        return Objects.equals(getId(), artist.getId());
    }
}