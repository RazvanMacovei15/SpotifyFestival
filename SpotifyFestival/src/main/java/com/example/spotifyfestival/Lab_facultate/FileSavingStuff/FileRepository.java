package com.example.spotifyfestival.Lab_facultate.FileSavingStuff;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Identifiable;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import com.example.spotifyfestival.GenericsPackage.MemoryRepository;

public abstract class FileRepository<K, V extends Identifiable<K>> extends MemoryRepository<K, V> {
    protected String filename;

    public FileRepository(String filename) {
        readFromFle();
        this.filename = filename;
    }

    protected abstract void readFromFle();
    protected abstract void writeToFile();
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
    public Iterable<V> getAll() {
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