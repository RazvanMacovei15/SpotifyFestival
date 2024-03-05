package com.example.spotifyfestival.database.helpers;

import com.example.spotifyfestival.database.entities.pojo.Identifiable;
import com.example.spotifyfestival.generics.MemoryRepository;

public abstract class DBGenericRepository< K, V extends Identifiable<K>> extends MemoryRepository<K,V>{
    public DBGenericRepository(){
    }
}