module com.example.spotifyfestival {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires spark.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires json;

    opens com.example.spotifyfestival to javafx.fxml;
    exports com.example.spotifyfestival;
    exports com.example.spotifyfestival.helperObsLis;
    opens com.example.spotifyfestival.helperObsLis to javafx.fxml;
    exports com.example.spotifyfestival.Controllers;
    opens com.example.spotifyfestival.Controllers to javafx.fxml;
    exports com.example.spotifyfestival.JSONObjects;
    opens com.example.spotifyfestival.JSONObjects to javafx.fxml;
    exports com.example.spotifyfestival.SpotifyAPI;
    opens com.example.spotifyfestival.SpotifyAPI to javafx.fxml;
}