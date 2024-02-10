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
    requires org.controlsfx.controls;

    exports com.example.spotifyfestival.API_Packages.API_URLS;
    opens com.example.spotifyfestival.API_Packages.API_URLS to javafx.fxml;
    exports com.example.spotifyfestival.API_Packages.RapidAPI;
    opens com.example.spotifyfestival.API_Packages.RapidAPI to javafx.fxml;
    exports com.example.spotifyfestival.API_Packages.SpotifyAPI;
    opens com.example.spotifyfestival.API_Packages.SpotifyAPI to javafx.fxml;
    exports com.example.spotifyfestival.UIPackage.DatabaseControllers;
    opens com.example.spotifyfestival.UIPackage.DatabaseControllers to javafx.fxml;
    exports com.example.spotifyfestival.DatabasePackage.DBHelpers;
    opens com.example.spotifyfestival.DatabasePackage.DBHelpers to javafx.fxml;
    exports com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;
    exports com.example.spotifyfestival.GenericsPackage;
    opens com.example.spotifyfestival.GenericsPackage to javafx.fxml;
    exports com.example.spotifyfestival.LabFacultate.FileSavingStuff;
    opens com.example.spotifyfestival.LabFacultate.FileSavingStuff to javafx.fxml;
    exports com.example.spotifyfestival.MainPackage;
    opens com.example.spotifyfestival.MainPackage to javafx.fxml;
    exports com.example.spotifyfestival.Tree;
    opens com.example.spotifyfestival.Tree to javafx.fxml;
    exports com.example.spotifyfestival.UIPackage.SpotifyControllers;
    opens com.example.spotifyfestival.UIPackage.SpotifyControllers to javafx.fxml;
    opens com.example.spotifyfestival.UIPackage;
    exports com.example.spotifyfestival.UIPackage;
    exports com.example.spotifyfestival.DatabasePackage.DAO;
    opens com.example.spotifyfestival.DatabasePackage.DAO to javafx.fxml;
    opens com.example.spotifyfestival.FXML_Files.UncategorizedScenes.UserInterfaces to javafx.fxml;
    exports com.example.spotifyfestival.UIPackage.DBControllers;
    opens com.example.spotifyfestival.UIPackage.DBControllers to javafx.fxml;
    exports com.example.spotifyfestival.API_Packages.APIServices;
    opens com.example.spotifyfestival.API_Packages.APIServices to javafx.fxml, javafx.graphics;
    exports com.example.spotifyfestival.UIPackage.HelperClasses;
    opens com.example.spotifyfestival.UIPackage.HelperClasses;
    exports com.example.spotifyfestival.NewFeatures;
    opens com.example.spotifyfestival.NewFeatures to javafx.fxml;
    opens com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

    // Export your application package to javafx.graphics module

}