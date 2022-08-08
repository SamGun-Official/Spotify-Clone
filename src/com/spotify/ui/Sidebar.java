package com.spotify.ui;

import com.spotify.ui.home.Home;
import com.spotify.ui.playlist.PlaylistUI;
import com.spotify.app.Account;
import com.spotify.app.Admin;
import com.spotify.app.Playlist;
import com.spotify.app.User;
import com.spotify.ui.LoginRegister.Flogin;
import com.spotify.ui.graphics.TranslucentScrollBarUI;
import com.spotify.ui.playlist.TrackRow;
import com.spotify.ui.search.TitleBarContentSearch;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Sidebar extends JPanel implements ActionListener {
    private JPanel navbar;
    private NavbarButton home;
    private NavbarButton search;
    private NavbarButton yourLibrary;
    private NavbarButton createPlaylist;
    private NavbarButton likedSongs;
    private JPanel linePanel;
    private JPanel playlistBar;
    private JScrollPane playlistBarScroll;
    private ArrayList<JButton> playlistsButton;
    private Window windowApp;
    private ArrayList<Account> accounts;
    private User currentUser;
    private Dimension sizeNavbar;

    public Sidebar(Window windowApp, User user, ArrayList<Account> accounts) {
        super();
        this.playlistsButton = new ArrayList<>();
        this.windowApp = windowApp;
        this.accounts = accounts;
        this.currentUser = user;

        this.setBackground(Threshold.palette_000000);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 40, 40)));
        this.setBounds(
            0, 0, (int)Threshold.sidebarArea.getWidth(), (int)Threshold.sidebarArea.getHeight()
        );
        this.setLayout(null);
        this.setPreferredSize(this.getSize());

        this.navbar = new JPanel();
        this.navbar.setBackground(Threshold.palette_000000);
        this.navbar.setBounds(
            0, (int)(Threshold.sidebarDraggable.getHeight()), this.getWidth(), 250
        );
        this.navbar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.navbar.setOpaque(false);
        this.navbar.setPreferredSize(this.navbar.getSize());

        sizeNavbar = new Dimension(navbar.getWidth() - 16, 40);

        this.home = new NavbarButton(
            "Home", sizeNavbar, "./src/com/spotify/assets/icons/navbar/home_default.png",
            "./src/com/spotify/assets/icons/navbar/home_hover.png",
            "./src/com/spotify/assets/icons/navbar/home_active.png", this
        );
        this.search = new NavbarButton(
            "Search", sizeNavbar,
            "./src/com/spotify/assets/icons/navbar/search_default.png",
            "./src/com/spotify/assets/icons/navbar/search_hover.png",
            "./src/com/spotify/assets/icons/navbar/search_active.png", this
        );
        this.yourLibrary = new NavbarButton(
            "Logout", sizeNavbar,
            "./src/com/spotify/assets/icons/navbar/logout_default.png",
            "./src/com/spotify/assets/icons/navbar/logout_hover.png",
            "./src/com/spotify/assets/icons/navbar/logout_hover.png", this
        );
        this.createPlaylist = new NavbarButton(
            "Create Playlist", sizeNavbar,
            "./src/com/spotify/assets/icons/navbar/add_icon_default.png",
            "./src/com/spotify/assets/icons/navbar/add_icon_hover.png",
            "./src/com/spotify/assets/icons/navbar/add_icon_hover.png", this
        );
        this.likedSongs = new NavbarButton(
            "Liked Songs", sizeNavbar,
            "./src/com/spotify/assets/icons/navbar/favorite_icon_default.png",
            "./src/com/spotify/assets/icons/navbar/favorite_icon_hover.png",
            "./src/com/spotify/assets/icons/navbar/favorite_icon_hover.png", this
        );

        this.linePanel = new JPanel();
        this.linePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 40, 40)));
        this.linePanel.setMinimumSize(new Dimension(this.navbar.getWidth() - 48, 1));
        this.linePanel.setPreferredSize(this.linePanel.getMinimumSize());
        this.linePanel.setSize(linePanel.getPreferredSize());

        this.navbar.add(home);
        this.navbar.add(search);
        this.navbar.add(yourLibrary);
        this.navbar.add(Box.createRigidArea(new Dimension((int)sizeNavbar.getWidth(), 24)));
        this.navbar.add(createPlaylist);
        this.navbar.add(likedSongs);
        this.navbar.add(Box.createRigidArea(new Dimension((int)sizeNavbar.getWidth(), 8)));
        this.navbar.add(linePanel);
        this.navbar.add(Box.createRigidArea(new Dimension((int)sizeNavbar.getWidth(), 7)));

        this.navbar.setBounds(
            0, (int)(Threshold.sidebarDraggable.getHeight()), this.getWidth(),
            (int)this.home.getHeight() + (int)this.search.getHeight() +
            (int)this.yourLibrary.getHeight() + (int)this.createPlaylist.getHeight() +
            (int)this.likedSongs.getHeight() + (int)this.linePanel.getHeight() + 39
        );
        this.navbar.setPreferredSize(this.navbar.getSize());

        initPlaylistBar();
