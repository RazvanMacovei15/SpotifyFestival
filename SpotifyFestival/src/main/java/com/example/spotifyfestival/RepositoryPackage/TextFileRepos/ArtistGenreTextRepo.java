package com.example.spotifyfestival.RepositoryPackage.TextFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import com.example.spotifyfestival.LabFacultate.FileSavingStuff.FileRepository;

import java.io.*;
import java.util.Iterator;

public class ArtistGenreTextRepo extends FileRepository<Integer, ArtistGenre> {
    public ArtistGenreTextRepo(String filename) {
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
                int artistId = Integer.parseInt(parts[1]);
                int genreId = Integer.parseInt(parts[2]);

                ArtistGenre aG = new ArtistGenre(id, artistId, genreId);
                super.add(aG.getId(), aG);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void writeToFile() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            Iterable<ArtistGenre> iterable = this.getAll();
            Iterator<ArtistGenre> it = iterable.iterator();
            while(it.hasNext()){
                ArtistGenre artistG = (ArtistGenre) it.next();
                writer.write(artistG.toString());
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
