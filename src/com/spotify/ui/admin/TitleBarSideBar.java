package com.spotify.ui.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TitleBarSideBar extends JPanel {
    int x, y;
    public TitleBarSideBar(JFrame frame) {
        super();
        this.setLayout(null);
        this.setBounds(0,0, ASSET.titleBarSideBar.width, ASSET.titleBarSideBar.height);
        this.setBackground(Color.green);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xx, yy;
                xx = e.getXOnScreen();
                yy = e.getYOnScreen();
                frame.setLocation(xx - x, yy - y);
            }
            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }
}
