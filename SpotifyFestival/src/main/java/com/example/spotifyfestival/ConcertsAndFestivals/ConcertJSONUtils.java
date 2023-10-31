package com.example.spotifyfestival.ConcertsAndFestivals;

import com.example.spotifyfestival.RapidAPI.RapidAPIConcertsAPI;
import com.example.spotifyfestival.RapidAPI.RapidAPIParameters;
import com.example.spotifyfestival.Tree.Tree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConcertJSONUtils extends Tree<Entity> {

    public ConcertJSONUtils(Entity data) {
        super(data);
    }

    @Override
    public void drawLocationPin(Entity userLocation) {

    }
    @Override
    public void drawVenuePin(Entity venue) {

    }
    @Override
    public void drawConcertPin(Entity concert) {

    }
    public static String detectDateTimeFormat(String dateTimeStr) {
        String[] dateFormats = {
                "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd"
        };

        for (String dateFormat : dateFormats) {
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            try {
                Date date = formatter.parse(dateTimeStr);
                if (date != null) {
                    return dateFormat;
                }
            } catch (ParseException e) {
                continue;
            }
        }

        return "Unknown format";
    }
    public static Date parseDateTime(String dateTimeStr) throws ParseException {
        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        return iso8601Format.parse(dateTimeStr);
    }
    public static String formatDate(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatter.format(date);
    }
    public static String formatTime(Date date) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        return timeFormatter.format(date);
    }
    public ObservableList<Concert> extractConcerts(String jsonResponse) {

        ObservableList<Concert> concertList = FXCollections.observableArrayList();

        List<Venue> listOfVenues = new ArrayList<>();
        Set<String> venueNamesSet = new HashSet<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray data = jsonObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {

                JSONObject jsonConcert = data.getJSONObject(i);
                List<Artist> artistList = new ArrayList<>();

                String concertDescription = jsonConcert.getString("description");

                JSONArray performers = jsonConcert.getJSONArray("performer");

                for (int j = 0; j < performers.length(); j++) {
                    JSONObject performerObject = performers.getJSONObject(j);

                    String artistName = performerObject.getString("name");
                    Artist artist = new Artist(artistName);
                    artistList.add(artist);
                }

                String time = null;
                String startDate = null;

                String dateAndTime = jsonConcert.getString("startDate");

                if (detectDateTimeFormat(dateAndTime).equals("yyyy-MM-dd'T'HH:mm:ssZ")) {
//
                    Date parsedDate = parseDateTime(dateAndTime);

                    if (parsedDate != null) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                        LocalDate date = LocalDate.parse(formatDate(parsedDate), formatter);

                        startDate = String.valueOf(date);

                        time = formatTime(parsedDate);

                    } else {
                        System.out.println("Failed to parse the date and time.");
                    }
                } else {
                    time = "Exact time of event UNKNOWN!";
                    startDate = dateAndTime;
                }

                JSONObject location = jsonConcert.getJSONObject("location");

                JSONObject address = location.getJSONObject("address");

                String city = address.getString("addressLocality");

                String streetAddress = null;

                if (!address.has("streetAddress")) {
                    streetAddress = "Exact street address unknown!";
                } else {
                    if(address.get("streetAddress").equals(null)){
                        streetAddress = "Exact street address unknown!";
                    }else{
                        streetAddress = address.getString("streetAddress");
                    }
                }

                String venueName = location.getString("name");
                String venueLatitude = null;
                String venueLongitude = null;

                if (!location.has("geo")) {
                    venueLatitude = "Exact location unknown!";
                    venueLongitude = "Exact location unknown!";
                } else {
                    JSONObject locationGEO = location.getJSONObject("geo");

                    venueLatitude = String.valueOf(locationGEO.getDouble("latitude"));
                    venueLongitude = String.valueOf(locationGEO.getDouble("longitude"));

                }

                Venue venue = null;
                Venue existingVenue = null;

                for (Venue venueToCheck : listOfVenues) {
                    venueNamesSet.add(venueToCheck.getVenueName());
                }

                if (venueNamesSet.contains(venueName)) {
                    for (Venue venueToCheck : listOfVenues) {
                        if (venueToCheck.getVenueName().equals(venueName)) {
                            // You found a venue with the matching name
                            existingVenue = venueToCheck;
                            Concert concert = new Concert(concertDescription, artistList, existingVenue, startDate, time);
                            concertList.add(concert);
                            // You can now work with the existingVenue as needed
                            break;
                        }
                    }
                } else {
                    // Create a new Venue and add it to the list
                    venue = new Venue(city, venueName, streetAddress, venueLatitude, venueLongitude);
                    listOfVenues.add(venue);
                    Concert concert = new Concert(concertDescription, artistList, venue, startDate, time);
                    concertList.add(concert);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return concertList;
    }
    @Override
    public ObservableList<Entity> getConcertsAtVenue(Entity venue) {
        //retrieve data from JSON
        ObservableList<Concert> allConcerts = extractConcerts(JSONConstant.getConstant());
        //list to store concerts as entities;
        ObservableList<Entity> allEntityConcerts = FXCollections.observableArrayList();
        //list that will store the venue concerts
        ObservableList<Entity> allVenueConcerts = FXCollections.observableArrayList();

        allEntityConcerts.addAll(allConcerts);

        if(venue instanceof Venue venueToCheck){

            for(Entity concertEntity : allEntityConcerts){

                if(concertEntity instanceof Concert concertToCheck){

                    if(venueToCheck.getVenueName().equals(concertToCheck.getVenue().getVenueName())){
                        allVenueConcerts.add(concertEntity);
                    }
                }
            }
            List<Concert> concertsAtVenueToCheck = new ArrayList<>();
            for(Entity entity : allVenueConcerts){
                if(entity instanceof Concert){
                    Concert concert = (Concert) entity;
                    concertsAtVenueToCheck.add(concert);
                }
            }

            venueToCheck.setListOfAllConcertsAtThatVenue(concertsAtVenueToCheck);
        }
        return allVenueConcerts;
    }
    public ObservableList<Concert> createListOfConcertsForEveryVenue(Venue venue){
        ObservableList<Concert> allConcerts = FXCollections.observableArrayList();
        allConcerts = extractConcerts(JSONConstant.getConstant());
        ObservableList<Concert> venueConcerts = FXCollections.observableArrayList();

        for(Concert concert : allConcerts){
            if(venue.getVenueName().equals(concert.getVenue().getVenueName())){
                venueConcerts.add(concert);
            }
        }
        venue.setListOfAllConcertsAtThatVenue(venueConcerts);
        return venueConcerts;
    }
    public ObservableList<Venue> createListOfALlVenues(ObservableList<Concert> list){
        ObservableList<Venue> listOfVenues =FXCollections.observableArrayList();
        String city = null;
        String streetAddress = null;
        String venueLatitude = null;
        String venueLongitude = null;
        for(int i = 0; i< list.size(); i++){

            city = list.get(i).getVenue().getCity();
            String venueName = list.get(i).getVenue().getVenueName();
            streetAddress = list.get(i).getVenue().getStreetAddress();
            venueLatitude = list.get(i).getVenue().getLocationLatitude();
            venueLongitude = list.get(i).getVenue().getLocationLongitude();

            Set<String> venueNamesSet = new HashSet<>();

            Venue venue = null;

            for (Venue venueToCheck : listOfVenues) {
                venueNamesSet.add(venueToCheck.getVenueName());
            }

            if (!venueNamesSet.contains(venueName)) {
                venue = new Venue(city, venueName, streetAddress, venueLatitude, venueLongitude);
                listOfVenues.add(venue);
            }
        }
        return listOfVenues;
    }

    public static void main(String[] args) {
        RapidAPIConcertsAPI rapidAPIConcertsAPI = RapidAPIConcertsAPI.getInstance();
        LocalDate future = LocalDate.now().plusDays(20);
        RapidAPIParameters parameters = new RapidAPIParameters(LocalDate.now(),future,"Cluj-Napoca");
        rapidAPIConcertsAPI.addParameters(parameters);
        rapidAPIConcertsAPI.getConcertsInYourArea();
        String json = rapidAPIConcertsAPI.httpRequest();
        Entity userLoc = new Entity();
        ConcertJSONUtils concertJSONUtils = new ConcertJSONUtils(userLoc);
        ObservableList<Concert> concertsE = concertJSONUtils.extractConcerts(json);
        System.out.println(concertsE.size());
    }
}