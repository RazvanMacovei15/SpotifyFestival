package com.example.spotifyfestival.Tree;

import com.example.spotifyfestival.DatabasePackage.DAO.ConcertDAO;
import com.example.spotifyfestival.DatabasePackage.DAO.FestivalDAO;
import com.example.spotifyfestival.DatabasePackage.DAO.FestivalStageDAO;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.*;
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

    public abstract Circle drawVenueCircle(int i, int numberOfVenueCircles, double venueLocationRadius, Entity entity, UserLocation userLocation);

    public abstract Circle drawConcertCircle(int i, int numberOfConcertCircles, double concertLocationRadius, Entity entity, double centerX, double centerY, int numberOfVenueCircles, int venueIndex);

    public abstract Circle drawFestivalCircle(int i, int numberOfVenueCircles, double venueCircleRadius, Entity entity);

    public abstract Circle drawStageCircle(int i, int numberOfConcertCircles, double concertLocationRadius, Entity entity, double centerX, double centerY, int numberOfVenueCircles, int venueIndex);

    protected List<Circle> allCircles = new ArrayList<>();

    public Tree<Entity> createTree(FestivalStageDAO festivalStageDAO, FestivalDAO festivalDAO, ConcertDAO concertDAO, String str, BorderPane canvasBorderPane, Canvas canvas, double userLocationRadius, double venueCircleRadius, double concertLocationRadius, GraphicsContext gc) {

        ConcertJSONUtils utils = new ConcertJSONUtils();
        ObservableList<Concert> concerts = utils.extractConcerts(str);

        UserLocation userLocation = new UserLocation(0);

        Tree<Entity> concertTree = new Tree<>(userLocation);
        TreeNode<Entity> root = concertTree.getRoot();

        Circle userLocationCircle = drawUserLocationCircle(userLocationRadius, canvas, userLocation);
        allCircles.add(userLocationCircle);
        ObservableList<Venue> venues = utils.createListOfALlVenues(concerts);

        ObservableList<Entity> entityLocations = FXCollections.observableArrayList();
        entityLocations.addAll(venues);

        Iterable<Festival> festivals = festivalDAO.getAll();
        for (Festival festival : festivals) {
            System.out.println(festival.getName());
            entityLocations.add(festival);
        }

        for (int i = 0; i < entityLocations.size(); i++) {

            Entity entity = entityLocations.get(i);
            Circle venueCircle = null;
            ObservableList<Entity> concertsOrStagesAtEntityVenue = FXCollections.observableArrayList();

            TreeNode<Entity> rootChild = new TreeNode<>(entity);
            root.addChild(rootChild);

            int numberOfVenueCircles = entityLocations.size();
            drawFestivalCircle(i, numberOfVenueCircles, venueCircleRadius, entity);

            if (entity instanceof Venue) {
                venueCircle = drawVenueCircle(i, numberOfVenueCircles, venueCircleRadius, entity, userLocation);
                allCircles.add(venueCircle);
                concertsOrStagesAtEntityVenue = ConcertJSONUtils.getConcertsAtVenue(entity, concerts);
                for (int j = 0; j < concertsOrStagesAtEntityVenue.size(); j++) {
                    Entity concertEntity = concertsOrStagesAtEntityVenue.get(j);

                    TreeNode<Entity> venueChild = new TreeNode<>(concertEntity);
                    rootChild.addChild(venueChild);
                    rootChild.toString();

                    int numberOfConcertCircles = concertsOrStagesAtEntityVenue.size();
                    double concertCircleX = venueCircle.getCenterX();
                    double concertCircleY = venueCircle.getCenterY();

                    Circle concertCircle = drawConcertCircle(j, numberOfConcertCircles, concertLocationRadius, concertEntity, concertCircleX, concertCircleY, numberOfVenueCircles, i);

                    allCircles.add(concertCircle);
                }
            } else if (entity instanceof Festival festival) {
                Venue venue = null;
                ObservableList<FestivalStage> stages = FXCollections.observableArrayList();


                venue = festival.getVenue();
                stages = festivalStageDAO.getAllStagesAtAVenueFestival(festivalStageDAO, venue);

                venueCircle = drawFestivalCircle(i, numberOfVenueCircles, venueCircleRadius, entity);
                allCircles.add(venueCircle);

                concertsOrStagesAtEntityVenue.addAll(stages);

                System.out.println(stages);

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

                    Circle stageCircle = drawStageCircle(j, numberOfStagesCircles, concertLocationRadius, stageEntity, stageCircleX, stageCircleY, numberOfVenueCircles, i);

                    allCircles.add(stageCircle);
                    for (int k = 0; k < concertObservableList.size(); k++) {
                        Entity concertEntity = concertObservableList.get(k);

                        TreeNode<Entity> stageChild = new TreeNode<>(concertEntity);
                        venueChild.addChild(stageChild);

                        int numberOfConcertCircles = concertObservableList.size();
                        double concertCircleX = stageCircle.getCenterX();
                        double concertCircleY = stageCircle.getCenterY();

                        Circle concertCircle = drawConcertCircle(k, numberOfConcertCircles, concertLocationRadius, concertEntity, concertCircleX, concertCircleY, numberOfStagesCircles, j);
                        allCircles.add(concertCircle);
                    }
                }
            }
        }
        for (Circle circle : allCircles) {
            if (!(circle.getUserData() == null)) {
                System.out.println(circle.getUserData().getClass());
            } else {
                System.out.println("null");
            }
            System.out.println();
        }
        concertTree.printTree(concertTree);
        return concertTree;
    }

    public List<Circle> getAllCircles() {
        return allCircles;
    }

    public void displayCirclesOneAtATime(BorderPane canvasBorderPane, GraphicsContext gc, Circle currentUserCircle, Circle currentFestivalOrVenueCircle, Circle currentStage) {
        List<Circle> circles = getAllCircles();

        int currentIndex = 0;

        // Set up the Timeline to update the list view with a delay
        Timeline timeline = new Timeline();
        for (int i = 0; i < circles.size() - 1; i++) {

            Circle firstCircleObject = circles.get(i);

            Circle nextCircleObject = circles.get(i + 1);

            KeyFrame keyFrame = null;
            KeyFrame lineKeyFrame = null;

            keyFrame = new KeyFrame(
                    Duration.millis(currentIndex * 300),
                    event -> {
                        canvasBorderPane.getChildren().add(firstCircleObject);
                    }
            );

            if (firstCircleObject.getUserData() instanceof UserLocation) {
                currentUserCircle = getCircleDetails(firstCircleObject);

                Circle circleForLambda = getCircleDetails(currentUserCircle);

                lineKeyFrame = new KeyFrame(
                        Duration.millis(currentIndex * 300),
                        event -> {
                            drawEdgeBetweenTwoPoints(circleForLambda.getCenterX(), circleForLambda.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                        }
                );
                timeline.getKeyFrames().add(lineKeyFrame);
                currentIndex++;
            } else if (firstCircleObject.getUserData() instanceof Venue) {
                currentFestivalOrVenueCircle = getCircleDetails(firstCircleObject);

                Circle circleForLambda = getCircleDetails(currentFestivalOrVenueCircle);

                if (nextCircleObject.getUserData() instanceof Concert) {
                    lineKeyFrame = new KeyFrame(
                            Duration.millis(currentIndex * 300),
                            event -> {
                                drawEdgeBetweenTwoPoints(circleForLambda.getCenterX(), circleForLambda.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                            }
                    );
                    timeline.getKeyFrames().add(lineKeyFrame);
                    currentIndex++;
                }

            } else if (firstCircleObject.getUserData() instanceof Festival) {
                currentFestivalOrVenueCircle = getCircleDetails(firstCircleObject);

                Circle circleForLambda = getCircleDetails(currentFestivalOrVenueCircle);

                if (nextCircleObject.getUserData() instanceof FestivalStage) {
                    lineKeyFrame = new KeyFrame(
                            Duration.millis(currentIndex * 300),
                            event -> {
                                drawEdgeBetweenTwoPoints(circleForLambda.getCenterX(), circleForLambda.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                            }
                    );
                    timeline.getKeyFrames().add(lineKeyFrame);
                    currentIndex++;
                }

            } else if (firstCircleObject.getUserData() instanceof FestivalStage) {
                if (currentUserCircle != null && currentFestivalOrVenueCircle != null){
                    currentStage = getCircleDetails(firstCircleObject);
                    Circle circleForLambda = getCircleDetails(firstCircleObject);
                    if (nextCircleObject.getUserData() instanceof Concert) {
                        lineKeyFrame = new KeyFrame(
                                Duration.millis(currentIndex * 300),
                                event -> {
                                    drawEdgeBetweenTwoPoints(circleForLambda.getCenterX(), circleForLambda.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                                }
                        );
                        timeline.getKeyFrames().add(lineKeyFrame);
                        currentIndex++;
                    }
                }
            } else if (firstCircleObject.getUserData() instanceof Concert) {
                if (currentUserCircle != null && currentFestivalOrVenueCircle != null) {
                    if (nextCircleObject.getUserData() instanceof Venue) {
                        currentFestivalOrVenueCircle = getCircleDetails(nextCircleObject);
                        Circle user = getCircleDetails(currentUserCircle);
                        Circle venue = getCircleDetails(currentFestivalOrVenueCircle);
                        lineKeyFrame = new KeyFrame(
                                Duration.millis(currentIndex * 300),
                                event -> {
                                    drawEdgeBetweenTwoPoints(user.getCenterX(), user.getCenterY(), venue.getCenterX(), venue.getCenterY(), gc);
                                }
                        );
                        timeline.getKeyFrames().add(lineKeyFrame);
                        currentIndex++;
                    } else if(nextCircleObject.getUserData() instanceof Festival){
                        currentFestivalOrVenueCircle = getCircleDetails(nextCircleObject);
                        Circle user = getCircleDetails(currentUserCircle);
                        lineKeyFrame = new KeyFrame(
                                Duration.millis(currentIndex * 300),
                                event -> {
                                    drawEdgeBetweenTwoPoints(user.getCenterX(), user.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                                }
                        );
                        timeline.getKeyFrames().add(lineKeyFrame);
                        currentIndex++;
                    } else if(nextCircleObject.getUserData() instanceof FestivalStage){
                        currentStage = getCircleDetails(nextCircleObject);
                        Circle festivalStage = getCircleDetails(nextCircleObject);
                        Circle festival = getCircleDetails(currentFestivalOrVenueCircle);
                        lineKeyFrame = new KeyFrame(
                                Duration.millis(currentIndex * 300),
                                event -> {
                                    drawEdgeBetweenTwoPoints(festivalStage.getCenterX(), festivalStage.getCenterY(), festival.getCenterX(), festival.getCenterY(), gc);
                                }
                        );
                        timeline.getKeyFrames().add(lineKeyFrame);
                        currentIndex++;
                    }else if (nextCircleObject.getUserData() instanceof Concert) {
                        if(currentFestivalOrVenueCircle.getUserData() instanceof Venue){
                            Circle festival = getCircleDetails(currentFestivalOrVenueCircle);

                            lineKeyFrame = new KeyFrame(
                                    Duration.millis(currentIndex * 300),
                                    event -> {
                                        drawEdgeBetweenTwoPoints(festival.getCenterX(), festival.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                                    }
                            );
                            timeline.getKeyFrames().add(lineKeyFrame);
                            currentIndex++;
                        }else{
                            Circle festival = getCircleDetails(currentStage);

                            lineKeyFrame = new KeyFrame(
                                    Duration.millis(currentIndex * 300),
                                    event -> {
                                        drawEdgeBetweenTwoPoints(festival.getCenterX(), festival.getCenterY(), nextCircleObject.getCenterX(), nextCircleObject.getCenterY(), gc);
                                    }
                            );
                            timeline.getKeyFrames().add(lineKeyFrame);
                            currentIndex++;
                        }


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
                    Duration.millis(currentIndex * 300),
                    event -> canvasBorderPane.getChildren().add(lastCircleObject)
            );
            timeline.getKeyFrames().add(lastKeyFrame);

        }
        // Play the timeline
        timeline.play();
    }

    public void drawEdgeBetweenTwoPoints(double Ax, double Ay, double Bx, double By, GraphicsContext gc) {
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
