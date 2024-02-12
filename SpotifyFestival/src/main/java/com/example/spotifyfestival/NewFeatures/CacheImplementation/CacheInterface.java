package com.example.spotifyfestival.NewFeatures.CacheImplementation;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;

public interface CacheInterface<K,V> {
    Iterable<V> getAll();
    void add(K key, V value) throws DuplicateEntityException;
    void update(K key, V value);
    void delete(K key);
    V getItem(K key);
    int getSize();
    void list();
}
