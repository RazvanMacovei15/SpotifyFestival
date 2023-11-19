package com.example.spotifyfestival.UserData.Domain;

import com.example.spotifyfestival.ConcertsAndFestivals.Entity;

public class ArtistGenre extends Entity {
    protected int artist_id;
    protected int genre_id;

    public ArtistGenre(int artist_id, int genre_id) {
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
}