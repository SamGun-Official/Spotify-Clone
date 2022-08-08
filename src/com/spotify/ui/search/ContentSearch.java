package com.spotify.ui.search;

import com.spotify.app.*;
import com.spotify.ui.*;
import com.spotify.ui.playlist.PlaylistUI;
import com.spotify.ui.playlist.TrackRow;
import com.spotify.ui.profile.ArtistProfile;
import com.spotify.ui.profile.ContentOtherUserProfile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JPanel;

public class ContentSearch extends JPanel {
    private ContainerCard songPanel;
    private ContainerCard artistPanel;
    private ContainerCard userPanel;
    private ContainerCard genrePanel;
    private ContainerCard playlistPanel;
    private ContainerCard albumPanel;

    public ContentSearch() {
        super();
        this.setBackground(new Color(18, 18, 18));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 0));
//        this.setPreferredSize(this.getSize());
        this.setPreferredSize(new Dimension(Threshold.contentArea.width, Threshold.contentArea.height));
    }

    public ContentSearch(ArrayList<Song> searchedSong, ArrayList<Artist> searchedArtist, ArrayList<User> searchedUser,
    ArrayList<Genre> searchedGenre, ArrayList<PlaylistSearch> searchedPlaylist, ArrayList<AlbumSearch> searchedAlbum,
    Window frame, User CurrentUser) {
        super();
        this.setBackground(new Color(18, 18, 18));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 0));
