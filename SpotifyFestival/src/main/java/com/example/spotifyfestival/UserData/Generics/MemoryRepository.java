package com.example.spotifyfestival.UserData.Generics;


import com.example.spotifyfestival.ConcertsAndFestivals.Identifiable;
import com.example.spotifyfestival.UserData.DuplicateEntityException;

import java.util.HashMap;
import java.util.Map;

public class MemoryRepository<K, V extends Identifiable<K>> implements CRUDRepoInterface<K,V> {
    private final Map<K, V> dataStore;

    public MemoryRepository() {
        dataStore = new HashMap<>();
    }

    @Override
    public Map<K, V> getAll() {
        return new HashMap<>(dataStore);
    }

    @Override
    public void add(K key, V value) throws DuplicateEntityException {
        dataStore.put(key, value);
    }

    @Override
    public void update(K key, V value) {
        if (dataStore.containsKey(key)) {
            dataStore.put(key, value);
        } else {
            throw new IllegalArgumentException("Key not found for update: " + key);
        }
    }

    @Override
    public void delete(K key) {
        dataStore.remove(key);
    }

    @Override
    public V getItem(K key) {
        return dataStore.get(key);
    }

    @Override
    public int getSize() {
        return dataStore.size();
    }

    public K getKey(){
        if (!dataStore.isEmpty()) {
            return dataStore.keySet().iterator().next();
        }
        // Return null or throw an exception if the map is empty
        return null;
    }

    @Override
    public void list() {
        for (Map.Entry<K, V> entry : dataStore.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}
