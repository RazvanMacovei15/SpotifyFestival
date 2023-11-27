package com.example.spotifyfestival.GenericsPackage;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Optional;

public class GenericObservableListRepo<T extends Entity> implements CRUDRepoForTableView<T> {
    protected ObservableList<T> tableViewList;
    public GenericObservableListRepo(){
        this.tableViewList = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<T> getAll() {
        return tableViewList;
    }

    @Override
    public void add(T value) throws DuplicateEntityException {
        for(T item : tableViewList){
            if(item.getId().equals(value.getId()))
                throw new DuplicateEntityException("Duplicate ID!");
        }
        tableViewList.add(value);
    }

    @Override
    public void update(T value) {
        Optional<T> optionalItem = Optional.ofNullable(getItem(value.getId()));
        optionalItem.ifPresentOrElse(
                oldGenre -> {
                    tableViewList.remove(oldGenre);
                    tableViewList.add(value);
                },
                () -> {
                    throw new IllegalStateException("Item to update with id " + value.getId() + " doesn't exist in the database!");
                }
        );
    }

    @Override
    public void delete(T value) {

    }

    @Override
    public T getItem(int id) {
        for(T item : tableViewList){
            if(item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void list() {

    }
}
