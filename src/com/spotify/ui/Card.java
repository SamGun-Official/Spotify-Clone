package com.spotify.ui;


import com.spotify.app.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Card extends JPanel implements Utility {
    JButton image;
    JButton title;
    JButton subtitle;

    public Card(String title, String subtitle, String imagePath) {
        super();
        this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        this.setPreferredSize(new Dimension(150, 250));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(new Color(24, 24, 24));

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                getThis().setBackground(new Color(30,30,30));
                getThis().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                getThis().setBackground(new Color(24,24,24));
                getThis().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        };
        this.addMouseListener(mouseListener);

        //width sisa : 130
        //height sisa : 230
        this.image = new JButton(scaleImageIcon(imagePath, 130, 180));
        this.image.setSize(new Dimension(130, 180));
        this.image.setPreferredSize(image.getSize());
        Window.initializeButton(image);
        this.image.setContentAreaFilled(false);
        this.image.addMouseListener(mouseListener);

        this.title = new JButton(title);
        Window.initializeButton(this.title);
        this.title.setToolTipText(title);
        this.title.setSize(new Dimension(130, 30));
        this.title.setPreferredSize(this.title.getSize());
        this.title.setForeground(new Color(255, 255, 255));
        this.title.setBackground(null);
        this.title.addMouseListener(mouseListener);

        this.subtitle = new JButton(subtitle);
        Window.initializeButton(this.subtitle);
        this.subtitle.setToolTipText(subtitle);
        this.subtitle.setSize(new Dimension(130, 20));
        this.subtitle.setPreferredSize(this.subtitle.getSize());
        this.subtitle.setForeground(new Color(179, 179, 179));
        this.subtitle.setBackground(null);
        this.subtitle.addMouseListener(mouseListener);


        this.add(image);
        this.add(this.title);
        this.add(this.subtitle);
    }

    public Card getThis() {
        return this;
    }

    public JButton getImage() {
        return image;
    }

    public void setImage(JButton image) {
        this.image = image;
    }

    public JButton getTitle() {
        return title;
    }

    public void setTitle(JButton title) {
        this.title = title;
    }

    public JButton getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(JButton subtitle) {
        this.subtitle = subtitle;
    }
}
