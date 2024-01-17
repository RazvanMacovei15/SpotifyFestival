package com.example.spotifyfestival.Tree;

import com.example.spotifyfestival.API_Packages.APIServices.ConcertAPIService;
import com.example.spotifyfestival.DatabasePackage.DAO.ConcertDAO;
import com.example.spotifyfestival.DatabasePackage.DAO.FestivalDAO;
import com.example.spotifyfestival.DatabasePackage.DAO.FestivalStageDAO;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.*;
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
    public abstract Circle drawUserLocationCircle(Canvas canvas, UserLocation userLocation);

    public abstract Circle drawVenueCircle(int i, int numberOfVenueCircles, Entity entity, UserLocation userLocation);

    public abstract Circle drawConcertCircle(int i, int numberOfConcertCircles, Entity entity, double centerX, double centerY, int numberOfVenueCircles, int venueIndex);

    public abstract Circle drawFestivalCircle(int i, int numberOfVenueCircles, Entity entity);

    public abstract Circle drawStageCircle(int i, int numberOfConcertCircles, Entity entity, double centerX, double centerY, int numberOfVenueCircles, int venueIndex);

    protected List<Circle> allCircles = new ArrayList<>();

    protected Tree<Entity> concertTree = null;

    public void createTree(FestivalStageDAO festivalStageDAO, FestivalDAO festivalDAO, ConcertDAO concertDAO, String str, Canvas canvas, double userLocationRadius, double venueCircleRadius, double concertLocationRadius) {

        ConcertAPIService utils = new ConcertAPIService();
        ObservableList<Concert> concerts = utils.extractConcerts(str);

        UserLocation userLocation = new UserLocation(0);

        concertTree = new Tree<>(userLocation);
        TreeNode<Entity> root = concertTree.getRoot();

        Circle userLocationCircle = drawUserLocationCircle(canvas, userLocation);
        allCircles.add(userLocationCircle);
        ObservableList<Venue> venues = utils.createListOfALlVenues(concerts);

        ObservableList<Entity> entityLocations = FXCollections.observableArrayList();
        entityLocations.addAll(venues);

        Iterable<Festival> festivals = festivalDAO.getAll();
        for (Festival festival : festivals) {
            entityLocations.add(festival);
        }

        for (int i = 0; i < entityLocations.size(); i++) {

            Entity entity = entityLocations.get(i);
            Circle venueCircle = null;
            ObservableList<Entity> concertsOrStagesAtEntityVenue = FXCollections.observableArrayList();

            TreeNode<Entity> rootChild = new TreeNode<>(entity);
            root.addChild(rootChild);

            int numberOfVenueCircles = entityLocations.size();
            drawFestivalCircle(i, numberOfVenueCircles, entity);

            if (entity instanceof Venue) {
                venueCircle = drawVenueCircle(i, numberOfVenueCircles, entity, userLocation);
                allCircles.add(venueCircle);
                concertsOrStagesAtEntityVenue = ConcertAPIService.getConcertsAtVenue(entity, concerts);
                for (int j = 0; j < concertsOrStagesAtEntityVenue.size(); j++) {
                    Entity concertEntity = concertsOrStagesAtEntityVenue.get(j);

                    TreeNode<Entity> venueChild = new TreeNode<>(concertEntity);
                    rootChild.addChild(venueChild);

                    int numberOfConcertCircles = concertsOrStagesAtEntityVenue.size();
                    double concertCircleX = venueCircle.getCenterX();
                    double concertCircleY = venueCircle.getCenterY();

                    Circle concertCircle = drawConcertCircle(j, numberOfConcertCircles, concertEntity, concertCircleX, concertCircleY, numberOfVenueCircles, i);

                    allCircles.add(concertCircle);
                }
            } else if (entity instanceof Festival festival) {
                Venue venue = null;
                ObservableList<FestivalStage> stages = FXCollections.observableArrayList();

                venue = festival.getVenue();
                stages = festivalStageDAO.getAllStagesAtAVenueFestival(festivalStageDAO, venue);

                venueCircle = drawFestivalCircle(i, numberOfVenueCircles, entity);
                allCircles.add(venueCircle);

                concertsOrStagesAtEntityVenue.addAll(stages);

                for (int j = 0; j < concertsOrStagesAtEntityVenue.size(); j++) {
                    Entity stageEntity = concertsOrStagesAtEntityVenue.get(j);

                    TreeNode<Entity> venueChild = new TreeNode<>(stageEntity);
                    rootChild.addChild(venueChild);

                    ObservableList<Concert> concertObservableList = FXCollections.observableArrayList();

                    if (stageEntity instanceof FestivalStage stage) {
                        concertObservableList = concertDAO.getAllConcertsForAStage(stage);
                    }


                    int numberOfStagesCircles = concertsOrStagesAtEntityVenue.size();
                    double stageCircleX = venueCircle.getCenterX();
                    double stageCircleY = venueCircle.getCenterY();

                    Circle stageCircle = drawStageCircle(j, numberOfStagesCircles, stageEntity, stageCircleX, stageCircleY, numberOfVenueCircles, i);

                    allCircles.add(stageCircle);
                    for (int k = 0; k < concertObservableList.size(); k++) {
                        Entity concertEntity = concertObservableList.get(k);

                        TreeNode<Entity> stageChild = new TreeNode<>(concertEntity);
                        venueChild.addChild(stageChild);

                        int numberOfConcertCircles = concertObservableList.size();
                        double concertCircleX = stageCircle.getCenterX();
                        double concertCircleY = stageCircle.getCenterY();

                        Circle concertCircle = drawConcertCircle(k, numberOfConcertCircles, concertEntity, concertCircleX, concertCircleY, numberOfStagesCircles, j);
                        allCircles.add(concertCircle);
                    }
                }
            }
        }
    }

    public List<Circle> getAllCircles() {
        return allCircles;
    }

    public void displayCirclesOneAtATime(BorderPane canvasBorderPane, GraphicsContext gc, Circle userCircle, Circle currentFestivalOrVenueCircle, Circle currentStage) {
        List<Circle> circles = getAllCircles();

        int currentIndex = 0;

        // Set up the Timeline to update the list view with a delay
        Timeline timeline = new Timeline();

        for (int i = 0; i < circles.size() - 1; i++) {

            Circle firstCircleObject = circles.get(i);
            Circle nextCircleObject = circles.get(i + 1);

            KeyFrame keyFrame = null;

            keyFrame = drawCircleKeyFrame(canvasBorderPane, currentIndex, firstCircleObject);

            currentIndex++;

            if (firstCircleObject.getUserData() instanceof UserLocation) {
                userCircle = getCircleDetails(firstCircleObject);
                handleUserLocation(userCircle, gc, currentIndex, nextCircleObject, timeline);
                currentIndex++;
            } else if (firstCircleObject.getUserData() instanceof Venue) {
                currentFestivalOrVenueCircle = getCircleDetails(firstCircleObject);
                handleVenueOrFestival(currentFestivalOrVenueCircle, gc, currentIndex, nextCircleObject, timeline);
                currentIndex++;
            } else if (firstCircleObject.getUserData() instanceof Festival) {
                currentFestivalOrVenueCircle = getCircleDetails(firstCircleObject);
                handleFestival(currentFestivalOrVenueCircle, gc, currentIndex, firstCircleObject, nextCircleObject, timeline);
                currentIndex++;
            } else if (firstCircleObject.getUserData() instanceof FestivalStage) {
                if (userCircle != null && currentFestivalOrVenueCircle != null) {
                    currentStage = getCircleDetails(firstCircleObject);
                    handleFestivalStage(gc, currentIndex, firstCircleObject, nextCircleObject, timeline);
                    currentIndex++;
                }
            } else if (firstCircleObject.getUserData() instanceof Concert) {
                if (userCircle != null && currentFestivalOrVenueCircle != null) {
                    handleConcert(gc, currentIndex, userCircle, currentFestivalOrVenueCircle, currentStage, nextCircleObject, timeline);
                }
            }
            timeline.getKeyFrames().add(keyFrame);
            currentIndex++;
        }
        // Add a separate key frame for the last circle
        if (!circles.isEmpty()) {
            Circle lastCircleObject = circles.get(circles.size() - 1);
            KeyFrame lastKeyFrame = drawLastCircleKeyFrame(canvasBorderPane, currentIndex, lastCircleObject);
            timeline.getKeyFrames().add(lastKeyFrame);
        }
        // Play the timeline
        timeline.play();
    }

    private void handleUserLocation(Circle userCircle, GraphicsContext gc, int currentIndex, Circle nextCircle, Timeline timeline) {
        Circle circleForLambda = getCircleDetails(userCircle);
        KeyFrame lineKeyFrame = getKeyFrameForEdge(gc, currentIndex, circleForLambda, nextCircle);
        timeline.getKeyFrames().add(lineKeyFrame);
    }

    private void handleVenueOrFestival(Circle currentFestivalOrVenueCircle, GraphicsContext gc, int currentIndex, Circle nextCircle, Timeline timeline) {
        Circle circleForLambda = getCircleDetails(currentFestivalOrVenueCircle);
        if (nextCircle.getUserData() instanceof Concert) {
            KeyFrame lineKeyFrame = getKeyFrameForEdge(gc, currentIndex, circleForLambda, nextCircle);
            timeline.getKeyFrames().add(lineKeyFrame);
        }
    }

    private void handleFestival(Circle currentFestivalOrVenueCircle, GraphicsContext gc, int currentIndex, Circle currentCircle, Circle nextCircle, Timeline timeline) {
        Circle circleForLambda = getCircleDetails(currentFestivalOrVenueCircle);
        if (nextCircle.getUserData() instanceof FestivalStage) {
            KeyFrame lineKeyFrame = getKeyFrameForEdge(gc, currentIndex, circleForLambda, nextCircle);
            timeline.getKeyFrames().add(lineKeyFrame);
        }

    }

    private void handleFestivalStage(GraphicsContext gc, int currentIndex, Circle firstCircle, Circle nextCircle, Timeline timeline) {
        Circle circleForLambda = getCircleDetails(firstCircle);
        if (nextCircle.getUserData() instanceof Concert) {
            KeyFrame lineKeyFrame = getKeyFrameForEdge(gc, currentIndex, circleForLambda, nextCircle);
            timeline.getKeyFrames().add(lineKeyFrame);
        }
    }

    private void handleConcert(GraphicsContext gc, int currentIndex, Circle currentUserCircle, Circle currentFestivalOrVenueCircle, Circle currentStage, Circle nextCircle, Timeline timeline) {
        if (nextCircle.getUserData() instanceof Venue) {
            currentFestivalOrVenueCircle = getCircleDetails(nextCircle);
            Circle user = getCircleDetails(currentUserCircle);
            Circle venue = getCircleDetails(currentFestivalOrVenueCircle);
            KeyFrame lineKeyFrame = getKeyFrameForEdge(gc, currentIndex, user, venue);
            timeline.getKeyFrames().add(lineKeyFrame);
        } else if (nextCircle.getUserData() instanceof Festival) {
            currentFestivalOrVenueCircle = getCircleDetails(nextCircle);
            Circle user = getCircleDetails(currentUserCircle);
            KeyFrame lineKeyFrame = getKeyFrameForEdge(gc, currentIndex, user, nextCircle);
            timeline.getKeyFrames().add(lineKeyFrame);
        } else if (nextCircle.getUserData() instanceof FestivalStage) {
            currentStage = getCircleDetails(nextCircle);
            Circle festivalStage = getCircleDetails(nextCircle);
            Circle festival = getCircleDetails(currentFestivalOrVenueCircle);
            KeyFrame lineKeyFrame = getKeyFrameForEdge(gc, currentIndex, festivalStage, festival);
            timeline.getKeyFrames().add(lineKeyFrame);
        } else if (nextCircle.getUserData() instanceof Concert) {
            if (currentFestivalOrVenueCircle.getUserData() instanceof Venue) {
                Circle festival = getCircleDetails(currentFestivalOrVenueCircle);
                KeyFrame lineKeyFrame = getKeyFrameForEdge(gc, currentIndex, festival, nextCircle);
                timeline.getKeyFrames().add(lineKeyFrame);
            } else {
                Circle festival = getCircleDetails(currentStage);
                KeyFrame lineKeyFrame = getKeyFrameForEdge(gc, currentIndex, festival, nextCircle);
                timeline.getKeyFrames().add(lineKeyFrame);
            }
        }
    }

    private KeyFrame drawLastCircleKeyFrame(BorderPane canvasBorderPane, int currentIndex, Circle lastCircleObject) {
        KeyFrame lastKeyFrame = new KeyFrame(
                Duration.millis(currentIndex * 150),
                event -> canvasBorderPane.getChildren().add(lastCircleObject)
        );
        return lastKeyFrame;
    }

    private KeyFrame drawCircleKeyFrame(BorderPane canvasBorderPane, int currentIndex, Circle firstCircleObject) {
        KeyFrame keyFrame;
        keyFrame = new KeyFrame(
                Duration.millis(currentIndex * 150),
                event -> {
                    canvasBorderPane.getChildren().add(firstCircleObject);
                }
        );
        return keyFrame;
    }

    private KeyFrame getKeyFrameForEdge(GraphicsContext gc, int currentIndex, Circle festival, Circle nextCircleObject) {
        KeyFrame lineKeyFrame;
        lineKeyFrame = new KeyFrame(
                Duration.millis(currentIndex * 150),
                event -> {
                    drawEdgeBetweenTwoPoints(festival.getCenterX(), festival.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                }
        );
        return lineKeyFrame;
    }

    private void drawEdgeBetweenTwoPoints(double Ax, double Ay, double Bx, double By, GraphicsContext gc) {
        gc.setStroke(Color.GREY);
        gc.setLineWidth(1);

        gc.strokeLine(Ax, Ay, Bx, By);
    }

    public static Circle getCircleDetails(Circle circleTwo) {
        double x = circleTwo.getCenterX();
        double y = circleTwo.getCenterY();
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
