package com.example.spotifyfestival.generics;

import java.util.Optional;

public interface GenericDAO<V> {
    void insertObjectInDB(V item);
    Optional<V> getItemByID(int id);
    void updateObjectInDB(V item);
    int deleteObjectByIDInDB(Integer id);
    void readAllObjectsFromTable();
    Object readItemAttributeFromDB(String fieldName, int fieldDataType, Object index);
}
