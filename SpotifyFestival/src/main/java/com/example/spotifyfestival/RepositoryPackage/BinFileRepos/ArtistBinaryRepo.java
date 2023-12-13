package com.example.spotifyfestival.RepositoryPackage.BinFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

public class ArtistBinaryRepo extends FileRepository<Integer, Artist> {
    public ArtistBinaryRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFle() {

    }

    @Override
    protected void writeToFile() {

    }
}
