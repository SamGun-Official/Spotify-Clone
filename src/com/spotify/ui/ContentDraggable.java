package com.spotify.ui;

import com.spotify.app.User;
import com.spotify.ui.profile.ContentProfile;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

class ProfileButton extends JButton {
    boolean isActive;

    public ProfileButton(String text) {
        super(text);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

public class ContentDraggable extends JPanel implements com.spotify.app.Utility {
    private int x, y, cursorSpace = 0;
    private JPanel buttonPanel;
    private JButton exitButton;
    private JButton maximizeButton;
    private JButton minimizeButton;
    private JButton prevButton;
    private JButton nextButton;
    private ProfileButton userButton;

    public ContentDraggable(Window frame) {
        super();
        this.setBounds(
            0, 0, Threshold.contentDraggable.width, Threshold.contentDraggable.height
        );
        this.setLayout(null);
        this.setOpaque(false);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
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
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xx = e.getXOnScreen();
                int yy = e.getYOnScreen();
                frame.setLocation(xx - x - Threshold.sidebarDraggable.width, yy - y);

//                if(Threshold.currentSize.width == Threshold.maximumSize.width) {
//                    Threshold.currentSize = Threshold.minimumSize;
//
//                    frame.setPreferredSize(Threshold.currentSize);
//                    frame.setSize(Threshold.currentSize);
//                    frame.resizeAllPanel();
//                    frame.repaint();
//                    frame.revalidate();
//                }
            }
        });

