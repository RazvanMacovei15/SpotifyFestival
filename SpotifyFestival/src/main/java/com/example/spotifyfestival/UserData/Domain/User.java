package com.example.spotifyfestival.UserData.Domain;

import com.example.spotifyfestival.ConcertsAndFestivals.Entity;

import java.util.Map;

public class User extends Entity {
    protected String email;
    protected String username;
    protected String password;
    protected Map<String,Integer> genreList;

    public User(String email, String username, String password, Map<String, Integer> genreList) {
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

    public Map<String, Integer> getGenreList() {
        return genreList;
    }

    public void setGenreList(Map<String, Integer> genreList) {
        this.genreList = genreList;
    }
}
