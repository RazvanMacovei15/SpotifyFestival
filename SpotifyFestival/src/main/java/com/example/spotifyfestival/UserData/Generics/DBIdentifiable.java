package com.example.spotifyfestival.UserData.Generics;

public interface DBIdentifiable<T> {
    T getId();

    void setId(T id);
}
