package com.example.spotifyfestival.UserData.FestivalDatabase.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    public static void main(String[] args) {
        checkDrivers();

        Connection connection = connect("FestivalDB");
    }

    private static boolean checkDrivers(){
        try{
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new org.sqlite.JDBC());
            return true;
        }catch (ClassNotFoundException | SQLException classNotFoundException){
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
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " +
                            location);
            return null;
        }
        return connection;
    }
}

