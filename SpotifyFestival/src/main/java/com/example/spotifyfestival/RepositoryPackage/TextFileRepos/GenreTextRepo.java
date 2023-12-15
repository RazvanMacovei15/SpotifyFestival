package com.example.spotifyfestival.RepositoryPackage.TextFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import com.example.spotifyfestival.LabFacultate.FileSavingStuff.FileRepository;

import java.io.*;
import java.util.Iterator;

public class GenreTextRepo extends FileRepository<Integer, Genre> {
    public GenreTextRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try(BufferedReader r = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split(",");

                if(parts.length != 2){
                    throw new IllegalStateException("this format is not allowed!!");
                }

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];


                Genre a = new Genre(id, name);
                super.add(a.getId(), a);
            }
        } catch (DuplicateEntityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            Iterable<Genre> iterable = this.getAll();
            Iterator<Genre> it = iterable.iterator();
            while(it.hasNext()){
                Genre artist = (Genre) it.next();
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
