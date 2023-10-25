package com.example.spotifyfestival.ConcertsAndFestivals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConcertGraphJSONUtils {
    private static final String jsonResponse = JSONConstant.getConstant();
    private static final ObservableList<String> locations = FXCollections.observableArrayList();

    public static ObservableList<List<String>> extractPerformers(String jsonResponse, String attributeName) {
        // Create an empty ObservableList to store the attribute values
        ObservableList<List<String>> attributeValues = FXCollections.observableArrayList();

        try {
            // Parse the JSON response
            JSONObject responseJson = new JSONObject(jsonResponse);
            JSONArray itemsArray = responseJson.getJSONArray("data");


            // Iterate through the items and extract the specified attribute
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject itemObject = itemsArray.getJSONObject(i);

                List<String> list = new ArrayList<>();

                JSONArray performerObjects = itemObject.getJSONArray("performer");

                for (int j = 0; j < performerObjects.length(); j++) {
                    JSONObject performerObject = performerObjects.getJSONObject(j);
                    String attributeValue = performerObject.getString(attributeName);
                    list.add(attributeValue);
                }
                attributeValues.add(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attributeValues;
    }

    public static ObservableList<String> extractAttribute(String jsonResponse, String attributeName) {
        // Create an empty ObservableList to store the attribute values
        ObservableList<String> attributeValues = FXCollections.observableArrayList();

        try {
            // Parse the JSON response
            JSONObject responseJson = new JSONObject(jsonResponse);

            JSONArray itemsArray = responseJson.getJSONArray("data");

            // Iterate through the items and extract the specified attribute
            for (int i = 0; i < itemsArray.length(); i++) {

                JSONObject itemObject = itemsArray.getJSONObject(i);

                JSONObject location = itemObject.getJSONObject("location");

                if (!location.has("geo")) {
                    String geoLatitude = "lat not known!";
                    locations.add(geoLatitude);
                } else {
                    JSONObject locationGEO = location.getJSONObject("geo");

                    double geoLatitude = locationGEO.getDouble("latitude");

                    locations.add(String.valueOf(geoLatitude));
                }
                String attributeValue = itemObject.getString(attributeName);
                attributeValues.add(attributeValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attributeValues;
    }



    public ObservableList<Concert> extractConcerts(String jsonResponse) {

        ObservableList<Concert> concertList = FXCollections.observableArrayList();


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
                JSONObject location = jsonConcert.getJSONObject("location");

                JSONObject address = location.getJSONObject("address");

                String city = address.getString("addressLocality");

                String streetAddress = null;

                if (!address.has("streetAddress")) {
                    streetAddress = "Exact street address unknown!";
                } else {
                    streetAddress = address.getString("streetAddress");
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
                Venue venue = new Venue(city, venueName, streetAddress, venueLatitude, venueLongitude);

                String time = null;
                LocalDateTime startDate = null;

                String dateAndTime = jsonConcert.getString("startDate");



                Concert concert = new Concert(concertDescription, artistList, venue, startDate, time);
                concertList.add(concert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return concertList;
    }

    public static void main(String[] args) {


        ConcertGraphJSONUtils concertGraphJSONUtils = new ConcertGraphJSONUtils();

        ObservableList<Concert> concerts = concertGraphJSONUtils.extractConcerts(JSONConstant.getConstant());
        System.out.println(concerts.get(1).getVenue());
    }
}
