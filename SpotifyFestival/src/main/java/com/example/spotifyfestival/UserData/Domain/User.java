package com.example.spotifyfestival.UserData.Domain;

import com.example.spotifyfestival.ConcertsAndFestivals.Entity;

import java.util.Map;

public class User extends Entity {
    protected String email;
    protected String username;
    protected String password;
    protected Map<Integer, Genre> genreList;

    public User(int id, String email, String username, String password, Map<Integer, Genre> genreList) {
        super(id);
        this.email = email;
        this.username = username;
        this.password = password;
        this.genreList = genreList;
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
}