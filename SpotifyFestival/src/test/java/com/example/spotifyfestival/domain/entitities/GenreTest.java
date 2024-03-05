package com.example.spotifyfestival.domain.entitities;

import com.example.spotifyfestival.database.entities.pojo.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GenreTest {

    private Genre genre;

    @BeforeEach
    public void setUp() {
        genre = new Genre(1, "Pop");
    }

    @Test
    public void testGetName() {
        assertEquals("Pop", genre.getName());
    }

    @Test
    public void testSetName() {
        genre.setName("Rock");
        assertEquals("Rock", genre.getName());
    }

    @Test
    public void testToString() {
        assertEquals("1,Pop", genre.toString());
    }

    @Test
    public void testEquals() {
        Genre sameGenre = new Genre(1, "Pop");
        Genre differentGenre = new Genre(2, "Rock");

        assertEquals(genre, sameGenre);
        assertNotEquals(genre, differentGenre);
    }
}
