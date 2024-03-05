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

    exports com.example.spotifyfestival.api.urls;
    opens com.example.spotifyfestival.api.urls to javafx.fxml;
    exports com.example.spotifyfestival.api.rapidapi;
    opens com.example.spotifyfestival.api.rapidapi to javafx.fxml;
    exports com.example.spotifyfestival.api.spotify;
    opens com.example.spotifyfestival.api.spotify to javafx.fxml;
    exports com.example.spotifyfestival.ui.database.controllers;
    opens com.example.spotifyfestival.ui.database.controllers to javafx.fxml;
    exports com.example.spotifyfestival.database.helpers;
    opens com.example.spotifyfestival.database.helpers to javafx.fxml;
    exports com.example.spotifyfestival.database.entities.pojo;
    exports com.example.spotifyfestival.generics;
    opens com.example.spotifyfestival.generics to javafx.fxml;
    exports com.example.spotifyfestival.main;
    opens com.example.spotifyfestival.main to javafx.fxml;
    exports com.example.spotifyfestival.tree;
    opens com.example.spotifyfestival.tree to javafx.fxml;
    exports com.example.spotifyfestival.ui.spotify.controllers;
    opens com.example.spotifyfestival.ui.spotify.controllers to javafx.fxml;
    opens com.example.spotifyfestival.ui;
    exports com.example.spotifyfestival.ui;
    exports com.example.spotifyfestival.database.dao;
    opens com.example.spotifyfestival.database.dao to javafx.fxml;
    opens com.example.spotifyfestival.FXML_Files.UncategorizedScenes.UserInterfaces to javafx.fxml;
    exports com.example.spotifyfestival.api.services;
    opens com.example.spotifyfestival.api.services to javafx.fxml, javafx.graphics;
    exports com.example.spotifyfestival.ui.helper.classes;
    opens com.example.spotifyfestival.ui.helper.classes;
    exports com.example.spotifyfestival.newfeatures;
    opens com.example.spotifyfestival.newfeatures to javafx.fxml;
    opens com.example.spotifyfestival.database.entities.pojo;

}