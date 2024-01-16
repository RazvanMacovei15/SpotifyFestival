package com.example.spotifyfestival.UIPackage.HelperClasses;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
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
