package com.example.spotifyfestival.GenericsPackage;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.DuplicateEntityException;

public interface CRUDInterface<T extends Entity> {
    void add(T item) throws DuplicateEntityException;
    void update(T newItem);
    void delete(int id);
    T getItem(int id);
    void list();
}
