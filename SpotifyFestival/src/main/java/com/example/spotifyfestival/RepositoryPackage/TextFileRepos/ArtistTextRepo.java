package com.example.spotifyfestival.RepositoryPackage.TextFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

public class ArtistTextRepo extends FileRepository<Integer, Artist> {
    public ArtistTextRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFle() {

    }

    @Override
    protected void writeToFile() {

    }
}
