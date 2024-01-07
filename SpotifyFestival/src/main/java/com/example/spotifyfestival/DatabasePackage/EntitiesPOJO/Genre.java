package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import java.util.Objects;

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

    @Override
    public String toString() {
        return name + " with ID=" + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Genre artist = (Genre) obj;
        return Objects.equals(getId(), artist.getId());
    }
}
