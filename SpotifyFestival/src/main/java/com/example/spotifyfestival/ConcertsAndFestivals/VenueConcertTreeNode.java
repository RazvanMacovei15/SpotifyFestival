package com.example.spotifyfestival.ConcertsAndFestivals;

public class VenueConcertTreeNode implements Identifiable {
    protected int id;

    public VenueConcertTreeNode(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

}
