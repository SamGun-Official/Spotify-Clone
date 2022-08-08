package com.spotify.ui.admin.form;

import com.spotify.app.Admin;
import com.spotify.app.Artist;
import com.spotify.app.Song;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class CreateSongForm extends JPanel {
    private JTextField SongTitle;
    private JTextField DateCreated;
    private JComboBox ArtistName;
    private JLabel TitleLabel;
    private JLabel Blank;
    private JLabel DateLabel;
    private JLabel ArtistLabel;
    private JButton CreateButton;

    public CreateSongForm(Admin admin) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        SongTitle = new JTextField();
        DateCreated = new JTextField();
        TitleLabel = new JLabel("SONG TITLE");
        DateLabel = new JLabel("DATE CREATED");
        ArtistLabel = new JLabel("ARTIST NAME");
        Blank = new JLabel("");
        if(admin.getArtists().size() >0){
            for (int i = 0; i < admin.getArtists().size(); i++) {
                ArtistName = new JComboBox(admin.getArtists().toArray());
                ArtistName.setSelectedIndex(i);
            }
        }
        else{
            ArtistName = new JComboBox();
        }
        CreateButton = new JButton("CREATE");
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        this.add(TitleLabel);
        this.add(SongTitle);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(ArtistLabel);
        this.add(ArtistName);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(DateLabel);
        this.add(DateCreated);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(CreateButton);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 50)));
        TitleLabel.setFont(f1);
        ArtistLabel.setFont(f1);
        DateLabel.setFont(f1);
        TitleLabel.setPreferredSize(new Dimension(150,25));
        TitleLabel.setForeground(new Color(226,226,226));
        SongTitle.setPreferredSize(new Dimension(300,25));
        ArtistName.setPreferredSize(new Dimension(300,25));
        DateLabel.setPreferredSize(new Dimension(150,25));
        DateLabel.setForeground(new Color(226,226,226));
        ArtistLabel.setPreferredSize(new Dimension(150,25));
        ArtistLabel.setForeground(new Color(226,226,226));
        DateCreated.setPreferredSize(new Dimension(300,25));
        Blank.setPreferredSize(new Dimension(150,25));
        CreateButton.setPreferredSize(new Dimension(125,25));
        CreateButton.setFont(f1);
        CreateButton.setBackground(Color.green);
        CreateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean kembar = false;
                for (int i = 0; i < admin.getSongs().size(); i++) {
                    if(admin.getSongs().get(i).getTitle().equals(SongTitle.getText()) && admin.getSongs().get(i).getArtist().getName().equals(ArtistName.getSelectedItem())){
                        kembar = true;
                    }
                }
                if(kembar){
                    JOptionPane.showMessageDialog(null,"Same Song");
                }
                else{
                    /*
                    PARAM
                        String title, String playlistName, String dateAdded, Artist artist, Genre genre,
                        int duration, int totalPlays, int totalLikes, String imagePath, String audioFile
                    */
                    admin.getSongs().add(new Song(
                        SongTitle.getText(), "", DateCreated.getText(),
                        admin.getArtists().get(ArtistName.getSelectedIndex()),
                        null, 0, 0, 0, null, null
                    ));
                    JOptionPane.showMessageDialog(null,"Success");
                }
            }
        });
    }

    public JTextField getSongTitle() {
        return SongTitle;
    }

    public void setSongTitle(JTextField songTitle) {
        SongTitle = songTitle;
    }

    public JComboBox getArtistName() {
        return ArtistName;
    }

    public void setArtistName(JComboBox artistName) {
        ArtistName = artistName;
    }

    public JLabel getTitleLabel() {
        return TitleLabel;
    }

    public void setTitleLabel(JLabel titleLabel) {
        TitleLabel = titleLabel;
    }

    public JLabel getBlank() {
        return Blank;
    }

    public void setBlank(JLabel blank) {
        Blank = blank;
    }

    public JButton getCreateButton() {
        return CreateButton;
    }

    public void setCreateButton(JButton createButton) {
        CreateButton = createButton;
    }

    public JTextField getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(JTextField dateCreated) {
        DateCreated = dateCreated;
    }

    public JLabel getDateLabel() {
        return DateLabel;
    }

    public void setDateLabel(JLabel dateLabel) {
        DateLabel = dateLabel;
    }

    public JLabel getArtistLabel() {
        return ArtistLabel;
    }

    public void setArtistLabel(JLabel artistLabel) {
        ArtistLabel = artistLabel;
    }
}
