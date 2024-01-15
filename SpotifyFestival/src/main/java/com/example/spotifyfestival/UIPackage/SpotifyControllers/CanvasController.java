package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.RapidAPI.RapidAPIConcertsAPI;
import com.example.spotifyfestival.API_Packages.RapidAPI.RapidAPIParameters;
import com.example.spotifyfestival.API_Packages.APIServices.SpotifyService;
import com.example.spotifyfestival.DatabasePackage.DAO.ConcertDAO;
import com.example.spotifyfestival.DatabasePackage.DAO.FestivalDAO;
import com.example.spotifyfestival.DatabasePackage.DAO.FestivalStageDAO;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.*;
import com.example.spotifyfestival.Tree.AbstractPrintTree;
import com.example.spotifyfestival.UIPackage.AppSwitchScenesMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Map;

public class CanvasController extends AbstractPrintTree {

    // Radius of the Earth in kilometers
    private static final double EARTH_RADIUS = 6371.0;
    @FXML
    Canvas canvas;
    @FXML
    GridPane mainGridPane;
    @FXML
    BorderPane canvasBorderPane;
    @FXML
    Label searchParametersLabel;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    Label cityLabel;
    @FXML
    Button getLocation;
    @FXML
    CheckComboBox<String> genreComboBox;
    @FXML
    CheckComboBox<String> venueComboBox;
    @FXML
    TextField radiusField;
    @FXML
    Button generateSuggestions;
    @FXML
    Button backButton;
    @FXML
    Button showConcertsInArea;
    @FXML
    ListView<String> detailsListView;
    protected double userLocationRadius;
    protected double venueCircleRadius;
    protected double concertCircleRadius;
    protected double x;

    protected double y;
    protected ConcertDAO concertDAO;
    protected FestivalDAO festivalDAO;
    protected FestivalStageDAO festivalStageDAO;

    protected RapidAPIConcertsAPI rapidAPIConcertsAPI;

    protected GraphicsContext gc;

    private Map<Genre, Integer> retrieveGenreCount() {
        TopGenresController controller = new TopGenresController();
        HttpResponse<String> response = SpotifyService.getUserTopArtists();
        String jsonResponse = response.body();
        ObservableList<Artist> allArtists = SpotifyService.extractArtists(jsonResponse);
        Map<Genre, Integer> genreCount = controller.getGenreCountFromResponse(allArtists);
        return genreCount;
    }

    public ObservableList<String> retrieveUserGenreHistory() {
        Map<Genre, Integer> genreCount = retrieveGenreCount();

        ObservableList<Genre> genres = FXCollections.observableArrayList();
        ObservableList<String> genresCount = FXCollections.observableArrayList();
        ObservableList<String> genreNames = FXCollections.observableArrayList();

        for (Map.Entry<Genre, Integer> entry : genreCount.entrySet()) {
            genres.add(entry.getKey());
        }
        for (Map.Entry<Genre, Integer> entry : genreCount.entrySet()) {
            genresCount.add(String.valueOf(entry.getValue()));
        }
        for (Genre genre : genres) {
            String name = genre.getName();
            genreNames.add(name);
        }
//        System.out.println(genreNames);

        return genreNames;
    }

    public void initialize() {
        concertDAO = ConcertDAO.getInstance();
        festivalDAO = FestivalDAO.getInstance();
        festivalStageDAO = FestivalStageDAO.getInstance();


        //initialize canvas
        double canvasW = 700;
        double canvasH = 600;

        canvas.setHeight(canvasH);
        canvas.setWidth(canvasW);

        userLocationRadius = 10;
        venueCircleRadius = 10;
        concertCircleRadius = 5;

        gc = canvas.getGraphicsContext2D();

        //retrieve user genre history
        ObservableList<String> genres = retrieveUserGenreHistory();
        genreComboBox.getItems().setAll(genres);

        Map<Genre, Integer> genreCount = retrieveGenreCount();
        genreComboBox.setDisable(true);
        venueComboBox.setDisable(true);
        radiusField.setDisable(true);

        //exercising with images

        // Load an image
        Image image = new Image("https://i.scdn.co/image/ab6761610000f178ee07b5820dd91d15d397e29c");

        // Create an ImageView with the image
        ImageView imageView = new ImageView(image);

        // Draw the image on the canvas
//        gc.drawImage(image, 0, 0);

        // Set the desired width and height to scale down the image
        double scaledWidth = 50;
        double scaledHeight = 50;

        // Set the fitWidth and fitHeight properties to scale the image
        imageView.setFitWidth(scaledWidth);
        imageView.setFitHeight(scaledHeight);

        mainGridPane.add(imageView, 0, 0);
    }

