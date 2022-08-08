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

import static javax.swing.JOptionPane.showMessageDialog;

public class VerifiyUserForm extends JPanel {
    private JComboBox SelectUser;
    private JButton Verify;
    private JLabel Blank;
    private JLabel ChooseUser;

    public VerifiyUserForm(Admin admin, ArrayList<Account> accounts) {
        ArrayList<User> u = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++) {
            if(accounts.get(i) instanceof User ){
                User user = (User) accounts.get(i);
                if(user.isRequestVerification()){
                    u.add(user);
                }
            }
        }
        String [] isiuser = new String[accounts.size()];
        for (int i = 0; i < accounts.size(); i++) {
            isiuser[i] = String.valueOf(accounts.get(i).getUsername());
        }
        SelectUser = new JComboBox(isiuser);
        this.setLayout(new FlowLayout(FlowLayout.LEFT,30,35));
        this.setPreferredSize(new Dimension((int) ASSET.content.getWidth(),(int)ASSET.content.getHeight()));
        this.setBackground(new Color(18, 18, 18));
        ChooseUser = new JLabel("USER");
        Blank = new JLabel("");
        Verify = new JButton("VERIFY");
        initializeComboBox(accounts);
        Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
        this.add(ChooseUser);
        this.add(SelectUser);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
        this.add(Blank);
        this.add(Verify);
        this.add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 50)));
        ChooseUser.setFont(f1);
        ChooseUser.setForeground(new Color(226,226,226));
        ChooseUser.setPreferredSize(new Dimension(150,25));
        Blank.setPreferredSize(new Dimension(150,25));
        Verify.setPreferredSize(new Dimension(125,25));
        Verify.setFont(f1);
        Verify.setBackground(Color.green);
        Verify.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (accounts.get(SelectUser.getSelectedIndex() + 1).getUsername().equals(SelectUser.getSelectedItem())) {
                    User user = (User) accounts.get(SelectUser.getSelectedIndex());
                    user.verificationMembershipResponse();
                    JOptionPane.showMessageDialog(null,"Verify Successful");
                }
                else {
                    showMessageDialog(null,"Verify Failed");
                }
                initializeComboBox(accounts);
                getThis().removeAll();
                getThis().add(ChooseUser);
                getThis().add(SelectUser);
                getThis().add(Box.createRigidArea(new Dimension((int)ASSET.content.getWidth()-100 , 0)));
                getThis().add(Blank);
                getThis().add(Verify);
                getThis().repaint();
                getThis().revalidate();
            }
        });
    }
    public VerifiyUserForm getThis() {
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
