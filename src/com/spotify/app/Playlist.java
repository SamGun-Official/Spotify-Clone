package com.spotify.app;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Playlist implements Serializable, Utility {
    private Color backdropColor;
    private ArrayList<Song> songs;
    private String type;
    private String name;
    private String description;
    private String owner;
    private String dateCreated;
    private String artImage;
    private int playlistID;
    private int totalLikes;
    private int totalSongs;
    private int totalDurations;

    public Playlist(Color backdropColor, ArrayList<Song> songs, String type, String name, String description,
    String owner, String dateCreated, String artImage, int playlistID, int totalLikes, int totalSongs) {
        this.backdropColor = backdropColor;
        this.songs = songs;
        this.type = type;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.dateCreated = getDateFormat(dateCreated);
        this.playlistID = playlistID;
        this.totalLikes = totalLikes;
        this.totalSongs = this.songs.size();
        this.totalDurations = 0;

        if(artImage.equals("default")) {
            this.artImage = "./src/com/spotify/assets/icons/music_icon.png";
        } else {
            this.artImage = artImage;
        }

        for(int i = 0; i < this.totalSongs; i++) {
            this.totalDurations += this.songs.get(i).getDuration();
        }
    }

    public int getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    public Color getBackdropColor() {
        return backdropColor;
    }

    public void setBackdropColor(Color backdropColor) {
        this.backdropColor = backdropColor;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getArtImage() {
        return artImage;
    }

    public void setArtImage(String artImage) {
        this.artImage = artImage;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public int getTotalSongs() {
        return totalSongs;
    }

    public void setTotalSongs(int totalSongs) {
        this.totalSongs = totalSongs;
    }

    public int getTotalDurations() {
        return totalDurations;
    }

    public void setTotalDurations(int totalDurations) {
        this.totalDurations = totalDurations;
    }

    @Override
    public String toString() {
        return getName() + " {" + getSongs() + "}";
    }
}
