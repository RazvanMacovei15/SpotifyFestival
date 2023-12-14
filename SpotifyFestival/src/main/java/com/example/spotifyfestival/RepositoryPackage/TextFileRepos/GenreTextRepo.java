package com.example.spotifyfestival.RepositoryPackage.TextFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

import java.io.FileWriter;
import java.io.IOException;

public class GenreTextRepo extends FileRepository<Integer, Genre> {
    public GenreTextRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {

    }

    @Override
    protected void writeToFile() {

    }

    @Override
    protected void clear() {
        try {
            // Open the file in write mode, which clears the content
            FileWriter fileWriter = new FileWriter(filename);

            // Close the file writer to save the changes
            fileWriter.close();

            //System.out.println("Text file cleared successfully.");
        } catch (IOException e) {
            // Handle exceptions such as file not found or permission issues
            throw new RuntimeException("Exception occurred");
        }
    }
}
