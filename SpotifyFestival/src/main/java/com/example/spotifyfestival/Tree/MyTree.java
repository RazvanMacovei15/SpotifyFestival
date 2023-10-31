package com.example.spotifyfestival.Tree;

import com.example.spotifyfestival.ConcertsAndFestivals.Identifiable;
import javafx.collections.ObservableList;

public class MyTree<T extends Identifiable> extends Tree {
    public MyTree(T data) {
        super(data);
    }
    @Override
    public ObservableList getConcertsAtVenue(Identifiable venue) {
        return null;
    }

    @Override
    public void drawLocationPin(Identifiable item) {

    }

    @Override
    public void drawVenuePin(Identifiable item) {

    }

    @Override
    public void drawConcertPin(Identifiable item) {

    }
}
