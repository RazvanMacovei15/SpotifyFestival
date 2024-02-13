package com.example.spotifyfestival.NewFeatures.CacheImplementation;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;

import java.util.HashMap;
import java.util.Map;

public class GenericMemoryCacheRepository<K,V > implements CacheInterface<K,V> {
    private final Map<K,V> cache;

    public GenericMemoryCacheRepository() {
        this.cache = new HashMap<>();
    }

    public Map<K, V> getCache() {
        return cache;
    }

    @Override
    public Iterable<V> getAll() {
        return cache.values();
    }

    @Override
    public void add(K key, V value) throws DuplicateEntityException {
        if(cache.containsKey(key)){
            throw new DuplicateEntityException("Key already exists");
        }
        cache.put(key, value);
    }

    @Override
    public void update(K key, V value) {
        if(cache.containsKey(key)){
            cache.put(key, value);
        }
    }

    @Override
    public void delete(K key) {
        if(cache.containsKey(key)){
            cache.remove(key);
        }
    }

    @Override
    public V getItem(K key) {
        if(cache.containsKey(key)){
            return cache.get(key);
        }
        return null;
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public void list() {
        for (Map.Entry<K, V> entry : cache.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }
}
