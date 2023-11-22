package com.example.spotifyfestival.UserData.JUnitTesting;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.JUnitTesting.ArtistValidator;
import com.example.spotifyfestival.UserData.Repos.FileRepos.ArtistFileRepo;
import com.example.spotifyfestival.UserData.Repos.FileRepos.ArtistRepoBinary;



import com.example.spotifyfestival.UserData.Generics.CRUDRepoInterface;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArtistRepositoryTest {

    @Test
    public void testTextFileRepository() {
        CRUDRepoInterface<String, Artist> repository = new ArtistFileRepo("artists.txt");
        Artist artist = new Artist("1", "John Doe");

        try {
            repository.add(artist.getId(), artist);
        } catch (DuplicateEntityException e) {
            fail("Should not throw DuplicateEntityException");
        }

        assertEquals(1, repository.getAll().spliterator().getExactSizeIfKnown());
        assertTrue(repository.getAll().spliterator().tryAdvance(a -> assertEquals(artist, a)));
    }

    @Test
    public void testBinaryFileRepository() {
        CRUDRepoInterface<String, Artist> repository = new ArtistRepoBinary("artists.bin");
        Artist artist = new Artist("2", "Jane Doe");

        try {
            repository.add(artist.getId(), artist);
        } catch (DuplicateEntityException e) {
            fail("Should not throw DuplicateEntityException");
        }

        assertEquals(1, repository.getAll().spliterator().getExactSizeIfKnown());
        assertTrue(repository.getAll().spliterator().tryAdvance(a -> assertEquals(artist, a)));
    }

    @Test
    public void testArtistValidator() {
        ArtistValidator validator = new ArtistValidator();
        Artist validArtist = new Artist("3", "Valid Artist");
        Artist invalidArtist = new Artist("4", "");

        assertTrue(validator.validate(validArtist));
        assertFalse(validator.validate(invalidArtist));
    }
}
