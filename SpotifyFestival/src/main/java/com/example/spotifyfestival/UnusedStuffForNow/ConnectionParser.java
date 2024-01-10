package com.example.spotifyfestival.UnusedStuffForNow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConnectionParser {

    private static Set<String> establishedConnections = new HashSet<>();

    public static List<String> parseList(List<String> dataList, int index, String currentUser, String currentVenue) {
        List<String> connections = new ArrayList<>();

        // Base case: reach the end of the list
        if (index >= dataList.size()) {
            return connections;
        }

        // Process current item in the list
        String currentItem = dataList.get(index);

        // If it's a user, update the currentUser variable
        if (currentItem.startsWith("user:")) {
            currentUser = currentItem.substring(5);
        }

        // If it's a venue, update the currentVenue variable
        else if (currentItem.startsWith("venue:")) {
            currentVenue = currentItem.substring(6);
        }

        // If it's a concert, create connections between user, venue, and concert
        else if (currentItem.startsWith("concert:")) {
            if (currentUser != null && currentVenue != null) {
                String userVenueConnection = currentUser + " -> " + currentVenue;
                String venueConcertConnection = currentVenue + " -> " + currentItem.substring(8);

                // Check if the connection has already been established
                if (establishedConnections.add(userVenueConnection)) {
                    connections.add(userVenueConnection);
                }
                if (establishedConnections.add(venueConcertConnection)) {
                    connections.add(venueConcertConnection);
                }
            }
        }

        // Recursive call to process the next item in the list
        connections.addAll(parseList(dataList, index + 1, currentUser, currentVenue));

        return connections;
    }

    public static void main(String[] args) {
        List<String> dataList = List.of(
                "user:User1",
                "venue:Venue1",
                "concert:Concert1",
                "concert:Concert2",
                "venue:Venue2",
                "concert:Concert3",
                "concert:Concert4",
                "concert:Concert5",
                "concert:Concert1" // Duplicate connection, won't be printed
        );

        List<String> connections = parseList(dataList, 0, null, null);

        // Print the connections
        for (String connection : connections) {
            System.out.println(connection);
        }
    }
}
