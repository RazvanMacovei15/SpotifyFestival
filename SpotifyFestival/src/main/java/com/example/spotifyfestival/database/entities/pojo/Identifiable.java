package com.example.spotifyfestival.database.entities.pojo;

public interface Identifiable<K> {
    K getId();
    void setId(K id);

}
