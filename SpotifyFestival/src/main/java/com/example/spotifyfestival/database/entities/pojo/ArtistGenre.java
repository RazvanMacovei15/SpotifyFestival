package com.example.spotifyfestival.database.entities.pojo;

import java.util.Objects;

public class ArtistGenre extends Entity {
    protected int artist_id;
    protected int genre_id;

    public ArtistGenre(int id, int artist_id, int genre_id) {
        super(id);
        this.artist_id = artist_id;
        this.genre_id = genre_id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    @Override
    public String toString() {
        return id + "," + artist_id + "," + genre_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistGenre that = (ArtistGenre) o;
        return artist_id == that.artist_id && genre_id == that.genre_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist_id, genre_id);
    }
}
