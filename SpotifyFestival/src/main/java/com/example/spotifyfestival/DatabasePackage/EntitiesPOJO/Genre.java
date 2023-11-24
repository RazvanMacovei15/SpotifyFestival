package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

public class Genre extends Entity {
    protected String name;

    public Genre(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