//        this.setPreferredSize(this.getSize());
        this.setPreferredSize(new Dimension(Threshold.contentArea.width, Threshold.contentArea.height));
        int contentHeight = 0;

        if(searchedSong.size() != 0) {
            songPanel = new ContainerCard("Songs", frame);
            for(Song song : searchedSong) {
                Card card = new Card(song.getTitle(), song.getArtist().getName(), song.getAlbumArt());
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
                songPanel.getContent().add(card);
            }

            this.add(songPanel);
            this.add(Box.createRigidArea(new Dimension(songPanel.getPreferredSize().width, 30)));

            contentHeight += songPanel.getPreferredSize().height + 30;
        }

        if(searchedArtist.size() != 0) {
            artistPanel = new ContainerCard("Artists", frame);
            for(Artist artist : searchedArtist) {
                Card card = new Card(artist.getName(), "Artist", "./src/com/spotify/assets/icons/music_icon.png");
                card.getImage().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ArtistProfile ap = new ArtistProfile(artist, frame);
                        frame.changeContent(
                            new Content(ap, true), new ContentDraggable(frame, frame.user), frame.user, frame.accounts
                        );
                    }
                });
                card.getTitle().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ArtistProfile ap = new ArtistProfile(artist, frame);
                        frame.changeContent(
                            new Content(ap, true), new ContentDraggable(frame, frame.user), frame.user, frame.accounts
                        );
                    }
                });

                artistPanel.getContent().add(card);
            }

            this.add(artistPanel);
            this.add(Box.createRigidArea(new Dimension(artistPanel.getPreferredSize().width, 30)));

            contentHeight += artistPanel.getPreferredSize().height + 30;
        }

        if(searchedUser.size() != 0) {
            userPanel = new ContainerCard("Users", frame);

            for(User user : searchedUser) {
                Card card = new Card(
                    user.getUsername(), user.getFirstName(), "./src/com/spotify/assets/icons_admin/02_user.png"
                );
                card.getImage().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ContentOtherUserProfile co = new ContentOtherUserProfile(frame, user, CurrentUser);
                        frame.changeContent(
                            new Content(co, true), new ContentDraggable(frame, frame.user), frame.user,
                            frame.accounts
                        );
                    }
                });
                card.getTitle().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ContentOtherUserProfile co = new ContentOtherUserProfile(frame, user, CurrentUser);
                        frame.changeContent(
                            new Content(co, true), new ContentDraggable(frame, frame.user), frame.user,
                            frame.accounts
                        );
                    }
                });

                userPanel.getContent().add(card);
            }

            this.add(userPanel);
            this.add(Box.createRigidArea(new Dimension(userPanel.getPreferredSize().width, 30)));

            contentHeight += userPanel.getPreferredSize().height + 30;
        }

        if(searchedGenre.size() != 0) {
            genrePanel = new ContainerCard("Genres", frame);

            for(Genre genre : searchedGenre) {
                Card card = new Card(genre.getName(), "", "./src/com/spotify/assets/icons/music_icon.png");
                genrePanel.getContent().add(card);
            }

            this.add(genrePanel);
            this.add(Box.createRigidArea(new Dimension(genrePanel.getPreferredSize().width, 30)));

            contentHeight += genrePanel.getPreferredSize().height + 30;
        }

        if(searchedPlaylist.size() != 0) {
            playlistPanel = new ContainerCard("Playlists", frame);

            for(PlaylistSearch playlistSearch : searchedPlaylist) {
                Card card = new Card(playlistSearch.getPlaylist().getName(), "By " + playlistSearch.getCreatorName(),
                playlistSearch.getPlaylist().getArtImage());
                card.getImage().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PlaylistUI panel = new PlaylistUI(
                            frame.accounts, frame.admin.getPlaylists().get(playlistSearch.getPlaylist()
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
                            frame.accounts, frame.admin.getPlaylists().get(playlistSearch.getPlaylist()
                            .getPlaylistID()), frame, PlaylistUI.NATIVE_PLAYLIST, -1
                        );
                        frame.changeContent(
                            new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user,
                            frame.accounts
                        );
                    }
                });
                playlistPanel.getContent().add(card);
            }

            this.add(playlistPanel);
            this.add(Box.createRigidArea(new Dimension(playlistPanel.getPreferredSize().width, 30)));

            contentHeight += playlistPanel.getPreferredSize().height + 30;
        }

        if(searchedAlbum.size() != 0) {
            albumPanel = new ContainerCard("Albums", frame);

            for(AlbumSearch albumSearch : searchedAlbum) {
                Card card = new Card(albumSearch.getPlaylist().getName(), "By " + albumSearch.getArtist().getName(),
                albumSearch.getPlaylist().getArtImage());
                card.getImage().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PlaylistUI panel = new PlaylistUI(
                            frame.accounts, frame.admin.getPlaylists().get(albumSearch.getPlaylist()
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
                            frame.accounts, frame.admin.getPlaylists().get(albumSearch.getPlaylist()
                            .getPlaylistID()), frame, PlaylistUI.NATIVE_PLAYLIST, -1
                        );
                        frame.changeContent(
                            new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user,
                            frame.accounts
                        );
                    }
                });
                albumPanel.getContent().add(card);
            }

            this.add(albumPanel);
            this.add(Box.createRigidArea(new Dimension(albumPanel.getPreferredSize().width, 30)));

            contentHeight += albumPanel.getPreferredSize().height + 30;
        }

        if(this.getPreferredSize().height < contentHeight) {
            this.setPreferredSize(new Dimension(this.getPreferredSize().width, contentHeight));
        }
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

    public ContainerCard getSongPanel() {
        return songPanel;
    }

    public void setSongPanel(ContainerCard songPanel) {
        this.songPanel = songPanel;
    }

    public ContainerCard getArtistPanel() {
        return artistPanel;
    }

    public void setArtistPanel(ContainerCard artistPanel) {
        this.artistPanel = artistPanel;
    }

    public ContainerCard getUserPanel() {
        return userPanel;
    }

    public void setUserPanel(ContainerCard userPanel) {
        this.userPanel = userPanel;
    }

    public ContainerCard getGenrePanel() {
        return genrePanel;
    }

    public void setGenrePanel(ContainerCard genrePanel) {
        this.genrePanel = genrePanel;
    }

    public ContainerCard getPlaylistPanel() {
        return playlistPanel;
    }

    public void setPlaylistPanel(ContainerCard playlistPanel) {
        this.playlistPanel = playlistPanel;
    }

    public ContainerCard getAlbumPanel() {
        return albumPanel;
    }

    public void setAlbumPanel(ContainerCard albumPanel) {
        this.albumPanel = albumPanel;
    }
}
