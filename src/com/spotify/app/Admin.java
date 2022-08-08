package com.spotify.app;

import java.io.Serializable;
import java.util.ArrayList;

public class Admin extends Account implements Serializable {
    private ArrayList<Artist> artists;
    private ArrayList<Genre> genres;
    private ArrayList<Playlist> playlists;
    private ArrayList<Song> songs;

    public Admin() {
        super("admin", "admin");
        this.artists = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.songs = new ArrayList<>();
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }
}
