package com.example.spotifyfestival.UserData.JUnitTesting;

import com.example.spotifyfestival.UserData.Domain.Artist;

public class ArtistValidator implements Validator<Artist> {

    @Override
    public boolean validate(Artist artist) {
        return artist != null && artist.getName() != null && !artist.getName().isEmpty();
    }
}