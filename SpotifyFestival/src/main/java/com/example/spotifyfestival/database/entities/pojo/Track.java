package com.example.spotifyfestival.database.entities.pojo;

import java.util.List;
import java.util.Objects;

public class Track extends Entity{

    protected String name;
    protected String spotifyID;
    protected String spotifyLink;
    protected String imageURL;
    protected List<Artist> artists;

    public Track(int id, String name, String spotifyID, String spotifyLink, String imageURL, List<Artist> artists) {
        super(id);
        this.name = name;
        this.spotifyID = spotifyID;
        this.spotifyLink = spotifyLink;
        this.imageURL = imageURL;
        this.artists = artists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpotifyID() {
        return spotifyID;
    }

    public void setSpotifyID(String spotifyID) {
        this.spotifyID = spotifyID;
    }

    public String getSpotifyLink() {
        return spotifyLink;
    }

    public void setSpotifyLink(String spotifyLink) {
        this.spotifyLink = spotifyLink;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public String toStringForUI() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(", performed by: ");
        if (artists.size() == 1){
            sb.append(artists.get(0).getName());
        }else{
            for(int i = 0 ; i < artists.size() - 1; i++){
                sb.append(artists.get(i).getName());
                sb.append(", ");
            }
            sb.append(artists.get(artists.size()-1).getName());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return id + "<>" +
                name + "<>" +
                artistsToString() + "<>" +
                spotifyID + "<>" +
                spotifyLink + "<>" +
                imageURL;

    }

    private String artistsToString(){
        if(this.artists == null || this.artists.isEmpty()){
            String s = "null";
            return s;
        }else{
            String[] artistsArray = new String[this.artists.size()];
            for(int i = 0; i< this.artists.size(); i++){
                artistsArray[i] = artists.get(i).getName();
            }
            StringBuilder sb = new StringBuilder();
            for (String artist : artistsArray) {
                sb.append(artist).append("|");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(spotifyID, track.spotifyID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spotifyID);
    }
}
