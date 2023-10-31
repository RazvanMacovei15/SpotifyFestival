package com.example.spotifyfestival.Controllers;

import com.example.spotifyfestival.ConcertsAndFestivals.*;
import com.example.spotifyfestival.Tree.Tree;
import com.example.spotifyfestival.Tree.TreeNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;


public class ConcertCanvasController {
    @FXML
    private BorderPane canvasBorderPane;
    @FXML
    public Canvas canvas;
    @FXML
    public GridPane mainGridPane;
    @FXML
    public Button drawTree;

    public void initialize() {

    }

    public void drawCanvas() {
        Entity userLocation = new Entity();
        userLocation.setId("10");
        ConcertGraphJSONUtils utils = new ConcertGraphJSONUtils(userLocation);
        ObservableList<Concert> concerts = utils.extractConcerts(JSONConstant.getConstant());
        List<Venue> listOfAllVenues = utils.createListOfALlVenues(concerts);
        ObservableList<Entity> entityConcerts = FXCollections.observableArrayList();
        ObservableList<Entity> venueConcerts = FXCollections.observableArrayList();
        for (Venue venue : listOfAllVenues) {
            venueConcerts.add(venue);
        }
        for (Concert concert : concerts) {
            entityConcerts.add(concert);
        }
        Tree<Entity> tree = utils.createTree(venueConcerts, userLocation);
        utils.printTree(tree);
        drawTreeOnCanvas(tree);
    }

