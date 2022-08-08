package com.spotify.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SidebarDraggable extends JPanel {
    private int x, y, cursorSpace = 0;

    public SidebarDraggable(Window frame) {
        super();
        this.setBackground(new Color(0, 0, 0));
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setBounds(
            cursorSpace, cursorSpace, (int)Threshold.sidebarDraggable.width - cursorSpace,
            (int)Threshold.sidebarDraggable.height - cursorSpace);
        this.setLayout(null);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX() + cursorSpace;
                y = e.getY() + cursorSpace;
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
                int xx = e.getXOnScreen();
                int yy = e.getYOnScreen();
                frame.setLocation(xx - x, yy - y);

                if(Threshold.currentSize.width == Threshold.maximumSize.width) {
                    Threshold.currentSize = Threshold.minimumSize;

                    frame.setPreferredSize(Threshold.currentSize);
                    frame.setSize(Threshold.currentSize);
                    frame.resizeAllPanel();
                    frame.repaint();
                    frame.revalidate();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        });
    }
}
