package com.spotify.ui.admin.form;

import com.spotify.app.Admin;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class UpdateSongForm extends JPanel {
    private JLabel ChooseArtist;
    private JLabel ChooseSong;
    private JLabel NewSongLabel;
    private JLabel Blank;
    private JComboBox ArtistName;
    private JComboBox Song;
    private JTextField InputNewTItle;
    private JButton UpdateButton;

    public UpdateSongForm(Admin admin) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        String Judul [] = new String[admin.getSongs().size()];
        Blank = new JLabel("");
        if(admin.getArtists().size() >0 && admin.getSongs().size()>0){
            for (int i = 0; i < admin.getArtists().size(); i++) {
                ArtistName = new JComboBox(admin.getArtists().toArray());
                ArtistName.setSelectedIndex(i);
            }
            for (int i = 0; i < admin.getSongs().size(); i++) {
                Judul[i] = admin.getSongs().get(i).getTitle();
            }
            Song = new JComboBox(Judul);
        }
        else{
            Song = new JComboBox();
            ArtistName = new JComboBox();
        }
        UpdateButton = new JButton("UPDATE");
        ChooseArtist = new JLabel("CHOOSE ARTIST");
        ChooseSong = new JLabel("CHOOSE SONG");
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        ChooseArtist.setFont(f1);
        ChooseSong.setFont(f1);
        ChooseSong.setForeground(new Color(226,226,226));
        ChooseArtist.setForeground(new Color(226,226,226));
        NewSongLabel = new JLabel("NEW SONG TITLE");
        InputNewTItle = new JTextField();
        this.add(ChooseArtist);
        this.add(ArtistName);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(ChooseSong);
        this.add(Song);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(NewSongLabel);
        this.add(InputNewTItle);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(UpdateButton);
        ArtistName.setPreferredSize(new Dimension(300,25));
        InputNewTItle.setPreferredSize(new Dimension(300,25));
        NewSongLabel.setPreferredSize(new Dimension(200,50));
        NewSongLabel.setFont(f1);
        NewSongLabel.setForeground(new Color(224,224,224));

        Song.setPreferredSize(new Dimension(300,25));
        ChooseArtist.setPreferredSize(new Dimension(200,50));
        ChooseSong.setPreferredSize(new Dimension(200,50));
        Blank.setPreferredSize(new Dimension(200,50));
        UpdateButton.setPreferredSize(new Dimension(125,25));
        UpdateButton.setFont(f1);
        UpdateButton.setBackground(Color.green);
        UpdateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                admin.getSongs().get(Song.getSelectedIndex()).setTitle(InputNewTItle.getText());
                JOptionPane.showMessageDialog(null,"Update Successful");
            }
        });
    }

    public JLabel getChooseArtist() {
        return ChooseArtist;
    }

    public void setChooseArtist(JLabel chooseArtist) {
        ChooseArtist = chooseArtist;
    }

    public JLabel getChooseSong() {
        return ChooseSong;
    }

    public void setChooseSong(JLabel chooseSong) {
        ChooseSong = chooseSong;
    }

    public JLabel getNewSongLabel() {
        return NewSongLabel;
    }

    public void setNewSongLabel(JLabel newSongLabel) {
        NewSongLabel = newSongLabel;
    }

    public JLabel getBlank() {
        return Blank;
    }

    public void setBlank(JLabel blank) {
        Blank = blank;
    }

    public JComboBox getArtistName() {
        return ArtistName;
    }

    public void setArtistName(JComboBox artistName) {
        ArtistName = artistName;
    }

    public JComboBox getSong() {
        return Song;
    }

    public void setSong(JComboBox song) {
        Song = song;
    }

    public JTextField getInputNewTItle() {
        return InputNewTItle;
    }

    public void setInputNewTItle(JTextField inputNewTItle) {
        InputNewTItle = inputNewTItle;
    }

    public JButton getUpdateButton() {
        return UpdateButton;
    }

    public void setUpdateButton(JButton updateButton) {
        UpdateButton = updateButton;
    }
}
