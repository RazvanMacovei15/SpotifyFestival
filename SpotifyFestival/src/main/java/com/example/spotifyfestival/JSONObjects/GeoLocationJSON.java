package com.example.spotifyfestival.JSONObjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoLocationJSON {
    @JsonProperty("ip")
    String ip;
    @JsonProperty("hostname")
    String hostname;
    @JsonProperty("city")
    String city;
    @JsonProperty("region")
    String region;
    @JsonProperty("country")
    String country;
    @JsonProperty("loc")
    String loc;
    @JsonProperty("org")
    String org;
    @JsonProperty("postal")
    String postal;
    @JsonProperty("timezone")
    String timezone;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
