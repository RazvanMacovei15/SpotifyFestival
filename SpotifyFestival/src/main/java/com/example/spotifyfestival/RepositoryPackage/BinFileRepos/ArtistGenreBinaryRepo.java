package com.example.spotifyfestival.RepositoryPackage.BinFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

public class ArtistGenreBinaryRepo extends FileRepository<Integer, ArtistGenre> {
    public ArtistGenreBinaryRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFle() {

    }

    @Override
    protected void writeToFile() {

    }
}
