package com.spotify.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NavbarButton extends JButton implements com.spotify.app.Utility {
    boolean isActive;

    private ImageIcon iconDefault;
    private ImageIcon iconHover;
    private ImageIcon iconActive;

    private Color colorForegroundActive;
    private Color colorForegroundDefault;
    private Color colorBackgroundActive;
    private Color colorBackgroundDefault;

    public NavbarButton(String text, Dimension size, String iconDefault, String iconHover, String iconActive,
    JPanel panel) {
        JLabel btnText = new JLabel(text);
        btnText.setBorder(new EmptyBorder(0, 24 + 16, 1, 0));
        btnText.setFont(new Font("Gotham", Font.BOLD, 14));
        btnText.setForeground(colorForegroundDefault);

        this.isActive = false;

        this.iconDefault = scaleImageIcon(iconDefault, 24, 24);
        this.iconHover = scaleImageIcon(iconHover, 24, 24);
        this.iconActive = scaleImageIcon(iconActive, 24, 24);

        this.colorForegroundActive = Color.white;
        this.colorForegroundDefault = new Color(179, 179, 179);
        this.colorBackgroundActive = new Color(40, 40, 40);
        this.colorBackgroundDefault = new Color(0, 0, 0);

        this.setBackground(colorBackgroundDefault);
        this.setBorder(new EmptyBorder(0, 16, 0, 16));
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setFocusable(false);
        this.setFocusPainted(false);
        this.setFont(new Font("Gotham", Font.BOLD, 14));
        this.setForeground(colorForegroundDefault);
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setIcon(this.iconDefault);
        this.setOpaque(true);
        this.setPreferredSize(size);
        this.setSize(size);
        this.setVerticalTextPosition(SwingConstants.CENTER);
        this.add(btnText);
        this.addActionListener((ActionListener)panel);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(!getThis().isActive) {
                    getThis().setIcon(getThis().iconHover);
                    getThis().setForeground(colorForegroundActive);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if(!getThis().isActive) {
                    getThis().setIcon(getThis().iconDefault);
                    getThis().setForeground(colorForegroundDefault);
                }
            }
        });
    }

    public void clicked(ActionEvent e) {
        if(e.getSource() == this) {
            if(!this.isActive) {
                setOpaque(true);
                this.setBackground(colorBackgroundActive);
                this.setForeground(colorForegroundActive);
                this.isActive = !this.isActive;
                this.setIcon(iconActive);
            }
        } else {
            setOpaque(true);
            this.setBackground(colorBackgroundDefault);
            this.setForeground(colorForegroundDefault);
            this.isActive = false;
            this.setIcon(iconDefault);
        }
    }

//    public void clicked(ActionEvent e) {
//        if(e.getSource() == this) {
//            if(this.isActive) {
//                this.setBackground(new Color(0, 0, 0));
//                setOpaque(false);
//            } else {
//                this.setBackground(new Color(40, 40, 40));
//                setOpaque(true);
//                this.isActive = !this.isActive;
//            }
//        } else {
//            setOpaque(true);
//            this.setBackground(new Color(0, 0, 0));
//            this.isActive = false;
//        }
//    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public NavbarButton getThis() {
        return this;
    }

    public ImageIcon getIconDefault() {
        return iconDefault;
    }

    public void setIconDefault(ImageIcon iconDefault) {
        this.iconDefault = iconDefault;
    }

    public ImageIcon getIconHover() {
        return iconHover;
    }

    public void setIconHover(ImageIcon iconHover) {
        this.iconHover = iconHover;
    }

    public ImageIcon getIconActive() {
        return iconActive;
    }

    public void setIconActive(ImageIcon iconActive) {
        this.iconActive = iconActive;
    }

    public Color getColorForegroundActive() {
        return colorForegroundActive;
    }

    public void setColorForegroundActive(Color colorForegroundActive) {
        this.colorForegroundActive = colorForegroundActive;
    }

    public Color getColorForegroundDefault() {
        return colorForegroundDefault;
    }

    public void setColorForegroundDefault(Color colorForegroundDefault) {
        this.colorForegroundDefault = colorForegroundDefault;
    }

    public Color getColorBackgroundActive() {
        return colorBackgroundActive;
    }

    public void setColorBackgroundActive(Color colorBackgroundActive) {
        this.colorBackgroundActive = colorBackgroundActive;
    }

    public Color getColorBackgroundDefault() {
        return colorBackgroundDefault;
    }

    public void setColorBackgroundDefault(Color colorBackgroundDefault) {
        this.colorBackgroundDefault = colorBackgroundDefault;
    }
}
