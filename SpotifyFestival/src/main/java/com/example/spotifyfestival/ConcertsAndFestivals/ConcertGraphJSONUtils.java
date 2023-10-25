package com.example.spotifyfestival.ConcertsAndFestivals;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConcertGraphJSONUtils {

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

                        System.out.println(startDate);

                        System.out.println(time);
                    } else {
                        System.out.println("Failed to parse the date and time.");
                    }
                } else {

                    time = "Exact time of event UNKNOWN!";
                    startDate = dateAndTime;

                    System.out.println(startDate);
                    System.out.println(time);
                }

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
        List<Venue> listOfALLVenues = new ArrayList<>();
        ObservableList<Concert> concerts = concertGraphJSONUtils.extractConcerts(JSONConstant.getConstant());
        for (int i = 0; i < concerts.size(); i++) {
            System.out.println();
            listOfALLVenues.add(concerts.get(i).getVenue());
            System.out.println(concerts.get(i).getVenue().getLocationLatitude());

            List<Artist> list = concerts.get(i).getListOfArtists();
            for (Artist artist : list) {
                System.out.println(artist.getName());
            }
            System.out.println("@");
            System.out.println(concerts.get(i).getVenue().getVenueName());
        }
        System.out.println();
        System.out.println("nr of venues: " + listOfALLVenues.size());
    }
}
