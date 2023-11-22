package com.example.spotifyfestival.UserData.Repos.FileRepos;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.FileSavingStuff.FileRepository;

import java.io.*;

public class ArtistFileRepo extends FileRepository<String, Artist> {
    public ArtistFileRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFle() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.filename));
            String line = null;
            while ( (line = reader.readLine()) != null){
                String[] tokens = line.split("[,]");
                if ( tokens.length != 2 )
                    continue;
                else{
                    String ID = tokens[0].trim();
                    String name = tokens[1].trim();

                    Artist a = new Artist(ID, name);
                    super.add(ID, a);
                }
            }
            reader.close();
        }
        catch (FileNotFoundException f){
            throw new RuntimeException("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename));
            Iterable<Artist> artists = this.getAll();
            for (Artist artist : artists)
                writer.write(artist.getId() + ", " + artist.getName()+ ", " + artist.getGenres() + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
