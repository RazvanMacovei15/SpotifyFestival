package com.example.spotifyfestival.RepositoryPackage.BinFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

public class GenreBinaryRepo extends FileRepository<Integer, Genre> {
    public GenreBinaryRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFle() {

    }

    @Override
    protected void writeToFile() {

    }
}