    public void onBackButtonClicked() {
        try {
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminMainScreen.fxml");
        } catch (IOException event) {
            throw new RuntimeException("Unable to move forward", event);
        }
    }

    public RapidAPIParameters processSelection() {
        LocalDate startDateArea = startDatePicker.getValue();
        LocalDate endDateArea = endDatePicker.getValue();
        String cityArea = cityLabel.getText().trim();

        RapidAPIParameters rapidAPIParameters = new RapidAPIParameters(startDateArea, endDateArea, cityArea);
        RapidAPIConcertsAPI.getInstance().addParameters(rapidAPIParameters);
        return rapidAPIParameters;
    }

    public void onGenerateSuggestionsButtonClicked() {
        System.out.println("WIP!!!");
    }

    public void onGetLocationButtonClicked() {
        rapidAPIConcertsAPI = RapidAPIConcertsAPI.getInstance();
        cityLabel.setText(rapidAPIConcertsAPI.getAttribute(rapidAPIConcertsAPI.handleIpInfoHttpResponse(), "city"));
    }

    public void onShowConcertsButtonClicked() {

        RapidAPIParameters parameters = processSelection();
        RapidAPIConcertsAPI api = RapidAPIConcertsAPI.getInstance();
        api.addParameters(parameters);
        String json = api.getConcertsInYourArea();

        createTree(festivalStageDAO, festivalDAO, concertDAO, json, canvasBorderPane, canvas, userLocationRadius, venueCircleRadius, concertCircleRadius, gc);
        displayCirclesOneAtATime(canvasBorderPane, gc, null, null, null);
    }

    @Override
    public Circle drawUserLocationCircle(double userLocationRadius, Canvas canvas, UserLocation userLocation) {
        //create circle that stores the user location info

        Circle userLocationCircle = new Circle();
        userLocationCircle.setRadius(userLocationRadius);

        x = canvas.getWidth() / 2;
        y = canvas.getHeight() * ((double) 3 /4) - 40;

        userLocationCircle.setCenterX(x);
        userLocationCircle.setCenterY(y);
        userLocationCircle.setFill(Color.BLUE);

        userLocationCircle.setUserData(userLocation);
        userLocationCircle.setOnMouseClicked(event -> {
            UserLocation user = (UserLocation) userLocationCircle.getUserData();
            String latitude = "Device Latitude: " + user.getLatitude();
            String longitude = "Device Longitude: " + user.getLongitude();
            ObservableList<String> userDetails = FXCollections.observableArrayList();
            userDetails.add(latitude);
            userDetails.add(longitude);
            detailsListView.setItems(userDetails);
        });

        return userLocationCircle;
    }

    @Override
    public Circle drawVenueCircle(int i, int numberOfVenueCircles, double venueCircleRadius, Entity entity, UserLocation userLocation) {
        double venueCenterX = x;
        double venueCenterY = y;
        double radiusFromUserLocation = 130;
        Circle venueLocationCircle = drawCircleOnTheRightSide(i, numberOfVenueCircles, venueCenterX, venueCenterY, radiusFromUserLocation, venueCircleRadius);
        venueLocationCircle.setFill(Color.RED);

        if(entity instanceof Venue venue){
            venueLocationCircle.setUserData(venue);

            venueLocationCircle.setOnMouseClicked(event -> {
                Venue venueToCheck = (Venue) venueLocationCircle.getUserData();
                ObservableList<String> venueDetails = FXCollections.observableArrayList();
                String x = "Venue NAME: "+venueToCheck.getVenueName();
                String y = "City: "+venueToCheck.getCity();
                String z = "Street Address: "+venueToCheck.getStreetAddress();
                String w = venueToCheck.getLocationLatitude() + "\n" + venueToCheck.getLocationLongitude();
                String u = "Distance From User: " + calculateDistance(userLocation.getLatitude(), userLocation.getLongitude(), Double.parseDouble(venueToCheck.getLocationLatitude()), Double.parseDouble(venueToCheck.getLocationLongitude())) + "km!";
                venueDetails.addAll(x, y, z, u);
                detailsListView.setItems(venueDetails);
            });
        }
        return venueLocationCircle;
    }

    //use the Haversine formula to calculate the distance between two geographical points given their latitude and longitude coordinates
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Convert latitude and longitude from degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Calculate the differences
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Haversine formula
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance in kilometers
        double distance = EARTH_RADIUS * c;

