package com.spotify.ui.profile;

//import com.spotify.app.Artist;
//import com.spotify.app.User;
//import com.spotify.ui.Content;
//import com.spotify.ui.ContentDraggable;
//import com.spotify.ui.Threshold;
//import com.spotify.ui.playlist.PlaylistUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.spotify.app.*;
import com.spotify.ui.*;
import com.spotify.ui.Window;
import com.spotify.ui.playlist.PlaylistUI;
import com.spotify.ui.profile.ArtistProfile;
import com.spotify.ui.profile.ContentOtherUserProfile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JPanel;

public class ArtistProfile extends JPanel {
    private JLabel lbPublicPlaylist;

    public ArtistProfile(Artist CurrentArtist, Window frame) {
        this.setBackground(new Color(28, 28, 28));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Threshold.contentArea.width,9000));
        this.add(new headerArtistProfile(CurrentArtist));

        int width = 70;
        int height = 460;

        lbPublicPlaylist = new JLabel("Public Playlist");
        lbPublicPlaylist.setBounds(70, 400, 500, 35);
        lbPublicPlaylist.setFont(new Font("Gotham", Font.BOLD, 27));
        lbPublicPlaylist.setForeground(new Color(255, 255, 255));
        this.add(lbPublicPlaylist);

        int ctrContainer = 0;

        for(int i = 0; i < CurrentArtist.getAlbumSize(); i++) {
            if(ctrContainer < 7) {
                ContainerPlaylist cp = new ContainerPlaylist();
                cp.setCursor(new java.awt.Cursor(Cursor.HAND_CURSOR));
                if(ctrContainer == 0) {
                    cp.setBounds(70, height, 200, 230);
                } else {
                    cp.setBounds(width, height, 200, 230);
                }
                int selected=i;
                cp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        PlaylistUI panel = new PlaylistUI(
                            frame.accounts, CurrentArtist.getAlbumIndex(selected), frame,
                            PlaylistUI.NATIVE_PLAYLIST, -1
                        );
                        frame.changeContent(
                            new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user, frame.accounts
                        );
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        PlaylistUI panel = new PlaylistUI(
                            frame.accounts, CurrentArtist.getAlbumIndex(selected), frame,
                            PlaylistUI.NATIVE_PLAYLIST, -1
                        );
                        frame.changeContent(
                            new Content(panel, true), new ContentDraggable(frame, frame.user), frame.user, frame.accounts
                        );
                    }
                });

                width += 230;
                cp.settitle(CurrentArtist.getAlbum().get(i).getName());

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
