package com.spotify.ui.profile;

import com.spotify.app.User;
import com.spotify.ui.Threshold;
import com.spotify.ui.graphics.RoundedPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class headerProfile extends JPanel {
    private JLabel lbProfileName;
    private JLabel lbFollowers;
    private JLabel lbFollowing;
    private JLabel lbProfile;
    private JLabel lbUserStatus;
    private RoundedPanel profilePicture;

    public headerProfile(User CurrentUser) {
        super();
        this.setBackground(new Color(76, 76, 76));
        this.setBounds(0, 0, Threshold.contentArea.width, 300);
        this.setLayout(null);
        this.setPreferredSize(new Dimension(Threshold.contentArea.width, 300));

        profilePicture = new RoundedPanel(230, new Color(52, 52, 52));
        profilePicture.setBackground(new Color(76, 76, 76));
        profilePicture.setBounds(30, 50, 230, 230);
        this.add(profilePicture);

        lbProfileName = new JLabel(CurrentUser.getFirstName()+" "+CurrentUser.getLastName());
        lbProfileName.setBounds(280, 140, 1000, 85);
        lbProfileName.setFont(new Font("Gotham", Font.BOLD, 75));
        lbProfileName.setForeground(new Color(255, 255, 255));
        this.add(lbProfileName);

        lbProfile = new JLabel("PROFILE");
        lbProfile.setFont(new Font("Gotham", Font.BOLD, 15));
        lbProfile.setBounds(280, 110, 500, 25);
        lbProfile.setForeground(new Color(255, 255, 255));
        this.add(lbProfile);

        if(CurrentUser.isPremium()) {
            lbUserStatus = new JLabel("Spotify Premium");
        } else {
            lbUserStatus = new JLabel("Spotify Free");
        }

        lbUserStatus.setBounds(365, 110, 500, 25);
        lbUserStatus.setFont(new Font("Gotham", Font.BOLD, 15));
        lbUserStatus.setForeground(new Color(255, 255, 255));
        this.add(lbUserStatus);

        lbFollowing = new JLabel(CurrentUser.getFollowingSize() + " Following");
        lbFollowing.setBounds(280, 230, 500, 25);
        lbFollowing.setFont(new Font("Gotham", Font.BOLD, 15));
        lbFollowing.setForeground(new Color(255, 255, 255));
        this.add(lbFollowing);

        lbFollowers=new JLabel(CurrentUser.getFollowerSize()+" Followers");
        lbFollowers.setBounds(375, 230, 500, 25);
        lbFollowers.setFont(new Font("Gotham", Font.BOLD, 15));
        lbFollowers.setForeground(new Color(255, 255, 255));
        this.add(lbFollowers);
    }
}