//        this.playlistBar = new JPanel();
//        this.playlistBar.add(Box.createRigidArea(new Dimension((int)sizeNavbar.getWidth(), 5)), 0);
//        this.playlistBar.setBackground(Threshold.palette_000000);
//        this.playlistBar.setBounds(
//            0, Threshold.sidebarDraggable.height + this.navbar.getHeight() + 5, this.getWidth() - 4, 0
//        );
//        this.playlistBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//        this.playlistBar.setPreferredSize(this.playlistBar.getSize());

        initPlaylistBarScroll();
//        this.playlistBarScroll = new JScrollPane(this.playlistBar);
//        this.playlistBarScroll.setBackground(Threshold.palette_000000);
//        this.playlistBarScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
//        this.playlistBarScroll.setBounds(
//            4, Threshold.sidebarDraggable.height + this.navbar.getHeight() - 5, this.getWidth() - 4,
//            this.getHeight() - (Threshold.sidebarDraggable.height + this.navbar.getHeight()) + 2
//        );
//        this.playlistBarScroll.setPreferredSize(this.playlistBarScroll.getSize());
//        this.playlistBarScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        this.playlistBarScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        this.playlistBarScroll.getVerticalScrollBar().setPreferredSize(new Dimension(
//            14, (int)this.playlistBarScroll.getVerticalScrollBar().getPreferredSize().getHeight()
//        ));
//        this.playlistBarScroll.getVerticalScrollBar().setUI(new TranslucentScrollBarUI(new Color(255, 255, 255, 77)) {
//            @Override
//            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
//                Graphics2D g2 = (Graphics2D)g.create();
//
//                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                g2.setColor(color);
//                g2.setPaint(color);
//                g2.fillRect(r.x, r.y, r.width - 2, r.height);
//                g2.dispose();
//            }
//
//            @Override
//            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
//                Color specificColor = new Color(0, 0, 0);
//                Graphics2D g2 = (Graphics2D)g.create();
//
//                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                g2.setColor(specificColor);
//                g2.setPaint(specificColor);
//                g2.fillRect(r.x, r.y, r.width, r.height);
//                g2.dispose();
//            }
//        });
//        this.playlistBarScroll.getVerticalScrollBar().setUnitIncrement(14);
//        this.playlistBarScroll.setVisible(true);



        this.add(navbar);
        this.add(playlistBarScroll);

        windowApp.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                playlistBarScroll.setLocation(
                    4, Threshold.sidebarDraggable.height + navbar.getHeight() - 5
                );
                playlistBarScroll.setPreferredSize(new Dimension(
                    getWidth() - 4, getHeight() - (Threshold.sidebarDraggable.height + navbar.getHeight()) + 2
                ));
                playlistBarScroll.setSize(playlistBarScroll.getPreferredSize());
                playlistBarScroll.revalidate();
                playlistBarScroll.repaint();
            }
        });
    }

    public void initPlaylistBar() {
        this.playlistBar = new JPanel();
        this.playlistBar.add(Box.createRigidArea(new Dimension((int)sizeNavbar.getWidth(), 5)), 0);
        this.playlistBar.setBackground(Threshold.palette_000000);
        this.playlistBar.setBounds(
                0, Threshold.sidebarDraggable.height + this.navbar.getHeight() + 5, this.getWidth() - 4, 0
        );
        this.playlistBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.playlistBar.setPreferredSize(this.playlistBar.getSize());
        addPlaylistsButton(windowApp.user);
    }

    public void initPlaylistBarScroll() {
        this.playlistBarScroll = new JScrollPane(this.playlistBar);
        this.playlistBarScroll.setBackground(Threshold.palette_000000);
        this.playlistBarScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.playlistBarScroll.setBounds(
                4, Threshold.sidebarDraggable.height + this.navbar.getHeight() - 5, this.getWidth() - 4,
                this.getHeight() - (Threshold.sidebarDraggable.height + this.navbar.getHeight()) + 2
        );
        this.playlistBarScroll.setPreferredSize(this.playlistBarScroll.getSize());
        this.playlistBarScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.playlistBarScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.playlistBarScroll.getVerticalScrollBar().setPreferredSize(new Dimension(
                14, (int)this.playlistBarScroll.getVerticalScrollBar().getPreferredSize().getHeight()
        ));
        this.playlistBarScroll.getVerticalScrollBar().setUI(new TranslucentScrollBarUI(new Color(255, 255, 255, 77)) {
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D)g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setPaint(color);
                g2.fillRect(r.x, r.y, r.width - 2, r.height);
                g2.dispose();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
                Color specificColor = new Color(0, 0, 0);
                Graphics2D g2 = (Graphics2D)g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(specificColor);
                g2.setPaint(specificColor);
                g2.fillRect(r.x, r.y, r.width, r.height);
                g2.dispose();
            }
        });
        this.playlistBarScroll.getVerticalScrollBar().setUnitIncrement(14);
        this.playlistBarScroll.setVisible(true);
    }

    public void addNewPlaylistButton() {
        this.playlistsButton.add(new JButton("My Playlist #" + (playlistsButton.size() + 1)));

        JButton tempButton = this.playlistsButton.get(this.playlistsButton.size() - 1);
        tempButton.putClientProperty("index", playlistsButton.size() - 1);
        tempButton.setBackground(Threshold.palette_000000);
        tempButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        tempButton.setBorderPainted(false);
        tempButton.setContentAreaFilled(false);
        tempButton.setFocusable(false);
        tempButton.setFocusPainted(false);
        tempButton.setFont(new Font("Gotham", Font.PLAIN, 14));
        tempButton.setForeground(Threshold.palette_b3b3b3);
        tempButton.setHorizontalAlignment(SwingConstants.LEFT);
        tempButton.setHorizontalTextPosition(JLabel.RIGHT);
        tempButton.setOpaque(true);
        tempButton.setPreferredSize(new Dimension(this.playlistBar.getWidth() - 40, 32));
        tempButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                tempButton.setForeground(Threshold.palette_ffffff);
            }

            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);

                JButton tempBtn = (JButton)me.getSource();
                int tempIndex = (int)tempBtn.getClientProperty("index");

                if(tempIndex != windowApp.activeUserPlaylist) {
                    windowApp.activeUserPlaylist = tempIndex;
                }

                PlaylistUI panel = new PlaylistUI(
                    accounts, currentUser.getPlaylists().get(tempIndex), windowApp, PlaylistUI.UNUSED_PLAYLIST,
                    tempIndex
                );
                windowApp.changeContent(
                    new Content(panel, true), new ContentDraggable(windowApp, currentUser), currentUser,
                    accounts
                );

                windowApp.changeUserPlaylistBtnColor();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                JButton tempBtn = (JButton)me.getSource();
                int tempIndex = (int)tempBtn.getClientProperty("index");

                if(tempIndex != windowApp.activeUserPlaylist) {
                    tempButton.setForeground(Threshold.palette_b3b3b3);
                }
            }
        });

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.now();

        currentUser.getPlaylists().add(new Playlist(
            new Color(83, 83, 83), new ArrayList<>(), "PLAYLIST", tempButton.getText(), "",
            currentUser.getFirstName() + " " + currentUser.getLastName(), dtf.format(localDate),
            "default", playlistsButton.size() - 1, 0, 0
        ));

        if(this.playlistsButton.size() * tempButton.getPreferredSize().getHeight() >
        this.playlistBar.getPreferredSize().getHeight()) {
            this.playlistBar.setPreferredSize(new Dimension(
                this.getWidth() - 4, (int)(this.playlistsButton.size() *
                tempButton.getPreferredSize().getHeight()) + 5 - 1
            ));
            this.playlistBar.setSize(this.playlistBar.getPreferredSize());
        }
        this.playlistBar.add(tempButton, 1);
        this.playlistBar.revalidate();
        this.playlistBar.repaint();
    }

    public void addPlaylistsButton(User user) {
        for(Playlist playlist : user.getPlaylists()) {
            System.out.println("playlist.getName() = " + playlist.getName());
            this.playlistsButton.add(new JButton(playlist.getName()));

            JButton tempButton = this.playlistsButton.get(this.playlistsButton.size() - 1);
            tempButton.putClientProperty("index", playlistsButton.size() - 1);
            tempButton.setBackground(Threshold.palette_000000);
            tempButton.setBorder(new EmptyBorder(0, 0, 0, 0));
            tempButton.setBorderPainted(false);
            tempButton.setContentAreaFilled(false);
            tempButton.setFocusable(false);
            tempButton.setFocusPainted(false);
            tempButton.setFont(new Font("Gotham", Font.PLAIN, 14));
            tempButton.setForeground(Threshold.palette_b3b3b3);
            tempButton.setHorizontalAlignment(SwingConstants.LEFT);
            tempButton.setHorizontalTextPosition(JLabel.RIGHT);
            tempButton.setOpaque(true);
            tempButton.setPreferredSize(new Dimension(this.playlistBar.getWidth() - 40, 32));
            tempButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent me) {
                    tempButton.setForeground(Threshold.palette_ffffff);
                }

                @Override
                public void mouseClicked(MouseEvent me) {
                    super.mouseClicked(me);

                    JButton tempBtn = (JButton)me.getSource();
                    int tempIndex = (int)tempBtn.getClientProperty("index");

                    if(tempIndex != windowApp.activeUserPlaylist) {
                        windowApp.activeUserPlaylist = tempIndex;
                    }

                    PlaylistUI panel = new PlaylistUI(
                        accounts, currentUser.getPlaylists().get(tempIndex), windowApp, PlaylistUI.UNUSED_PLAYLIST,
                        tempIndex
                    );
                    windowApp.changeContent(
                        new Content(panel, true), new ContentDraggable(windowApp, currentUser), currentUser,
                        accounts
                    );

                    windowApp.changeUserPlaylistBtnColor();
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    JButton tempBtn = (JButton)me.getSource();
                    int tempIndex = (int)tempBtn.getClientProperty("index");

                    if(tempIndex != windowApp.activeUserPlaylist) {
                        tempButton.setForeground(Threshold.palette_b3b3b3);
                    }
                }
            });

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate localDate = LocalDate.now();

            if(this.playlistsButton.size() * tempButton.getPreferredSize().getHeight() >
                this.playlistBar.getPreferredSize().getHeight()) {
                this.playlistBar.setPreferredSize(new Dimension(
                    this.getWidth() - 4, (int)(this.playlistsButton.size() *
                    tempButton.getPreferredSize().getHeight()) + 5 - 1
                ));
                this.playlistBar.setSize(this.playlistBar.getPreferredSize());
            }
            this.playlistBar.add(tempButton, 1);
            this.playlistBar.revalidate();
            this.playlistBar.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.home.clicked(e);
        this.search.clicked(e);
        this.yourLibrary.clicked(e);
        this.createPlaylist.clicked(e);
        this.likedSongs.clicked(e);
        if(e.getSource() == this.home) {
            if(home.isActive) {
                windowApp.changeContent(new Content(new Home(currentUser, windowApp, accounts)),
                new ContentDraggable(windowApp, currentUser), currentUser, accounts);
                windowApp.activeUserPlaylist = -1;
                windowApp.changeUserPlaylistBtnColor();
            }
        } else if(e.getSource() == this.search) {
            if(search.isActive) {
                JLabel labelTemp = new JLabel("Search");
                labelTemp.setFont(new Font("Gotham", Font.PLAIN, 50));
                JPanel panel = new JPanel();
                panel.setBackground(new Color(18, 18, 18));
                panel.add(labelTemp);
                windowApp.changeContent(
                    new Content(panel), new TitleBarContentSearch(windowApp, currentUser, accounts),
                    currentUser, accounts
                );
                windowApp.activeUserPlaylist = -1;
                windowApp.changeUserPlaylistBtnColor();
            }
        } else if(e.getSource() == this.yourLibrary) {
            windowApp.changeUserPlaylistBtnColor();

            if(yourLibrary.isActive) {
                Flogin frameLogin = new Flogin(accounts);
                windowApp.setVisible(false);
                windowApp = null;
                frameLogin.setVisible(true);
            }
        } else if(e.getSource() == this.createPlaylist) {
            this.createPlaylist.setActive(false);
            this.createPlaylist.setBackground(Threshold.palette_000000);
            this.createPlaylist.setOpaque(false);
            this.addNewPlaylistButton();

            /*
            System.out.println("createPlaylist");
            System.out.println("playlistsButton.size() = " + this.playlistsButton.size());
            */
        } else if(e.getSource() == likedSongs) {
            this.likedSongs.setActive(true);
            this.likedSongs.setBackground(Threshold.palette_000000);
            this.likedSongs.setOpaque(false);

            if(likedSongs.isActive) {
                /*
                Admin admin = (Admin)accounts.get(0);
                PlaylistUI panel = new PlaylistUI(
                    accounts, admin.getPlaylists().get(0), windowApp, PlaylistUI.NATIVE_PLAYLIST, -1
                );
                */
                PlaylistUI panel = new PlaylistUI(
                    accounts, currentUser.getLikedSongs(), windowApp, PlaylistUI.LIKED_PLAYLIST, -1
                );
                windowApp.changeContent(
                    new Content(panel, true), new ContentDraggable(windowApp, currentUser), currentUser, accounts
                );
                windowApp.activeUserPlaylist = -1;
                windowApp.changeUserPlaylistBtnColor();

                if(windowApp.user.getLikedSongs().getSongs().size() > 0) {
                    for(int i = 0; i < panel.getTrackTableList().getComponentCount(); i++) {
                        TrackRow tempRow = (TrackRow)panel.getTrackTableList().getComponent(i);
                        tempRow.changeHeartIconOnLiked(windowApp);
                    }
                }
            }
        }
    }

    public JPanel getNavbar() {
        return navbar;
    }

    public void setNavbar(JPanel navbar) {
        this.navbar = navbar;
    }

    public NavbarButton getHome() {
        return home;
    }

    public void setHome(NavbarButton home) {
        this.home = home;
    }

    public NavbarButton getSearch() {
        return search;
    }

    public void setSearch(NavbarButton search) {
        this.search = search;
    }

    public NavbarButton getYourLibrary() {
        return yourLibrary;
    }

    public void setYourLibrary(NavbarButton yourLibrary) {
        this.yourLibrary = yourLibrary;
    }

    public NavbarButton getCreatePlaylist() {
        return createPlaylist;
    }

    public void setCreatePlaylist(NavbarButton createPlaylist) {
        this.createPlaylist = createPlaylist;
    }

    public NavbarButton getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(NavbarButton likedSongs) {
        this.likedSongs = likedSongs;
    }

    public JPanel getLinePanel() {
        return linePanel;
    }

    public void setLinePanel(JPanel linePanel) {
        this.linePanel = linePanel;
    }

    public JPanel getPlaylistBar() {
        return playlistBar;
    }

    public void setPlaylistBar(JPanel playlistBar) {
        this.playlistBar = playlistBar;
    }

    public JScrollPane getPlaylistBarScroll() {
        return playlistBarScroll;
    }

    public void setPlaylistBarScroll(JScrollPane playlistBarScroll) {
        this.playlistBarScroll = playlistBarScroll;
    }

    public ArrayList<JButton> getPlaylistsButton() {
        return playlistsButton;
    }

    public void setPlaylistsButton(ArrayList<JButton> playlistsButton) {
        this.playlistsButton = playlistsButton;
    }
}
