package com.example.spotifyfestival.UIPackage.SpotifyControllers;

import com.example.spotifyfestival.API_Packages.RapidAPI.RapidAPIConcertsAPI;
import com.example.spotifyfestival.API_Packages.RapidAPI.RapidAPIParameters;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Artist;
import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Genre;
import com.example.spotifyfestival.UtilsPackage.AppSwitchScenesMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.Control;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Date;
import org.controlsfx.control.CheckComboBox;
import java.util.Map;

import static com.example.spotifyfestival.UIPackage.SpotifyControllers.TopArtistsController.getUserTopArtistsOver4Weeks;
import static com.example.spotifyfestival.UIPackage.SpotifyControllers.TopGenresController.getUserTopArtists;

public class CanvasController {
    @FXML
    Canvas canvas;
    @FXML
    GridPane mainGridPane;
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
    protected RapidAPIConcertsAPI rapidAPIConcertsAPI;

    private Map<Genre, Integer> retrieveGenreCount(){
        TopGenresController controller = new TopGenresController();
        HttpResponse<String> response = getUserTopArtists();
        String jsonResponse = response.body().toString();
        ObservableList<Artist> allArtists = controller.extractArtists(jsonResponse);
        Map<Genre, Integer> genreCount = controller.getGenreCountFromResponse(allArtists);
        return genreCount;
    }

    public ObservableList<String> retrieveUserGenreHistory(){
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

    public void initialize(){
        //retrieve user genre history
        ObservableList<String> genres = retrieveUserGenreHistory();
        Map<Genre, Integer> genreCount = retrieveGenreCount();
        System.out.println(genreCount);
        System.out.println(genres);
    }

    public void onBackButtonClicked(ActionEvent e){
        try {
            AppSwitchScenesMethods.switchScene(e, "/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminMainScreen.fxml");
        } catch (IOException event) {
            throw new RuntimeException("Unable to move forward", event);
        }
    }

    public RapidAPIParameters processSelection(){
        LocalDate startDateArea = startDatePicker.getValue();
        LocalDate endDateArea = endDatePicker.getValue();
        String cityArea = cityLabel.getText().trim();

        RapidAPIParameters rapidAPIParameters = new RapidAPIParameters(startDateArea, endDateArea, cityArea);
        RapidAPIConcertsAPI.getInstance().addParameters(rapidAPIParameters);
        return rapidAPIParameters;
    }

    public void onGenerateSuggestionsButtonClicked(){
        System.out.println("WIP!!!");
    }
    public void onGetLocationButtonClicked(){
        rapidAPIConcertsAPI = RapidAPIConcertsAPI.getInstance();
        cityLabel.setText(rapidAPIConcertsAPI.getAttribute(rapidAPIConcertsAPI.handleIpInfoHttpResponse(), "city"));
    }
    public void onShowConcertsButtonClicked(){
        RapidAPIParameters parameters = processSelection();
        RapidAPIConcertsAPI api = RapidAPIConcertsAPI.getInstance();
        api.addParameters(parameters);
        api.getConcertsInYourArea();
    }
}
