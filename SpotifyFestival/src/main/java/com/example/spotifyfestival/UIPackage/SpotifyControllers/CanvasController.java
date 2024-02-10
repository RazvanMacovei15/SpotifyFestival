package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.APIServices.JSONConstant;
import com.example.spotifyfestival.API_Packages.APIServices.SpotifyService;
import com.example.spotifyfestival.API_Packages.RapidAPI.RapidAPIConcertsAPI;
import com.example.spotifyfestival.API_Packages.RapidAPI.RapidAPIParameters;
import com.example.spotifyfestival.API_Packages.SpotifyAPI.SpotifyAuthFlowService;
import com.example.spotifyfestival.DatabasePackage.DAO.ConcertDAO;
import com.example.spotifyfestival.DatabasePackage.DAO.FestivalDAO;
import com.example.spotifyfestival.DatabasePackage.DAO.FestivalStageDAO;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.*;
import com.example.spotifyfestival.Tree.AbstractPrintTree;
import com.example.spotifyfestival.UIPackage.HelperClasses.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.controlsfx.control.CheckComboBox;

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
    protected double concertLocationRadius;
    protected double x;
    protected double y;
    protected ConcertDAO concertDAO;
    protected FestivalDAO festivalDAO;
    protected FestivalStageDAO festivalStageDAO;
    protected RapidAPIConcertsAPI rapidAPIConcertsAPI;
    protected GraphicsContext gc;

    private Map<Genre, Integer> retrieveGenreCount() {
        HttpResponse<String> response = SpotifyService.getUserTopArtists();
        assert response != null;
        String jsonResponse = response.body();
        ObservableList<Artist> allArtists = SpotifyService.extractArtists(jsonResponse);
        Map<Genre, Integer> genreCount = SpotifyService.getGenreCountFromResponse(allArtists);
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
        concertLocationRadius = 5;

        gc = canvas.getGraphicsContext2D();

        //retrieve user genre history
        ObservableList<String> genres = retrieveUserGenreHistory();
        genreComboBox.getItems().setAll(genres);

        Map<Genre, Integer> genreCount = retrieveGenreCount();
        genreComboBox.setDisable(true);
        venueComboBox.setDisable(true);
        radiusField.setDisable(true);
    }

    public void onBackButtonClicked() {
        Helper.backToMainPageCondition();
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
        Map<Genre, Integer> topGenres = SpotifyService.getTopMostGenresListened();
//        System.out.println(topGenres);
        for(Genre genre : topGenres.keySet()){
            System.out.println("looking for: "+genre+" in the map!");
            searchGenreThroughTree(genre);
        }
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
//        String json = api.getConcertsInYourArea();

        createTree(festivalStageDAO, festivalDAO, concertDAO, JSONConstant.getJsonData(), canvas, userLocationRadius, venueCircleRadius, concertCircleRadius);
        displayCirclesOneAtATime(canvasBorderPane, gc, null, null, null);
    }

    @Override
    public Circle drawUserLocationCircle(Canvas canvas, UserLocation userLocation) {
        //create circle that stores the user location info

        Circle userLocationCircle = new Circle();
        userLocationCircle.setRadius(userLocationRadius);

        x = canvas.getWidth() / 2;
        y = canvas.getHeight() * ((double) 3 / 4) - 40;

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

        // Create a Tooltip for the circle
        Tooltip tooltip = new Tooltip("This is the USER's LOCATION");

        // Add a mouse entered event handler to show the tooltip
        userLocationCircle.setOnMouseEntered(event -> {
            Tooltip.install(userLocationCircle, tooltip);
        });

        // Add a mouse exited event handler to hide the tooltip
        userLocationCircle.setOnMouseExited(event -> {
            Tooltip.uninstall(userLocationCircle, tooltip);
        });

        return userLocationCircle;
    }

    @Override
    public Circle drawVenueCircle(int i, int numberOfVenueCircles, Entity entity, UserLocation userLocation) {
        double venueCenterX = x;
        double venueCenterY = y;
        double radiusFromUserLocation = 140;
        Circle venueLocationCircle = drawCircleOnTheRightSide(i, numberOfVenueCircles, venueCenterX, venueCenterY, radiusFromUserLocation, venueCircleRadius);
        venueLocationCircle.setFill(Color.RED);

        if (entity instanceof Venue venue) {
            venueLocationCircle.setUserData(venue);

            venueLocationCircle.setOnMouseClicked(event -> {
                Venue venueToCheck = (Venue) venueLocationCircle.getUserData();
                ObservableList<String> venueDetails = FXCollections.observableArrayList();
                String x = "Venue NAME: " + venueToCheck.getVenueName();
                String y = "City: " + venueToCheck.getCity();
                String z = "Street Address: " + venueToCheck.getStreetAddress();
                String w = venueToCheck.getLocationLatitude() + "\n" + venueToCheck.getLocationLongitude();
                String u = "Distance From User: " + calculateDistance(userLocation.getLatitude(), userLocation.getLongitude(), Double.parseDouble(venueToCheck.getLocationLatitude()), Double.parseDouble(venueToCheck.getLocationLongitude())) + "km!";
                venueDetails.addAll(x, y, z, u);
                detailsListView.setItems(venueDetails);
            });
            // Create a Tooltip for the circle
            Tooltip tooltip = new Tooltip("This is " +venue.getVenueName() + "'s LOCATION");

            // Add a mouse entered event handler to show the tooltip
            venueLocationCircle.setOnMouseEntered(event -> {
                Tooltip.install(venueLocationCircle, tooltip);
            });

            // Add a mouse exited event handler to hide the tooltip
            venueLocationCircle.setOnMouseExited(event -> {
                Tooltip.uninstall(venueLocationCircle, tooltip);
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
    public Circle drawConcertCircle(int i, int numberOfConcertCircles, Entity entity, double centerX, double centerY, int numberOfVenueCircles, int venueIndex) {
        double radiusFromVenueLocation = 20;
        Circle concertLocationCircle = null;
        if (venueIndex < numberOfVenueCircles / 4) {
            concertLocationCircle = drawCircleOnTheRightSide(i, numberOfConcertCircles, centerX, centerY, radiusFromVenueLocation, concertLocationRadius);
        } else {
            concertLocationCircle = drawCircleOnTheLeftSide(i, numberOfConcertCircles, centerX, centerY, radiusFromVenueLocation, concertLocationRadius);

        }

        concertLocationCircle.setFill(Color.GREEN);


        if (entity instanceof Concert concert) {
            concertLocationCircle.setUserData(concert);


            concertLocationCircle.setOnMouseClicked(event -> {
                System.out.println(concert);
                ObservableList<String> concertToListView = FXCollections.observableArrayList();
                String info = null;

                SpotifyAuthFlowService auth = SpotifyAuthFlowService.getInstance();
                if (concert.getListOfArtists().isEmpty()) {
                    info = "INFORMATION UNAVAILABLE!";
                } else {
                    info = concert.listOfArtistToString(concert.getListOfArtists());
                }
                for(Artist artist : concert.getListOfArtists()){
                    System.out.println(SpotifyService.returnArtistGenresFromSpotifyID(artist.getSpotifyId(), auth.getAccessToken()));
                }

                concertToListView.addAll(
                        "Description: " +
                                concert.getDescription(),
                        "Venue: " + concert.getVenue().getVenueName(),
                        "Artists: " + info,
                        "Date: " + concert.getStartOfTheConcert(),
                        "Time: " + concert.getTime());
                detailsListView.setItems(concertToListView);
            });
        }
        return concertLocationCircle;
    }

    @Override
    public Circle drawFestivalCircle(int i, int numberOfVenueCircles, Entity entity) {
        double venueCenterX = x;
        double venueCenterY = y;
        double radiusFromUserLocation = 240;

        Circle festivalLocationCircle = drawCircleOnTheRightSide(i, numberOfVenueCircles, venueCenterX, venueCenterY, radiusFromUserLocation, venueCircleRadius);
        festivalLocationCircle.setFill(Color.DARKMAGENTA);

        if (entity instanceof Festival festival) {
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
    public Circle drawStageCircle(int i, int numberOfConcertCircles, Entity entity, double centerX, double centerY, int numberOfVenueCircles, int venueIndex) {
        double radiusFromVenueLocation = 50;
        Circle concertLocationCircle = null;
        if (venueIndex < numberOfVenueCircles / 4) {
            concertLocationCircle = drawCircleOnTheRightSide(i, numberOfConcertCircles, centerX, centerY, radiusFromVenueLocation, concertLocationRadius);
        } else {
            concertLocationCircle = drawCircleOnTheLeftSide(i, numberOfConcertCircles, centerX, centerY, radiusFromVenueLocation, concertLocationRadius);

        }

        concertLocationCircle.setFill(Color.BROWN);
//        Circle circleForLambda = getCircleDetails(concertLocationCircle);

        if (entity instanceof FestivalStage stage) {
            concertLocationCircle.setUserData(stage);

            concertLocationCircle.setOnMouseClicked(event -> {
                System.out.println(stage);
            });
        }
        return concertLocationCircle;
    }

    @Override
    public void highlightCircle(Circle circle) {
        System.out.println(canvasBorderPane.getChildren());
        for(Node node : canvasBorderPane.getChildren()){
            if(node instanceof Circle circleToCheck){
                if(circleToCheck.getUserData() instanceof Concert concert && circle.getUserData() instanceof Concert secondConcert){
                    if(concert.getDescription().equals(secondConcert.getDescription())){
                        circleToCheck.setFill(Color.LIMEGREEN);
                    } else {
                        if(!circleToCheck.getFill().equals(Color.LIMEGREEN)){
                            circleToCheck.setFill(Color.GREY);
                        }

                    }
                }
            }
        }
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
