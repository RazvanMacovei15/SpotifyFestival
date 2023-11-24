package com.example.spotifyfestival.DatabasePackage.DBHelpers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Identifiable;
import com.example.spotifyfestival.GenericsPackage.MemoryRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DBGenericRepository< K, V extends Identifiable<K>> extends MemoryRepository<K,V> {
    protected String URL = "festivalDB";

    public String getURL() {
        return URL;
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() {
        Connection connection = connect(URL);
    }

    public static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ":Could not start SQLite Drivers");
            return false;
        }
    }

    public static Connection connect(String location) {
        checkDrivers();
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;

        try {
            connection = DriverManager.getConnection(dbPrefix + location);
            System.out.println("it works!");
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " +
                            location);
            return null;
        }
        return connection;
    }

}