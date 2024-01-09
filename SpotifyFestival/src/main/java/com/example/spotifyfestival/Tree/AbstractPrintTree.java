package com.example.spotifyfestival.Tree;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Concert;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.UserLocation;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.UnusedStuffForNow.ConcertsAndFestivals.ConcertJSONUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class AbstractPrintTree {
    public abstract Circle drawUserLocationCircle(double userLocationRadius, Canvas canvas);

    public void createTree(String str, Canvas canvas, double userLocationRadius) {

        ConcertJSONUtils utils = new ConcertJSONUtils();
        ObservableList<Concert> concerts = utils.extractConcerts(str);


        for (Concert concert : concerts)
        {
            System.out.println(concert.getDescription());
            System.out.println(concert.getVenue());
            System.out.println(concert.getListOfArtists());
            System.out.println(concert.getTime());
            System.out.println(concert.getStartOfTheConcert());
            System.out.println();
        }

        UserLocation userLocation = new UserLocation(0);
//        System.out.println(userLocation.getLatitude());

        Tree<Entity> concertTree = new Tree<>(userLocation);
        TreeNode<Entity> root = concertTree.getRoot();

        Circle userLocationCircle = drawUserLocationCircle(userLocationRadius, canvas);
        userLocationCircle.setUserData(userLocation);
        userLocationCircle.setOnMouseClicked(event -> {
                    UserLocation user = (UserLocation) userLocationCircle.getUserData();
                    System.out.println(user.getLatitude());
                    System.out.println(user.getLongitude());
                });
        ObservableList<Venue> venues = utils.createListOfALlVenues(concerts);

        ObservableList<Entity> entityVenues = FXCollections.observableArrayList();
        entityVenues.addAll(venues);

        for (Entity entity : entityVenues) {

            TreeNode<Entity> rootChild = new TreeNode<>(entity);
            root.addChild(rootChild);

            ObservableList<Entity> concertsAtEntityVenue = utils.getConcertsAtVenue(entity, concerts);

            for (Entity concertEntity : concertsAtEntityVenue) {
                TreeNode<Entity> venueChild = new TreeNode<>(concertEntity);
                rootChild.addChild(venueChild);
            }
        }
        concertTree.printTree(concertTree);
    }

}
