package com.example.spotifyfestival.NewFeatures.CacheImplementation;

public interface FileInterface<T> {
    void writeToFile();
    void readFromFile();
    void deleteFile();
    void listFile();
}
