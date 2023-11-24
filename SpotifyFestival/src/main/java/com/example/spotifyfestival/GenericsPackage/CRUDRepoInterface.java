package com.example.spotifyfestival.GenericsPackage;

import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;

public interface CRUDRepoInterface<K, V> {
    Iterable<V> getAll();
    void add(K key, V value) throws DuplicateEntityException;
    void update(K key, V value);
    void delete(K key);
    V getItem(K key);
    int getSize();
    void list();
}
