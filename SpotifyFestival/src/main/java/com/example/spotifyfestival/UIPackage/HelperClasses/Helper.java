package com.example.spotifyfestival.UIPackage.HelperClasses;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;

public class Helper {
    public static void backToMainPageCondition() {
        if(UserManager.isAdmin()){
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/adminMainScreen.fxml");
        }else{
            AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/UncategorizedScenes/UserInterfaces/userMainScreen.fxml");
        }
    }
    public static void getBackToDBList(){
        AppSwitchScenesMethods.switchScene("/com/example/spotifyfestival/FXML_Files/DatabaseScenes/MainDatabaseScene.fxml");
    }

    public static void loadCover(ImageView image, String imageURL) {
        // Load the image from resources
        Image imageS = new Image(Helper.class.getResource(imageURL).toExternalForm());

        // Set the desired width and height to scale down the image
        double scaledWidth = 300;
        double scaledHeight = 150;

        // Set the fitWidth and fitHeight properties to scale the image
        image.setFitWidth(scaledWidth);
        image.setFitHeight(scaledHeight);
        // Set the image to the ImageView
        image.setImage(imageS);
    }
    public static void loadSpotifyCover(ImageView image, String imageURL) {
        // Load the image from resources
        Image imageS = new Image(Helper.class.getResource(imageURL).toExternalForm());

        // Set the desired width and height to scale down the image
        double scaledWidth = 300;
        double scaledHeight = 150;

        // Set the fitWidth and fitHeight properties to scale the image
        image.setFitWidth(scaledWidth);
        image.setFitHeight(scaledHeight);
        // Set the image to the ImageView
        image.setImage(imageS);
    }

    public static void mouseHoverUpOnButton(Button button) {
        // Create a ScaleTransition
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), button);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);

        // Set up event handlers for mouse enter and exit
        button.setOnMouseEntered(event -> {
            // Play the zoom-in animation
            scaleTransition.playFromStart();
        });

        button.setOnMouseExited(event -> {
            // Reverse the animation on mouse exit
            scaleTransition.setRate(-1.0);
            scaleTransition.playFrom("end");
        });
    }
}
