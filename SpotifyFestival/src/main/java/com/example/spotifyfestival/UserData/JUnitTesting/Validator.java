package com.example.spotifyfestival.UserData.JUnitTesting;

public interface Validator<T> {
    boolean validate(T entity);
}