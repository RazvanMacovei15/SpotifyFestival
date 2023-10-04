package com.example.spotifyfestival.Controllers;

import java.util.*;

public class SortedBag<T> {
    private Map<T, Integer> elements;

    public SortedBag() {
        elements = new HashMap<>();
    }

    public void add(T item) {
        elements.put(item, elements.getOrDefault(item, 0) + 1);
    }

    public void removeOne(T item) {
        if (elements.containsKey(item)) {
            int count = elements.get(item);
            if (count > 1) {
                elements.put(item, count - 1);
            } else {
                elements.remove(item);
            }
        }
    }

    public void removeAll(T item) {
        elements.remove(item);
    }

    public int countOccurrences(T item) {
        return elements.getOrDefault(item, 0);
    }

    @Override
    public String toString() {
        return elements.toString();
    }

}
