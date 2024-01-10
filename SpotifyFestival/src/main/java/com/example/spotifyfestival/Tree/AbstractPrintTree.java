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
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPrintTree {
    public abstract Circle drawUserLocationCircle(double userLocationRadius, Canvas canvas, UserLocation userLocation);

    public abstract Circle drawVenueCircle(int i, int numberOfVenueCircles, double venueLocationRadius, Entity entity);

    public abstract Circle drawConcertCircle(int i, int numberOfConcertCircles, double concertLocationRadius, Entity entity, double centerX, double centerY);

    public abstract Circle drawFestivalCircle();

    public abstract Circle drawStageCircle();

    protected List<Circle> allCircles = new ArrayList<>();

    public void createTree(String str, BorderPane canvasBorderPane, Canvas canvas, double userLocationRadius, double venueCircleRadius, double concertLocationRadius, GraphicsContext gc) {

        ConcertJSONUtils utils = new ConcertJSONUtils();
        ObservableList<Concert> concerts = utils.extractConcerts(str);

        UserLocation userLocation = new UserLocation(0);

        Tree<Entity> concertTree = new Tree<>(userLocation);
        TreeNode<Entity> root = concertTree.getRoot();

        Circle userLocationCircle = drawUserLocationCircle(userLocationRadius, canvas, userLocation);
        allCircles.add(userLocationCircle);
        ObservableList<Venue> venues = utils.createListOfALlVenues(concerts);

        ObservableList<Entity> entityVenues = FXCollections.observableArrayList();
        entityVenues.addAll(venues);

        for (int i = 0; i < entityVenues.size(); i++) {

            Entity venueEntity = entityVenues.get(i);

            TreeNode<Entity> rootChild = new TreeNode<>(venueEntity);
            root.addChild(rootChild);

            int numberOfVenueCircles = entityVenues.size();

            Circle venueCircle = drawVenueCircle(i, numberOfVenueCircles, venueCircleRadius, venueEntity);

            allCircles.add(venueCircle);


            ObservableList<Entity> concertsAtEntityVenue = utils.getConcertsAtVenue(venueEntity, concerts);

            for (int j = 0; j < concertsAtEntityVenue.size(); j++) {
                Entity concertEntity = concertsAtEntityVenue.get(j);

                TreeNode<Entity> venueChild = new TreeNode<>(concertEntity);
                rootChild.addChild(venueChild);

                int numberOfConcertCircles = concertsAtEntityVenue.size();
                double concertCircleX = venueCircle.getCenterX();
                double concertCircleY = venueCircle.getCenterY();

                Circle concertCircle = drawConcertCircle(j, numberOfConcertCircles, concertLocationRadius, concertEntity, concertCircleX, concertCircleY);

                allCircles.add(concertCircle);
            }
        }
        concertTree.printTree(concertTree);
        displayCirclesOneAtATime(allCircles, canvasBorderPane, gc, null, null);
    }

    private void displayCirclesOneAtATime(List<Circle> circles, BorderPane canvasBorderPane, GraphicsContext gc, Circle currentUserCircle, Circle currentVenueCircle) {

        int currentIndex = 0;

        // Set up the Timeline to update the list view with a delay
        Timeline timeline = new Timeline();
        for (int i = 0; i < circles.size() - 1; i++) {

            Circle firstCircleObject = circles.get(i);

            Circle nextCircleObject = circles.get(i + 1);

            KeyFrame keyFrame = null;
            KeyFrame lineKeyFrame = null;

            keyFrame = new KeyFrame(
                    Duration.seconds(currentIndex),
                    event -> {
                        canvasBorderPane.getChildren().add(firstCircleObject);
                    }
            );

            if (firstCircleObject.getUserData() instanceof UserLocation) {
                currentUserCircle = getCircleDetails(firstCircleObject);

                Circle circleForLambda = getCircleDetails(currentUserCircle);

                lineKeyFrame = new KeyFrame(
                        Duration.seconds(currentIndex),
                        event -> {
                            drawEdgeBetweenTwoPoints(circleForLambda.getCenterX(), circleForLambda.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                        }
                );
                timeline.getKeyFrames().add(lineKeyFrame);
                currentIndex++;
            } else if (firstCircleObject.getUserData() instanceof Venue) {
                currentVenueCircle = getCircleDetails(firstCircleObject);

                Circle circleForLambda = getCircleDetails(currentVenueCircle);

                if(nextCircleObject.getUserData() instanceof Concert){
                    lineKeyFrame = new KeyFrame(
                            Duration.seconds(currentIndex),
                            event -> {
                                drawEdgeBetweenTwoPoints(circleForLambda.getCenterX(), circleForLambda.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                            }
                    );
                    timeline.getKeyFrames().add(lineKeyFrame);
                    currentIndex++;
                }
            } else if (firstCircleObject.getUserData() instanceof Concert) {
                if(currentUserCircle != null && currentVenueCircle != null){
                    if(nextCircleObject.getUserData() instanceof Venue){
                        currentVenueCircle = getCircleDetails(nextCircleObject);

                    }else{
                        lineKeyFrame = new KeyFrame(
                                Duration.seconds(currentIndex),
                                event -> {
                                    drawEdgeBetweenTwoPoints(firstCircleObject.getCenterX(), firstCircleObject.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                                }
                        );
                        timeline.getKeyFrames().add(lineKeyFrame);
                        currentIndex++;
                    }
                }
            }


            timeline.getKeyFrames().add(keyFrame);
            currentIndex++;



        }
        // Add a separate key frame for the last circle
        if (!circles.isEmpty()) {
            Circle lastCircleObject = circles.get(circles.size() - 1);

            KeyFrame lastKeyFrame = new KeyFrame(
                    Duration.seconds(currentIndex),
                    event -> canvasBorderPane.getChildren().add(lastCircleObject)
            );
            timeline.getKeyFrames().add(lastKeyFrame);

        }
        for (KeyFrame key : timeline.getKeyFrames()) {
            System.out.println(key);
        }

        // Play the timeline
        timeline.play();
    }

    public void drawEdgeBetweenTwoPoints(double Ax, double Ay, double Bx, double By, GraphicsContext gc) {
        gc.setStroke(Color.GREY);
        gc.setLineWidth(1);

        gc.strokeLine(Ax, Ay, Bx, By);
    }

    public Circle getCircleDetails(Circle circleTwo){
        double x = circleTwo.getCenterX();
        double y  = circleTwo.getCenterY();
        double radius = circleTwo.getRadius();
        Object userData = circleTwo.getUserData();
        Circle circleToReturn = new Circle();
        circleToReturn.setCenterX(x);
        circleToReturn.setCenterY(y);
        circleToReturn.setRadius(radius);
        circleToReturn.setUserData(userData);
        return circleToReturn;
    }
}
