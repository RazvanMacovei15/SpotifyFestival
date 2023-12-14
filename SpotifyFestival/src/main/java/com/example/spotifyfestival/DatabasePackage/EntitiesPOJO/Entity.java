package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import java.io.Serializable;

public class Entity implements Identifiable<Integer>, Serializable {
    protected Integer id;
    public Entity(int id) {
        this.id = id;
    }
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                '}';
    }
}