    public void drawTreeOnCanvas(Tree<Entity> tree) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Iterate through the tree and draw nodes and edges
        newDrawTreeNodes(tree.getRoot(), gc);
    }

    private void drawTreeNodes(TreeNode<Entity> root, GraphicsContext gc) {

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        double canvasWidth = 600;
        double canvasHeight = 600;
        double radius2 = 20;
        canvas.setHeight(canvasHeight);
        canvas.setWidth(canvasWidth);

        // Calculate the coordinates for the center of the canvas
        double x = canvas.getWidth() / 2;
        double y = canvas.getHeight() / 2;

        Circle newCircle = new Circle(x,y,radius2, Color.GREEN);

        newCircle.setOnMouseClicked(event -> {
            System.out.println("it works");
        });

        canvasBorderPane.getChildren().add(newCircle);

        // Create a blue circle
        double radius = 20;
        Circle userLocationCircle = new Circle();
        userLocationCircle.setCenterX(x);
        userLocationCircle.setCenterY(y);
        userLocationCircle.setRadius(radius);
        userLocationCircle.setFill(Color.BLUE);

        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Add the userLocationCircle to the mainGridPane
        canvasBorderPane.getChildren().add(userLocationCircle);


        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        double radiusFromCenter1 = 100;
        double radiusFromCenter2 = 200;
        gc.strokeOval(x - radiusFromCenter1, y - radiusFromCenter1, 2 * radiusFromCenter1, 2 * radiusFromCenter1);

        // Draw edges if necessary
        for (TreeNode<Entity> rootChild : root.getChildren()) {
            List<TreeNode<Entity>> venueChildren = rootChild.getChildren();

            gc.setStroke(Color.BLACK);  // Set the line color
            gc.setLineWidth(2.0);       // Set the line width

            double angleStep = 360.0/venueChildren.size();
            // Add your code to draw edges here if needed
            for(int i = 0; i < venueChildren.size(); i++){

                double angle = i * angleStep;
                double x2 = x + radiusFromCenter1 * Math.cos(Math.toRadians(angle));
                double y2 = y + radiusFromCenter1 * Math.sin(Math.toRadians(angle));
                int circleRadius  = 20;
                //select venue
                TreeNode<Entity> venueNode = venueChildren.get(i);
                // Draw a line between two points
                gc.strokeLine(x, y, x2, y2);

                gc.setFill(Color.GREEN);
                gc.fillOval(x2 - circleRadius, y2 - circleRadius, circleRadius * 2, circleRadius * 2);

                //get venue children
                List<TreeNode<Entity>> list = venueNode.getChildren();
//                parse through children and get each concert one at a time
                for(int j = 0; j< list.size(); j++){
                    double angle2 = i * angleStep;
                    double x3 = x + radiusFromCenter2 * Math.cos(Math.toRadians(angle2));
                    double y3 = y + radiusFromCenter2 * Math.sin(Math.toRadians(angle2));
                    int circleRadius2  = 20;

                    TreeNode<Entity> concertEntity = list.get(j);

                    Entity concert = concertEntity.getData();

                    // Draw a line between two points
                    gc.strokeLine(x2, y2, x3, y3);

                    gc.setFill(Color.BLACK);
                    gc.fillOval(x3 - circleRadius2, y3 - circleRadius2, circleRadius2 * 2, circleRadius2 * 2);

                }
            }
        }
    }

    private void newDrawTreeNodes(TreeNode<Entity> root, GraphicsContext gc)
    {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        double canvasWidth = 600;
        double canvasHeight = 600;
        double userLocationRadius = 10;
        double venueLocationRadius = 20;
        double concertLocationRadius = 25;
        double radiusFromRoot = 150;
        double radiusFromVenue = 100;

        canvas.setWidth(canvasWidth);
        canvas.setHeight(canvasHeight);

        //center of the canvas
        double x = canvas.getWidth()/2;
        double y = canvas.getHeight()/2;

        Entity userLocation = root.getData();

        //create circle that stores the user location info
        Circle userLocationCircle =  new Circle();
        userLocationCircle.setRadius(userLocationRadius);
        userLocationCircle.setCenterX(x);
        userLocationCircle.setCenterY(y);
        userLocationCircle.setFill(Color.BLUE);

        //add userLocation info to the circle
        userLocationCircle.setUserData(userLocation);

        canvasBorderPane.getChildren().add(userLocationCircle);

        //create the circle where the venues will sit on
        gc.setStroke(Color.GREY);
        gc.setLineWidth(1);
        double venuesRadiusX = 100;
        double venuesRadiusY = 100;
        gc.strokeOval(x, y, venuesRadiusX, venuesRadiusY);

        List<TreeNode<Entity>> venuesListE = root.getChildren();
        int numberOfCircles = venuesListE.size();
        for(int i = 0; i < numberOfCircles; i++)
        {
            //create the circle where the concerts will sit on

            //create the circle representing the venue and add data to it
            Circle venueDataCircle = drawCircleAtPoint(i, numberOfCircles, x, y, radiusFromRoot, venueLocationRadius);
            venueDataCircle.setFill(Color.GREEN);
            canvasBorderPane.getChildren().add(venueDataCircle);

            TreeNode<Entity> venueNode = venuesListE.get(i);
            Entity venueE = venueNode.getData();
            if(venueE instanceof Venue venue){
                venueDataCircle.setUserData(venue);
            }
            List<TreeNode<Entity>> concertsAtVenueE = venueNode.getChildren();

            int noOfConcertsAtVenue = concertsAtVenueE.size();
            //concert circle attributes
            int noOfConcertCircles = noOfConcertsAtVenue;
            double concertX = venueDataCircle.getCenterX();
            double concertY = venueDataCircle.getCenterY();
            for(int j = 0; j < noOfConcertsAtVenue; j++)
            {
                Circle concertDataCircle = drawCircleAtPoint(j, noOfConcertCircles, concertX, concertY, radiusFromVenue, concertLocationRadius);
                concertDataCircle.setFill(Color.RED);
                canvasBorderPane.getChildren().add(concertDataCircle);

                TreeNode<Entity> concertNode = concertsAtVenueE.get(j);
                Entity concertE = concertNode.getData();
                if(concertE instanceof Concert concert)
                {
                    concertDataCircle.setUserData(concert);
                }
            }
        }
    }
    public Circle drawCircleAtPoint(int i, int numberOfCircles, double circleCenterX, double circleCenterY, double radius, double circleRadius){
        double angle = 2 * Math.PI * i/numberOfCircles;
        double circleX = circleCenterX + radius * Math.cos(angle);
        double circleY = circleCenterY + radius * Math.sin(angle);
        Circle circleToAdd = new Circle();
        circleToAdd.setCenterX(circleX);
        circleToAdd.setCenterY(circleY);
        circleToAdd.setRadius(circleRadius);

        return circleToAdd;
    }

    public void drawEdgeBetweenTwoPoints(double Ax, double Ay, double Bx, double By, GraphicsContext gc)
    {
        gc.setStroke(Color.GREY);
        gc.setLineWidth(1);

        gc.strokeLine(Ax, Ay, Bx, By);
    }
}