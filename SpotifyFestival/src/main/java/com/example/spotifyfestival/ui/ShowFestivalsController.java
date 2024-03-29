package com.example.spotifyfestival.ui;

import com.example.spotifyfestival.main.App;
import com.example.spotifyfestival.api.rapidapi.RapidAPIConcertsAPI;
import com.example.spotifyfestival.ui.helper.classes.AppSwitchScenesMethods;
import com.example.spotifyfestival.ui.helper.classes.Helper;
import com.example.spotifyfestival.ui.spotify.controllers.RapidAPIDialogController;
import com.example.spotifyfestival.api.rapidapi.RapidAPIParameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;

public class ShowFestivalsController {
    @FXML
    public GridPane mainGridPane;

    public void onGenerateSuggestionList() {
        AppSwitchScenesMethods.switchSceneDatabase("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/ConcertCanvas/CanvasScene.fxml");
    }

    public void onGetBackButtonClicked(ActionEvent event) {
        Helper.backToMainPageCondition();
    }

    @FXML
    public void showAPIParametersDialog() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        dialog.setTitle("Search parameters: ");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/chooseTheSearchParameters.fxml"));

        try {
            dialog.getDialogPane().setContent(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            RapidAPIDialogController controller = loader.getController();
            RapidAPIParameters parameters = controller.processSelection();
            RapidAPIConcertsAPI concertsRapidAPI = RapidAPIConcertsAPI.getInstance();
            concertsRapidAPI.addParameters(parameters);
            concertsRapidAPI.getConcertsInYourArea();

        } else {
            System.out.println("Cancel Clicked");
        }
    }
}
