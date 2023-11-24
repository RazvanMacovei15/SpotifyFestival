package com.example.spotifyfestival.GenericsPackage;

public class KeyValue<K, V> implements Comparable<KeyValue<K, V>> {
    private K key;
    private V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public int compareTo(KeyValue<K, V> other) {
        // Compare KeyValue objects based on their values.
        if (value instanceof Comparable) {
            return ((Comparable<V>) value).compareTo(other.value);
        }
        throw new UnsupportedOperationException("Values are not comparable.");
    }
}