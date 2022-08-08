package com.spotify.ui.admin.form;


import com.spotify.app.Admin;
import com.spotify.app.Playlist;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreateAlbumForm extends JPanel{
    private JLabel ColorLabel;
    private JTextField InputColor;
    private JLabel SongLabel;
    private JComboBox SongList;
    private JLabel TypeLabel;
    private JComboBox TypeList;
    private JLabel PlaylistName;
    private JTextField InputName;
    private JLabel DescLabel;
    private JTextField InputDesc;
    private JLabel ArtistLabel;
    private JComboBox ArtistList;
    private JButton Create;
    private JLabel Blank;

    public CreateAlbumForm(Admin admin) {
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        String [] Judul  = new String[admin.getSongs().size()];
        String [] Artis  = new String[admin.getArtists().size()];
        String [] Type = {"Playlist","Single","Album","EP"};
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        ColorLabel = new JLabel("CHOOSE BACKDROP COLOR");
        InputColor = new JTextField();
        SongLabel = new JLabel("CHOOSE SONG");
        if(admin.getArtists().size() >0 && admin.getSongs().size()>0){
            for (int i = 0; i < admin.getArtists().size(); i++) {
                SongList = new JComboBox(admin.getSongs().toArray());
                SongList.setSelectedIndex(i);
                Artis[i] = admin.getArtists().get(i).getName();
            }
            for (int i = 0; i < admin.getSongs().size(); i++) {
                Judul[i] = admin.getSongs().get(i).getTitle();
            }
            ArtistList = new JComboBox(Artis);
            SongList = new JComboBox(Judul);
        }
        else{
            ArtistList = new JComboBox();
            SongList = new JComboBox();
        }
        SongList = new JComboBox(Judul);
        TypeLabel = new JLabel("CHOOSE TYPE");
        TypeList = new JComboBox(Type);
        PlaylistName = new JLabel("PLAYLIST NAME");
        InputName = new JTextField();
        DescLabel = new JLabel("PLAYLIST DESC");
        InputDesc = new JTextField();
        Create = new JButton("CREATE");
        Create.setBackground(Color.green);
        ArtistLabel = new JLabel("OWNER");
        ArtistList = new JComboBox(Artis);
        Blank = new JLabel();
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        this.add(ColorLabel);
        this.add(InputColor);
        ColorLabel.setFont(f1);
        ColorLabel.setPreferredSize(new Dimension(250,25));
        InputColor.setPreferredSize(new Dimension(300,25));
        ColorLabel.setForeground(new Color(226,226,226));
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(SongLabel);
        this.add(SongList);
        SongLabel.setFont(f1);
        SongLabel.setPreferredSize(new Dimension(250,25));
        SongList.setPreferredSize(new Dimension(300,25));
        SongLabel.setForeground(new Color(226,226,226));
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(TypeLabel);
        this.add(TypeList);
        TypeLabel.setFont(f1);
        TypeLabel.setPreferredSize(new Dimension(250,25));
        TypeList.setPreferredSize(new Dimension(300,25));
        TypeLabel.setForeground(new Color(226,226,226));
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(PlaylistName);
        this.add(InputName);
        PlaylistName.setFont(f1);
        PlaylistName.setPreferredSize(new Dimension(250,25));
        InputName.setPreferredSize(new Dimension(300,25));
        PlaylistName.setForeground(new Color(226,226,226));
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(DescLabel);
        this.add(InputDesc);
        DescLabel.setFont(f1);
        DescLabel.setPreferredSize(new Dimension(250,25));
        InputDesc.setPreferredSize(new Dimension(300,25));
        DescLabel.setForeground(new Color(226,226,226));
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(ArtistLabel);
        this.add(ArtistList);
        ArtistLabel.setFont(f1);
        ArtistLabel.setPreferredSize(new Dimension(250,25));
        ArtistList.setPreferredSize(new Dimension(300,25));
        ArtistLabel.setForeground(new Color(226,226,226));
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        Blank.setPreferredSize(new Dimension(250,25));
        this.add(Blank);
        this.add(Create);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 50)));
        Create.setPreferredSize(new Dimension(125,25));
        /* Constructor Error */
//        Create.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println(TypeList.getSelectedItem().toString());
//                admin.getPlaylists().add(new Playlist(InputColor.getCaretColor(), new ArrayList<>(), TypeList.getSelectedItem().toString(),
//                        InputName.getText(),InputDesc.getText(),ArtistList.getSelectedItem().toString(),formatter.format(date),"C:\\Users\\user\\Desktop\\Kuliah\\SEM 2\\PROYEK\\GABUNGAN\\src\\com\\spotify\\assets\\ads",0,
//                        0));
//                System.out.println(admin.getPlaylists().get(ArtistList.getSelectedIndex()).getName());
//                JOptionPane.showMessageDialog(null,"Create Successful");
//            }
//        });
    }


}
