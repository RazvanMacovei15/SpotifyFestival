package com.example.spotifyfestival.RepositoryPackage.TextFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

import java.io.*;

public class ArtistTextRepo extends FileRepository<Integer, Artist> {
    protected String filename;

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

                System.out.println(a);
            }



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {

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



    public static void main(String[] args) {
        String filename = "ArtistsTextRepo.txt" ;
        ArtistTextRepo artistTextRepo = new ArtistTextRepo(filename);
    }
}
