package com.example.spotifyfestival.NewFeatures.CacheImplementation.TopGenres;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.NewFeatures.CacheImplementation.GenericMemoryCacheRepository;
import javafx.collections.ObservableList;

public class TopGenres extends GenericMemoryCacheRepository<String, Genre> {
    public TopGenres() {

    }
}
