package com.example.spotifyfestival.DatabasePackage.ValidatorClasses;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.GenericsPackage.Validator;
import javafx.collections.ObservableList;

public class ArtistValidator implements Validator<Artist> {

    public static void validateArtist(Artist artist) throws IllegalArgumentException {
        validateName(artist.getName());
        validateGenres(artist.getGenres());
        validateSpotifyId(artist.getSpotify_id());
        // Add more validation rules as needed
    }

    private static void validateName(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    private static void validateGenres(ObservableList<Genre> genres) throws IllegalArgumentException {
        if (genres == null || genres.isEmpty()) {
            throw new IllegalArgumentException("Genres list cannot be null or empty");
        }
    }

    private static void validateSpotifyId(String spotifyId) throws IllegalArgumentException {
        // Add your validation rules for Spotify ID
        // For example, check for length, format, etc.
        if (spotifyId == null || spotifyId.trim().isEmpty()) {
            throw new IllegalArgumentException("Spotify ID cannot be null or empty");
        }
    }

    @Override
    public boolean validate(Artist item) {
        if(item == null){
            return false;
        }
        validateArtist(item);
        return true;
    }
}
