package com.example.spotifyfestival.GenericsPackage;

import com.example.spotifyfestival.Lab_facultate.DuplicateEntityException;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CRUDRepoForTableView<T>{
    ObservableList<T> getAll();
    void add(T value) throws DuplicateEntityException;
    void update(T value);
    void delete(T value);
    T getItem(int id);
    int getSize();
    void list();
}
