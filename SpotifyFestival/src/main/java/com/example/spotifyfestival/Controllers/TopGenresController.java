package com.example.spotifyfestival.Controllers;

import com.example.spotifyfestival.APPHelperMethods;
import javafx.event.ActionEvent;

import java.io.IOException;

public class TopGenresController {

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            APPHelperMethods.switchScene(event, "afterLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

}
