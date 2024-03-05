package com.example.spotifyfestival.generics;

import com.example.spotifyfestival.database.entities.pojo.Entity;
import com.example.spotifyfestival.database.entities.pojo.DuplicateEntityException;

public interface CRUDInterface<T extends Entity> {
    void add(T item) throws DuplicateEntityException;
    void update(T newItem);
    void delete(int id);
    T getItem(int id);
    void list();
}
