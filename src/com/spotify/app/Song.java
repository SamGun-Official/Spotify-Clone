package com.spotify.app;

import java.io.Serializable;

public class Song implements Serializable, Utility {
    private String title;
    private String playlistName;
    private String dateAdded;
    private Artist artist;
    private Genre genre;
    private int duration;
    private int totalPlays;
    private int totalLikes;
    private String albumArt;
    private String audioFile;

    public Song(String title, String playlistName, String dateAdded, Artist artist, Genre genre, int duration,
    int totalPlays, int totalLikes, String imagePath, String audioFile) {
        this.title = title;
        this.playlistName = playlistName;
        this.dateAdded = getSimpleDateFormat(dateAdded);
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.totalPlays = totalPlays;
        this.totalLikes = totalLikes;

        if(imagePath.equals("default")) {
            this.albumArt = "./src/com/spotify/assets/icons/music_icon.png";
            this.audioFile = "";
        } else {
            this.albumArt = imagePath;
            this.audioFile = audioFile;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTotalPlays() {
        return totalPlays;
    }

    public void setTotalPlays(int totalPlays) {
        this.totalPlays = totalPlays;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    @Override
    public String toString() {
        return (
            "Song{" + "title=" + title + ", playlistName=" + playlistName + ", dateAdded=" + dateAdded +
            ", artist=" + artist + ", genre=" + genre + ", duration=" + duration + ", totalPlays=" + totalPlays +
            ", totalLikes=" + totalLikes + ", albumArt=" + albumArt + ", audioFile=" + audioFile + '}'
        );
    }
}
