package com.spotify.ui;

import com.spotify.app.Account;
import com.spotify.app.Admin;
import com.spotify.app.Artist;
import com.spotify.app.Genre;
import com.spotify.app.Song;
import com.spotify.app.User;
import com.spotify.ui.graphics.ComponentResizer;
import com.spotify.ui.home.Home;
import com.spotify.ui.playlist.PlaylistUI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Window extends JFrame implements com.spotify.app.Utility {
    public ArrayList<Integer> shuffleOnDemand;
    public JLayeredPane contentLayeredPane;

    public Player audioPlayer;
    public Content contentPanel;
    public Sidebar sidebarPanel;
    public ContentDraggable contentDraggable;
    public SidebarDraggable sidebarDraggable;
    public PlayButtonEvent playButtonEvent;

    public ArrayList<Account> accounts;
    public Admin admin;
    public User user;

    public boolean activePlayer = false;
    public boolean isFirstTime = true;
    public float audioVolume = 0.0f;
    public int activeTrackNumber = 0;
    public int activeUserPlaylist = -1;
    public int countOnShuffle = 0;
    public int currentPlaylistTrackCount = 0;
    public String activePlaylistName = "";

    private int page;
    private ArrayList<ContentPanel> contentPanels;

    /* long startTime = System.currentTimeMillis();
    long endTime = System.currentTimeMillis();

    NumberFormat formatter = new DecimalFormat("#0.000");
    System.out.println("\nExecution Time: " + formatter.format((endTime - startTime) / 1000d) + " seconds"); */

    public Window(User user, ArrayList<Account> accounts) {
        /* Threshold.maximumSize = new Dimension(1920, 1080); */
        contentPanels = new ArrayList<>();
        page = -1;

        this.accounts = accounts;
        this.admin = (Admin)accounts.get(0);
        this.user = user;

        this.setPreferredSize(Threshold.currentSize);
        this.setSize(this.getPreferredSize());

        /* this.initAudioPlayer(admin, true); */
        this.initAudioPlayer();
        this.initContent(user);
        this.initSidebarPanel(user, accounts);

        this.playButtonEvent = null;

        this.setTitle("Spotify");
        /* this.initDynamicComponent(); */

        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setLayout(null);

        /* this.getContentPane().setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
        this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f)); */

        this.getContentPane().setBackground(new Color(18, 18, 18));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                Threshold.currentSize = getSize();

                resizeAllPanel();
            }
        }); */

        Threshold.currentPosition.setLocation(this.getLocation());

        this.changeContent(
            new Content(new Home(user, this, accounts)), new ContentDraggable(this, user), user, accounts
        );
        this.activeUserPlaylist = -1;
        this.changeUserPlaylistBtnColor();

        this.pack();
        this.setVisible(true);
    }

    public final void resizeAllPanel() {
        //<editor-fold defaultstate="collapsed" desc="Resize handler for audioPlayer.">
        JPanel resizePanel = audioPlayer.getPlayedSong();
        resizePanel.setBounds(
            0, (int)(getSize().getHeight() - Threshold.playerArea.height),
            (int)(getSize().getWidth() * 30 / 100) + 6, Threshold.playerArea.height
        );
        audioPlayer.setPlayedSong(resizePanel);
        audioPlayer.getPlayedSong().revalidate();
        audioPlayer.getPlayedSong().repaint();

        resizePanel = audioPlayer.getMusicPlayer();
        resizePanel.setBounds(
            (int)audioPlayer.getPlayedSong().getWidth(),
            (int)(getSize().getHeight() - Threshold.playerArea.height),
            ((int)getSize().getWidth() - (int)(getSize().getWidth() * 30 / 100) * 2) - 12,
            (int)Threshold.playerArea.height
        );
        audioPlayer.setMusicPlayer(resizePanel);
        audioPlayer.getMusicPlayer().revalidate();
        audioPlayer.getMusicPlayer().repaint();

        resizePanel = audioPlayer.getPlayerControl();
        resizePanel.setBounds(
            (int)(audioPlayer.getPlayedSong().getWidth() + audioPlayer.getMusicPlayer().getWidth()),
            (int)(getSize().getHeight() - Threshold.playerArea.height),
            (int)(getSize().getWidth() * 30 / 100) + 6, (int)Threshold.playerArea.height
        );
        audioPlayer.setPlayerControl(resizePanel);
        audioPlayer.getPlayerControl().revalidate();
        audioPlayer.getPlayerControl().repaint();

        Threshold.playerArea.width = (int)getSize().getWidth();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Resize handler for contentPanel.">
        contentPanel.setPreferredSize(new Dimension(
            (int)(getSize().getWidth() - sidebarPanel.getSize().getWidth()),
            (int)(getSize().getHeight() - Threshold.playerArea.height)
        ));
        contentPanel.setSize(contentPanel.getPreferredSize());
        contentPanel.revalidate();
        contentPanel.repaint();

        Threshold.contentArea.width = (int)contentPanel.getSize().getWidth();
        Threshold.contentArea.height = (int)contentPanel.getSize().getHeight();

        contentDraggable.setPreferredSize(new Dimension(
            (int)(getSize().getWidth() - sidebarPanel.getSize().getWidth()), Threshold.contentDraggable.height
        ));
        contentDraggable.setSize(contentDraggable.getPreferredSize());
        contentDraggable.revalidate();
        contentDraggable.repaint();

        Threshold.contentDraggable.width = (int)contentDraggable.getSize().getWidth();
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Resize handler for sidebarPanel.">
        sidebarPanel.setPreferredSize(new Dimension(
            (int)(getSize().getWidth() - contentPanel.getSize().getWidth()),
            (int)(getSize().getHeight() - Threshold.playerArea.height)
        ));
        sidebarPanel.setSize(sidebarPanel.getPreferredSize());
        sidebarPanel.revalidate();
        sidebarPanel.repaint();

        Threshold.sidebarArea.width = (int)sidebarPanel.getSize().getWidth();
        Threshold.sidebarArea.height = (int)sidebarPanel.getSize().getHeight();

        sidebarDraggable.setPreferredSize(new Dimension(
            (int)(getSize().getWidth() - contentPanel.getSize().getWidth() - 4),
            Threshold.sidebarDraggable.height - 4
        ));
        sidebarDraggable.setSize(sidebarDraggable.getPreferredSize());
        sidebarDraggable.revalidate();
        sidebarDraggable.repaint();

        Threshold.sidebarDraggable.width = (int)sidebarDraggable.getSize().getWidth();
        //</editor-fold>

        this.contentLayeredPane.setPreferredSize(new Dimension(
            Threshold.currentSize.width - Threshold.sidebarArea.width,
            Threshold.currentSize.height - Threshold.playerArea.height
        ));
        this.contentLayeredPane.setSize(this.contentLayeredPane.getPreferredSize());
        this.contentLayeredPane.revalidate();
        this.contentLayeredPane.repaint();
    }

    public final void initAudioPlayer() {
        this.audioPlayer = new Player(
            this, new Song(
                "", "", "01/01/1970", new Artist("", false, ""), new Genre(""),
                0, -1, -1, "./src/com/spotify/assets/icons/music_icon.png", ""
            ), isFirstTime
        );
        this.add(this.audioPlayer.getPlayedSong());
        this.add(this.audioPlayer.getMusicPlayer());
        this.add(this.audioPlayer.getPlayerControl());
    }

    public final void initContent(User user) {
        JPanel jPanel = new JPanel();
        jPanel.setBackground(new Color(18, 18, 18));

        this.contentDraggable = new ContentDraggable(this, user);
        this.contentPanel = new Content(jPanel);

        this.contentLayeredPane = new JLayeredPane();
        this.contentLayeredPane.setLocation(Threshold.sidebarArea.width, 0);
        this.contentLayeredPane.setPreferredSize(new Dimension(
            Threshold.currentSize.width - Threshold.sidebarArea.width,
            Threshold.currentSize.height - Threshold.playerArea.height
        ));
        this.contentLayeredPane.setSize(this.contentLayeredPane.getPreferredSize());

        this.contentLayeredPane.add(this.contentDraggable, JLayeredPane.PALETTE_LAYER);
        this.contentLayeredPane.add(this.contentPanel, JLayeredPane.DEFAULT_LAYER);

        this.add(this.contentLayeredPane);
    }

    public final void initSidebarPanel(User user, ArrayList<Account> accounts) {
        this.sidebarDraggable = new SidebarDraggable(this);
        this.sidebarPanel = new Sidebar(this, user, accounts);

        this.sidebarPanel.getHome().setActive(true);
        this.sidebarPanel.getHome().setIcon(this.sidebarPanel.getHome().getIconActive());
        this.sidebarPanel.getHome().setForeground(this.sidebarPanel.getHome().getColorForegroundActive());
        this.sidebarPanel.getHome().setBackground(this.sidebarPanel.getHome().getColorBackgroundActive());

        this.sidebarPanel.add(this.sidebarDraggable);
        this.add(this.sidebarPanel);
    }

    public final ComponentResizer initDynamicComponent() {
        ComponentResizer componentResizer = new ComponentResizer();

        componentResizer.registerComponent(this);
        componentResizer.setSnapSize(new Dimension(1, 1));
        componentResizer.setMinimumSize(Threshold.minimumSize);
        componentResizer.setMaximumSize(Threshold.maximumSize);

        return componentResizer;
    }

    public void changeContent(Content content, ContentDraggable titleBarContent, User user,
    ArrayList<Account> accounts) {
        this.getLayeredPane().remove(this.getContentDraggable());
        this.getLayeredPane().remove(this.getContentPanel());

        this.setContentDraggable(titleBarContent);
        this.setContentPanel(content);

        this.getLayeredPane().add(this.getContentDraggable(), JLayeredPane.PALETTE_LAYER);
        this.getLayeredPane().add(this.getContentPanel(), JLayeredPane.DEFAULT_LAYER);

        /*
        this.remove(this.getContentDraggable());
        this.remove(this.getContentPanel());

        this.setContentDraggable(titleBarContent);
        this.setContentPanel(content);

        this.add(this.getContentDraggable());
        this.add(this.getContentPanel());
        */

        this.revalidate();
        this.repaint();

        int button = 0;

        if(sidebarPanel.getHome().isActive) {
            button = 1;
        } else if(sidebarPanel.getSearch().isActive) {
            button = 2;
        } else if(sidebarPanel.getYourLibrary().isActive) {
            button = 3;
        } else if(sidebarPanel.getLikedSongs().isActive) {
            button = 4;
        }

        if(page + 1 == contentPanels.size()) {
            contentPanels.add(new ContentPanel(this.getContentPanel(), this.getContentDraggable(), button));
            page++;
        } else {
            page++;
            contentPanels.add(new ContentPanel(this.getContentPanel(), this.getContentDraggable(), button));
        }
    }

    public void previousPageButtonPressed() {
        System.out.println("page = " + page);
        System.out.println("contentPanels.size() = " + contentPanels.size());
        if(page > 0) {
            page--;
            /* System.out.println("page = " + page); */

            this.getLayeredPane().remove(this.getContentPanel());
            this.getLayeredPane().remove(this.getContentDraggable());
            this.remove(this.getSidebarPanel());

            this.setContentPanel(contentPanels.get(page).getContent());
            this.setContentDraggable(contentPanels.get(page).getTitleBarContent());
            /* this.setSidebar(contentPanels.get(page).getSideBar()); */
            resetSidebar(contentPanels.get(page).getButton());

            this.getLayeredPane().add(this.getContentPanel());
            this.getLayeredPane().add(this.getContentDraggable());
            this.add(this.getSidebarPanel());

            changeUserPlaylistBtnColor();

            this.resizeAllPanel();
            this.repaint();
            this.revalidate();
        }
    }

    public void nextPageButtonPressed() {
        this.resizeAllPanel();
        this.repaint();
        this.revalidate();

        System.out.println("page = " + page);
        System.out.println("contentPanels.size() = " + contentPanels.size());
        if(page < contentPanels.size() - 1) {
            page++;
            /* System.out.println("page = " + page); */

            this.getLayeredPane().remove(this.getContentPanel());
            this.getLayeredPane().remove(this.getContentDraggable());
            this.remove(this.getSidebarPanel());

            this.setContentPanel(contentPanels.get(page).getContent());
            this.setContentDraggable(contentPanels.get(page).getTitleBarContent());
            /* this.setSidebar(contentPanels.get(page).getSideBar()); */
            resetSidebar(contentPanels.get(page).getButton());

            this.getLayeredPane().add(this.getContentPanel());
            this.getLayeredPane().add(this.getContentDraggable());
            this.add(this.getSidebarPanel());

            changeUserPlaylistBtnColor();

            this.resizeAllPanel();
            this.repaint();
            this.revalidate();
        }
    }

    public void changeUserPlaylistBtnColor() {
        if(this.getContentPanel().getViewport().getView() instanceof PlaylistUI) {
            PlaylistUI tempUI = (PlaylistUI)this.getContentPanel().getViewport().getView();

            for(int i = 0; i < this.sidebarPanel.getPlaylistsButton().size(); i++) {
                if(i != tempUI.getPlaylistID()) {
                    sidebarPanel.getPlaylistsButton().get(i).setForeground(Threshold.palette_b3b3b3);
                }
            }

            if((tempUI.getPlaylistStyle() == PlaylistUI.CUSTOM_PLAYLIST ||
            tempUI.getPlaylistStyle() == PlaylistUI.UNUSED_PLAYLIST)) {
                activeUserPlaylist = tempUI.getPlaylistID();
                sidebarPanel.getPlaylistsButton().get(activeUserPlaylist).setForeground(Threshold.palette_ffffff);
            } else {
                activeUserPlaylist = -1;
            }
        } else {
            activeUserPlaylist = -1;

            for(int i = 0; i < this.sidebarPanel.getPlaylistsButton().size(); i++) {
                sidebarPanel.getPlaylistsButton().get(i).setForeground(Threshold.palette_b3b3b3);
            }
        }
    }

    public void resetNavbarButton(NavbarButton button) {
        button.setActive(false);
        button.setForeground(button.getColorForegroundDefault());
        button.setBackground(button.getColorBackgroundDefault());
        button.setIcon(button.getIconDefault());
    }

    public void resetSidebar(int button) {
        resetNavbarButton(this.sidebarPanel.getHome());
        resetNavbarButton(this.sidebarPanel.getSearch());
        resetNavbarButton(this.sidebarPanel.getYourLibrary());
        resetNavbarButton(this.sidebarPanel.getLikedSongs());
        if(button == 1) {
            this.sidebarPanel.getHome().setActive(true);
            this.sidebarPanel.getHome().setIcon(this.sidebarPanel.getHome().getIconActive());
            this.sidebarPanel.getHome().setForeground(this.sidebarPanel.getHome().getColorForegroundActive());
            this.sidebarPanel.getHome().setBackground(this.sidebarPanel.getHome().getColorBackgroundActive());
        } else if(button == 2) {
            this.sidebarPanel.getSearch().setActive(true);
            this.sidebarPanel.getSearch().setIcon(this.sidebarPanel.getSearch().getIconActive());
            this.sidebarPanel.getSearch().setForeground(this.sidebarPanel.getSearch().getColorForegroundActive());
            this.sidebarPanel.getSearch().setBackground(this.sidebarPanel.getSearch().getColorBackgroundActive());
        } else if(button == 3) {
            this.sidebarPanel.getYourLibrary().setActive(true);
            this.sidebarPanel.getYourLibrary().setIcon(this.sidebarPanel.getYourLibrary().getIconActive());
            this.sidebarPanel.getYourLibrary().setForeground(this.sidebarPanel.getYourLibrary().getColorForegroundActive());
            this.sidebarPanel.getYourLibrary().setBackground(this.sidebarPanel.getYourLibrary().getColorBackgroundActive());
        } else if(button == 4) {
            this.sidebarPanel.getLikedSongs().setActive(true);
            this.sidebarPanel.getLikedSongs().setIcon(this.sidebarPanel.getLikedSongs().getIconActive());
            this.sidebarPanel.getLikedSongs().setForeground(this.sidebarPanel.getLikedSongs().getColorForegroundActive());
            this.sidebarPanel.getLikedSongs().setBackground(this.sidebarPanel.getLikedSongs().getColorBackgroundActive());
        } else if(button == 0) {
            System.out.println("ERROR resetSidebar Button = 0");
        }
    }

    public static void initializeButton(JButton button) {
        button.setBackground(new Color(48, 48, 48));
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        button.setBorderPainted(false);
        //button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Gotham", Font.PLAIN, 18));
        button.setForeground(Threshold.palette_ffffff);
        button.setOpaque(true);
    }

    @Override
    public JLayeredPane getLayeredPane() {
        return contentLayeredPane;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<ContentPanel> getContentPanels() {
        return contentPanels;
    }

    public void setContentPanels(ArrayList<ContentPanel> contentPanels) {
        this.contentPanels = contentPanels;
    }

    @Override
    public void setLayeredPane(JLayeredPane layeredPane) {
        this.contentLayeredPane = layeredPane;
    }

    public Content getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(Content contentPanel) {
        this.contentPanel = contentPanel;
    }

    public Sidebar getSidebarPanel() {
        return sidebarPanel;
    }

    public void setSidebarPanel(Sidebar sidebarPanel) {
        this.sidebarPanel = sidebarPanel;
    }

    public ContentDraggable getContentDraggable() {
        return contentDraggable;
    }

    public Player getAudioPlayer() {
        return audioPlayer;
    }

    public void setAudioPlayer(Player audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    public void setContentDraggable(ContentDraggable contentDraggable) {
        this.contentDraggable = contentDraggable;
    }

    public SidebarDraggable getSidebarDraggable() {
        return sidebarDraggable;
    }

    public void setSidebarDraggable(SidebarDraggable sidebarDraggable) {
        this.sidebarDraggable = sidebarDraggable;
    }
}
