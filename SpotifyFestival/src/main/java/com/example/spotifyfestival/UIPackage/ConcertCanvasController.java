//package com.example.spotifyfestival.Controllers;
//
//import com.example.spotifyfestival.UnusedStuffForNow.helperObsLis.ConcertsAndFestivals.*;
//import com.example.spotifyfestival.Tree.Tree;
//import com.example.spotifyfestival.Tree.TreeNode;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.Button;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.util.Duration;
//
//import java.util.List;
//
//
//public class ConcertCanvasController {
//    @FXML
//    private BorderPane canvasBorderPane;
//    @FXML
//    public Canvas canvas;
//    @FXML
//    public GridPane mainGridPane;
//    @FXML
//    public Button drawTree;
//
//    public void initialize() {
//    }
//    public void drawCanvas() {
//
////        RapidAPIConcertsAPI rapidAPIConcertsAPI = RapidAPIConcertsAPI.getInstance();
////        LocalDate future = LocalDate.now().plusDays(20);
////        RapidAPIParameters parameters = new RapidAPIParameters(LocalDate.now(),future,"Cluj-Napoca");
////        rapidAPIConcertsAPI.addParameters(parameters);
////        rapidAPIConcertsAPI.getConcertsInYourArea();
////        String json = rapidAPIConcertsAPI.httpRequest();
////        Entity userLoc = new Entity();
////        ConcertJSONUtils concertJSONUtils = new ConcertJSONUtils(userLoc);
////        ObservableList<Concert> concertsE = concertJSONUtils.extractConcerts(json);
////        System.out.println(concertsE.size());
//
//        Entity userLocation = new Entity();
//        userLocation.setId(10);
//        ConcertJSONUtils utils = new ConcertJSONUtils(userLocation);
//        ObservableList<Concert> concerts = utils.extractConcerts(JSONConstant.getConstant());
//        List<Venue> listOfAllVenues = utils.createListOfALlVenues(concerts);
//        ObservableList<Entity> entityConcerts = FXCollections.observableArrayList();
//        ObservableList<Entity> venueConcerts = FXCollections.observableArrayList();
//        for (Venue venue : listOfAllVenues) {
//            venueConcerts.add(venue);
//        }
//        for (Concert concert : concerts) {
//            entityConcerts.add(concert);
//        }
//        Tree<Entity> tree = utils.createTree(venueConcerts, userLocation);
//        utils.printTree(tree);
//        drawTreeOnCanvas(tree);
//    }
//    public void drawTreeOnCanvas(Tree<Entity> tree) {
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        // Clear the canvas
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//
//        // Iterate through the tree and draw nodes and edges
//        drawTreeNodes(tree.getRoot(), gc);
//    }
//    private void drawTreeNodes(TreeNode<Entity> root, GraphicsContext gc)
//    {
//        long secondsToSleep = 1;
//        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        double canvasWidth = 600;
//        double canvasHeight = 600;
//        double userLocationRadius = 10;
//        double venueLocationRadius = 20;
//        double concertLocationRadius = 25;
//        double radiusFromRoot = 150;
//        double radiusFromVenue = 100;
//
//        canvas.setWidth(canvasWidth);
//        canvas.setHeight(canvasHeight);
//
//        //center of the canvas
//        double x = canvas.getWidth()/2;
//        double y = canvas.getHeight()/2;
//
//        Entity userLocation = root.getData();
//
//        //create circle that stores the user location info
//        Circle userLocationCircle =  new Circle();
//        userLocationCircle.setRadius(userLocationRadius);
//        userLocationCircle.setCenterX(x);
//        userLocationCircle.setCenterY(y);
//        userLocationCircle.setFill(Color.BLUE);
//
//        //add userLocation info to the circle
//        userLocationCircle.setUserData(userLocation);
//
//        canvasBorderPane.getChildren().add(userLocationCircle);
//
//        List<TreeNode<Entity>> venuesListE = root.getChildren();
//        int numberOfCircles = venuesListE.size();
//
//        for(int i = 0; i < numberOfCircles; i++)
//        {
//            //create the circle representing the venue and add data to it
//            Circle venueDataCircle = drawCircleAtPoint(i, numberOfCircles, x, y, radiusFromRoot, venueLocationRadius);
//            venueDataCircle.setFill(Color.GREEN);
//
//            //create line between venue and root
//            drawEdgeBetweenTwoPoints(x, y, venueDataCircle.getCenterX(), venueDataCircle.getCenterY(), gc);
//
//            TreeNode<Entity> venueNode = venuesListE.get(i);
//            Entity venueE = venueNode.getData();
//            if(venueE instanceof Venue venue){
//                venueDataCircle.setUserData(venue);
//            }
//
//            venueDataCircle.setOnMouseClicked(event -> {
//                Venue venue = (Venue) venueDataCircle.getUserData();
//                System.out.println(venue.getVenueName());
//            });
//
//            List<TreeNode<Entity>> concertsAtVenueE = venueNode.getChildren();
//
//            int noOfConcertsAtVenue = concertsAtVenueE.size();
//
//            //concert circle attributes
//            int noOfConcertCircles = noOfConcertsAtVenue;
//            double concertX = venueDataCircle.getCenterX();
//            double concertY = venueDataCircle.getCenterY();
//            // Create a timeline with a 1-second delay for each circle
//            Timeline timeline = new Timeline(
//                    new KeyFrame(Duration.seconds(2), event -> {
//                        canvasBorderPane.getChildren().add(venueDataCircle);
//                    })
//            );
//            timeline.play();
//
//            for(int j = 0; j < noOfConcertsAtVenue; j++)
//            {
//                Circle concertDataCircle = drawCircleAtPoint(j, noOfConcertCircles, concertX, concertY, radiusFromVenue, concertLocationRadius);
//                concertDataCircle.setFill(Color.RED);
//
//                //create line between venue and root
//                drawEdgeBetweenTwoPoints(venueDataCircle.getCenterX(), venueDataCircle.getCenterY(), concertDataCircle.getCenterX(), concertDataCircle.getCenterY(),gc);
//
//                TreeNode<Entity> concertNode = concertsAtVenueE.get(j);
//                Entity concertE = concertNode.getData();
//                if(concertE instanceof Concert concert)
//                {
//                    concertDataCircle.setUserData(concert);
//                }
//
//                concertDataCircle.setOnMouseClicked(event -> {
//                    Concert concert = (Concert) concertDataCircle.getUserData();
//                    System.out.println(concert.getDescription());
//                });
//
//                // Create a timeline with a 1-second delay for each concert circle
//                Timeline concertTimeline = new Timeline(
//                        new KeyFrame(Duration.seconds(5), event -> {
//                            canvasBorderPane.getChildren().add(concertDataCircle);
//                        })
//                );
//                concertTimeline.play();
//
//            }
//        }
//    }
//    public Circle drawCircleAtPoint(int i, int numberOfCircles, double circleCenterX, double circleCenterY, double radius, double circleRadius){
//        double angle = 2 * Math.PI * i/numberOfCircles;
//        double circleX = circleCenterX + radius * Math.cos(angle);
//        double circleY = circleCenterY + radius * Math.sin(angle);
//        Circle circleToAdd = new Circle();
//        circleToAdd.setCenterX(circleX);
//        circleToAdd.setCenterY(circleY);
//        circleToAdd.setRadius(circleRadius);
//
//        return circleToAdd;
//    }
//    public void drawEdgeBetweenTwoPoints(double Ax, double Ay, double Bx, double By, GraphicsContext gc)
//    {
//        gc.setStroke(Color.GREY);
//        gc.setLineWidth(1);
//
//        gc.strokeLine(Ax, Ay, Bx, By);
//    }
//}