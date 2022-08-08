package com.spotify.ui.admin.form;

import com.spotify.app.Admin;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeleteAlbumForm extends JPanel {
    private JLabel ArtisLabel;
    private JLabel AlbumLabel;
    private JComboBox ArtistName;
    private JComboBox AlbumName;
    private JButton DeleteButton;
    private JLabel Blank;

    public DeleteAlbumForm(Admin admin) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(), (int) ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        String [] pl = new String[admin.getPlaylists().size()];
        if(admin.getArtists().size() >0 && admin.getSongs().size()>0){
            for (int i = 0; i < admin.getPlaylists().size(); i++) {
                AlbumName = new JComboBox(admin.getPlaylists().toArray());
                AlbumName.setSelectedIndex(i);
                pl[i] = admin.getPlaylists().get(i).getName();
            }
            AlbumName = new JComboBox(pl);
        }
        else{
            ArtistName = new JComboBox();
            AlbumName = new JComboBox();
        }
        ArtistName = new JComboBox(admin.getArtists().toArray());
        AlbumName = new JComboBox(pl);
        ArtisLabel = new JLabel("CHOOSE ARTIST NAME");
        AlbumLabel = new JLabel("CHOOSE ALBUM");
        Blank = new JLabel("");
        DeleteButton = new JButton("DELETE");
        Font f1 = new Font(Font.SANS_SERIF, Font.BOLD, 15);
        this.add(ArtisLabel);
        this.add(ArtistName);
        this.add(Box.createRigidArea(new Dimension((int) ASSET.content.getWidth() - 100, 0)));
        this.add(AlbumLabel);
        this.add(AlbumName);
        this.add(Box.createRigidArea(new Dimension((int) ASSET.content.getWidth() - 100, 0)));
        this.add(Blank);
        this.add(DeleteButton);
        this.add(Box.createRigidArea(new Dimension((int) ASSET.content.getWidth() - 100, 50)));

        ArtisLabel.setPreferredSize(new Dimension(200, 25));
        ArtistName.setPreferredSize(new Dimension(300, 25));
        ArtisLabel.setFont(f1);
        ArtisLabel.setForeground(new Color(226, 226, 226));
        ArtistName.setPreferredSize(new Dimension(300, 25));
        AlbumLabel.setPreferredSize(new Dimension(200, 25));
        AlbumLabel.setFont(f1);
        AlbumLabel.setForeground(new Color(226, 226, 226));
        AlbumName.setPreferredSize(new Dimension(300, 25));
        Blank.setPreferredSize(new Dimension(200, 25));
        DeleteButton.setPreferredSize(new Dimension(125, 25));
        DeleteButton.setFont(f1);
        DeleteButton.setBackground(Color.red);
        DeleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                admin.getPlaylists().remove(AlbumName.getSelectedIndex());
            }
        });
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

    public JButton getDeleteButton() {
        return DeleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        DeleteButton = deleteButton;
    }

    public JLabel getBlank() {
        return Blank;
    }

    public void setBlank(JLabel blank) {
        Blank = blank;
    }
}
