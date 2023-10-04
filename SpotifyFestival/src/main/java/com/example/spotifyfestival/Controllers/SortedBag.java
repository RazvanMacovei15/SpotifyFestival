package com.example.spotifyfestival.Controllers;

import java.util.*;

public class SortedBag<T> implements Iterable<T> {
    private Map<T, Integer> elements;
    private TreeMap<T, Integer> sortedElements;

    public SortedBag() {
        elements = new HashMap<>();
        sortedElements = new TreeMap<>(new ValueComparator(elements));
    }

    public int getSize() {
        return sortedElements.size();
    }

    public void add(T item) {
        elements.put(item, elements.getOrDefault(item, 0) + 1);
        sortedElements.put(item, elements.get(item));
    }

    public void removeOne(T item) {
        if (elements.containsKey(item)) {
            int count = elements.get(item);
            if (count > 1) {
                elements.put(item, count - 1);
                sortedElements.put(item, elements.get(item));
            } else {
                elements.remove(item);
                sortedElements.remove(item);
            }
        }
    }

    public void removeAll(T item) {
        if (elements.containsKey(item)) {
            elements.remove(item);
            sortedElements.remove(item);
        }
    }

    public int countOccurrences(T item) {
        return elements.getOrDefault(item, 0);
    }

    @Override
    public Iterator<T> iterator() {
        return new SortedBagIterator();
    }

    @Override
    public String toString() {
        return sortedElements.toString();
    }

    // Custom comparator to sort by values in descending order
    private class ValueComparator implements Comparator<T> {
        private Map<T, Integer> map;

        public ValueComparator(Map<T, Integer> map) {
            this.map = map;
        }

        @Override
        public int compare(T a, T b) {
            int cmp = map.get(b).compareTo(map.get(a));
            if (cmp != 0) {
                return cmp; // If counts are different, sort by count
            }
            // If counts are the same, sort alphabetically by item (key)
            return a.toString().compareTo(b.toString());
        }
    }

    // Inner class for the iterator
    private class SortedBagIterator implements Iterator<T> {
        private Iterator<Map.Entry<T, Integer>> iterator = sortedElements.entrySet().iterator();

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            return iterator.next().getKey();
        }
    }
}
