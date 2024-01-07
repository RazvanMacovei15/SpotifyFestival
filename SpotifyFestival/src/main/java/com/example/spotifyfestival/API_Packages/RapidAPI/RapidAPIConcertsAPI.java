package com.example.spotifyfestival.API_Packages.RapidAPI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class RapidAPIConcertsAPI {
    private final String geoLocationToken = "2245965a2814a6";
    private final String XRapidAPIKey = "8825308c83msh816c47e237e52aep1735c4jsnf6aa260c1616";
    private String publicIPAddress;

    public String getXRapidAPIKey() {
        return XRapidAPIKey;
    }

    private int countNumberAttempts;
    private Date lastUpdatedDate;
    private final String filepath = "RapidAPICounter.txt";
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private RapidAPIConcertsAPI() {
        countNumberAttempts = 0;
        lastUpdatedDate = new Date();
        checkIfFileExists();
    }

    private RapidAPIConcertsAPI(int count, Date date) {
        this.countNumberAttempts = count;
        this.lastUpdatedDate = date;
    }

    private static RapidAPIConcertsAPI instance;

    public static RapidAPIConcertsAPI getInstance() {
        if (instance == null) {
            instance = new RapidAPIConcertsAPI();
        }
        return instance;
    }

    public void writeCountAndDateToFile(int n, Date date) {
        try {
            FileWriter fileWriter = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            //Write the integer value to the file
            bw.write(String.valueOf(n));
            bw.newLine();

            //Format the Date as String
            String formattedDate = formatter.format(date);
            bw.write(formattedDate);

            //Close the BW and FW
            bw.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateFile() {
        try {
            FileWriter fileWriter = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            //Write the integer value to the file
            bw.write(String.valueOf(countNumberAttempts));
            bw.newLine();

            //Format the Date as String
            String formattedDate = formatter.format(lastUpdatedDate);
            bw.write(formattedDate);

            //Close the BW and FW
            bw.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkIfFileExists() {
        File file = new File(filepath);
        if (file.exists()) {
            readFromFile();
        } else {
            writeCountAndDateToFile(0, new Date());
        }
    }

    private void readFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String count = br.readLine();
            String date = br.readLine();

            countNumberAttempts = Integer.parseInt(count);
            lastUpdatedDate = formatter.parse(date);

        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void incrementIntAndDate() {
        Date date = new Date();

        checkExecutionCondition(checkIf24hHavePassed(date));

        int n = countNumberAttempts;
        n++;

        writeCountAndDateToFile(n, date);
    }

    double formattedDiff;

    private boolean checkIf24hHavePassed(Date date) {
        //calc the diff
        long timeDiff = date.getTime() - lastUpdatedDate.getTime();
        //convert to hours
        double diffInHours = (double) timeDiff / (1000 * 60 * 60);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        formattedDiff = Double.parseDouble(decimalFormat.format(diffInHours));

        return formattedDiff > 24.00;
    }

    private void checkExecutionCondition(Boolean bool) {
        if (countNumberAttempts >= 10) {
            if (bool) {
                countNumberAttempts = 0;
                updateFile();

            } else {
                System.out.println("You have x amount of hours to wait!");
            }
        }
    }

    public String URIBuilder() {

        RapidAPIParameters parameters = list.get(0);

        LocalDate startDate = parameters.getStartDate();
        LocalDate endDate = parameters.getEndDate();
        String location = parameters.getCity();

        String start = String.valueOf(startDate);
        String end = String.valueOf(endDate);

        String encodedStart = URLEncoder.encode(start);
        String encodedEnd = URLEncoder.encode(end);
        String encodedLocation = URLEncoder.encode(location);

        return "https://concerts-artists-events-tracker.p.rapidapi.com/location?name=" + encodedLocation +
                "&minDate=" + encodedStart +
                "&maxDate=" + encodedEnd +
                "&page=1";

    }

    public void getConcertsInYourArea() {
        Date date = new Date();

        if (checkIf24hHavePassed(date)) {
            System.out.println("More then 24h have passed! Rapid API counter is reset!");

            resetCounterAndDate(date);

            countNumberAttempts++;

            writeCountAndDateToFile(countNumberAttempts, lastUpdatedDate);

            httpRequest();
        } else if (countNumberAttempts < 10 && !checkIf24hHavePassed(date)) {
            countNumberAttempts++;

            lastUpdatedDate = date;

            writeCountAndDateToFile(countNumberAttempts, lastUpdatedDate);

            httpRequest();
        } else {
            System.out.println("You have to wait " + (24 - formattedDiff) + " more hours until you are able to reuse this API! Have a nice day!");
        }
    }

    private void resetCounterAndDate(Date date) {
        countNumberAttempts = 0;
        lastUpdatedDate = date;
    }

    public String httpRequest() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URIBuilder()))
                .header("X-RapidAPI-Key", getXRapidAPIKey())
                .header("X-RapidAPI-Host", "concerts-artists-events-tracker.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response.body());
        return response.body();
    }

    private ObservableList<RapidAPIParameters> list = FXCollections.observableArrayList();

    public void addParameters(RapidAPIParameters rapidAPIParameters) {
        list.add(0, rapidAPIParameters);
    }

    public HttpResponse<String> handleIpInfoHttpResponse() {

        HttpResponse<String> response = null;

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ipinfo.io"))
                .header("Authorization", "Bearer " + geoLocationToken) // Add the token as an "Authorization" header
                .GET()
                .build();
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.err.println("HTTP Request failed with status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public HttpResponse<String> handleIPAPIHttpResponse(String ipAddress) {

        HttpResponse<String> response = null;

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://ip-api.com/json/" + ipAddress))
                .GET()
                .build();
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.err.println("HTTP Request failed with status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String getAttribute(HttpResponse<String> response, String attribute) {
        String attributeToReturn = null;
        String jsonResponse = response.body().toString();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            attributeToReturn = jsonObject.getString(attribute);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attributeToReturn;
    }

    public String getPublicIPAddress() {
        try {
            // Make an HTTP request to a service that provides the public IP address
            URL url = new URL("https://api.ipify.org?format=text");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            publicIPAddress = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return publicIPAddress;
    }

    private double userLongitude;
    private double userLatitude;

    public void getCoordinates(String str) {
        String[] coordinates = str.split(",");
        if (coordinates.length == 2) {
            userLatitude = Double.parseDouble(coordinates[0]);
            userLongitude = Double.parseDouble(coordinates[1]);
        } else {
            System.out.println("You parsed the wrong attribute!");
        }
    }

    public static void main(String[] args) {
        RapidAPIConcertsAPI rapidAPIConcertsAPI = RapidAPIConcertsAPI.getInstance();
        HttpResponse<String> response = rapidAPIConcertsAPI.handleIpInfoHttpResponse();

        String city = rapidAPIConcertsAPI.getAttribute(response, "city");
        String location = rapidAPIConcertsAPI.getAttribute(response, "loc");
        System.out.println(city);
        System.out.println(location);
        rapidAPIConcertsAPI.getCoordinates(location);
        rapidAPIConcertsAPI.getPublicIPAddress();
        System.out.println(rapidAPIConcertsAPI.handleIPAPIHttpResponse(rapidAPIConcertsAPI.getPublicIPAddress()).body());
        System.out.println(rapidAPIConcertsAPI.getAttribute(getInstance().handleIPAPIHttpResponse(rapidAPIConcertsAPI.getPublicIPAddress()), "city"));
    }
}