package com.example.spotifyfestival.Controllers;

import com.example.spotifyfestival.APPHelperMethods;
import com.example.spotifyfestival.App;
import com.example.spotifyfestival.RapidAPI.RapidAPIConcertsAPI;
import com.example.spotifyfestival.RapidAPI.RapidAPIDialogController;
import com.example.spotifyfestival.RapidAPI.RapidAPIParameters;
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

    public void onGenerateSuggestionList(ActionEvent actionEvent) throws IOException {
        APPHelperMethods.switchSceneForCanvas(actionEvent, "ConcertCanvas.fxml");
    }

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            APPHelperMethods.switchScene(event, "afterLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    @FXML
    public void showAPIParametersDialog(){

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        dialog.setTitle("Search parameters: ");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("chooseTheSearchParameters.fxml"));

        try{
            dialog.getDialogPane().setContent(loader.load());
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            RapidAPIDialogController controller = loader.getController();
            RapidAPIParameters parameters = controller.processSelection();
            RapidAPIConcertsAPI concertsRapidAPI = RapidAPIConcertsAPI.getInstance();
            concertsRapidAPI.addParameters(parameters);
            concertsRapidAPI.getConcertsInYourArea();

        }else{
            System.out.println("Cancel Clicked");
        }
    }



}
