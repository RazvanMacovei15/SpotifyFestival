package com.example.spotifyfestival.GenericsPackage;

import java.util.*;

public class MapValueSorter {
    public static <K, V extends Comparable<V>> Map<K, V> sortByValuesDescendingWithAlphabetical(Map<K, V> map) {
        List<Map.Entry<K, V>> entryList = new ArrayList<>(map.entrySet());

        Collections.sort(entryList, (entry1, entry2) -> {
            int valueComparison = entry2.getValue().compareTo(entry1.getValue());

            if (valueComparison != 0) {
                // If values are different, sort by values in descending order.
                return valueComparison;
            } else {
                // If values are the same, sort by keys alphabetically.
                return entry1.getKey().toString().compareTo(entry2.getKey().toString());
            }
        });

        Map<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
