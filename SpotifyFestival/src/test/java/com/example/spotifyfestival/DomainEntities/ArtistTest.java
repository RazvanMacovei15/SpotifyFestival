package com.example.spotifyfestival.DomainEntities;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArtistTest {

    private Artist artist;
    private Genre genre;

    @BeforeEach
    public void setUp() {
        artist = new Artist(1, "ArtistName", "spotify123");
        genre = new Genre(1, "Pop");
    }

    @Test
    public void testGetGenres() {
        assertNull(artist.getGenres());
    }

    @Test
    public void testSetGenres() {
        ObservableList<Genre> genres = FXCollections.observableArrayList();
        genres.add(genre);

        artist.setGenres(genres);
        assertEquals(genres, artist.getGenres());
    }

    @Test
    public void testAddGenre() throws DuplicateEntityException {
        artist.addGenre(genre);
        assertTrue(artist.getGenres().contains(genre));
    }

    @Test
    public void testAddDuplicateGenre() {
        assertThrows(DuplicateEntityException.class, () -> {
            artist.addGenre(genre);
            artist.addGenre(genre);
        });
    }

    @Test
    public void testSetName() {
        artist.setName("NewName");
        assertEquals("NewName", artist.getName());
    }

    @Test
    public void testSetSpotifyId() {
        artist.setSpotifyId("newSpotifyId");
        assertEquals("newSpotifyId", artist.getSpotifyId());
    }

    @Test
    public void testToString() {
        assertEquals("1,ArtistName,spotify123", artist.toString());
    }

    @Test
    public void testEquals() {
        Artist sameArtist = new Artist(1, "ArtistName", "spotify123");
        Artist differentArtist = new Artist(2, "OtherArtist", "spotify456");

        assertEquals(artist, sameArtist);
        assertNotEquals(artist, differentArtist);
    }
}
