package com.spotify.ui.admin.form;


import com.spotify.app.Admin;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeleteArtistForm extends JPanel{
    private JComboBox SelectArtist;
    private JButton Delete;
    private JLabel Blank;
    private JLabel ChooseName;

    public DeleteArtistForm(Admin admin) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        if(admin.getArtists().size() >0){
            for (int i = 0; i < admin.getArtists().size(); i++) {
                SelectArtist = new JComboBox(admin.getArtists().toArray());
                SelectArtist.setSelectedIndex(i);
            }
        }
        else{
            SelectArtist = new JComboBox();
        }
        Delete = new JButton("DELETE");
        ChooseName = new JLabel("SELECT ARTIST");
        Blank = new JLabel("");
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        this.add(ChooseName);
        this.add(SelectArtist);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(Delete);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 50)));
        ChooseName.setPreferredSize(new Dimension(200,25));
        SelectArtist.setPreferredSize(new Dimension(300,25));
        Blank.setPreferredSize(new Dimension(200,25));
        Delete.setPreferredSize(new Dimension(125,25));
        Delete.setFont(f1);
        ChooseName.setForeground(new Color(226,226,226));
        Delete.setBackground(Color.red);
        ChooseName.setFont(f1);
        ChooseName.setBackground(Color.white);
        Delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //SelectArtist.getSelectedIndex();
                admin.getArtists().remove(SelectArtist.getSelectedIndex());
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
}