        initializeExitMinimizeMaximizeButtons(frame);
//        initializePrevNextButtons(frame);
//        initializeUserProfileButton(frame, CurrentUser);
    }

    public ContentDraggable(Window frame, User CurrentUser) {
        super();
        this.setBackground(Color.yellow);
        this.setBounds(
            0, 0, Threshold.contentDraggable.width, Threshold.contentDraggable.height
        );
        this.setLayout(null);
        this.setOpaque(false);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
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
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int xx = e.getXOnScreen();
                int yy = e.getYOnScreen();
                frame.setLocation(xx - x - Threshold.sidebarDraggable.width, yy - y);

//                if(Threshold.currentSize.width == Threshold.maximumSize.width) {
//                    Threshold.currentSize = Threshold.minimumSize;
//
//                    frame.setPreferredSize(Threshold.currentSize);
//                    frame.setSize(Threshold.currentSize);
//                    frame.resizeAllPanel();
//                    frame.repaint();
//                    frame.revalidate();
//                }
            }
        });

        initializeExitMinimizeMaximizeButtons(frame);
        initializePrevNextButtons(frame);
        initializeUserProfileButton(frame, CurrentUser);
    }

    public final void initializeExitMinimizeMaximizeButtons(Window frame) {
        int buttonWidth = 45;
        int buttonHeight = 30;
        int componentCount = 2;

        buttonPanel = new JPanel();
        buttonPanel.setBounds(
            this.getWidth() - (componentCount * buttonWidth), 0, componentCount * buttonWidth, buttonHeight
        );
        buttonPanel.setLayout(null);

        minimizeButton = new JButton(scaleImageIcon(
            "./src/com/spotify/assets/icons/window/minimize_normal.png", 45, 30
        ));
        minimizeButton.setBackground(new Color(24, 24, 24));
        minimizeButton.setBorder(null);
        minimizeButton.setBounds(0, 0, buttonWidth, buttonHeight);
        minimizeButton.setFocusable(false);
        minimizeButton.setFont(new Font("Gotham", Font.PLAIN, 18));
        minimizeButton.setForeground(new Color(255, 255, 255));
        minimizeButton.setName("Minimize_Button");
        minimizeButton.addActionListener((ActionEvent e) -> {
            frame.setState(JFrame.ICONIFIED);
        });
        minimizeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                minimizeButton.setIcon(scaleImageIcon(
                    "./src/com/spotify/assets/icons/window/minimize_clicked.png", 45, 30
                ));
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                minimizeButton.setIcon(scaleImageIcon(
                    "./src/com/spotify/assets/icons/window/minimize_normal.png", 45, 30
                ));
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                minimizeButton.setIcon(scaleImageIcon(
                    "./src/com/spotify/assets/icons/window/minimize_hover.png", 45, 30
                ));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                minimizeButton.setIcon(scaleImageIcon(
                    "./src/com/spotify/assets/icons/window/minimize_normal.png", 45, 30
                ));
            }
        });

        /*
        String iconPath, buttonName;

        if(Threshold.currentSize.width == Threshold.maximumSize.width &&
        Threshold.currentSize.height == Threshold.maximumSize.height) {
            iconPath = "./src/com/spotify/assets/icons/window/restore_normal.png";
            buttonName = "Restore_Normal";
        } else {
            iconPath = "./src/com/spotify/assets/icons/window/maximize_normal.png";
            buttonName = "Maximize_Normal";
        }

        maximizeButton = new JButton(scaleImageIcon(iconPath, 45, 30));
        maximizeButton.setBackground(new Color(24, 24, 24));
        maximizeButton.setBorder(null);
        maximizeButton.setBounds(buttonWidth, 0, buttonWidth, buttonHeight);
        maximizeButton.setFocusable(false);
        maximizeButton.setFont(new Font("Gotham", Font.PLAIN, 18));
        maximizeButton.setForeground(new Color(255, 255, 255));
        maximizeButton.setName(buttonName);
        maximizeButton.addActionListener((ActionEvent e) -> {
            if(Threshold.currentSize.width == Threshold.minimumSize.width &&
                    Threshold.currentSize.height == Threshold.minimumSize.height) {
                Threshold.currentPosition.setLocation(frame.getLocation());
                Threshold.currentSize = Threshold.maximumSize;
                frame.setLocation(0, 0);

                prevButton.setBounds(
                        (int)prevButton.getLocation().getX() + 16, (int)prevButton.getLocation().getY(),
                        (int)prevButton.getSize().getWidth(), (int)prevButton.getSize().getHeight()
                );
                prevButton.revalidate();
                prevButton.repaint();

                nextButton.setBounds(
                        (int)nextButton.getLocation().getX() + 16, (int)nextButton.getLocation().getY(),
                        (int)nextButton.getSize().getWidth(), (int)nextButton.getSize().getHeight()
                );
                nextButton.revalidate();
                nextButton.repaint();
            } else {
                Threshold.currentSize = Threshold.minimumSize;
                frame.setLocation(Threshold.currentPosition);

                prevButton.setBounds(
                        (int)prevButton.getLocation().getX() - 16, (int)prevButton.getLocation().getY(),
                        (int)prevButton.getSize().getWidth(), (int)prevButton.getSize().getHeight()
                );
                prevButton.revalidate();
                prevButton.repaint();

                nextButton.setBounds(
                        (int)nextButton.getLocation().getX() - 16, (int)nextButton.getLocation().getY(),
                        (int)nextButton.getSize().getWidth(), (int)nextButton.getSize().getHeight()
                );
                nextButton.revalidate();
                nextButton.repaint();
            }

            frame.setPreferredSize(Threshold.currentSize);
            frame.setSize(Threshold.currentSize);
            frame.resizeAllPanel();
            frame.repaint();
            frame.revalidate();
        });
        maximizeButton.addMouseListener(new MouseAdapter() {
            boolean isExpired = true;

            @Override
            public void mousePressed(MouseEvent me) {
                isExpired = false;

                if(maximizeButton.getName().equals("Maximize_Normal")) {
                    maximizeButton.setIcon(scaleImageIcon(
                        "./src/com/spotify/assets/icons/window/maximize_clicked.png", 45, 30
                    ));
                } else {
                    maximizeButton.setIcon(scaleImageIcon(
                        "./src/com/spotify/assets/icons/window/restore_clicked.png", 45, 30
                    ));
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if(isExpired == false) {
                    if(maximizeButton.getName().equals("Maximize_Normal")) {
                        maximizeButton.setName("Restore_Normal");
                        maximizeButton.setIcon(scaleImageIcon(
                            "./src/com/spotify/assets/icons/window/restore_normal.png", 45, 30
                        ));
                    } else {
                        maximizeButton.setName("Maximize_Normal");
                        maximizeButton.setIcon(scaleImageIcon(
                            "./src/com/spotify/assets/icons/window/maximize_normal.png", 45, 30
                        ));
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                if(maximizeButton.getName().equals("Maximize_Normal")) {
                    maximizeButton.setIcon(scaleImageIcon(
                        "./src/com/spotify/assets/icons/window/maximize_hover.png", 45, 30
                    ));
                } else {
                    maximizeButton.setIcon(scaleImageIcon(
                        "./src/com/spotify/assets/icons/window/restore_hover.png", 45, 30
                    ));
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                isExpired = true;

                if(maximizeButton.getName().equals("Maximize_Normal")) {
                    maximizeButton.setIcon(scaleImageIcon(
                        "./src/com/spotify/assets/icons/window/maximize_normal.png", 45, 30
                    ));
                } else {
                    maximizeButton.setIcon(scaleImageIcon(
                        "./src/com/spotify/assets/icons/window/restore_normal.png", 45, 30
                    ));
                }
            }
        });
        */

        exitButton = new JButton(scaleImageIcon(
            "./src/com/spotify/assets/icons/window/close_normal.png", 45, 30
        ));
        exitButton.setBackground(new Color(24, 24, 24));
        exitButton.setBorder(null);
        exitButton.setBounds(buttonWidth * (componentCount - 1), 0, buttonWidth, buttonHeight);
        exitButton.setFocusable(false);
        exitButton.setFont(new Font("Gotham", Font.PLAIN, 18));
        exitButton.setForeground(new Color(255, 255, 255));
        exitButton.setName("Close_Button");
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                exitButton.setIcon(scaleImageIcon(
                    "./src/com/spotify/assets/icons/window/close_clicked.png", 45, 30
                ));
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                exitButton.setIcon(scaleImageIcon(
                    "./src/com/spotify/assets/icons/window/close_normal.png", 45, 30
                ));
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                exitButton.setIcon(scaleImageIcon(
                    "./src/com/spotify/assets/icons/window/close_hover.png", 45, 30
                ));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                exitButton.setIcon(scaleImageIcon(
                    "./src/com/spotify/assets/icons/window/close_normal.png", 45, 30
                ));
            }
        });

        buttonPanel.add(minimizeButton);
        /*
        buttonPanel.add(maximizeButton);
        */
        buttonPanel.add(exitButton);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                buttonPanel.setBounds(
                    getWidth() - (componentCount * buttonWidth), 0, componentCount * buttonWidth, buttonHeight
                );
            }
        });

        this.add(buttonPanel);
    }

    public final void initializePrevNextButtons(Window frame) {
        int buttonWidth = 32;
        int buttonHeight = 32;
        int leftMargin;

        if(Threshold.currentSize.width >= 1280) {
            leftMargin = 32;
        } else {
            leftMargin = 16;
        }

        prevButton = new JButton(scaleImageIcon(
            "./src/com/spotify/assets/icons/backward_icon_disabled_rounded.png", 32, 32
        ));
        nextButton = new JButton(scaleImageIcon(
            "./src/com/spotify/assets/icons/forward_icon_disabled_rounded.png", 32, 32
        ));

        Window.initializeButton(prevButton);
        Window.initializeButton(nextButton);

        prevButton.setBounds(
            leftMargin, ((this.getHeight() - buttonHeight) / 2) - cursorSpace, buttonWidth, buttonHeight
        );
        nextButton.setBounds(
            prevButton.getWidth() + prevButton.getX() + 16, ((this.getHeight() - buttonHeight) / 2) - cursorSpace,
            buttonWidth, buttonHeight
        );

        prevButton.addActionListener((ActionEvent e) -> {
            frame.previousPageButtonPressed();
        });
        nextButton.addActionListener((ActionEvent e) -> {
            frame.nextPageButtonPressed();
        });

        prevButton.setOpaque(false);
        nextButton.setOpaque(false);

        this.add(prevButton);
        this.add(nextButton);
    }

    public final void initializeUserProfileButton(Window frame, User CurrentUser) {
        int width = 100;
        int height = 40;

        userButton = new ProfileButton(CurrentUser.getFirstName());
        userButton.setBounds(
            buttonPanel.getX() - width - 30, ((this.getHeight() - height) / 2) - cursorSpace, width, height
        );
        userButton.addActionListener((ActionEvent e) -> {
            if(!userButton.isActive) {
                frame.getLayeredPane().remove(frame.getContentPanel());
                ContentProfile cp = new ContentProfile(frame, CurrentUser);
                frame.setContentPanel(new Content(cp));
                frame.getLayeredPane().add(frame.getContentPanel());
                frame.repaint();
                frame.revalidate();
            }
        });

        Window.initializeButton(userButton);

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
}
