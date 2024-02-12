package com.example.spotifyfestival.DatabasePackage.EntitiesPOJO;

import java.util.Objects;

public class SpotifyUser {
    private String country;
    private String displayName;
    private String email;
    private String spotifyId;
    private String uri;
    private int followers;

    public SpotifyUser(String displayName, String email, String spotifyId, String country, String uri, int followers) {
        this.displayName = displayName;
        this.email = email;
        this.spotifyId = spotifyId;
        this.uri = uri;
        this.followers = followers;
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpotifyId() {
        return spotifyId;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    @Override
    public String toString() {
        return "SpotifyUser{" +
                "country='" + country + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", spotifyId='" + spotifyId + '\'' +
                ", uri='" + uri + '\'' +
                ", followers=" + followers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpotifyUser that = (SpotifyUser) o;
        return Objects.equals(spotifyId, that.spotifyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spotifyId);
    }
}
