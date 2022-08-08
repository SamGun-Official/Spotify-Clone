package com.spotify.ui.home;

import com.spotify.app.*;
import com.spotify.ui.*;
import com.spotify.ui.Window;
import com.spotify.ui.playlist.PlaylistUI;
import com.spotify.ui.playlist.TrackRow;
import com.spotify.ui.search.ContentSearch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Home extends JPanel {
    private Window frame;
    private int contentHeight;
    private ArrayList<Account> accounts;
    private ContainerCard containerCardRecentlyPlayedPlaylists;
    private ContainerCard containerCardMostLikedPlaylists;
    private ContainerCard containerCardMostLikedSongs;
    private ContainerCard containerCardMostPlayedSongs;
    private ArrayList<Playlist> recentlyPlayedPlaylists;
    private PriorityQueue<Playlist> mostLikedPlaylists;
    private PriorityQueue<Song> mostLikedSongs;
    private PriorityQueue<Song> mostPlayedSongs;

    public Home() {
        super();
        this.setBackground(new Color(18, 18, 18));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 0));
        this.setPreferredSize(new Dimension(Threshold.contentArea.width, Threshold.contentArea.height));
    }

    public Home(User user, Window frame, ArrayList<Account> accounts) {
        super();
        this.accounts = accounts;
        this.setBackground(new Color(18, 18, 18));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 0));
        this.setPreferredSize(new Dimension(Threshold.contentArea.width, Threshold.contentArea.height));
        
        this.recentlyPlayedPlaylists = user.getRecentlyPlayed();
        this.frame = frame;

        initRecentlyPlayed();
        initPriorityQueue();
        initMostLikedPlaylistsCard();
        initMostLikedSongsCard();
        initMostPlayedSongsCard();

        if(this.getPreferredSize().height < contentHeight) {
            this.setPreferredSize(new Dimension(this.getPreferredSize().width, contentHeight + 20));
        }
    }

    public void initPriorityQueue() {
        mostLikedPlaylists = new PriorityQueue<>(new Comparator<Playlist>() {
            @Override
            public int compare(Playlist o1, Playlist o2) {
                if (o1.getTotalLikes() > o2.getTotalLikes())
                    return -1;
                else if (o1.getTotalLikes() == o2.getTotalLikes())
                    return 0;
                else
                    return 1;
            }
        });
        mostLikedSongs = new PriorityQueue<>(new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                if (o1.getTotalLikes() > o2.getTotalLikes())
                    return -1;
                else if (o1.getTotalLikes() == o2.getTotalLikes())
                    return 0;
                else
                    return 1;
            }
        });
        mostPlayedSongs = new PriorityQueue<>(new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                if (o1.getTotalPlays() > o2.getTotalPlays())
                    return -1;
                else if (o1.getTotalPlays() == o2.getTotalPlays())
                    return 0;
                else
                    return 1;
            }
        });
        
        for (Account account : accounts) {
            if (account instanceof Admin) {
                Admin admin = (Admin) account;
                for (Playlist playlist : admin.getPlaylists()) {
                    mostLikedPlaylists.add(playlist);
                }
                for (Song song : admin.getSongs()) {
                    mostLikedSongs.add(song);
                    mostPlayedSongs.add(song);
                }
            }
            else if (account instanceof User) {
                User user = (User) account;
                for (Playlist playlist : user.getPlaylists()) {
                    mostLikedPlaylists.add(playlist);
                }
            }
        }
    }
    public void initMostLikedPlaylistsCard() {
        if (mostLikedPlaylists.isEmpty()) {
            return;
        }
        containerCardMostLikedPlaylists = new ContainerCard("Most Liked Playlists", frame);

        while (!mostLikedPlaylists.isEmpty()) {
            Playlist playlist = mostLikedPlaylists.poll();
            Card card = new Card(playlist.getName(), playlist.getTotalLikes() + " likes", playlist.getArtImage());
            card.getImage().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PlaylistUI panel = new PlaylistUI(
                            frame.accounts, frame.admin.getPlaylists().get(playlist
                            .getPlaylistID()), frame, PlaylistUI.NATIVE_PLAYLIST, -1
                    );
                    frame.changeContent(
                            new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user,
                            frame.accounts
                    );
                }
            });
            card.getTitle().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PlaylistUI panel = new PlaylistUI(
                            frame.accounts, frame.admin.getPlaylists().get(playlist
                            .getPlaylistID()), frame, PlaylistUI.NATIVE_PLAYLIST, -1
                    );
                    frame.changeContent(
                            new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user,
                            frame.accounts
                    );
                }
            });
            containerCardMostLikedPlaylists.getContent().add(card);
        }

        this.add(containerCardMostLikedPlaylists);
        this.add(Box.createRigidArea(new Dimension(containerCardMostLikedPlaylists.getPreferredSize().width, 30)));

        contentHeight += containerCardMostLikedPlaylists.getPreferredSize().height + 30;
    }    
    public void initMostLikedSongsCard() {
        if (mostLikedSongs.isEmpty()) {
            return;
        }
        containerCardMostLikedSongs = new ContainerCard("Most Liked Songs", frame);

        while (!mostLikedSongs.isEmpty()) {
            Song song = mostLikedSongs.poll();
            Card card = new Card(song.getTitle(), song.getTotalLikes() + " likes", song.getAlbumArt());
            card.getImage().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    searchedSongClicked(song, frame);
                }
            });
            card.getTitle().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    searchedSongClicked(song, frame);
                }
            });
            containerCardMostLikedSongs.getContent().add(card);
        }

        this.add(containerCardMostLikedSongs);
        this.add(Box.createRigidArea(new Dimension(containerCardMostLikedSongs.getPreferredSize().width, 30)));

        contentHeight += containerCardMostLikedSongs.getPreferredSize().height + 30;
    }
    public void initMostPlayedSongsCard() {
        if (mostPlayedSongs.isEmpty()) {
            return;
        }
        containerCardMostPlayedSongs = new ContainerCard("Most Played Songs", frame);

        while (!mostPlayedSongs.isEmpty()) {
            Song song = mostPlayedSongs.poll();
            Card card = new Card(song.getTitle(), song.getTotalPlays() + " plays", song.getAlbumArt());
            card.getImage().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    searchedSongClicked(song, frame);
                }
            });
            card.getTitle().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    searchedSongClicked(song, frame);
                }
            });
            containerCardMostPlayedSongs.getContent().add(card);
        }

        this.add(containerCardMostPlayedSongs);
        this.add(Box.createRigidArea(new Dimension(containerCardMostPlayedSongs.getPreferredSize().width, 30)));

        contentHeight += containerCardMostPlayedSongs.getPreferredSize().height + 30;
    }

    public void initRecentlyPlayed() {
        if (recentlyPlayedPlaylists.isEmpty()) {
            return;
        }
        containerCardRecentlyPlayedPlaylists = new ContainerCard("Recently Played", frame);
        for(Playlist playlist : recentlyPlayedPlaylists) {
            Card card = new Card(playlist.getName(), playlist.getDescription(), playlist.getArtImage());
            card.getImage().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PlaylistUI panel = new PlaylistUI(
                            frame.accounts, frame.admin.getPlaylists().get(playlist
                            .getPlaylistID()), frame, PlaylistUI.NATIVE_PLAYLIST, -1
                    );
                    frame.changeContent(
                            new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user,
                            frame.accounts
                    );
                }
            });
            card.getTitle().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PlaylistUI panel = new PlaylistUI(
                            frame.accounts, frame.admin.getPlaylists().get(playlist
                            .getPlaylistID()), frame, PlaylistUI.NATIVE_PLAYLIST, -1
                    );
                    frame.changeContent(
                            new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user,
                            frame.accounts
                    );
                }
            });
            containerCardRecentlyPlayedPlaylists.getContent().add(card);
        }

        this.add(containerCardRecentlyPlayedPlaylists);
        this.add(Box.createRigidArea(new Dimension(containerCardRecentlyPlayedPlaylists.getPreferredSize().width, 30)));

        contentHeight += containerCardRecentlyPlayedPlaylists.getPreferredSize().height + 30;
    }

    private void searchedSongClicked(Song song, Window frame) {
        System.out.println("PASS 1");
        for(Playlist playlist : frame.admin.getPlaylists()) {
            System.out.println("PASS 2");
            if(song.getPlaylistName().equals(playlist.getName())) {
                System.out.println("PASS 3");
                PlaylistUI panel = new PlaylistUI(
                        frame.accounts, frame.admin.getPlaylists().get(playlist.getPlaylistID()),
                        frame, PlaylistUI.NATIVE_PLAYLIST, -1
                );
                frame.changeContent(
                        new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user,
                        frame.accounts
                );

                for(int i = 0; i < playlist.getSongs().size(); i++) {
                    System.out.println("PASS 4");
                    if(playlist.getSongs().get(i).getTitle()
                            .equals(song.getTitle())) {
                        System.out.println("PASS 5");
                        TrackRow tempRow = (TrackRow)panel.getTrackTableList().getComponent(i);
                        PlaylistUI tempUI = (PlaylistUI)frame.getContentPanel().getViewport().getView();

                        if(!frame.activePlaylistName.equals(tempUI.getPlaylistTitle().getText()) ||
                                (frame.activePlaylistName.equals(tempUI.getPlaylistTitle().getText()) &&
                                        tempRow.getTrackNumber() != frame.activeTrackNumber)) {
                            System.out.println("PASS 6");
                            frame.countOnShuffle = 0;
                        }

                        tempRow.playButtonAction(frame);
                    }
                }
            }
        }
    }
}