        return distance;
    }

    @Override
    public Circle drawConcertCircle(int i, int numberOfConcertCircles, double concertLocationRadius, Entity entity, double centerX, double centerY, int numberOfVenueCircles, int venueIndex) {
        double radiusFromVenueLocation = 20;
        Circle concertLocationCircle = null;
        if(venueIndex < numberOfVenueCircles/4){
            concertLocationCircle = drawCircleOnTheRightSide(i, numberOfConcertCircles, centerX, centerY, radiusFromVenueLocation, concertLocationRadius);
        }else{
            concertLocationCircle = drawCircleOnTheLeftSide(i, numberOfConcertCircles, centerX, centerY, radiusFromVenueLocation, concertLocationRadius);

        }

        concertLocationCircle.setFill(Color.GREEN);
        Circle circleForLambda = getCircleDetails(concertLocationCircle);

        if(entity instanceof Concert concert){
            concertLocationCircle.setUserData(concert);

            concertLocationCircle.setOnMouseClicked(event -> {
                System.out.println(concert);
                ObservableList<String> concertToListView = FXCollections.observableArrayList();

                concertToListView.addAll(
                        "Description: " +
                                concert.getDescription(),
                                "Venue: " + concert.getVenue().getVenueName(),
                                concert.listOfArtistToString(concert.getListOfArtists()),
                                "Date: " + concert.getStartOfTheConcert(),
                                "Time: " + concert.getTime());
                detailsListView.setItems(concertToListView);
            });
        }
        return concertLocationCircle;
    }

    @Override
    public Circle drawFestivalCircle(int i, int numberOfVenueCircles, double venueCircleRadius, Entity entity) {
        double venueCenterX = x;
        double venueCenterY = y;
        double radiusFromUserLocation = 240;

        Circle festivalLocationCircle = drawCircleOnTheRightSide(i, numberOfVenueCircles, venueCenterX, venueCenterY, radiusFromUserLocation, venueCircleRadius);
        festivalLocationCircle.setFill(Color.DARKMAGENTA);

        if(entity instanceof Festival festival){
            festivalLocationCircle.setUserData(festival);

            festivalLocationCircle.setOnMouseClicked(event -> {
                Festival venueToCheck = (Festival) festivalLocationCircle.getUserData();
                ObservableList<String> festivals = FXCollections.observableArrayList();
                festivals.add(venueToCheck.getName());
                detailsListView.setItems(festivals);
            });
        }
        return festivalLocationCircle;
    }

    @Override
    public Circle drawStageCircle(int i, int numberOfConcertCircles, double concertLocationRadius, Entity entity, double centerX, double centerY, int numberOfVenueCircles, int venueIndex) {
        double radiusFromVenueLocation = 50;
        Circle concertLocationCircle = null;
        if(venueIndex < numberOfVenueCircles/4){
            concertLocationCircle = drawCircleOnTheRightSide(i, numberOfConcertCircles, centerX, centerY, radiusFromVenueLocation, concertLocationRadius);
        }else{
            concertLocationCircle = drawCircleOnTheLeftSide(i, numberOfConcertCircles, centerX, centerY, radiusFromVenueLocation, concertLocationRadius);

        }

        concertLocationCircle.setFill(Color.GREENYELLOW);
        Circle circleForLambda = getCircleDetails(concertLocationCircle);

        if(entity instanceof FestivalStage stage){
            concertLocationCircle.setUserData(stage);

            concertLocationCircle.setOnMouseClicked(event -> {
                System.out.println(stage);
            });
        }
        return concertLocationCircle;
    }

    public Circle drawCircleOnTheRightSide(int i, int numberOfCircles, double circleCenterX, double circleCenterY, double radius, double circleRadius) {
        double angle = 2 * Math.PI * i / numberOfCircles;
        double circleX = circleCenterX + radius * Math.cos(angle);
        double circleY = circleCenterY + radius * Math.sin(angle);
        Circle circleToAdd = new Circle();
        circleToAdd.setCenterX(circleX);
        circleToAdd.setCenterY(circleY);
        circleToAdd.setRadius(circleRadius);

        return circleToAdd;
    }

    public Circle drawCircleOnTheLeftSide(int i, int numberOfCircles, double circleCenterX, double circleCenterY, double radius, double circleRadius) {
        double angle = 2 * Math.PI * i / numberOfCircles;
        double circleX = circleCenterX - radius * Math.cos(angle);
        double circleY = circleCenterY - radius * Math.sin(angle);
        Circle circleToAdd = new Circle();
        circleToAdd.setCenterX(circleX);
        circleToAdd.setCenterY(circleY);
        circleToAdd.setRadius(circleRadius);

        return circleToAdd;
    }
}
