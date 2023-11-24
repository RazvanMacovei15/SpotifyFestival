package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

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
        return "ArtistGenre{" +
                "artist_id=" + artist_id +
                ", genre_id=" + genre_id +
                ", id=" + id +
                '}';
    }
}
