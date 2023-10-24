package com.example.spotifyfestival.ConcertsAndFestivals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConcertJSONUtils {

    private static final String jsonResponse = JSONConstant.getConstant();

    private static final ObservableList<String> locations = FXCollections.observableArrayList();

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

                if(!location.has("geo")){
                    String geoLatitude = "lat not known!";
                    locations.add(geoLatitude);
                }else{
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

                for(int j = 0; j < performerObjects.length(); j++){
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

    public static void main(String[] args) {
        System.out.println(extractAttribute(JSONConstant.getConstant(), "endDate"));
        System.out.println(extractPerformers(JSONConstant.getConstant(),"name"));

        System.out.println(locations);
    }
}
