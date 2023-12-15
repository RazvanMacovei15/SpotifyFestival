package com.example.spotifyfestival.DatabasePackage.DBHelpers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Identifiable;
import com.example.spotifyfestival.GenericsPackage.MemoryRepository;

public abstract class DBGenericRepository< K, V extends Identifiable<K>> extends MemoryRepository<K,V>{
    public DBGenericRepository(){
    }
}