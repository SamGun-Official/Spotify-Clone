package com.spotify.app;

import java.io.Serializable;

public class PlaylistSearch implements Serializable {
    private Playlist playlist;
    private Account creator;

    public PlaylistSearch(Playlist playlist, Account creator) {
        this.playlist = playlist;
        this.creator = creator;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public String getCreatorName() {
        if(this.creator instanceof Admin) {
            return "Spotify";
        } else if(this.creator instanceof User) {
            User temp = (User)creator;
            String str = temp.getFirstName();
            return str;
        }
        return null;
    }

    @Override
    public String toString() {
        if(this.creator instanceof Admin) {
            return "Creator=Spotify, Playlist: " + playlist;
        } else {
            return "Creator=" + creator + ", Playlist: " + playlist;
        }
    }
}
