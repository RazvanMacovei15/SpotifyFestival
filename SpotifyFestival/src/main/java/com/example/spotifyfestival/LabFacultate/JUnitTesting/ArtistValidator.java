package com.example.spotifyfestival.LabFacultate.JUnitTesting;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;

public class ArtistValidator implements Validator<Artist> {

    @Override
    public boolean validate(Artist artist) {
        return artist != null && artist.getName() != null && !artist.getName().isEmpty();
    }
}