package com.example.spotifyfestival.NewFeatures.CacheImplementation;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;
import javafx.collections.ObservableList;

import java.io.File;

public abstract class CacheFileRepo<K,V> extends GenericMemoryCacheRepository<K, V>{
    protected String filename;
    private ObservableList<V> obsList;
    public CacheFileRepo(String filename) {
        this.filename = filename;
        readFromFile();
    }
    public abstract void writeToFile();
    public abstract void readFromFile();
    public abstract void listFile();

    public void resetFile() {
        File file = new File(filename);
        if(file.exists()){
            file.delete();
        }
    }

    @Override
    public void add(K key, V value) {
        try {
            super.add(key, value);
        } catch (DuplicateEntityException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(K key, V value) {
        super.update(key, value);
        writeToFile();
    }

    @Override
    public void delete(K key) {
        super.delete(key);
        writeToFile();
    }

    @Override
    public V getItem(K key) {
        return super.getItem(key);
    }

    @Override
    public int getSize() {
        return super.getSize();
    }

    @Override
    public void list() {
        super.list();
    }

    public ObservableList<V> getObsList() {
        return obsList;
    }

    public void setObsList(ObservableList<V> obsList) {
        this.obsList = obsList;
    }
}
