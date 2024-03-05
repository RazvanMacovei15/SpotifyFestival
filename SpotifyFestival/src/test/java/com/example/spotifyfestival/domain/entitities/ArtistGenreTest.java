package com.example.spotifyfestival.domain.entitities;

import com.example.spotifyfestival.database.entities.pojo.ArtistGenre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ArtistGenreTest {

    private ArtistGenre artistGenre;

    @BeforeEach
    public void setUp() {
        artistGenre = new ArtistGenre(1, 2, 3);
    }

    @Test
    public void testGetArtist_id() {
        assertEquals(2, artistGenre.getArtist_id());
    }

    @Test
    public void testSetArtist_id() {
        artistGenre.setArtist_id(4);
        assertEquals(4, artistGenre.getArtist_id());
    }

    @Test
    public void testGetGenre_id() {
        assertEquals(3, artistGenre.getGenre_id());
    }

    @Test
    public void testSetGenre_id() {
        artistGenre.setGenre_id(5);
        assertEquals(5, artistGenre.getGenre_id());
    }

    @Test
    public void testToString() {
        assertEquals("1,2,3", artistGenre.toString());
    }

    @Test
    public void testEquals() {
        ArtistGenre sameArtistGenre = new ArtistGenre(1, 2, 3);
        ArtistGenre differentArtistGenre = new ArtistGenre(4, 5, 6);

        assertEquals(artistGenre, sameArtistGenre);
        assertNotEquals(artistGenre, differentArtistGenre);
    }
}
