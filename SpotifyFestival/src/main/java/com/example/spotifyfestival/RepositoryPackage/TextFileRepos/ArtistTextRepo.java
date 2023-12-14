package com.example.spotifyfestival.RepositoryPackage.TextFileRepos;

import com.example.spotifyfestival.DatabasePackage.DAO.ArtistDAO;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

import java.io.*;
import java.util.Map;

public class ArtistTextRepo extends FileRepository<Integer, Artist> {
    protected String filename ;
    public ArtistTextRepo(String filename) {
        super(filename);
        this.filename = filename;
    }

    @Override
    protected void readFromFle() {
        try {
            File file = new File(filename);

            if (!file.exists()) {

                throw new FileNotFoundException("File not found: " + filename);

            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("[;]");

                if (tokens.length != 3) {
                    throw new IllegalStateException("This format is not allowed!");
                } else {
                    Integer artist_id = Integer.parseInt(tokens[0].trim());
                    String name = tokens[1].trim();
                    String spotifyId = tokens[2].trim();
                    Artist a = new Artist(artist_id, name, spotifyId);
                    super.add(artist_id, a);
                }
            }

            reader.close();
        } catch (FileNotFoundException f) {
            throw new RuntimeException("File not found");
        } catch (IOException | DuplicateEntityException | NumberFormatException e) {
            throw new RuntimeException("Error reading from file", e);
        }
    }

    @Override
    protected void writeToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            Iterable<Artist> map = this.getAll();
            for(Artist a : map)
            {
                writer.write(a.toString() + "\n");
            }
            writer.close();
        }
        catch (IOException io) {
            throw new RuntimeException("Exception occurred");
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

    public static void main(String[] args) {
        String filename = "ArtistsTextRepo";
        ArtistTextRepo artistTextRepo = new ArtistTextRepo(filename);
        ArtistDAO artistDAO = ArtistDAO.getInstance();

        Iterable<Artist> artistsForText = artistDAO.getAll();
        for(Artist artist : artistsForText){
            try {
                artistTextRepo.add(artist.getId(), artist);
            } catch (DuplicateEntityException e) {
                throw new RuntimeException(e);
            }
        }
        artistTextRepo.writeToFile();
    }
}
