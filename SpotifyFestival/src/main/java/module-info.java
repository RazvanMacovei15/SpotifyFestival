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

    exports com.example.spotifyfestival.Services.API_Services;
    opens com.example.spotifyfestival.Services.API_Services to javafx.fxml;
    exports com.example.spotifyfestival.API_Packages.API_URLS;
    opens com.example.spotifyfestival.API_Packages.API_URLS to javafx.fxml;
    exports com.example.spotifyfestival.Services;
    opens com.example.spotifyfestival.Services to javafx.fxml;
    exports com.example.spotifyfestival.API_Packages.SpotifyAPI;
    opens com.example.spotifyfestival.API_Packages.SpotifyAPI to javafx.fxml;
    opens com.example.spotifyfestival.Controllers.SpotifyControllers to javafx.fxml;
    exports com.example.spotifyfestival.API_Packages.RapidAPI;
    opens com.example.spotifyfestival.API_Packages.RapidAPI to javafx.fxml;
    exports com.example.spotifyfestival.MainPackage;
    opens com.example.spotifyfestival.MainPackage to javafx.fxml;
    exports com.example.spotifyfestival.UtilsPackage;
    opens com.example.spotifyfestival.UtilsPackage to javafx.fxml;

}