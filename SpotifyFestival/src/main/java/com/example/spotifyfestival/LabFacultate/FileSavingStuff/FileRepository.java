package com.example.spotifyfestival.LabFacultate.FileSavingStuff;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Identifiable;
import com.example.spotifyfestival.GenericsPackage.MemoryRepository;
import com.example.spotifyfestival.LabFacultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

public abstract class FileRepository<K, V extends Identifiable<K>> extends MemoryRepository<K, V> {
    protected String filename;

    public FileRepository(String filename) {
        this.filename = filename;
        this.tableViewList = FXCollections.observableArrayList();
        readFromFile();
    }

    ObservableList<V> tableViewList;

    public ObservableList<V> getTableList() {
        return tableViewList;
    }

    protected abstract void readFromFile();
    protected abstract void writeToFile();
    protected abstract void clear();
    @Override
    public void add(K id, V value) throws DuplicateEntityException{
        super.add(id, value);
        tableViewList.add(value);
        writeToFile();
    }

    @Override
    public void delete(K id){
        V value = super.getItem(id);
        super.delete(id);
        tableViewList.remove(value);
        writeToFile();
    }

    @Override
    public Iterable<V> getAll() {
        return super.getAll();
    }

    @Override
    public void update(K key, V value) {
        Optional<V> optional = Optional.ofNullable(getItem(value.getId()));
        optional.ifPresentOrElse(
                oldItem -> {
                    tableViewList.remove(oldItem);
                    tableViewList.add(value);
                },
                () -> {
                    throw new IllegalArgumentException("Item to update with id " + value.getId() + " doesn't exist in the database!");
                }
        );
        super.update(key, value);
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
}