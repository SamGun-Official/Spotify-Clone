package com.spotify.ui.profile;

import com.spotify.app.User;
import com.spotify.ui.Content;
import com.spotify.ui.ContentDraggable;
import com.spotify.ui.Threshold;
import com.spotify.ui.Window;
import com.spotify.ui.playlist.PlaylistUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.*;
import javax.swing.border.LineBorder;

public class ContentOtherUserProfile extends JPanel {
    private JLabel lbPublicPlaylist;
    private JLabel btnFollow;

    public ContentOtherUserProfile(Window frame, User CurrentUser, User Target) {
        this.setBackground(new Color(28,28,28));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Threshold.contentArea.width,Threshold.contentArea.height));
        this.add(new headerProfile(CurrentUser));

        int width = 70;
        int height = 460;

        lbPublicPlaylist = new JLabel("Public Playlist");
        lbPublicPlaylist.setBounds(70, 400, 500, 35);
        lbPublicPlaylist.setFont(new Font("Gotham", Font.BOLD, 27));
        lbPublicPlaylist.setForeground(new Color(255, 255, 255));
        this.add(lbPublicPlaylist);

        if(Target.checkFollowedUser(CurrentUser.getUsername())) {
            btnFollow = new JLabel("Follow");
        } else {
            btnFollow = new JLabel("Unfollow");
        }
        btnFollow.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
        btnFollow.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(btnFollow.getText().equals("Unfollow")) {
                    btnFollow.setText("Follow");
                    Target.removeFollowing(CurrentUser);
                    CurrentUser.removeFollowing(Target);
                } else {
                    btnFollow.setText("Unfollow");
                    Target.Following(CurrentUser);
                    CurrentUser.Follower(Target);
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

        btnFollow.setBackground(new Color(28, 28, 28));
        btnFollow.setBorder(new LineBorder(new Color(255, 255, 255)));
        btnFollow.setBounds(70, 320, 200, 60);
        btnFollow.setFont(new Font("Gotham", Font.BOLD, 25));
        btnFollow.setForeground(new Color(255, 255, 255));
        btnFollow.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.add(btnFollow);

        int ctrContainer = 0;

        if(CurrentUser.getLikedSongs() != null) {
            ContainerPlaylist cp = new ContainerPlaylist();

            if(ctrContainer == 0) {
                cp.setBounds(70, height, 200, 250);
            } else {
                cp.setBounds(width, height, 200, 250);
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
                    cp.setBounds(width,height,200,230);
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
