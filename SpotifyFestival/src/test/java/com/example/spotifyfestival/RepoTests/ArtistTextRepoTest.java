package com.example.spotifyfestival.RepoTests;

import com.example.spotifyfestival.database.entities.pojo.Artist;
import com.example.spotifyfestival.database.entities.pojo.DuplicateEntityException;
import com.example.spotifyfestival.RepositoryPackage.TextFileRepos.ArtistTextRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArtistTextRepoTest {

    private ArtistTextRepo artistTextRepo;
    private final String filename = "C:\\Users\\razva\\Desktop\\my git Repos\\Personale\\SpotifyFestival\\ArtistsTextRepo";

    @BeforeEach
    public void setUp() {
        artistTextRepo = new ArtistTextRepo(filename);
        artistTextRepo.clear();
    }

    @Test
    public void testAdd() throws IOException {
        Artist artist = new Artist(301, "TestArtist", "testSpotifyId");
        try {
            artistTextRepo.add(artist.getId(), artist);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }

        // Check if the file contains the added artist
        ArtistTextRepo readRepo = new ArtistTextRepo(filename);
        assertEquals(artist, readRepo.getItem(301));
    }

    @Test
    public void testDelete() throws IOException {
        Artist artist = new Artist(30, "TestArtist", "testSpotifyId");
        try {
            artistTextRepo.add(artist.getId(), artist);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }

        artistTextRepo.delete(30);

        // Check if the file does not contain the deleted artist
        ArtistTextRepo readRepo = new ArtistTextRepo(filename);
        assertEquals(null, readRepo.getItem(30));
    }

    @Test
    public void testUpdate() throws IOException {
        Artist artist = new Artist(30, "TestArtist", "testSpotifyId");
        try {
            artistTextRepo.add(artist.getId(), artist);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }

        Artist updatedArtist = new Artist(30, "UpdatedArtist", "updatedSpotifyId");
        artistTextRepo.update(updatedArtist.getId(), updatedArtist);

        // Check if the file contains the updated artist
        ArtistTextRepo readRepo = new ArtistTextRepo(filename);
        assertEquals(updatedArtist, readRepo.getItem(30));
    }


    @Test
    public void testClear() throws IOException {
        Artist artist = new Artist(30, "TestArtist", "testSpotifyId");
        try {
            artistTextRepo.add(artist.getId(), artist);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }

        artistTextRepo.clear();

        // Check if the file is empty after clearing the repository
        File file = new File(filename);
        assertEquals(0, file.length());
    }

}
