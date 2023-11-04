package com.example.spotifyfestival.UserData.Generics;

import com.example.spotifyfestival.ConcertsAndFestivals.Identifiable;
import com.example.spotifyfestival.UserData.DuplicateEntityException;

import java.util.Map;

public abstract class FileRepository<K, V extends Identifiable<K>> extends MemoryRepository<K, V> {
    protected String filename;

    public FileRepository(String filename) {
        readFromFle();
        this.filename = filename;
    }

    protected abstract void readFromFle();
    protected abstract void writeToFile();
    protected abstract void readLine();
    @Override
    public void add(K id, V value) throws DuplicateEntityException{
        super.add(id, value);
        writeToFile();
    }

    @Override
    public void delete(K id){
        super.delete(id);
        writeToFile();
    }

    @Override
    public Map<K, V> getAll() {
        return super.getAll();
    }

    @Override
    public void update(K key, V value) {
        super.update(key, value);
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
}