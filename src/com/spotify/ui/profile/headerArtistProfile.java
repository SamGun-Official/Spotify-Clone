package com.spotify.ui.profile;

import com.spotify.app.Artist;
import com.spotify.app.User;
import com.spotify.ui.Threshold;
import com.spotify.ui.graphics.RoundedPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class headerArtistProfile extends JPanel {
    private JLabel lbProfileName;
    private JLabel lbdescription;
    private JLabel lbProfile;
    private JLabel lbUserStatus;
    private RoundedPanel profilePicture;

    public headerArtistProfile(Artist CurrentArtist) {
        super();
        this.setBackground(new Color(76, 76, 76));
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Threshold.contentArea.width, 300));
        this.setBounds(0, 0, Threshold.contentArea.width, 300);

        profilePicture = new RoundedPanel(230, new Color(52, 52, 52));
        profilePicture.setBackground(new Color(76, 76, 76));
        profilePicture.setBounds(30, 50, 230, 230);
        this.add(profilePicture);

        lbProfileName=new JLabel(CurrentArtist.getName());
        lbProfileName.setBounds(280, 140, 1000, 85);
        lbProfileName.setFont(new Font("Gotham", Font.BOLD, 75));
        lbProfileName.setForeground(new Color(255, 255, 255));
        this.add(lbProfileName);

        lbProfile=new JLabel("PROFILE");
        lbProfile.setBounds(280, 110, 500, 25);
        lbProfile.setFont(new Font("Gotham", Font.BOLD, 15));
        lbProfile.setForeground(new Color(255, 255, 255));
        this.add(lbProfile);

        if(CurrentArtist.isVerified()) {
            lbUserStatus = new JLabel("Verified");
        } else {
            lbUserStatus = new JLabel("Not Verified");
        }

        lbUserStatus.setBounds(365, 110, 500, 25);
        lbUserStatus.setFont(new Font("Gotham", Font.BOLD, 15));
        lbUserStatus.setForeground(new Color(255, 255, 255));
        this.add(lbUserStatus);

        lbdescription = new JLabel(CurrentArtist.getDescription());
        lbdescription.setBounds(280, 230, 500, 25);
        lbdescription.setFont(new Font("Gotham", Font.BOLD, 15));
        lbdescription.setForeground(new Color(255, 255, 255));
        this.add(lbdescription);
    }
}
