package com.spotify.ui.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavbarButtonAdmin extends JButton {
    boolean isActive;
    public NavbarButtonAdmin(String text, Dimension size, JFrame frame, String filepath) {
        super(text);
        this.addActionListener((ActionListener) frame);
        this.isActive = false;
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setHorizontalTextPosition(JLabel.RIGHT);
        this.setFocusable(false);
        this.setOpaque(true);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setPreferredSize(size);
        this.setIconTextGap(20);
        this.setIcon(WindowAdmin.resizeIcon(filepath, 20, 20));
        this.setBorder(new EmptyBorder(0,10,0,0));
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setForeground(Color.white);
        this.setFont(new Font("Gotham", Font.PLAIN, 18));
        this.setBackground(new Color(0, 0, 0));
    }

    public void clicked(ActionEvent e) {
        if (e.getSource() == this) {
            if (this.isActive) {
                this.setBackground(new Color(0, 0, 0));
                setOpaque(false);
            }
            else {
                this.setBackground(new Color(40, 40, 40));
                setOpaque(true);
            }
            this.isActive = !this.isActive;
        }
        else {
            setOpaque(true);
            this.setBackground(new Color(0, 0, 0));
            this.isActive = false;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
