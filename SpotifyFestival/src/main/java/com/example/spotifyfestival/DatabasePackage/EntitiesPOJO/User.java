package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import java.util.Map;

public class User extends Entity {
    protected String email;
    protected String username;
    protected String password;
    protected String role;
    protected Map<Integer, Genre> genreList;

    protected String spotifyId;

    public User(int id, String email, String username, String password, String role, Map<Integer, Genre> genreList, String spotifyId) {
        super(id);
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.genreList = genreList;
        this.spotifyId = spotifyId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<Integer, Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(Map<Integer, Genre> genreList) {
        this.genreList = genreList;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", genreList=" + genreList +
                ", spotifyId='" + spotifyId + '\'' +
                +
                '}';
    }
}