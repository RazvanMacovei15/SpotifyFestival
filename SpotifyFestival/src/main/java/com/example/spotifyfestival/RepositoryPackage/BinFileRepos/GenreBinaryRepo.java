package com.example.spotifyfestival.RepositoryPackage.BinFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

import java.io.IOException;
import java.io.RandomAccessFile;

public class GenreBinaryRepo extends FileRepository<Integer, Genre> {
    public GenreBinaryRepo(String filename) {
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
            // Open the file in read-write mode
            RandomAccessFile file = new RandomAccessFile(filename, "rw");

            // Set the file length to 0 to truncate the content
            file.setLength(0);

            // Close the file to save the changes
            file.close();

            System.out.println("Binary file cleared successfully.");
        } catch (IOException e) {
            // Handle exceptions such as file not found or permission issues
            throw new RuntimeException("Exception occurred");
        }
    }
}
