package com.spotify.ui.profile;

import com.spotify.app.User;
import com.spotify.ui.Content;
import com.spotify.ui.ContentDraggable;
import com.spotify.ui.graphics.RoundedPanel;
import com.spotify.ui.Threshold;
import com.spotify.ui.Window;
import com.spotify.ui.playlist.PlaylistUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class ContentProfile extends JPanel {
    private JLabel lbPublicPlaylist;
    private JLabel btnUpgrade;

    public ContentProfile(Window frame, User CurrentUser) {
        this.setBackground(new Color(28, 28, 28));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Threshold.contentArea.width, Threshold.contentArea.height + 200));
        this.add(new headerProfile(CurrentUser));

        int width = 70;
        int height = 460;

        lbPublicPlaylist = new JLabel("Public Playlist");
        lbPublicPlaylist.setBounds(70, 400, 500, 35);
        lbPublicPlaylist.setFont(new Font("Gotham", Font.BOLD, 27));
        lbPublicPlaylist.setForeground(new Color(255, 255, 255));
        this.add(lbPublicPlaylist);


        btnUpgrade = new JLabel();

        if(CurrentUser.isRequestVerification()) {
            btnUpgrade.setText("Waiting Vertification");
        } else {
            btnUpgrade.setText("Upgrade to Premium");
        }

        btnUpgrade.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
        btnUpgrade.setBackground(new Color(28, 28, 28));
        btnUpgrade.setBorder(new LineBorder(new Color(255, 255, 255)));
        btnUpgrade.setBounds(70, 320, 300, 60);
        btnUpgrade.setFont(new Font("Gotham", Font.BOLD, 25));
        btnUpgrade.setForeground(new Color(255, 255, 255));
        btnUpgrade.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.add(btnUpgrade);
        if(CurrentUser.isPremium()) {
            btnUpgrade.setVisible(false);
        }

        btnUpgrade.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(btnUpgrade.getText().equals("Upgrade to Premium")) {
                    btnUpgrade.setText("Waiting Vertification");
                    CurrentUser.requestMembershipVerification();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        int ctrContainer = 0;

        if(CurrentUser.getLikedSongs() != null) {
            ContainerPlaylist cp = new ContainerPlaylist();

            if(ctrContainer == 0) {
                cp.setBounds(70, height, 200, 230);
            } else {
                cp.setBounds(width, height, 200, 230);
            }
            cp.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
            cp.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    PlaylistUI panel = new PlaylistUI(
                            frame.accounts, CurrentUser.getLikedSongs(), frame,
                            PlaylistUI.NATIVE_PLAYLIST, -1
                    );
                    frame.changeContent(
                            new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user, frame.accounts
                    );
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    PlaylistUI panel = new PlaylistUI(
                            frame.accounts,  CurrentUser.getLikedSongs(), frame,
                            PlaylistUI.NATIVE_PLAYLIST, -1
                    );
                    frame.changeContent(
                            new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user, frame.accounts
                    );
                }
            });

            cp.settitle("Liked Song");

            this.add(cp);

            width += 230;
            ctrContainer++;
        }

        for(int i = 0; i < CurrentUser.getPlaylistsSize(); i++) {
            if(ctrContainer < 7) {
                ContainerPlaylist cp = new ContainerPlaylist();
                int selected=i;
                if(ctrContainer == 0) {
                    cp.setBounds(70, height, 200, 230);
                } else {
                    cp.setBounds(width, height, 200, 230);
                }
                cp.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
                cp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        PlaylistUI panel = new PlaylistUI(
                                frame.accounts, CurrentUser.getPlaylistIndex(selected), frame,
                                PlaylistUI.NATIVE_PLAYLIST, -1
                        );
                        frame.changeContent(
                                new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user, frame.accounts
                        );
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        PlaylistUI panel = new PlaylistUI(
                                frame.accounts, CurrentUser.getPlaylistIndex(selected), frame,
                                PlaylistUI.NATIVE_PLAYLIST, -1
                        );
                        frame.changeContent(
                                new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user, frame.accounts
                        );
                    }
                });

                width += 230;
                cp.settitle(CurrentUser.getPlaylists().get(i).getName());

                this.add(cp);

                ctrContainer++;
            }else{
                ctrContainer = 0;
                height += 240;
            }
        }

        if(ctrContainer == 0) {
            lbPublicPlaylist.setVisible(false);
        }
    }
}
