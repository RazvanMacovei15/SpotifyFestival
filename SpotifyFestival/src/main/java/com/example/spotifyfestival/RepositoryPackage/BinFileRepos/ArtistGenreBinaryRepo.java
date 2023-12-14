package com.example.spotifyfestival.RepositoryPackage.BinFileRepos;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.ArtistGenre;
import com.example.spotifyfestival.Lab_facultate.FileSavingStuff.FileRepository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ArtistGenreBinaryRepo extends FileRepository<Integer, ArtistGenre> {
    public ArtistGenreBinaryRepo(String filename) {
        super(filename);
    }

    @Override
    protected void readFromFile() {
        File file = new File(this.filename);
        if (!file.exists()) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.filename));
                out.writeObject(new HashMap());
                out.close();
            } catch (IOException var9) {
                throw new RuntimeException(var9);
            }
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(this.filename));

            try {
                this.dataStore = (Map)in.readObject();
            } catch (Throwable var6) {
                try {
                    in.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            in.close();
        } catch (EOFException var7) {
            throw new RuntimeException("Unexpected end of file", var7);
        } catch (ClassNotFoundException | IOException var8) {
            throw new RuntimeException(var8);
        }
    }

    @Override
    protected void writeToFile() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(this.filename));
            out.writeObject(this.dataStore);
            out.close();
        } catch (IOException var2) {
            throw new RuntimeException(var2);
        }
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
