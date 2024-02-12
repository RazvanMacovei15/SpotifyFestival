package com.example.spotifyfestival.GenericsPackage;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Identifiable;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemoryRepository<K, V extends Identifiable<K>> implements RepoInterface<K,V> {
    protected Map<K, V> dataStore;

    public Map<K, V> getDataStore() {
        return dataStore;
    }

    public MemoryRepository() {
        this.dataStore = new HashMap<>();
    }
    @Override
    public Iterable<V> getAll() {
        return dataStore.values();
    }
    @Override
    public void add(K key, V value) throws DuplicateEntityException{
        if(dataStore.put(key, value) != null)
            throw new DuplicateEntityException("Duplicate ID!!!");
    }
    @Override
    public void update(K key, V value) {
        if (!dataStore.containsKey(key)) throw new RuntimeException("Key does not exist!");
        dataStore.replace(key, value);

    }
    @Override
    public void delete(K key) {
        if(!dataStore.containsKey(key))throw new RuntimeException("Key does not exist!");
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
    @Override
    public void list() {
        for (Map.Entry<K, V> entry : dataStore.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue().toString());
        }
    }
    public List<K> getListOfKeys() {
        return dataStore.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}