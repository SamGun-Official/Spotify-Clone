package com.spotify.ui.admin.form;

import com.spotify.app.Account;
import com.spotify.app.Admin;
import com.spotify.app.User;
import com.spotify.ui.admin.ASSET;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DeleteUserForm extends JPanel {
    private JComboBox SelectUser;
    private JButton Delete;
    private JLabel Blank;
    private JLabel ChooseUser;

    public DeleteUserForm(Admin admin, ArrayList<Account> accounts) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        ChooseUser = new JLabel("USER");
        Blank = new JLabel("");
        Delete = new JButton("DELETE");
        initializeComboBox(accounts);
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        this.add(ChooseUser);
        this.add(SelectUser);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(Delete);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 50)));
        ChooseUser.setFont(f1);
        ChooseUser.setForeground(new Color(226,226,226));
        Blank.setPreferredSize(new Dimension(150,25));
        Delete.setPreferredSize(new Dimension(125,25));
        ChooseUser.setPreferredSize(new Dimension(150,25));
        Delete.setFont(f1);
        Delete.setBackground(Color.red);
        Delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (accounts.get(SelectUser.getSelectedIndex() + 1).getUsername().equals(SelectUser.getSelectedItem())) {
                    accounts.remove(SelectUser.getSelectedIndex() + 1);
                    JOptionPane.showMessageDialog(null,"Delete Successful");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Delete Failed");
                }
                initializeComboBox(accounts);
                getThis().removeAll();
                getThis().add(ChooseUser);
                getThis().add(SelectUser);
                getThis().add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
                getThis().add(Blank);
                getThis().add(Delete);
                getThis().add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 50)));
                getThis().repaint();
                getThis().revalidate();
            }
        });
    }
    public DeleteUserForm getThis() {
        return this;
    }

    public void initializeComboBox(ArrayList<Account> accounts) {
        String[] usernames = new String[accounts.size() - 1];
        int ctr = 0;
        for (Account account : accounts) {
            if (account instanceof User) {
                usernames[ctr++] = String.valueOf(account.getUsername());
            }
        }
        if (accounts.size() > 1) {
            SelectUser = new JComboBox(usernames);
        }
        else {
            SelectUser = new JComboBox();
        }
        SelectUser.setPreferredSize(new Dimension(300,25));
    }
}
