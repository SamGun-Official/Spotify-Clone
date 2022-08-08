package com.spotify.ui.admin.form;


import com.spotify.app.Admin;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UpdateAlbumForm extends JPanel {
    private JComboBox ArtistName;
    private JComboBox AlbumName;
    private JLabel ArtisLabel;
    private JLabel AlbumLabel;
    private JLabel newAlbumLabel;
    private JButton UpdateButton;
    private JTextField newAlbumName;
    private JLabel Blank;

    public UpdateAlbumForm(Admin admin) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        String [] judul = new String[admin.getPlaylists().size()];
        if(admin.getPlaylists().size() > 0){
            for (int i = 0; i < admin.getPlaylists().size(); i++) {
                judul[i] = admin.getPlaylists().get(i).getName();
            }
            ArtistName = new JComboBox(admin.getArtists().toArray());
            AlbumName = new JComboBox(judul);
        }
        else{
            ArtistName = new JComboBox();
            AlbumName = new JComboBox();
        }
        ArtisLabel = new JLabel("CHOOSE ARTIST NAME");
        AlbumLabel = new JLabel("CHOOSE ALBUM");
        newAlbumLabel = new JLabel("NEW ALBUM NAME");
        Blank = new JLabel("");
        newAlbumName = new JTextField();
        UpdateButton = new JButton("UPDATE");
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        this.add(ArtisLabel);
        this.add(ArtistName);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(AlbumLabel);
        this.add(AlbumName);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(newAlbumLabel);
        this.add(newAlbumName);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(UpdateButton);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 50)));

        ArtisLabel.setPreferredSize(new Dimension(200,25));
        ArtistName.setPreferredSize(new Dimension(300,25));
        ArtisLabel.setFont(f1);
        ArtisLabel.setForeground(new Color(226,226,226));
        ArtistName.setPreferredSize(new Dimension(300,25));
        AlbumLabel.setPreferredSize(new Dimension(200,25));
        AlbumLabel.setFont(f1);
        AlbumLabel.setForeground(new Color(226,226,226));
        newAlbumLabel.setPreferredSize(new Dimension(200,25));
        newAlbumLabel.setFont(f1);
        newAlbumName.setPreferredSize(new Dimension(300,25));
        newAlbumLabel.setForeground(new Color(226,226,226));
        AlbumName.setPreferredSize(new Dimension(300,25));
        Blank.setPreferredSize(new Dimension(200,25));
        UpdateButton.setPreferredSize(new Dimension(125,25));
        UpdateButton.setFont(f1);
        UpdateButton.setBackground(Color.green);
        UpdateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                admin.getPlaylists().get(AlbumName.getSelectedIndex()).setName(newAlbumName.getText());
                JOptionPane.showMessageDialog(null,"Update Successful");
            }
        });
    }

    public JComboBox getArtistName() {
        return ArtistName;
    }

    public void setArtistName(JComboBox artistName) {
        ArtistName = artistName;
    }

    public JComboBox getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(JComboBox albumName) {
        AlbumName = albumName;
    }

    public JLabel getArtisLabel() {
        return ArtisLabel;
    }

    public void setArtisLabel(JLabel artisLabel) {
        ArtisLabel = artisLabel;
    }

    public JLabel getAlbumLabel() {
        return AlbumLabel;
    }

    public void setAlbumLabel(JLabel albumLabel) {
        AlbumLabel = albumLabel;
    }

    public JButton getUpdateButton() {
        return UpdateButton;
    }

    public void setUpdateButton(JButton updateButton) {
        UpdateButton = updateButton;
    }

    public JTextField getNewAlbumName() {
        return newAlbumName;
    }

    public void setNewAlbumName(JTextField newAlbumName) {
        this.newAlbumName = newAlbumName;
    }

    public JLabel getBlank() {
        return Blank;
    }

    public void setBlank(JLabel blank) {
        Blank = blank;
    }
}
