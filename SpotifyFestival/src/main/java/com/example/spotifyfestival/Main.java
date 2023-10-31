package com.example.spotifyfestival;

import com.example.spotifyfestival.ConcertsAndFestivals.*;
import com.example.spotifyfestival.Tree.Tree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Entity userLocation = new Entity();
        ConcertJSONUtils utils = new ConcertJSONUtils(userLocation);
        ObservableList<Concert> concerts = utils.extractConcerts(JSONConstant.getConstant());
        List<Venue> listOfAllVenues = utils.createListOfALlVenues(concerts);
        ObservableList<Entity> entityConcerts = FXCollections.observableArrayList();
        ObservableList<Entity> venueConcerts = FXCollections.observableArrayList();
        for(Venue venue : listOfAllVenues)
        {
            venueConcerts.add(venue);
        }
        for(Concert concert : concerts)
        {
            entityConcerts.add(concert);
        }
        Tree<Entity> tree =  utils.createTree(venueConcerts, userLocation);
        utils.printTree(tree);
    }
}