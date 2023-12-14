package com.example.spotifyfestival.RepositoryPackage.TextFileRepos;

import com.example.spotifyfestival.DatabasePackage.DAO.ArtistDAO;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArtistTextRepo extends FileRepository<Integer, Artist> {
    protected String filename;

    public ArtistTextRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Integer id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String spotifyId = parts[2].trim();
                    Artist artist = new Artist(id, name, spotifyId); // Assuming Artist has a constructor with id and name
                    super.add(id, artist);
                }
            }
        } catch (IOException | DuplicateEntityException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    @Override
    protected void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Artist entry : getAll()) {
                int id = entry.getId();
                String name = entry.getName();
                String spotifyId = entry.getSpotify_id();
                writer.write(String.format("%d, %s, %s%n", id, name, spotifyId));

            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions appropriately
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

//    public List<Artist> getAll(){
//        List<Artist> artists = new ArrayList<>();
//        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
//            String line;
//            while(true){
//                try{
//                    if((line = reader.readLine()) == null) break;;
//                } catch (IOException e){
//                    throw new RuntimeException(e);
//                }
//                String[] parts = line.split(",");
//                if(parts.length != 3){
//                    throw new IllegalStateException("this format is not allowed!!!");
//                }
//                int id = Integer.parseInt(parts[0]);
//                String name = parts[1];
//                String spotifyId = parts[2];
//
//                Artist newArtist = new Artist(id, name, spotifyId);
//                artists.add(newArtist);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return artists;
//    }
//
//    @Override
//    protected void readFromFile() {
//        try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
//            String line;
//
//            while ((line = reader.readLine()) != null) {
//                String[] tokens = line.split(",");
//
//                if (tokens.length != 3) {
//                    throw new IllegalStateException("This format is not allowed!");
//
//                } else {
//                    int artist_id = Integer.parseInt(tokens[0].trim());
//                    String name = tokens[1].trim();
//                    String spotifyId = tokens[2].trim();
//                    Artist a = new Artist(artist_id, name, spotifyId);
//                    super.add(artist_id, a);
//                }
//            }
//
//        } catch (FileNotFoundException f) {
//            throw new RuntimeException("File not found");
//        } catch (IOException | DuplicateEntityException | NumberFormatException e) {
//            throw new RuntimeException("Error reading from file", e);
//        }
//    }
//
//    @Override
//    protected void writeToFile() {
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
//            ArtistDAO artistDAO = ArtistDAO.getInstance();
//            Iterable<Artist> artistsForText = artistDAO.getAll();
//            Iterable<Artist> map = artistsForText;
//            for (Artist a : map) {
//                writer.write(a.toString() + "\n");
//            }
//            writer.close();
//        } catch (IOException io) {
//            throw new RuntimeException("Exception occurred");
//        }
//    }
//
//    @Override
//    protected void clear() {
//        try {
//            // Open the file in write mode, which clears the content
//            FileWriter fileWriter = new FileWriter(filename);
//
//            // Close the file writer to save the changes
//            fileWriter.close();
//
//            //System.out.println("Text file cleared successfully.");
//        } catch (IOException e) {
//            // Handle exceptions such as file not found or permission issues
//            throw new RuntimeException("Exception occurred");
//        }
//    }
//
//    public void readFromDB(){
//        ArtistDAO artistDAO = ArtistDAO.getInstance();
//        Iterable<Artist> artistsForText = artistDAO.getAll();
//    }
//
//    private void initializeFile() {
//        File file = new File(filename);
//
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//                System.out.println("File created successfully");
//                // Add default items to the file
//                addDefaultItems();
//
//            } catch (IOException e) {
//                throw new RuntimeException("Error creating file", e);
//            }
//        }
//    }
//
//    private void addDefaultItems() {
//        ArtistDAO artistDAO = ArtistDAO.getInstance();
//        Iterable<Artist> artistsForText = artistDAO.getAll();
//        //
////        List<Artist> defaultArtists = new ArrayList<>();
////        // Add your default artists here
////        defaultArtists.add(new Artist(1, "Default Artist 1", "default_spotify_id_1"));
////        defaultArtists.add(new Artist(2, "Default Artist 2", "default_spotify_id_2"));
//
//        // Write default items to the file
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
//            for (Artist artist : artistsForText) {
//                writer.write(artist.toString() + "\n");
//                super.add(artist.getId(), artist);
//            }
//        } catch (IOException | DuplicateEntityException e) {
//            throw new RuntimeException("Error writing default items to file", e);
//        }
//    }
//
    public static void main(String[] args) {
        String filename = "G:\\CS\\Summer Projects\\SpotifyFestival\\ArtistsTextRepo.txt" ;
        ArtistTextRepo artistTextRepo = new ArtistTextRepo(filename);
        artistTextRepo.writeToFile();
    }
}
