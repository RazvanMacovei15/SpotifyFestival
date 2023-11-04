package com.example.spotifyfestival.UserData.Generics;

import com.example.spotifyfestival.UserData.DuplicateEntityException;

import java.util.Map;

public interface CRUDRepoInterface<K, V> {
    Map<K, V> getAll();
    void add(K key, V value) throws DuplicateEntityException;
    void update(K key, V value);
    void delete(K key);
    V getItem(K key);
    int getSize();
    void list();
}
