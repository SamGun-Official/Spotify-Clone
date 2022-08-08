package com.spotify.ui.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TitleBarContent extends JPanel {
    private int x, y;
    private JPanel buttonPanel;
    private JButton exitButton;
    private JButton maximizeButton;
    private JButton minimizeButton;
    private JButton prevButton;
    private JButton nextButton;
    private JButton userButton;
    public TitleBarContent(JFrame frame) {
        super();
        this.setLayout(null);
        this.setBounds(ASSET.sideBar.width,0, ASSET.titleBarContent.width,
                ASSET.titleBarContent.height);
        this.setBackground(new Color(24,24,24));
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
                frame.setLocation(xx - x - ASSET.titleBarSideBar.width, yy - y);
            }
            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        initializeExitMinimizeMaximizeButtons(frame);
        initializePrevNextButtons(frame);
        initializeUserProfileButton(frame);
    }

    public void initializeExitMinimizeMaximizeButtons(JFrame frame) {
        int buttonWidth = 45;
        int buttonHeight = 30;
        buttonPanel = new JPanel();
        buttonPanel.setBounds(this.getWidth() - (3 * buttonWidth), 0, 3 * buttonWidth, buttonHeight);
        buttonPanel.setLayout(null);

        exitButton = new JButton("✕");
        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setBackground(new Color(255,24,24));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setBackground(new Color(24,24,24));
            }
        });
        exitButton.setFont(new Font("Gotham", Font.PLAIN, 15));
        exitButton.setBounds(buttonWidth * 2,0, buttonWidth, buttonHeight);
        exitButton.setBorder(null);
        exitButton.setFocusable(false);
        exitButton.setBackground(new Color(24, 24, 24));
        exitButton.setForeground(new Color(255, 255, 255));
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        maximizeButton = new JButton("□");
        maximizeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e){

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                maximizeButton.setBackground(new Color(88, 84, 84));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                maximizeButton.setBackground(new Color(24, 24, 24));
            }
        });
        maximizeButton.setFont(new Font("Gotham", Font.PLAIN, 15));
        maximizeButton.setBounds(buttonWidth, 0, buttonWidth, buttonHeight);
        maximizeButton.setBorder(null);
        maximizeButton.setFocusable(false);
        maximizeButton.setBackground(new Color(24, 24, 24));
        maximizeButton.setForeground(new Color(255, 255, 255));
        maximizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        maximizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setLocation(0,0);
                frame.setSize(ASSET.fullscreen);
                frame.repaint();
                frame.revalidate();
            }
        });

        minimizeButton = new JButton("–");
        minimizeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                minimizeButton.setBackground(new Color(88, 84, 84));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                minimizeButton.setBackground(new Color(24, 24, 24));
            }
        });
        minimizeButton.setFont(new Font("Gotham", Font.PLAIN, 18));
        minimizeButton.setBounds(0, 0, buttonWidth, buttonHeight);
        minimizeButton.setBorder(null);
        minimizeButton.setFocusable(false);
        minimizeButton.setBackground(new Color(24, 24, 24));
        minimizeButton.setForeground(new Color(255, 255, 255));
        minimizeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        minimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }
        });
        buttonPanel.add(exitButton);
        buttonPanel.add(minimizeButton);
        buttonPanel.add(maximizeButton);
        this.add(buttonPanel);
    }
    public void initializePrevNextButtons(JFrame frame) {
        int buttonHeight = 40;
        int buttonWidth = 40;
        prevButton = new JButton("<");
        nextButton = new JButton(">");
        WindowAdmin.initializeButton(prevButton);
        WindowAdmin.initializeButton(nextButton);
        prevButton.setBounds(30, (this.getHeight() - buttonHeight) / 2, buttonWidth, buttonHeight);
        nextButton.setBounds(prevButton.getWidth() + prevButton.getX() + 10, (this.getHeight() - buttonHeight) / 2,
                buttonWidth,
                buttonHeight);
        this.add(prevButton);
        this.add(nextButton);
    }

    public void initializeUserProfileButton(JFrame frame) {
        int width = 100;
        int height = 40;
        userButton = new JButton("Name");
        userButton.setBounds(buttonPanel.getX() - width - 30, (this.getHeight() - height) / 2, width, height);
        WindowAdmin.initializeButton(userButton);
        this.add(userButton);
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public void setButtonPanel(JPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void setExitButton(JButton exitButton) {
        this.exitButton = exitButton;
    }

    public JButton getMaximizeButton() {
        return maximizeButton;
    }

    public void setMaximizeButton(JButton maximizeButton) {
        this.maximizeButton = maximizeButton;
    }

    public JButton getMinimizeButton() {
        return minimizeButton;
    }

    public void setMinimizeButton(JButton minimizeButton) {
        this.minimizeButton = minimizeButton;
    }

    public JButton getPrevButton() {
        return prevButton;
    }

    public void setPrevButton(JButton prevButton) {
        this.prevButton = prevButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public void setNextButton(JButton nextButton) {
        this.nextButton = nextButton;
    }

    public JButton getUserButton() {
        return userButton;
    }

    public void setUserButton(JButton userButton) {
        this.userButton = userButton;
    }
}
