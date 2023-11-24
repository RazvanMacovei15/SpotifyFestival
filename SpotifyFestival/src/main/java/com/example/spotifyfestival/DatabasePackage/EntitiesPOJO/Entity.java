package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

public class Entity implements Identifiable<Integer> {
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
}