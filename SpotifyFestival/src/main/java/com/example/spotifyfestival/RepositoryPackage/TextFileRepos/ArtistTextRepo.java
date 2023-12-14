package com.example.spotifyfestival.RepositoryPackage.TextFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

import java.io.*;
import java.util.Iterator;

public class ArtistTextRepo extends FileRepository<Integer, Artist> {

    public ArtistTextRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try(BufferedReader r = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts.length != 3){
                    throw new IllegalStateException("this format is not allowed!!");
                }

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String spotifyId = parts[2];

                Artist a = new Artist(id, name, spotifyId);
                super.add(a.getId(), a);
            }
        } catch (DuplicateEntityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            Iterable<Artist> iterable = this.getAll();
            Iterator<Artist> it = iterable.iterator();
            while(it.hasNext()){
                Artist artist = (Artist) it.next();
                writer.write(artist.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void clear() {
        // Simply delete the contents of the file for clearing the repository
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print("");
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }
}
