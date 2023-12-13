package com.example.spotifyfestival.RepositoryPackage.TextFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

public class GenreTextRepo extends FileRepository<Integer, Genre> {
    public GenreTextRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFle() {

    }

    @Override
    protected void writeToFile() {

    }
}
