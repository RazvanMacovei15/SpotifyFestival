package com.example.spotifyfestival.ConcertsAndFestivals;

import com.example.spotifyfestival.UserData.DuplicateEntityException;
import com.example.spotifyfestival.UserData.Generics.MemoryRepository;

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