package com.example.spotifyfestival.RepositoryPackage.TextFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

public class ArtistGenreTextRepo extends FileRepository<Integer, ArtistGenre> {
    public ArtistGenreTextRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFle() {

    }

    @Override
    protected void writeToFile() {

    }
}
