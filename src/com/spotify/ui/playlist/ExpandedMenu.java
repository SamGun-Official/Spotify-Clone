package com.spotify.ui.playlist;

import com.spotify.ui.Content;
import com.spotify.ui.ContentDraggable;
import com.spotify.ui.Threshold;
import com.spotify.ui.Window;
import com.spotify.ui.profile.ArtistProfile;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ExpandedMenu extends JPanel {
    static final int UNUSED_PLAYLIST = 0;
    static final int NATIVE_PLAYLIST = 1;
    static final int CUSTOM_PLAYLIST = 2;
    static final int LIKED_PLAYLIST = 100;
    static final int QUEUE_PLAYLIST = 101;

    static final int ACTION_BAR_ROW = 50;
    static final int TRACK_ROW = 51;

    private int globalWidth = 240;
    private int globalHeight = 40;

    private Window windowApp;
    private PlaylistUI dataUI;
    private TrackRow dataRow;

    public ExpandedMenu(int playlistStyle, int relativeComponent) {
        try {
            if(relativeComponent == ACTION_BAR_ROW) {
                if(playlistStyle == NATIVE_PLAYLIST) {
                    this.constructNativeABR();
                } else if(playlistStyle == CUSTOM_PLAYLIST || playlistStyle == UNUSED_PLAYLIST) {
                    this.constructCustomABR();
                }
            } else if(relativeComponent == TRACK_ROW) {
                if(playlistStyle == NATIVE_PLAYLIST || playlistStyle == LIKED_PLAYLIST) {
                    this.constructNativeTR();
                } else if(playlistStyle == CUSTOM_PLAYLIST || playlistStyle == QUEUE_PLAYLIST) {
                    this.constructCustomTR();
                }
            }
        } catch(Exception e) {
            throw new Error(e);
        }
    }

    public ExpandedMenu(Window windowApp, PlaylistUI dataUI, int playlistStyle, int relativeComponent) {
        try {
            this.windowApp = windowApp;
            this.dataUI = dataUI;

            if(relativeComponent == ACTION_BAR_ROW) {
                if(playlistStyle == NATIVE_PLAYLIST) {
                    this.constructNativeABR();
                } else if(playlistStyle == CUSTOM_PLAYLIST || playlistStyle == UNUSED_PLAYLIST) {
                    this.constructCustomABR();
                }
            }
        } catch(Exception e) {
            throw new Error(e);
        }
    }

    public ExpandedMenu(Window windowApp, TrackRow dataRow, int playlistStyle, int relativeComponent) {
        try {
            this.windowApp = windowApp;
            this.dataRow = dataRow;

            if(relativeComponent == TRACK_ROW) {
                if(playlistStyle == NATIVE_PLAYLIST || playlistStyle == LIKED_PLAYLIST) {
                    this.constructNativeTR();
                } else if(playlistStyle == CUSTOM_PLAYLIST || playlistStyle == QUEUE_PLAYLIST) {
                    this.constructCustomTR();
                }
            }
        } catch(Exception e) {
            throw new Error(e);
        }
    }

    private void constructNativeABR() {
        int componentCount = 2;

        JButton addToQueue = initButton(true, "Add to queue");
        addToQueue.addActionListener((ActionEvent ae) -> {
            for(int i = 0; i < windowApp.admin.getPlaylists().get(dataUI.getPlaylistID()).getSongs().size(); i++) {
                windowApp.user.getQueueList().getSongs().add(
                    windowApp.admin.getPlaylists().get(dataUI.getPlaylistID()).getSongs().get(i)
                );
            }
        });

        JButton addToPlaylist = initButton(false, "Add to playlist");
        addToPlaylist.addActionListener((ActionEvent ae) -> {
        });

        this.setBackground(new Color(40, 40, 40));
        this.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(40, 40, 40)));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setOpaque(true);
        this.setVisible(true);

        this.add(addToQueue, -1);
        this.add(addToPlaylist, -1);

        this.setPreferredSize(new Dimension(globalWidth, (globalHeight * componentCount) + 8));
        this.setSize(this.getPreferredSize());
    }

    private void constructCustomABR() {
        int componentCount = 2;

        JButton addToQueue = initButton(true, "Add to queue");
        addToQueue.addActionListener((ActionEvent ae) -> {
            for(int i = 0; i < windowApp.user.getPlaylists().get(dataUI.getPlaylistID()).getSongs().size(); i++) {
                windowApp.user.getQueueList().getSongs().add(
                    windowApp.admin.getPlaylists().get(dataUI.getPlaylistID()).getSongs().get(i)
                );
            }
        });

        JButton deletePlaylist = initButton(false, "Delete");
        deletePlaylist.addActionListener((ActionEvent ae) -> {
            windowApp.sidebarPanel.getPlaylistsButton().removeAll(windowApp.sidebarPanel.getPlaylistsButton());
            windowApp.user.getPlaylists().remove(dataUI.getPlaylistID());

            PlaylistUI panel = new PlaylistUI(
                    windowApp.accounts, windowApp.user.getLikedSongs(), windowApp, PlaylistUI.LIKED_PLAYLIST, -1
            );
            windowApp.changeContent(
                    new Content(panel, true), new ContentDraggable(windowApp, windowApp.user), windowApp.user,
                    windowApp.accounts
            );
//            windowApp.setContentPanels(new ArrayList<>());
//            windowApp.setPage(-1);
            windowApp.getContentPanels().remove(windowApp.getContentPanels().get(windowApp.getContentPanels().size() - 1));
            windowApp.setPage(windowApp.getPage() - 1);
            windowApp.activeUserPlaylist = -1;
//            windowApp.initSidebarPanel(windowApp.user, windowApp.accounts);
            windowApp.sidebarPanel.remove(windowApp.sidebarPanel.getPlaylistBarScroll());
            windowApp.sidebarPanel.initPlaylistBar();
            windowApp.sidebarPanel.initPlaylistBarScroll();
            windowApp.sidebarPanel.add(windowApp.sidebarPanel.getPlaylistBarScroll());
            windowApp.resetSidebar(4);
            windowApp.repaint();
            windowApp.revalidate();
        });

        this.setBackground(new Color(40, 40, 40));
        this.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(40, 40, 40)));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setOpaque(true);
        this.setVisible(true);

        this.add(addToQueue, -1);
        this.add(deletePlaylist, -1);

        this.setPreferredSize(new Dimension(globalWidth, (globalHeight * componentCount) + 8));
        this.setSize(this.getPreferredSize());
    }

    private void constructNativeTR() {
        int componentCount = 3;

        JButton addToQueue = initButton(true, "Add to queue");
        addToQueue.addActionListener((ActionEvent ae) -> {
            windowApp.user.getQueueList().getSongs().add(dataRow.song);
        });

        JButton goToArtist = initButton(false, "Go to artist");
        goToArtist.addActionListener((ActionEvent ae) -> {
            ArtistProfile ap = new ArtistProfile(dataRow.getSong().getArtist(), windowApp);
            windowApp.changeContent(
                new Content(ap, true), new ContentDraggable(windowApp, windowApp.user), windowApp.user,
                windowApp.accounts
            );
        });

        JButton addToPlaylist = initButton(false, "Add to playlist");
        addToPlaylist.addActionListener((ActionEvent ae) -> {
//            ExpandedMenu tempMenu = new ExpandedMenu(windowApp, this, playlistStyle, ExpandedMenu.TRACK_ROW);
//            tempMenu.putClientProperty("subMenu", 1);
//            tempMenu.setLocation(
//                (int)expandedMenuBtn.getLocationOnScreen().getX() - Threshold.sidebarArea.width -
//                tempMenu.getWidth() + expandedMenuBtn.getWidth(), expandedMenuBtn.getHeight() +
//                (int)expandedMenuBtn.getLocationOnScreen().getY()
//            );
        });

        this.setBackground(new Color(40, 40, 40));
        this.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(40, 40, 40)));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setOpaque(true);
        this.setVisible(true);

        this.add(addToQueue, -1);
        this.add(goToArtist, -1);
        this.add(addToPlaylist, -1);

        this.setPreferredSize(new Dimension(globalWidth, (globalHeight * componentCount) + 8));
        this.setSize(this.getPreferredSize());
    }

    private void constructCustomTR() {
        int componentCount = 5;

        JButton addToQueue = initButton(true, "Add to queue");
        addToQueue.addActionListener((ActionEvent ae) -> {
            windowApp.user.getQueueList().getSongs().add(dataRow.song);
        });

        JButton goToArtist = initButton(false, "Go to artist");
        goToArtist.addActionListener((ActionEvent ae) -> {
            ArtistProfile ap = new ArtistProfile(dataRow.getSong().getArtist(), windowApp);
            windowApp.changeContent(
                new Content(ap, true), new ContentDraggable(windowApp, windowApp.user), windowApp.user,
                windowApp.accounts
            );
        });

        JButton goToAlbum = initButton(true, "Go to album");
        goToAlbum.addActionListener((ActionEvent ae) -> {
            int playlistID = 0;

            for(int i = 0; i < windowApp.admin.getPlaylists().size(); i++) {
                if(windowApp.admin.getPlaylists().get(i).getName().equals(dataRow.getPlaylistName())) {
                    playlistID = windowApp.admin.getPlaylists().get(i).getPlaylistID();
                }
            }

            PlaylistUI panel = new PlaylistUI(
                windowApp.accounts, windowApp.admin.getPlaylists().get(playlistID), windowApp,
                PlaylistUI.NATIVE_PLAYLIST, -1
            );
            windowApp.changeContent(
                new Content(panel, true), new ContentDraggable(windowApp, windowApp.user), windowApp.user,
                windowApp.accounts
            );
        });

        JButton removeFromThis = initButton(false, "Remove from this playlist");
        removeFromThis.addActionListener((ActionEvent ae) -> {
        });

        JButton addToPlaylist = initButton(false, "Add to playlist");
        addToPlaylist.addActionListener((ActionEvent ae) -> {
        });

        this.setBackground(new Color(40, 40, 40));
        this.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(40, 40, 40)));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setOpaque(true);
        this.setVisible(true);

        this.add(addToQueue, -1);
        this.add(goToArtist, -1);
        this.add(goToAlbum, -1);
        this.add(removeFromThis, -1);
        this.add(addToPlaylist, -1);

        this.setPreferredSize(new Dimension(globalWidth, (globalHeight * componentCount) + 8));
        this.setSize(this.getPreferredSize());
    }

    private JButton initButton(boolean setBorder, String buttonText) {
        StringBuilder stringBuilder = new StringBuilder("   ");
        stringBuilder.append(buttonText);

        JButton tempButton = new JButton(stringBuilder.toString());
        tempButton.setBackground(new Color(62, 62, 62));
        tempButton.setBorder(null);
        tempButton.setContentAreaFilled(false);
        tempButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tempButton.setFocusable(false);
        tempButton.setFocusPainted(false);
        tempButton.setFont(new Font("Gotham", Font.BOLD, 13));
        tempButton.setForeground(Threshold.palette_b3b3b3);
        tempButton.setHorizontalAlignment(SwingConstants.LEFT);
        tempButton.setMaximumSize(new Dimension(globalWidth - 8, globalHeight));
        tempButton.setMinimumSize(tempButton.getMaximumSize());
        tempButton.setOpaque(false);
        tempButton.setPreferredSize(tempButton.getMaximumSize());
        tempButton.setSize(tempButton.getPreferredSize());
        tempButton.setVerticalAlignment(SwingConstants.CENTER);
        tempButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                tempButton.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                tempButton.setOpaque(false);
            }
        });

        if(setBorder == true) {
            tempButton.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(62, 62, 62)));
        }

        return tempButton;
    }
}
