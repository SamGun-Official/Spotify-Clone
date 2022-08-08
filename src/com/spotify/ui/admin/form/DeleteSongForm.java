package com.spotify.ui.admin.form;


import com.spotify.app.Admin;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DeleteSongForm extends JPanel{
    private JComboBox SelectArtist;
    private JComboBox SelectSong;
    private JButton Delete;
    private JLabel Blank;
    private JLabel ChooseName;
    private JLabel ChooseTitle;

    public DeleteSongForm(Admin admin) {
        String Judul [] = new String[admin.getSongs().size()];
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        if(admin.getArtists().size() >0 && admin.getSongs().size()>0){
            for (int i = 0; i < admin.getArtists().size(); i++) {
                SelectArtist = new JComboBox(admin.getArtists().toArray());
                SelectArtist.setSelectedIndex(i);
            }
            for (int i = 0; i < admin.getSongs().size(); i++) {
                Judul[i] = admin.getSongs().get(i).getTitle();
            }
            SelectSong = new JComboBox(Judul);
        }
        else{
            SelectSong = new JComboBox();
            SelectArtist = new JComboBox();
        }
        ChooseName = new JLabel("ARTIST NAME");
        ChooseTitle = new JLabel("SONG TITLE");
        Blank = new JLabel("");
        Delete = new JButton("DELETE");
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        this.add(ChooseName);
        this.add(SelectArtist);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(ChooseTitle);
        this.add(SelectSong);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(Delete);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 50)));
        ChooseName.setFont(f1);
        ChooseName.setForeground(new Color(226,226,226));
        ChooseTitle.setFont(f1);
        ChooseTitle.setForeground(new Color(226,226,226));
        ChooseName.setPreferredSize(new Dimension(150,25));
        SelectArtist.setPreferredSize(new Dimension(300,25));
        SelectSong.setPreferredSize(new Dimension(300,25));
        ChooseTitle.setPreferredSize(new Dimension(150,25));
        Blank.setPreferredSize(new Dimension(150,25));
        Delete.setPreferredSize(new Dimension(125,25));
        Delete.setFont(f1);
        Delete.setBackground(Color.red);
        Delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                admin.getSongs().remove(SelectSong.getSelectedIndex());
                JOptionPane.showMessageDialog(null,"Delete Successful");
            }
        });
    }

    public JComboBox getSelectArtist() {
        return SelectArtist;
    }

    public void setSelectArtist(JComboBox selectArtist) {
        SelectArtist = selectArtist;
    }

    public JButton getDelete() {
        return Delete;
    }

    public void setDelete(JButton delete) {
        Delete = delete;
    }

    public JLabel getBlank() {
        return Blank;
    }

    public void setBlank(JLabel blank) {
        Blank = blank;
    }

    public JLabel getChooseName() {
        return ChooseName;
    }

    public void setChooseName(JLabel chooseName) {
        ChooseName = chooseName;
    }

    public JComboBox getSelectSong() {
        return SelectSong;
    }

    public void setSelectSong(JComboBox selectSong) {
        SelectSong = selectSong;
    }
}
