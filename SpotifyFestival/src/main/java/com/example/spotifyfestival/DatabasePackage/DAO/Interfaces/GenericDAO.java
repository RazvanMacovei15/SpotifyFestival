package com.example.spotifyfestival.DatabasePackage.DAO.Interfaces;

import java.sql.Connection;
import java.util.Optional;

public interface GenericDAO<V> {
    void insertObjectInDB(V item);
    Optional<V> getItemByID(int id);
    void update(V item);
    int deleteByID(Integer id);
    void readAllObjectsFromTable();
    Object readItemAttribute(String tableName, String fieldName, int fieldDataType,
                             String indexFieldName, int indexDataType, Object index);
}
