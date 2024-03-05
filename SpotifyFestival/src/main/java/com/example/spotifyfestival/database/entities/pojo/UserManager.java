package com.example.spotifyfestival.database.entities.pojo;

public class UserManager {
    public static boolean isAdmin = false;

    public static void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public static boolean isAdmin() {
        return isAdmin;
    }
}
