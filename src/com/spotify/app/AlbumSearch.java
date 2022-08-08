package com.spotify.app;

import java.io.Serializable;

public class AlbumSearch implements Serializable {
    private Playlist playlist;
    private Artist artist;

    public AlbumSearch(Playlist playlist, Artist artist) {
        this.playlist = playlist;
        this.artist = artist;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
