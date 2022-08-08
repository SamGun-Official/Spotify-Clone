package com.spotify.ui.profile;

import com.spotify.app.User;
import com.spotify.ui.Threshold;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class SeeAllProfilePlaylist extends JPanel {
    public SeeAllProfilePlaylist(User CurrentUser) {
        this.setBackground(new Color(12, 50, 100));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Threshold.contentArea.width, Threshold.contentArea.height));

        int ctrContainer = 0;
        int width = 70;
        int height = 60;

        if(CurrentUser.getLikedSongs() != null) {
            ContainerPlaylist cp = new ContainerPlaylist();

            if(ctrContainer == 0) {
                cp.setBounds(70, height, 200, 250);
            } else {
                cp.setBounds(width, height, 200, 250);
            }

            cp.settitle("Liked Song");

            width += 230;
            ctrContainer++;
        }

        for(int i = 0; i < CurrentUser.getPlaylistsSize(); i++) {
            if(ctrContainer < 7) {
                ContainerPlaylist cp = new ContainerPlaylist();

                if(ctrContainer == 0) {
                    cp.setBounds(70, height, 200, 250);
                } else {
                    cp.setBounds(width, height, 200, 250);
                }

                width += 230;
                cp.settitle(CurrentUser.getPlaylists().get(i).getName());

                this.add(cp);

                ctrContainer++;
            }

            if(ctrContainer > 7) {
                ctrContainer = 0;
                height += 350;
            }
        }
    }
}
