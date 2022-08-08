package com.spotify.app;

import java.io.Serializable;
import java.util.ArrayList;

public class Artist implements Serializable {
    private String name;
    private boolean isVerified;
    private String description;
    private ArrayList<Playlist> album;

    public Artist(String name, boolean isVerified, String description) {
        this.name = name;
        this.isVerified = isVerified;
        this.description = description;
        this.album = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Playlist> getAlbum() {
        return album;
    }

    public Playlist getAlbumIndex(int i){
        return album.get(i);
    }

    public void setAlbum(ArrayList<Playlist> album) {
        this.album = album;
    }

    public int getAlbumSize() {
        return this.album.size();
    }

    @Override
    public String toString() {
        return getName();
    }
}
