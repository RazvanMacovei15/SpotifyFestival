module com.example.spotifyfestival {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.core;
    requires json;
    requires java.net.http;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires spark.core;
    requires java.desktop;
    requires junit;


    opens com.example.spotifyfestival to javafx.fxml;
    exports com.example.spotifyfestival;
    exports com.example.spotifyfestival.UnusedStuffForNow.helperObsLis;
    opens com.example.spotifyfestival.UnusedStuffForNow.helperObsLis to javafx.fxml;
//    exports com.example.spotifyfestival.Controllers;
    opens com.example.spotifyfestival.Controllers to javafx.fxml;

    exports com.example.spotifyfestival.SpotifyAPI;
    opens com.example.spotifyfestival.SpotifyAPI to javafx.fxml;
    exports com.example.spotifyfestival.API_URLS;
    opens com.example.spotifyfestival.API_URLS to javafx.fxml;
    exports com.example.spotifyfestival.RapidAPI;
    opens com.example.spotifyfestival.RapidAPI to javafx.fxml;

}