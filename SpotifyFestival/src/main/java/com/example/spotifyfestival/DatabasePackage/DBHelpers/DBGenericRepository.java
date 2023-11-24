package com.example.spotifyfestival.DatabasePackage.DBHelpers;

import com.example.spotifyfestival.DatabasePackage.EntitiesPOJO.Identifiable;
import com.example.spotifyfestival.GenericsPackage.MemoryRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DBGenericRepository< K, V extends Identifiable<K>> extends MemoryRepository<K,V> implements AutoCloseable{
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public DBGenericRepository(){
        openConnection();
    }
    private void openConnection() {
        if (checkDrivers()) {
            String dbLocation = "festivalDB"; // Replace with the actual location
            String dbPrefix = "jdbc:sqlite:";

            try {
                connection = DriverManager.getConnection(dbPrefix + dbLocation);
                System.out.println("Database connection established!");
            } catch (SQLException exception) {
                Logger.getAnonymousLogger().log(Level.SEVERE,
                        LocalDateTime.now() + ": Could not connect to SQLite DB at " + dbLocation);
            }
        }
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
    public void closeConnection() {
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
    @Override
    public void close()  {
        closeConnection();
    }
}