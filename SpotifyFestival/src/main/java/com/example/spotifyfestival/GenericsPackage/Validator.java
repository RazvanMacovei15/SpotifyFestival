package com.example.spotifyfestival.GenericsPackage;

public interface Validator<T> {
    boolean validate(T item);
}
