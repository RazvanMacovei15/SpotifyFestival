package com.example.spotifyfestival.ConcertsAndFestivals;

import com.example.spotifyfestival.Tree.Tree;
import com.example.spotifyfestival.Tree.TreeNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConcertGraphJSONUtils {

    private final Map<Venue, List<Concert>> canvasRepo = new HashMap<>();

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

        int concertId= 0;
        int venueId= 0;


        List<Venue> listOfVenues = new ArrayList<>();
        Set<String> venueNamesSet = new HashSet<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray data = jsonObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {

                concertId = i;

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
                    streetAddress = address.getString("streetAddress");
                }

                venueId = i;

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
                            Concert concert = new Concert(concertId, concertDescription, artistList, existingVenue, startDate, time);
                            concertList.add(concert);
                            // You can now work with the existingVenue as needed
                            break;
                        }
                    }
                } else {
                    // Create a new Venue and add it to the list
                    venue = new Venue(venueId, city, venueName, streetAddress, venueLatitude, venueLongitude);
                    listOfVenues.add(venue);
                    Concert concert = new Concert(concertId, concertDescription, artistList, venue, startDate, time);
                    concertList.add(concert);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return concertList;
    }

    private Map<Venue, List<Concert>> createCanvasRepo(String jsonResponse){
        ObservableList<Concert> allConcerts = FXCollections.observableArrayList();
        allConcerts = extractConcerts(jsonResponse);

        Map<Venue, List<Concert>> mapToReturn = new HashMap<>();

        for(Concert concert : allConcerts){
            String venueName = concert.getVenue().getVenueName();

        }

        return null;
    }

    private List<Concert> createListOfConcertsForEveryVenue(Concert venue){
        ObservableList<Concert> allConcerts = FXCollections.observableArrayList();
        allConcerts = extractConcerts(JSONConstant.getConstant());
        List<Concert> venueConcerts = new ArrayList<>();

        for(Concert concert : allConcerts){
            if(venue.getVenue().getVenueName().equals(concert.getVenue().getVenueName())){
                venueConcerts.add(concert);
            }
        }
        venue.getVenue().setListOfAllConcertsAtThatVenue(venueConcerts);
        return venueConcerts;
    }

    private ObservableList<Concert> createListOfALlVenues(ObservableList<Concert> list){
        ObservableList<Concert> listOfVenues =FXCollections.observableArrayList();
        int venueId = 0;
        String city = null;
        String streetAddress = null;
        String venueLatitude = null;
        String venueLongitude = null;
        for(int i = 0; i< list.size(); i++){
            venueId = i;
            city = list.get(i).getVenue().getCity();
            String venueName = list.get(i).getVenue().getVenueName();
            streetAddress = list.get(i).getVenue().getStreetAddress();
            venueLatitude = list.get(i).getVenue().getLocationLatitude();
            venueLongitude = list.get(i).getVenue().getLocationLongitude();

            Set<String> venueNamesSet = new HashSet<>();

            Venue venue = null;
            Venue existingVenue = null;

            for (Concert venueToCheck : listOfVenues) {
                venueNamesSet.add(venueToCheck.getVenue().getVenueName());
            }

            if (!venueNamesSet.contains(venueName)) {
                venue = new Venue(venueId, city, venueName, streetAddress, venueLatitude, venueLongitude);
                listOfVenues.add(new Concert(venueId, venue));
            }
        }
        return listOfVenues;
    }

    public Tree<Concert> createCanvasTree(ObservableList<Concert> venues, Concert userLocation){
        Tree<Concert> canvasTree = new Tree<>(userLocation);

        for(int i = 0; i < venues.size(); i++)
        {
            TreeNode<Concert> rootChild = new TreeNode<>(venues.get(i));

            canvasTree.getRoot().addChild(rootChild);

            List<Concert> concertsPerVenue = createListOfConcertsForEveryVenue(rootChild.getData());

            for(int j = 0; j < concertsPerVenue.size(); j++)
            {
                TreeNode<Concert> venueChild = new TreeNode<>(rootChild.getData());

                rootChild.addChild(venueChild);
            }
        }
        return canvasTree;
    }

    public void printTree(Tree<Concert> tree) {
        printTreeRecursive(tree.getRoot(), 0);
    }

    private void printTreeRecursive(TreeNode<Concert> node, int depth) {
        if (node == null) {
            return;
        }

        // Print the node's data with an indent based on the depth
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  "); // Two spaces per depth level
        }

        Concert concert = node.getData(); // Get the Concert object
        System.out.println(indent.toString() + concert.toString());

        // Recursively print the children
        for (TreeNode<Concert> child : node.getChildren()) {
            printTreeRecursive(child, depth + 1);
        }
    }

    public static void main(String[] args) {
        ConcertGraphJSONUtils concertGraphJSONUtils = new ConcertGraphJSONUtils();
        ObservableList<Concert> concerts = concertGraphJSONUtils.extractConcerts(JSONConstant.getConstant());

        ObservableList<Concert> venues = concertGraphJSONUtils.createListOfALlVenues(concerts);
        Tree<Concert> tree = concertGraphJSONUtils.createCanvasTree(venues, new Concert(100, new Venue(100, "ClujNapoca", "a", "a", "a", "a")));
        concertGraphJSONUtils.printTree(tree);
        System.out.println(tree.getRoot().getChildren().get(1).getData().toString());
    }
}
