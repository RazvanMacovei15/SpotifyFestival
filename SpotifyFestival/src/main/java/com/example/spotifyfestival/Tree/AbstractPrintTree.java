package com.example.spotifyfestival.Tree;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Concert;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Entity;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.UserLocation;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Venue;
import com.example.spotifyfestival.UnusedStuffForNow.ConcertsAndFestivals.ConcertJSONUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPrintTree {
    public abstract Circle drawUserLocationCircle(double userLocationRadius, Canvas canvas, UserLocation userLocation);
    public abstract Circle drawVenueCircle(int i, int numberOfVenueCircles, double venueLocationRadius, Entity entity);
    public abstract Circle drawConcertCircle(double concertLocationRadius);
    public abstract Circle drawFestivalCircle();
    public abstract Circle drawStageCircle();

    protected List<Object> allCircles = new ArrayList<>();
    
    public void createTree(String str, BorderPane canvasBorderPane, Canvas canvas, double userLocationRadius, double venueCircleRadius, double concertLocationRadius) {

        ConcertJSONUtils utils = new ConcertJSONUtils();
        ObservableList<Concert> concerts = utils.extractConcerts(str);

//        for (Concert concert : concerts)
//        {
//            System.out.println(concert.getDescription());
//            System.out.println(concert.getVenue());
//            System.out.println(concert.getListOfArtists());
//            System.out.println(concert.getTime());
//            System.out.println(concert.getStartOfTheConcert());
//            System.out.println();
//        }

        UserLocation userLocation = new UserLocation(0);

        Tree<Entity> concertTree = new Tree<>(userLocation);
        TreeNode<Entity> root = concertTree.getRoot();

        Circle userLocationCircle = drawUserLocationCircle(userLocationRadius, canvas, userLocation);
        allCircles.add(userLocationCircle);
        ObservableList<Venue> venues = utils.createListOfALlVenues(concerts);

        ObservableList<Entity> entityVenues = FXCollections.observableArrayList();
        entityVenues.addAll(venues);

        for (int i = 0; i < entityVenues.size(); i++) {

            Entity entity = entityVenues.get(i);

            TreeNode<Entity> rootChild = new TreeNode<>(entity);
            root.addChild(rootChild);

            int numberOfVenueCircles = entityVenues.size();

            Circle venueCircle = drawVenueCircle(i, numberOfVenueCircles, venueCircleRadius, entity);

            allCircles.add(venueCircle);

            ObservableList<Entity> concertsAtEntityVenue = utils.getConcertsAtVenue(entity, concerts);

            for (Entity concertEntity : concertsAtEntityVenue) {
                TreeNode<Entity> venueChild = new TreeNode<>(concertEntity);
                rootChild.addChild(venueChild);

                drawConcertCircle(concertLocationRadius);
            }
        }
        concertTree.printTree(concertTree);

        displayCirclesOneAtATime(allCircles, canvasBorderPane);
    }

    private void displayCirclesOneAtATime(List<Object> circles, BorderPane canvasBorderPane){

        int currentIndex = 0;
        // Set up the Timeline to update the list view with a delay
        Timeline timeline = new Timeline();
        for (Object venueLocationCircle : circles) {
            KeyFrame keyFrame = null;
            if(venueLocationCircle instanceof Circle circle){
                keyFrame = new KeyFrame(
                        Duration.seconds(currentIndex),
                        event -> {
                            canvasBorderPane.getChildren().add(circle);                    }
                );
            }

            timeline.getKeyFrames().add(keyFrame);
            currentIndex++;
        }

        // Play the timeline
        timeline.play();
    }
}
