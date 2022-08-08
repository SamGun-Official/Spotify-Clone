package com.spotify.ui.admin.form;


import com.spotify.app.Admin;
import com.spotify.app.Artist;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CreateArtistForm extends JPanel {
    private JTextField ArtistName;
    private JTextField Artistdesc;
    private JLabel NameLabel;
    private JLabel DescLabel;
    private JLabel Blank;
    private JButton CreateButton;
    private JLabel VerifyLabel;
    private JCheckBox verifyArtist;

    public CreateArtistForm(Admin admin) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        NameLabel = new JLabel("ARTIST NAME");
        DescLabel = new JLabel("ARTIST DESC");
        VerifyLabel = new JLabel("VERIFY");
        verifyArtist = new JCheckBox();
        Blank = new JLabel("");
        ArtistName = new JTextField();
        Artistdesc = new JTextField();
        CreateButton = new JButton("CREATE");
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        this.add(NameLabel);
        this.add(ArtistName);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(DescLabel);
        this.add(Artistdesc);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(VerifyLabel);
        this.add(verifyArtist);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(CreateButton);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 50)));
        NameLabel.setPreferredSize(new Dimension(150,50));
        NameLabel.setFont(f1);
        NameLabel.setForeground(new Color(226,226,226));
        VerifyLabel.setPreferredSize(new Dimension(150,50));
        VerifyLabel.setFont(f1);
        VerifyLabel.setForeground(new Color(226,226,226));
        verifyArtist.setBorder(null);
        ArtistName.setPreferredSize(new Dimension(300,25));
        DescLabel.setPreferredSize(new Dimension(150,25));
        DescLabel.setFont(f1);
        DescLabel.setForeground(new Color(226,226,226));
        Blank.setPreferredSize(new Dimension(150,25));
        Artistdesc.setPreferredSize(new Dimension(300,25));
        CreateButton.setPreferredSize(new Dimension(125,25));
        CreateButton.setFont(f1);
        CreateButton.setBackground(Color.green);
        CreateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean kembar = false;
                for (int i = 0; i < admin.getArtists().size(); i++) {
                    if(admin.getArtists().get(i).getName().equals(ArtistName.getText())){
                        kembar = true;
                    }
                }
                if(kembar){
                    JOptionPane.showMessageDialog(null,"Already Registered");
                }
                else{
                    admin.getArtists().add(new Artist(ArtistName.getText(),verifyArtist.isSelected(),Artistdesc.getText()));
                    JOptionPane.showMessageDialog(null,"Success");
                }
            }
        });
    }

    public JLabel getVerifyLabel() {
        return VerifyLabel;
    }

    public void setVerifyLabel(JLabel verifyLabel) {
        VerifyLabel = verifyLabel;
    }

    public JCheckBox getVerifyArtist() {
        return verifyArtist;
    }

    public void setVerifyArtist(JCheckBox verifyArtist) {
        this.verifyArtist = verifyArtist;
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
}
