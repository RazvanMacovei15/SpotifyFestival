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
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.example.spotifyfestival to javafx.fxml;
    exports com.example.spotifyfestival;
    exports com.example.spotifyfestival.UnusedStuffForNow.helperObsLis;
    opens com.example.spotifyfestival.UnusedStuffForNow.helperObsLis to javafx.fxml;
    exports com.example.spotifyfestival.Controllers;
    opens com.example.spotifyfestival.Controllers to javafx.fxml;

    exports com.example.spotifyfestival.SpotifyAPI;
    opens com.example.spotifyfestival.SpotifyAPI to javafx.fxml;
    exports com.example.spotifyfestival.API_URLS;
    opens com.example.spotifyfestival.API_URLS to javafx.fxml;
    exports com.example.spotifyfestival.RapidAPI;
    opens com.example.spotifyfestival.RapidAPI to javafx.fxml;

}