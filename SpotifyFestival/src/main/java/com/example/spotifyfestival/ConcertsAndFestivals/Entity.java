package com.example.spotifyfestival.ConcertsAndFestivals;

import com.example.spotifyfestival.UserData.Domain.Artist;
import com.example.spotifyfestival.UserData.Domain.User;

import java.util.Random;

public class Entity implements Identifiable<String> {
    protected String id;
    public Entity() {
        id = generateUniqueID();
    }
    private String generateUniqueID() {
        Random random = new Random();
        String prefix;
        if (this instanceof Concert) {
            prefix = "C";
        } else if (this instanceof Venue) {
            prefix = "V";
        }
        else if (this instanceof Artist) {
            prefix = "A";
        }
        else if(this instanceof User){
            prefix = "U";
        }
        else{
            prefix = "X";
        }
        int randomDigits = 1000 + random.nextInt(9000); // Generates a 4-digit random number
        return prefix + randomDigits;
    }
    @Override
    public String getId() {
        return id;
    }
    @Override
    public void setId(String id) {
        this.id = id;
    }
}