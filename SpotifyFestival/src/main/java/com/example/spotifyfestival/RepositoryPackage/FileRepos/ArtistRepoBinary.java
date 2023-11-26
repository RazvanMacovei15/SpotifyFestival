package com.example.spotifyfestival.RepositoryPackage.FileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

import java.io.*;
import java.util.Map;

public class ArtistRepoBinary extends FileRepository<Integer, Artist> {
    public ArtistRepoBinary(String filename) {
        super(filename);
    }
    @Override
    public void readFromFle() {
        // try-with-resources
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            dataStore = (Map<Integer, Artist>) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void writeToFile()
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(dataStore);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}