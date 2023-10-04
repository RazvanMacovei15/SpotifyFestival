package com.example.spotifyfestival;

import javafx.event.ActionEvent;

import java.io.IOException;

public class MainController {


    public void loginWithSpotify(ActionEvent event) throws Exception {

        onAwaitingConfirmationScene(event);



//        SpotifyAuthFlowService spotifyAuthFlowService = SpotifyAuthFlowService.getInstance();
//
//        onAwaitingConfirmationScene(event);
//
//        Thread workerThread = new Thread(() -> {
//            spotifyAuthFlowService.backendThatNeedsChange();
//
//            String accessToken = spotifyAuthFlowService.getAccessToken();
//
//            System.out.println(accessToken);
//
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    Parent root = null;
//                    try {
//                        root = FXMLLoader.load(getClass().getResource("afterLoginScreen.fxml"));
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    Stage stage = App.getPrimaryStage();
//                    stage.setScene(new Scene(root));
//                    stage.show();
//                }
//            });
//        });
//        workerThread.start();





//        onAwaitingConfirmationScene(event);

//        HelperMethods helperMethods = new HelperMethods();
//
//        helperMethods.createLoginSimulation();
//
//        helperMethods.simulateConfirmation();

//        spotifyAuthFlowService.backendThatNeedsChange(new Runnable() {
//            @Override
//            public void run() {
//               onGetBackButtonClicked(event);
//            }
//        }, () -> this.onBackToLoginClicked(event));
    }
    public void onAwaitingConfirmationScene(ActionEvent event) throws Exception {
        HelperMethods.switchScene(event, "awaitConfirmation.fxml");
    }

    public void onGetBackButtonClicked(ActionEvent event){
        try {
            HelperMethods.switchScene(event, "afterLoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to go back", e);
        }
    }

    public void onTopListsButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "topLists.fxml");
    }
    public void onTopArtistsButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "TopArtists.fxml");
    }
    public void onTopTracksButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "TopTracks.fxml");
    }
    public void onTopGenresButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "TopGenres.fxml");
    }

    public void onGetFestivalSuggestionButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "getFestivalSuggestions.fxml");
    }

    public void onGenerateSuggestionList(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "placeholderForFuture.fxml");
    }
     public void onLogOffButtonClicked(ActionEvent actionEvent) throws IOException {
        HelperMethods.switchScene(actionEvent, "NotLoggedIn.fxml");
    }
    public void onBackToLoginClicked(ActionEvent actionEvent) {
        try {
            HelperMethods.switchScene(actionEvent, "mainScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException("Unable to move forward", e);
        }
    }
}
