package com.example.spotifyfestival.DatabasePackage.DBHelpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtils {
    private static Connection connection;

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ":Could not start SQLite Drivers");
            return false;
        }
    }

    public static Connection getConnection(String location) throws SQLException {
        if (connection == null || connection.isClosed()) {
            // If connection is not already created, create a new one
            connection = connect(location);
        }
        return connection;
    }

    private static Connection connect(String location) {
        checkDrivers();

        String dbPrefix = "jdbc:sqlite";
        Connection conn;

        try {
            conn = DriverManager.getConnection(dbPrefix + ":" + location);
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " + location);
            return null;
        }
        return conn;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed!");
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not close SQLite DB connection");
        }
    }
}


