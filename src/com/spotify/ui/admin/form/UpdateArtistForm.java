package com.spotify.ui.admin.form;


import com.spotify.app.Admin;
import com.spotify.app.Song;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UpdateArtistForm extends JPanel {
    private JTextField ArtistName;
    private JTextField Artistdesc;
    private JLabel ChooseName;
    private JLabel NameLabel;
    private JLabel DescLabel;
    private JComboBox DropdownName;
    private JLabel Blank;
    private JButton CreateButton;

    public UpdateArtistForm(Admin admin) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        if(admin.getArtists().size()>0){
            for (int i = 0; i < admin.getArtists().size(); i++) {
                DropdownName = new JComboBox(admin.getArtists().toArray());
                DropdownName.setSelectedIndex(i);
            }
        }
        else{
            DropdownName = new JComboBox();
        }
        ChooseName = new JLabel("CHOOSE ARTIST NAME");
        NameLabel = new JLabel("NEW ARTIST NAME");
        DescLabel = new JLabel("NEW ARTIST DESC");
        Blank = new JLabel("");
        ArtistName = new JTextField();
        Artistdesc = new JTextField();
        CreateButton = new JButton("UPDATE");
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        this.add(ChooseName);
        this.add(DropdownName);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(NameLabel);
        this.add(ArtistName);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(DescLabel);
        this.add(Artistdesc);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(CreateButton);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 50)));

        ChooseName.setPreferredSize(new Dimension(200,25));
        DropdownName.setPreferredSize(new Dimension(300,25));
        ChooseName.setFont(f1);
        ChooseName.setForeground(new Color(226,226,226));
        NameLabel.setPreferredSize(new Dimension(200,50));
        NameLabel.setFont(f1);
        NameLabel.setForeground(new Color(226,226,226));
        ArtistName.setPreferredSize(new Dimension(300,25));
        DescLabel.setPreferredSize(new Dimension(200,25));
        DescLabel.setFont(f1);
        DescLabel.setForeground(new Color(226,226,226));
        Blank.setPreferredSize(new Dimension(200,25));
        Artistdesc.setPreferredSize(new Dimension(300,25));
        CreateButton.setPreferredSize(new Dimension(125,25));
        CreateButton.setFont(f1);
        CreateButton.setBackground(Color.green);
        CreateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                admin.getArtists().get(DropdownName.getSelectedIndex()).setName(ArtistName.getText());
                admin.getArtists().get(DropdownName.getSelectedIndex()).setName(Artistdesc.getText());
                JOptionPane.showMessageDialog(null,"Update Success");
            }
        });
    }

    public JTextField getArtistName() {
        return ArtistName;
    }

    public void setArtistName(JTextField artistName) {
        ArtistName = artistName;
    }

    public JTextField getArtistdesc() {
        return Artistdesc;
    }

    public void setArtistdesc(JTextField artistdesc) {
        Artistdesc = artistdesc;
    }

    public JLabel getNameLabel() {
        return NameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        NameLabel = nameLabel;
    }

    public JLabel getDescLabel() {
        return DescLabel;
    }

    public void setDescLabel(JLabel descLabel) {
        DescLabel = descLabel;
    }

    public JButton getCreateButton() {
        return CreateButton;
    }

    public void setCreateButton(JButton createButton) {
        CreateButton = createButton;
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

    public JComboBox getDropdownName() {
        return DropdownName;
    }

    public void setDropdownName(JComboBox dropdownName) {
        DropdownName = dropdownName;
    }
}
