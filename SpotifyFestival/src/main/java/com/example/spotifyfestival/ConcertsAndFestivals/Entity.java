package com.example.spotifyfestival.ConcertsAndFestivals;

import java.util.Random;

public class Entity implements Identifiable {
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
        } else {
            prefix = "U"; // "U" for unknown or other entities
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