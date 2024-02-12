package com.example.spotifyfestival.GenericsPackage;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Identifiable;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;

import java.sql.SQLException;

public interface MemoryRepoInterface<K, V extends Identifiable<K>> {
    Iterable<V> getAll();
    void add(K key, V value) throws DuplicateEntityException, SQLException;
    void update(K key, V value);
    void delete(K key);
    V getItem(K key);
    int getSize();
    void list();
}
