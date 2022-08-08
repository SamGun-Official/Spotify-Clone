package com.spotify.ui.playlist;

import com.spotify.app.Account;
import com.spotify.app.Artist;
import com.spotify.app.Playlist;
import com.spotify.app.Song;
import com.spotify.ui.Content;
import com.spotify.ui.ContentDraggable;
import com.spotify.ui.Threshold;
import com.spotify.ui.Window;
import com.spotify.ui.graphics.LinearGradient;
import com.spotify.ui.graphics.TranslucentScrollBarUI;
import com.spotify.ui.profile.ArtistProfile;
import com.spotify.ui.profile.ContentOtherUserProfile;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class PlaylistUI extends JPanel implements com.spotify.app.Utility {
    public static final int UNUSED_PLAYLIST = 0;
    public static final int NATIVE_PLAYLIST = 1;
    public static final int CUSTOM_PLAYLIST = 2;
    public static final int LIKED_PLAYLIST = 100;
    public static final int QUEUE_PLAYLIST = 101;

    protected boolean summonEMH = false;
    private int paddingX, paddingY;
    private final int playlistStyle;
    private final int playlistID;

    protected JPanel mainInfo;
    protected LinearGradient infoGradientPanel;
    protected JPanel innerPanel;
    protected JButton imageHolder;
    protected JPanel infoPanel;
    protected JLabel playlistType;
    protected JLabel playlistTitle;
    protected JButton albumOwner;
    protected JLabel playlistInfo;

    protected int solidColoredPanelHeightConstant;
    protected LinearGradient linearGradientPanel;
    protected JPanel solidColoredPanel;
    protected JLayeredPane mainList;

    protected LinearGradient rootPaneHeaderJacket;
    protected JPanel rootPaneHeader;
    protected int rootPaneOpacity = 0; // Alpha color between 0 - 255
    protected JPanel innerHolder;
    protected JScrollPane contentHolder;
    protected JScrollBar scrollBar;
    protected JPanel scrollBarHolder;
    protected JPanel stickyHeaderRow;
    protected JLayeredPane infoSectionWrapper;

    protected JPanel actionBarRow;
    protected JButton playAllBtn;
    protected JButton addLibraryBtn;
    protected JButton expandedMenuBtn;

    protected JPanel trackList;
    protected JPanel headerWrapper;
    protected JPanel trackTableList;
    protected JPanel copyrightHolderInfo;
    protected ColumnHeader columnHeader;
    protected ColumnHeader dupColumnHeader;

    protected ArrayList<Account> accounts;

    public PlaylistUI(ArrayList<Account> accounts, Playlist activePlaylist, Window windowApp, int playlistStyle,
    int playlistID) {
        this.accounts = accounts;
        this.playlistStyle = playlistStyle;
        this.playlistID = playlistID;

        this.setLayout(null);
        this.setPreferredSize(new Dimension(
            Threshold.contentArea.width, Threshold.contentArea.height
        ));
        this.setSize(this.getPreferredSize());

        this.initMainInfo(
            windowApp, activePlaylist.getBackdropColor(), activePlaylist.getArtImage(), activePlaylist.getType(),
            activePlaylist.getName(), activePlaylist.getOwner(), activePlaylist.getDateCreated(),
            activePlaylist.getTotalSongs(), activePlaylist.getTotalDurations(), activePlaylist.getTotalLikes()
        );
        this.initMainList(
            activePlaylist.getBackdropColor(), activePlaylist.getSongs(), activePlaylist.getName(), playlistID,
            windowApp
        );
        this.initScrollableHolder(
            activePlaylist.getBackdropColor(), windowApp
        );

        windowApp.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                setPreferredSize(new Dimension(
                    Threshold.currentSize.width - Threshold.sidebarArea.width,
                    Threshold.currentSize.height - Threshold.playerArea.height
                ));
                setSize(getPreferredSize());
                revalidate();
                repaint();

                resizeMainInfo(activePlaylist.getArtImage());
                resizeMainList(windowApp);
                resizeScrollableHolder(windowApp);
            }
        });
    }

    /**
     *  Methods that resizing PlaylistUI main panel to fit
     *  currentSize of Threshold.contentArea.
     */
    public final void resizeMainPanel() {
        this.setPreferredSize(new Dimension(
            Threshold.currentSize.width - Threshold.sidebarArea.width,
            Threshold.currentSize.height - Threshold.playerArea.height
        ));
        this.setSize(this.getPreferredSize());
    }

    /**
     *  Resizing main info panel and its components.
     *
     *  @param imagePath Filepath to imageFile
     */
    public final void resizeMainInfo(String imagePath) {
        mainInfo.setPreferredSize(new Dimension(
            Threshold.currentSize.width - Threshold.sidebarArea.width, 340
        ));
        mainInfo.setSize(mainInfo.getPreferredSize());
        mainInfo.revalidate();
        mainInfo.repaint();

        infoGradientPanel.setPreferredSize(mainInfo.getPreferredSize());
        infoGradientPanel.setSize(infoGradientPanel.getPreferredSize());
        infoGradientPanel.revalidate();
        infoGradientPanel.repaint();

        if(Threshold.currentSize.width >= 1280) {
            paddingX = 32;
            paddingY = 24;
        } else {
            paddingX = 16;
            paddingY = 24;
        }

        innerPanel.setBounds(
            paddingX, 0, mainInfo.getWidth() - (paddingX * 2), mainInfo.getHeight() - paddingY
        );
        innerPanel.setPreferredSize(innerPanel.getSize());
        innerPanel.revalidate();
        innerPanel.repaint();

        if(Threshold.currentSize.width >= 1280) {
            imageHolder.setMaximumSize(new Dimension(232, 232));
            imageHolder.setMinimumSize(new Dimension(232, 232));
            imageHolder.setPreferredSize(imageHolder.getMaximumSize());
            imageHolder.setSize(imageHolder.getPreferredSize());
        } else {
            imageHolder.setMaximumSize(new Dimension(192, 192));
            imageHolder.setMinimumSize(new Dimension(192, 192));
            imageHolder.setPreferredSize(imageHolder.getMinimumSize());
            imageHolder.setSize(imageHolder.getPreferredSize());
        }

        imageHolder.setIcon(getArtImage(imagePath));
        imageHolder.revalidate();
        imageHolder.repaint();

        infoPanel.setPreferredSize(new Dimension(
            mainInfo.getWidth() - imageHolder.getWidth() - 24, imageHolder.getHeight()
        ));
        infoPanel.setSize(infoPanel.getPreferredSize());
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    /**
     *  Resizing bottom panel (mainList) of main panel to fit
     *  currentSize of mainPanel (Padding are also dynamically
     *  calculated during this (sub-)methods.
     *
     *  @param windowApp Referencing to contentPanel that is in
     *                   windowApp (Used by trackList panel)
     */
    public final void resizeMainList(Window windowApp) {
        this.resizeActionBarRow();
        this.resizeTrackList(windowApp);

        linearGradientPanel.setPreferredSize(new Dimension(
            Threshold.currentSize.width - Threshold.sidebarArea.width, 232
        ));
        linearGradientPanel.setSize(linearGradientPanel.getPreferredSize());
        linearGradientPanel.revalidate();
        linearGradientPanel.repaint();

        solidColoredPanel.setPreferredSize(new Dimension(
            Threshold.contentArea.width, solidColoredPanelHeightConstant
        ));
        solidColoredPanel.setSize(solidColoredPanel.getPreferredSize());
        solidColoredPanel.revalidate();
        solidColoredPanel.repaint();

        int mainListHeight;

        if(actionBarRow.getHeight() + trackList.getHeight() > Threshold.contentArea.height - mainInfo.getHeight()) {
            mainListHeight = actionBarRow.getHeight() + trackList.getHeight();
        } else {
            mainListHeight = Threshold.contentArea.height - mainInfo.getHeight();
        }

        mainList.setPreferredSize(new Dimension(
            Threshold.contentArea.width, mainListHeight
        ));
        mainList.setSize(mainList.getPreferredSize());
        mainList.revalidate();
        mainList.repaint();
    }

    /**
     *  Method is called after resizing mainInfo and mainList to
     *  determine height of layeredPane that hold panel toward
     *  main scrollBar.
     *
     *  @param windowApp Referencing to contentDraggablePanel that is in
     *                   windowApp.
     */
    public final void resizeScrollableHolder(Window windowApp) {
        rootPaneHeaderJacket.setBounds(windowApp.getContentDraggable().getBounds());
        rootPaneHeaderJacket.setPreferredSize(rootPaneHeaderJacket.getSize());
        rootPaneHeaderJacket.revalidate();
        rootPaneHeaderJacket.repaint();

        rootPaneHeader.setBounds(windowApp.getContentDraggable().getBounds());
        rootPaneHeader.setPreferredSize(rootPaneHeader.getSize());
        rootPaneHeader.revalidate();
        rootPaneHeader.repaint();

        innerHolder.setPreferredSize(new Dimension(
            (int)getPreferredSize().getWidth(), mainInfo.getHeight() + mainList.getHeight()
        ));
        innerHolder.setSize(innerHolder.getPreferredSize());
        innerHolder.revalidate();
        innerHolder.repaint();

        contentHolder.setPreferredSize(getPreferredSize());
        contentHolder.setSize(getPreferredSize());
        contentHolder.revalidate();
        contentHolder.repaint();

        scrollBar.setPreferredSize(new Dimension(
            14, (int)getPreferredSize().getHeight() - 50 + 7
        ));
        scrollBar.revalidate();
        scrollBar.repaint();

        stickyHeaderRow.setPreferredSize(new Dimension(
            Threshold.contentArea.width, Threshold.contentArea.height
        ));
        stickyHeaderRow.setSize(stickyHeaderRow.getPreferredSize());
        stickyHeaderRow.revalidate();
        stickyHeaderRow.repaint();

        scrollBarHolder.setPreferredSize(getPreferredSize());
        scrollBarHolder.setSize(scrollBarHolder.getPreferredSize());
        scrollBarHolder.revalidate();
        scrollBarHolder.repaint();

        infoSectionWrapper.setPreferredSize(getPreferredSize());
        infoSectionWrapper.setSize(infoSectionWrapper.getPreferredSize());
        infoSectionWrapper.revalidate();
        infoSectionWrapper.repaint();
    }

    /**
     *  Resize actionBarRow and its components.
     */
    public final void resizeActionBarRow() {
        actionBarRow.setPreferredSize(new Dimension(
            Threshold.currentSize.width - Threshold.sidebarArea.width, 56 + (paddingY * 2)
        ));
        actionBarRow.setSize(actionBarRow.getPreferredSize());
        actionBarRow.revalidate();
        actionBarRow.repaint();

        if(playlistStyle != UNUSED_PLAYLIST) {
            playAllBtn.setLocation(paddingX, paddingY);
            playAllBtn.setPreferredSize(playAllBtn.getMinimumSize());
            playAllBtn.setSize(playAllBtn.getPreferredSize());
            playAllBtn.revalidate();
            playAllBtn.repaint();
        }

        if(playlistStyle != LIKED_PLAYLIST && playlistStyle != QUEUE_PLAYLIST) {
            /*
            if(playlistStyle == NATIVE_PLAYLIST) {
                addLibraryBtn.setLocation(paddingX + 56 + 32, (actionBarRow.getHeight() - 32) / 2);
                addLibraryBtn.setPreferredSize(addLibraryBtn.getMaximumSize());
                addLibraryBtn.setSize(addLibraryBtn.getPreferredSize());
                addLibraryBtn.revalidate();
                addLibraryBtn.repaint();

                expandedMenuBtn.setLocation(paddingX + 56 + 32 + 32 + 24, ((actionBarRow.getHeight() - 32) / 2) - 0);
            } else if(playlistStyle == CUSTOM_PLAYLIST) {
                expandedMenuBtn.setLocation(paddingX + 56 + 32, ((actionBarRow.getHeight() - 32) / 2) - 0);
            } else if(playlistStyle == UNUSED_PLAYLIST) {
                expandedMenuBtn.setLocation(paddingX, ((actionBarRow.getHeight() - 32) / 2) - 0);
            }
            */
            if(playlistStyle == NATIVE_PLAYLIST || playlistStyle == CUSTOM_PLAYLIST) {
                expandedMenuBtn.setLocation(paddingX + 56 + 32, ((actionBarRow.getHeight() - 32) / 2) - 0);
            } else if(playlistStyle == UNUSED_PLAYLIST) {
                expandedMenuBtn.setLocation(paddingX, ((actionBarRow.getHeight() - 32) / 2) - 0);
            }

            expandedMenuBtn.setPreferredSize(expandedMenuBtn.getMaximumSize());
            expandedMenuBtn.setSize(expandedMenuBtn.getPreferredSize());
            expandedMenuBtn.revalidate();
            expandedMenuBtn.repaint();
        }
    }

    /**
     *  Resize trackList panel and all of its components.
     *
     *  @param windowApp Referencing to contentPanel that is in
     *                   windowApp to determine positions of
     *                   stickyPanel.
     */
    public final void resizeTrackList(Window windowApp) {
        int gapSize;

        if(Threshold.currentSize.width >= 1280) {
            gapSize = 16;
        } else {
            gapSize = 10;
        }

        trackList.setPreferredSize(new Dimension(
            Threshold.currentSize.width - Threshold.sidebarArea.width, columnHeader.getHeight() +
            gapSize + trackTableList.getHeight() + copyrightHolderInfo.getHeight()
        ));
        trackList.setSize(trackList.getPreferredSize());
        trackList.revalidate();
        trackList.repaint();

        dupColumnHeader.setLocation(paddingX + 1, 0);
        dupColumnHeader.setPreferredSize(new Dimension(
            trackList.getWidth() - (paddingX * 2) - 2, dupColumnHeader.getHeight()
        ));
        dupColumnHeader.setSize(dupColumnHeader.getPreferredSize());
        dupColumnHeader.revalidate();
        dupColumnHeader.repaint();
        dupColumnHeader.resizeAll();

        headerWrapper.setLocation(0, windowApp.getContentDraggable().getHeight());
        headerWrapper.setPreferredSize(new Dimension(
            trackList.getWidth(), headerWrapper.getHeight()
        ));
        headerWrapper.setSize(headerWrapper.getPreferredSize());
        headerWrapper.revalidate();
        headerWrapper.repaint();

        columnHeader.setLocation(paddingX + 1, 0);
        columnHeader.setPreferredSize(new Dimension(
            trackList.getWidth() - (paddingX * 2) - 2, columnHeader.getHeight()
        ));
        columnHeader.setSize(columnHeader.getPreferredSize());
        columnHeader.revalidate();
        columnHeader.repaint();
        columnHeader.resizeAll();

        trackTableList.setBounds(
            paddingX + 1, columnHeader.getHeight() + gapSize, columnHeader.getWidth(), trackTableList.getHeight()
        );
        trackTableList.setPreferredSize(trackTableList.getSize());
        trackTableList.revalidate();
        trackTableList.repaint();

        for(int i = 0; i < trackTableList.getComponentCount(); i++) {
            TrackRow localFieldResizer = (TrackRow)trackTableList.getComponent(i);
            localFieldResizer.setPreferredSize(new Dimension(
                trackTableList.getWidth(), localFieldResizer.getHeight()
            ));
            localFieldResizer.setSize(localFieldResizer.getPreferredSize());
            localFieldResizer.resizeAll();
            localFieldResizer.revalidate();
            localFieldResizer.repaint();
        }

        copyrightHolderInfo.setBounds(
            0, columnHeader.getHeight() + gapSize + trackTableList.getHeight(), Threshold.contentArea.width, 32
        );
        copyrightHolderInfo.setPreferredSize(copyrightHolderInfo.getSize());
        copyrightHolderInfo.revalidate();
        copyrightHolderInfo.repaint();

        trackList.setPreferredSize(new Dimension(
            Threshold.currentSize.width - Threshold.sidebarArea.width, columnHeader.getHeight() +
            gapSize + trackTableList.getHeight() + copyrightHolderInfo.getHeight()
        ));
        trackList.setSize(trackList.getPreferredSize());
        trackList.revalidate();
        trackList.repaint();
    }

    /**
     *  Dynamically set playlist info based on given parameters.
     *
     *  @param backdropColor Color that are used as background
     *  @param imagePath String filePath to get artImage
     *  @param listType Get playlist type either its native categorized or
     *                  custom categorized (Such as Queue, Liked Songs, etc.)
     *  @param listName Playlist name
     *  @param listOwner Contributor of this playlist
     *                   (Can be artists, or users)
     *  @param dateCreated Date created of this playlist
     *  @param totalSongs Total songs in number
     *  @param totalDurations Overall duration in number
     *  @param totalLikes
     */
    public final void initMainInfo(Window windowApp, Color backdropColor, String imagePath, String listType,
    String listName, String listOwner, String dateCreated, int totalSongs, int totalDurations, int totalLikes) {
        mainInfo = new JPanel();
        mainInfo.setBackground(backdropColor);
        mainInfo.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(40, 40, 40)));
        mainInfo.setBounds(
            0, 0, Threshold.contentArea.width, 340
        );
        mainInfo.setLayout(null);
        mainInfo.setPreferredSize(mainInfo.getSize());

        infoGradientPanel = new LinearGradient(new Color(0, 0, 0, 0), new Color(0, 0, 0, 128));
        infoGradientPanel.setBounds(mainInfo.getBounds());
        infoGradientPanel.setLayout(null);
        infoGradientPanel.setOpaque(false);
        infoGradientPanel.setPreferredSize(infoGradientPanel.getPreferredSize());

        mainInfo.add(infoGradientPanel);

        if(Threshold.currentSize.width >= 1280) {
            paddingX = 32;
            paddingY = 24;
        } else {
            paddingX = 16;
            paddingY = 24;
        }

        innerPanel = new JPanel();
        innerPanel.setBorder(null);
        innerPanel.setBounds(
            paddingX, 0, mainInfo.getWidth() - (paddingX * 2), mainInfo.getHeight() - paddingY
        );
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.LINE_AXIS));
        innerPanel.setOpaque(false);
        innerPanel.setPreferredSize(innerPanel.getSize());

        imageHolder = new JButton();
        imageHolder.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        imageHolder.setBackground(new Color(40, 40, 40));
        imageHolder.setBorder(null);
        imageHolder.setBorderPainted(false);
        imageHolder.setContentAreaFilled(false);
        imageHolder.setFocusable(false);
        imageHolder.setFocusPainted(false);
        imageHolder.setIcon(getArtImage(imagePath));
        imageHolder.setName("Image_Holder");
        imageHolder.setOpaque(true);
        imageHolder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }
        });

        if(Threshold.currentSize.width >= 1280) {
            imageHolder.setMaximumSize(new Dimension(232, 232));
            imageHolder.setMinimumSize(new Dimension(232, 232));
            imageHolder.setPreferredSize(imageHolder.getMaximumSize());
            imageHolder.setSize(imageHolder.getMaximumSize());
        } else {
            imageHolder.setMaximumSize(new Dimension(192, 192));
            imageHolder.setMinimumSize(new Dimension(192, 192));
            imageHolder.setPreferredSize(imageHolder.getMinimumSize());
            imageHolder.setSize(imageHolder.getMinimumSize());
        }

        infoPanel = new JPanel();
        infoPanel.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        infoPanel.setBorder(null);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setPreferredSize(new Dimension(
            mainInfo.getWidth() - imageHolder.getWidth() - 24, imageHolder.getHeight()
        ));
        infoPanel.setSize(infoPanel.getPreferredSize());

        /* 47 --> Top Plus 1 --> Middle Plus 2 */
        /* 71 --> Top Plus 1 --> Middle Plus 1 */
        /* 95 --> Top Plus 0 --> Middle Plus 0 */

        playlistType = new JLabel(listType);
        playlistType.setAlignmentX(SwingConstants.LEFT);
        playlistType.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        playlistType.setFont(new Font("Gotham", Font.BOLD, 12));
        playlistType.setForeground(Threshold.palette_ffffff);
        playlistType.setHorizontalAlignment(SwingConstants.LEFT);
        playlistType.setMaximumSize(new Dimension(
            Integer.MAX_VALUE, (int)playlistType.getPreferredSize().getHeight()
        ));
        playlistType.setOpaque(false);

//        JLabel playlistTitle = new JLabel("<html>" + listName + "</html>");
        playlistTitle = new JLabel(listName);
        playlistTitle.setAlignmentX(SwingConstants.LEFT);
        playlistTitle.setAlignmentY(JComponent.BOTTOM_ALIGNMENT);
        playlistTitle.setFont(new Font("Gotham", Font.BOLD, 47));
        playlistTitle.setForeground(Threshold.palette_ffffff);
        playlistTitle.setHorizontalAlignment(SwingConstants.LEFT);
        playlistTitle.setMaximumSize(new Dimension(
            Integer.MAX_VALUE, (int)playlistTitle.getPreferredSize().getHeight()
        ));
        playlistTitle.setOpaque(false);

        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, -0.05);
        playlistTitle.setFont(playlistTitle.getFont().deriveFont(attributes));

        albumOwner = new JButton(listOwner);
        albumOwner.setBorder(null);
        albumOwner.setBorderPainted(false);
        albumOwner.setContentAreaFilled(false);
        albumOwner.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        albumOwner.setFocusable(false);
        albumOwner.setFocusPainted(false);
        albumOwner.setFont(new Font("Gotham", Font.BOLD, 14));
        albumOwner.setForeground(Threshold.palette_ffffff);
        albumOwner.setHorizontalAlignment(SwingConstants.LEFT);
        albumOwner.setOpaque(false);
        albumOwner.setVerticalAlignment(SwingConstants.BOTTOM);
        albumOwner.addActionListener((ActionEvent ae) -> {
            if(playlistStyle == NATIVE_PLAYLIST) {
                Artist tempArtist = null;

                for(int i = 0; i < windowApp.admin.getArtists().size(); i++) {
                    if(windowApp.admin.getArtists().get(i).getName().equals(listOwner)) {
                        tempArtist = windowApp.admin.getArtists().get(i);
                    }
                }

                ArtistProfile ap = new ArtistProfile(tempArtist, windowApp);
                windowApp.changeContent(
                    new Content(ap, true), new ContentDraggable(windowApp, windowApp.user), windowApp.user,
                    windowApp.accounts
                );
            } else {
                ContentOtherUserProfile co = new ContentOtherUserProfile(windowApp, windowApp.user, windowApp.user);
                windowApp.changeContent(
                    new Content(co, true), new ContentDraggable(windowApp, windowApp.user), windowApp.user,
                    windowApp.accounts
                );
            }
        });
        albumOwner.addMouseListener(new MouseAdapter() {
            Font font = albumOwner.getFont();
            Map attributes = font.getAttributes();

            @Override
            public void mouseEntered(MouseEvent me) {
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                albumOwner.setFont(font.deriveFont(attributes));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                attributes.put(TextAttribute.UNDERLINE, null);
                albumOwner.setFont(font.deriveFont(attributes));
            }
        });

        playlistInfo = new JLabel(getStampString(dateCreated.split(" "), totalLikes, totalSongs, totalDurations));
        playlistInfo.setFont(new Font("Gotham", Font.PLAIN, 14));
        playlistInfo.setForeground(Threshold.palette_b3b3b3);
        playlistInfo.setHorizontalAlignment(SwingConstants.LEFT);
        playlistInfo.setOpaque(false);

        JPanel emptyInfo = new JPanel();
        emptyInfo.setBorder(null);
        emptyInfo.setLayout(new BoxLayout(emptyInfo, BoxLayout.X_AXIS));
        emptyInfo.setOpaque(false);
        emptyInfo.add(albumOwner);
        emptyInfo.setMaximumSize(new Dimension(
            Integer.MAX_VALUE, (int)emptyInfo.getPreferredSize().getHeight()
        ));

        if(!playlistInfo.getText().equalsIgnoreCase("")) {
            emptyInfo.add(playlistInfo);
        }

        /* Font Tengah: 47 */
        infoPanel.add(playlistType, -1);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 0)), -1);
        infoPanel.add(playlistTitle, -1);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 11)), -1);
        infoPanel.add(emptyInfo, -1);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)), -1);
        /* Need to add picture */

//        /* Font Tengah: 71 */
//        infoPanel.add(playlistType, -1);
//        infoPanel.add(Box.createRigidArea(new Dimension(0, -1)), -1);
//        infoPanel.add(playlistTitle, -1);
//        infoPanel.add(Box.createRigidArea(new Dimension(0, 9)), -1);
//        infoPanel.add(emptyInfo, -1);
//        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)), -1);
//        /* Need to add picture */

//        /* Font Tengah: 95 */
//        infoPanel.add(playlistType, -1);
//        infoPanel.add(Box.createRigidArea(new Dimension(0, -2)), -1);
//        infoPanel.add(playlistTitle, -1);
//        infoPanel.add(Box.createRigidArea(new Dimension(0, 8)), -1);
//        infoPanel.add(emptyInfo, -1);
//        infoPanel.add(Box.createRigidArea(new Dimension(0, 3)), -1);
//        /* Need to add picture */

        innerPanel.add(imageHolder);
        innerPanel.add(Box.createRigidArea(new Dimension(24, 0)));
        innerPanel.add(infoPanel);

        infoGradientPanel.add(innerPanel);
    }

    /**
     *  Fetch songs and display it in panel as a list.
     *
     *  @param backdropColor Color that are used as background
     *  @param songs Song data to be passed on trackTableList
     *  @param playlistName Referencing to windowApp variable
     *  @param playlistID
     *  @param windowApp Referencing to contentPanel that is in
     *                   windowApp to pass song data to each row
     */
    public final void initMainList(Color backdropColor, ArrayList<Song> songs, String playlistName, int playlistID,
    Window windowApp) {
        this.initActionBarRow(windowApp, playlistName);
        this.initTrackList(songs, playlistName, playlistID, windowApp);

        linearGradientPanel = new LinearGradient(new Color(0, 0, 0, 153), new Color(18, 18, 18, 255));
        linearGradientPanel.setBounds(
            0, 0, Threshold.contentArea.width, 232
        );
        linearGradientPanel.setLayout(null);
        linearGradientPanel.setOpaque(false);
        linearGradientPanel.setPreferredSize(linearGradientPanel.getPreferredSize());

        if(actionBarRow.getHeight() + trackList.getHeight() > linearGradientPanel.getHeight()) {
            solidColoredPanelHeightConstant = (
                Threshold.contentArea.height - actionBarRow.getHeight() +
                trackList.getHeight() - linearGradientPanel.getHeight()
            );
        } else {
            if(Threshold.contentArea.height > mainInfo.getHeight() + linearGradientPanel.getHeight()) {
                solidColoredPanelHeightConstant = Threshold.contentArea.height - mainInfo.getHeight() +
                linearGradientPanel.getHeight();
            } else {
                solidColoredPanelHeightConstant = 0;
            }
        }

        solidColoredPanel = new JPanel();
        solidColoredPanel.setBackground(new Color(18, 18, 18));
        solidColoredPanel.setBorder(null);
        solidColoredPanel.setBounds(
            0, linearGradientPanel.getHeight(), Threshold.contentArea.width, solidColoredPanelHeightConstant
        );
        solidColoredPanel.setLayout(null);
        solidColoredPanel.setOpaque(true);
        solidColoredPanel.setPreferredSize(solidColoredPanel.getPreferredSize());

        mainList = new JLayeredPane();
        mainList.setBackground(backdropColor);
        mainList.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(40, 40, 40)));

        if(actionBarRow.getHeight() + trackList.getHeight() > Threshold.contentArea.height - mainInfo.getHeight()) {
            mainList.setBounds(
                0, mainInfo.getHeight(), Threshold.contentArea.width,
                actionBarRow.getHeight() + trackList.getHeight()
            );
        } else {
            mainList.setBounds(
                0, mainInfo.getHeight(), Threshold.contentArea.width,
                Threshold.contentArea.height - mainInfo.getHeight()
            );
        }

        mainList.setLayout(null);
        mainList.setOpaque(true);
        mainList.setPreferredSize(mainList.getSize());
        mainList.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                if(mainList.getHeight() > linearGradientPanel.getHeight()) {
                    solidColoredPanelHeightConstant = mainList.getHeight() - linearGradientPanel.getHeight();

                    solidColoredPanel.setPreferredSize(new Dimension(
                        (int)solidColoredPanel.getPreferredSize().getWidth(), solidColoredPanelHeightConstant
                    ));
                    solidColoredPanel.setSize(solidColoredPanel.getPreferredSize());
                }
            }
        });

        mainList.add(actionBarRow, JLayeredPane.PALETTE_LAYER);
        mainList.add(trackList, JLayeredPane.PALETTE_LAYER);
        mainList.add(linearGradientPanel, JLayeredPane.DEFAULT_LAYER);
        mainList.add(solidColoredPanel, JLayeredPane.DEFAULT_LAYER);
    }

    /**
     *  Assemble info & list panel into single scrollable panel.
     *
     *  @param backdropColor Color that are used in stickyPanel
     *  @param windowApp Referencing to contentPanel that is in
     *                   windowApp to determine position of stickyPanel
     */
    public final void initScrollableHolder(Color backdropColor, Window windowApp) {
        rootPaneHeaderJacket = new LinearGradient(new Color(0, 0, 0, 153), new Color(0, 0, 0, 153));
        rootPaneHeaderJacket.setBounds(windowApp.getContentDraggable().getBounds());
        rootPaneHeaderJacket.setLayout(null);
        rootPaneHeaderJacket.setOpaque(false);
        rootPaneHeaderJacket.setPreferredSize(rootPaneHeaderJacket.getSize());

        rootPaneHeader = new JPanel();
        rootPaneHeader.add(rootPaneHeaderJacket);
        rootPaneHeader.setBackground(new Color(
            backdropColor.getRed(), backdropColor.getGreen(), backdropColor.getBlue(), rootPaneOpacity
        ));
        rootPaneHeader.setBounds(windowApp.getContentDraggable().getBounds());
        rootPaneHeader.setLayout(null);
        rootPaneHeader.setOpaque(true);
        rootPaneHeader.setPreferredSize(rootPaneHeader.getSize());
        rootPaneHeader.setVisible(false);

        innerHolder = new JPanel();
        innerHolder.setLayout(null);
        innerHolder.setPreferredSize(new Dimension(
            Threshold.contentArea.width, mainInfo.getHeight() + mainList.getHeight()
        ));
        innerHolder.setSize(innerHolder.getPreferredSize());
        innerHolder.addMouseWheelListener((MouseWheelEvent mwe) -> {
            if(mwe.getUnitsToScroll() > 0) {
                scrollBar.setValue(scrollBar.getValue() + 100);
            } else {
                scrollBar.setValue(scrollBar.getValue() - 100);
            }

            slideRootPaneHeaderOpacity(backdropColor);
            showColumnHeader();
        });

        innerHolder.add(mainInfo, -1);
        innerHolder.add(mainList, -1);

        contentHolder = new JScrollPane(innerHolder);
        contentHolder.setBackground(new Color(18, 18, 18));
        contentHolder.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(40, 40, 40)));
        contentHolder.setBounds(
            0, 0, Threshold.contentArea.width, Threshold.contentArea.height
        );
        contentHolder.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentHolder.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        contentHolder.getVerticalScrollBar().setUnitIncrement(10);
        contentHolder.setVisible(true);

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 1;

        scrollBar = new JScrollBar();
        scrollBar.setModel(contentHolder.getVerticalScrollBar().getModel());
        scrollBar.setOpaque(false);
        scrollBar.setPreferredSize(new Dimension(14, Threshold.contentArea.height - 50 + 7));
        scrollBar.addAdjustmentListener((AdjustmentEvent ae) -> {
            slideRootPaneHeaderOpacity(backdropColor);
            showColumnHeader();
        });
        scrollBar.setUI(new TranslucentScrollBarUI(new Color(255, 255, 255, 77)) {
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D)g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setPaint(color);
                g2.fillRect(r.x, r.y, r.width - 2, r.height - 2);
                g2.dispose();
            }
        });

        stickyHeaderRow = new JPanel();
        stickyHeaderRow.add(headerWrapper);
        stickyHeaderRow.setBackground(Color.YELLOW);
        stickyHeaderRow.setBorder(null);
        stickyHeaderRow.setLayout(null);
        stickyHeaderRow.setOpaque(false);
        stickyHeaderRow.setPreferredSize(new Dimension(
            Threshold.contentArea.width, Threshold.contentArea.height
        ));
        stickyHeaderRow.setSize(stickyHeaderRow.getPreferredSize());
        stickyHeaderRow.setVisible(true);

        scrollBarHolder = new JPanel(new GridBagLayout());
        scrollBarHolder.add(scrollBar, gridConstraints);
        scrollBarHolder.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 40, 40)));
        scrollBarHolder.setOpaque(false);
        scrollBarHolder.setPreferredSize(new Dimension(
            Threshold.contentArea.width, Threshold.contentArea.height
        ));
        scrollBarHolder.setSize(scrollBarHolder.getPreferredSize());
        scrollBarHolder.setVisible(true);

        infoSectionWrapper = new JLayeredPane();
        infoSectionWrapper.setName("infoSectionWrapper");
        infoSectionWrapper.setPreferredSize(new Dimension(
            Threshold.contentArea.width, Threshold.contentArea.height
        ));
        infoSectionWrapper.setSize(infoSectionWrapper.getPreferredSize());

        infoSectionWrapper.add(stickyHeaderRow, JLayeredPane.PALETTE_LAYER);
        infoSectionWrapper.add(scrollBarHolder, JLayeredPane.PALETTE_LAYER);
        infoSectionWrapper.add(contentHolder, JLayeredPane.DEFAULT_LAYER);

        this.add(rootPaneHeader);
        this.add(infoSectionWrapper);
    }

    /**
     *  Supposedly to make slow and smooth appearance of stickyPanel but
     *  this method need to be optimized. Major issues are flashing and
     *  flickering.
     *
     *  @param backdropColor Color to be adjusted based on scrollBarEvent.
     */
    public final void slideRootPaneHeaderOpacity(Color backdropColor) {
        if(scrollBar.getValue() > 280) {
            rootPaneHeader.setVisible(true);
            rootPaneHeader.setBackground(new Color(
                backdropColor.getRed(), backdropColor.getGreen(), backdropColor.getBlue(), 255
            ));
        } else {
            rootPaneHeader.setVisible(false);
            rootPaneOpacity = 0;
        }
    }

    /**
     *  Method to set visibility of stickyPanel on scrollBarEvent.
     */
    public final void showColumnHeader() {
        if(scrollBar.getValue() >= mainInfo.getHeight() + actionBarRow.getHeight() - 60) {
            headerWrapper.setVisible(true);
        } else {
            headerWrapper.setVisible(false);
        }
    }

    /**
     *  Method to set actionBarRow.
     *
     *  @param windowApp
     *  @param playlistName
     */
    public final void initActionBarRow(Window windowApp, String playlistName) {
        actionBarRow = new JPanel();
        actionBarRow.setBackground(Color.pink);
        actionBarRow.setBorder(null);
        actionBarRow.setBounds(
            0, 0, Threshold.contentArea.width, 56 + (paddingY * 2)
        );
        actionBarRow.setLayout(null);
        actionBarRow.setOpaque(false);
        actionBarRow.setPreferredSize(actionBarRow.getSize());

        if(playlistStyle != UNUSED_PLAYLIST) {
            if(windowApp.activePlaylistName.equals(playlistName) && windowApp.activePlayer == true) {
                playAllBtn = new JButton(scaleImageIcon(
                    "./src/com/spotify/assets/icons/pause_white_rounded.png", 56, 56
                ));
                playAllBtn.setName("Pause_Button");
            } else {
                playAllBtn = new JButton(scaleImageIcon(
                    "./src/com/spotify/assets/icons/play_white_rounded.png", 56, 56
                ));
                playAllBtn.setName("Play_Button");
            }
            playAllBtn.setBackground(Color.green);
            playAllBtn.setBorder(null);
            playAllBtn.setBorderPainted(false);
            playAllBtn.setContentAreaFilled(false);
            playAllBtn.setFocusable(false);
            playAllBtn.setFocusPainted(false);
            playAllBtn.setLocation(paddingX, paddingY);
            playAllBtn.setMaximumSize(new Dimension(60, 60));
            playAllBtn.setMinimumSize(new Dimension(56, 56));
            playAllBtn.setOpaque(false);
            playAllBtn.setPreferredSize(playAllBtn.getMinimumSize());
            playAllBtn.setSize(playAllBtn.getPreferredSize());
            //<editor-fold defaultstate="collapsed" desc="MouseEvent handler for play all button.">
            playAllBtn.addActionListener((ActionEvent ae) -> {
                if(!windowApp.activePlaylistName.equals(playlistName)) {
                    if(windowApp.audioPlayer.isShuffle() == true) {
                        windowApp.countOnShuffle = 0;
                    }

                    TrackRow tempRow = (TrackRow)this.getTrackTableList().getComponent(0);
                    tempRow.playButtonAction(windowApp);
                } else {
                    TrackRow tempRow = (TrackRow)this.getTrackTableList().getComponent(
                        windowApp.activeTrackNumber - 1
                    );
                    tempRow.playButtonAction(windowApp);
                }
            });
            playAllBtn.addMouseListener(new MouseAdapter() {
                boolean isPressed = false;

                @Override
                public void mousePressed(MouseEvent me) {
                    isPressed = true;

                    if(playAllBtn.getName().equalsIgnoreCase("Play_Button")) {
                        playAllBtn.setIcon(scaleImageIcon(
                            "src/com/spotify/assets/icons/play_white_rounded.png", 56, 56
                        ));
                    } else {
                        playAllBtn.setIcon(scaleImageIcon(
                            "src/com/spotify/assets/icons/pause_white_rounded.png", 56, 56
                        ));
                    }
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    isPressed = false;
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                    playAllBtn.setLocation(paddingX - 2, paddingY - 2);
                    playAllBtn.setPreferredSize(playAllBtn.getMaximumSize());
                    playAllBtn.setSize(playAllBtn.getPreferredSize());

                    if(playAllBtn.getName().equalsIgnoreCase("Play_Button")) {
                        playAllBtn.setIcon(scaleImageIcon(
                            "src/com/spotify/assets/icons/play_white_rounded.png", 60, 60
                        ));
                    } else {
                        playAllBtn.setIcon(scaleImageIcon(
                            "src/com/spotify/assets/icons/pause_white_rounded.png", 60, 60
                        ));
                    }
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    if(isPressed == false) {
                        playAllBtn.setLocation(paddingX, paddingY);
                        playAllBtn.setPreferredSize(playAllBtn.getMinimumSize());
                        playAllBtn.setSize(playAllBtn.getPreferredSize());

                        if(playAllBtn.getName().equalsIgnoreCase("Play_Button")) {
                            playAllBtn.setIcon(scaleImageIcon(
                                "src/com/spotify/assets/icons/play_white_rounded.png", 56, 56
                            ));
                        } else {
                            playAllBtn.setIcon(scaleImageIcon(
                                "src/com/spotify/assets/icons/pause_white_rounded.png", 56, 56
                            ));
                        }
                    }
                }
            });
            //</editor-fold>
        }

        if(playlistStyle != LIKED_PLAYLIST && playlistStyle != QUEUE_PLAYLIST) {
            /*
            if(playlistStyle == NATIVE_PLAYLIST) {
                addLibraryBtn = new JButton(scaleImageIcon(
                    "./src/com/spotify/assets/icons/likes_off.png", 32, 32
                ));
                addLibraryBtn.setBackground(Color.green);
                addLibraryBtn.setBorder(null);
                addLibraryBtn.setBorderPainted(false);
                addLibraryBtn.setContentAreaFilled(false);
                addLibraryBtn.setFocusable(false);
                addLibraryBtn.setFocusPainted(false);
                addLibraryBtn.setLocation(paddingX + 56 + 32, (actionBarRow.getHeight() - 32) / 2);
                addLibraryBtn.setMaximumSize(new Dimension(32, 32));
                addLibraryBtn.setMinimumSize(new Dimension(32, 32));
                addLibraryBtn.setName("Button_Off");
                addLibraryBtn.setOpaque(false);
                addLibraryBtn.setPreferredSize(addLibraryBtn.getMaximumSize());
                addLibraryBtn.setSize(addLibraryBtn.getPreferredSize());
                //<editor-fold defaultstate="collapsed" desc="MouseEvent handler for add to library button.">
                addLibraryBtn.addActionListener((ActionEvent ae) -> {
                    if(addLibraryBtn.getName().equals("Button_Off")) {
                        addLibraryBtn.setIcon(scaleImageIcon(
                            "src/com/spotify/assets/icons/likes_on.png", 32, 32
                        ));
                        addLibraryBtn.setName("Button_On");
                    } else if(addLibraryBtn.getName().equals("Button_On")) {
                        addLibraryBtn.setIcon(scaleImageIcon(
                            "src/com/spotify/assets/icons/likes_off.png", 32, 32
                        ));
                        addLibraryBtn.setName("Button_Off");
                    }
                });
                addLibraryBtn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent me) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {
                    }
                });
                //</editor-fold>
            }
            */

            expandedMenuBtn = new JButton(scaleImageIcon(
                "./src/com/spotify/assets/icons/three_dots_default.png", 32, 32
            ));
            expandedMenuBtn.setBackground(Color.green);
            expandedMenuBtn.setBorder(null);
            expandedMenuBtn.setBorderPainted(false);
            expandedMenuBtn.setContentAreaFilled(false);
            expandedMenuBtn.setFocusable(false);
            expandedMenuBtn.setFocusPainted(false);
            expandedMenuBtn.addActionListener((ActionEvent ae) -> {
                PlaylistUI tempUI = (PlaylistUI)windowApp.getContentPanel().getViewport().getView();

                if(tempUI.summonEMH == false) {
                    tempUI.summonEMH = true;

                    JPanel expandedMenuHolder = new JPanel();
                    expandedMenuHolder.setBorder(null);
                    expandedMenuHolder.setBackground(Color.PINK);
                    expandedMenuHolder.setLayout(null);
                    expandedMenuHolder.setOpaque(false);
                    expandedMenuHolder.setPreferredSize(new Dimension(
                        Threshold.contentArea.width, Threshold.contentArea.height
                    ));
                    expandedMenuHolder.putClientProperty("EMH", 9999);
                    expandedMenuHolder.setSize(expandedMenuHolder.getPreferredSize());
                    expandedMenuHolder.setVisible(true);
                    expandedMenuHolder.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            tempUI.summonEMH = false;

                            for(int i = 0; i < tempUI.infoSectionWrapper.getComponentCount(); i++) {
                                if(tempUI.infoSectionWrapper.getComponent(i) instanceof JPanel) {
                                    JPanel tempPanel = (JPanel)tempUI.infoSectionWrapper.getComponent(i);

                                    if(tempPanel.getClientProperty("EMH") != null) {
                                        tempPanel.remove(tempPanel.getComponent(0));
                                        tempPanel.revalidate();
                                        tempPanel.repaint();

                                        tempUI.infoSectionWrapper.remove(tempPanel);
                                        tempUI.infoSectionWrapper.revalidate();
                                        tempUI.infoSectionWrapper.repaint();

                                        break;
                                    }
                                }
                            }
                        }
                    });

                    ExpandedMenu tempMenu = new ExpandedMenu(windowApp, this, playlistStyle,
                    ExpandedMenu.ACTION_BAR_ROW);
                    tempMenu.setLocation(
                        (int)expandedMenuBtn.getLocationOnScreen().getX() - tempMenu.getWidth() +
                        expandedMenuBtn.getWidth(), expandedMenuBtn.getHeight() +
                        (int)expandedMenuBtn.getLocationOnScreen().getY()
                    );

                    expandedMenuHolder.add(tempMenu);
                    tempUI.infoSectionWrapper.add(expandedMenuHolder, JLayeredPane.PALETTE_LAYER);
                } else {
                    tempUI.summonEMH = false;

                    for(int i = 0; i < tempUI.infoSectionWrapper.getComponentCount(); i++) {
                        if(tempUI.infoSectionWrapper.getComponent(i) instanceof JPanel) {
                            JPanel tempPanel = (JPanel)tempUI.infoSectionWrapper.getComponent(i);

                            if(tempPanel.getClientProperty("EMH") != null) {
                                tempPanel.remove(tempPanel.getComponent(0));
                                tempPanel.revalidate();
                                tempPanel.repaint();

                                tempUI.infoSectionWrapper.remove(tempPanel);
                                tempUI.infoSectionWrapper.revalidate();
                                tempUI.infoSectionWrapper.repaint();

                                break;
                            }
                        }
                    }
                }
            });
            expandedMenuBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent me) {
                    expandedMenuBtn.setIcon(scaleImageIcon(
                        "./src/com/spotify/assets/icons/three_dots_hover.png", 32, 32
                    ));
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    expandedMenuBtn.setIcon(scaleImageIcon(
                        "./src/com/spotify/assets/icons/three_dots_default.png", 32, 32
                    ));
                }
            });

            /*
            if(playlistStyle == NATIVE_PLAYLIST) {
                expandedMenuBtn.setLocation(paddingX + 56 + 32 + 32 + 24, ((actionBarRow.getHeight() - 32) / 2) - 0);
            } else if(playlistStyle == CUSTOM_PLAYLIST) {
                expandedMenuBtn.setLocation(paddingX + 56 + 32, ((actionBarRow.getHeight() - 32) / 2) - 0);
            } else if(playlistStyle == UNUSED_PLAYLIST) {
                expandedMenuBtn.setLocation(paddingX, ((actionBarRow.getHeight() - 32) / 2) - 0);
            }
            */
            if(playlistStyle == NATIVE_PLAYLIST || playlistStyle == CUSTOM_PLAYLIST) {
                expandedMenuBtn.setLocation(paddingX + 56 + 32, ((actionBarRow.getHeight() - 32) / 2) - 0);
            } else if(playlistStyle == UNUSED_PLAYLIST) {
                expandedMenuBtn.setLocation(paddingX, ((actionBarRow.getHeight() - 32) / 2) - 0);
            }

            expandedMenuBtn.setMaximumSize(new Dimension(32, 32));
            expandedMenuBtn.setMinimumSize(new Dimension(32, 32));
            expandedMenuBtn.setOpaque(false);
            expandedMenuBtn.setPreferredSize(expandedMenuBtn.getMaximumSize());
            expandedMenuBtn.setSize(expandedMenuBtn.getPreferredSize());
            expandedMenuBtn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent me) {
                }

                @Override
                public void mouseExited(MouseEvent me) {
                }
            });
        }

        if(playlistStyle != UNUSED_PLAYLIST) {
            actionBarRow.add(playAllBtn);
        }

        if(playlistStyle != LIKED_PLAYLIST && playlistStyle != QUEUE_PLAYLIST) {
            /*
            if(playlistStyle == NATIVE_PLAYLIST) {
                actionBarRow.add(addLibraryBtn);
            }
            */

            actionBarRow.add(expandedMenuBtn);
        }
    }

    /**
     *  Method that are resizing "table" look alike panel and its component.
     *
     *  @param songs ArrayList of song that is in playlist
     *  @param playlistName Referencing to windowApp variable
     *  @param playlistID
     *  @param windowApp Referencing to contentPanel that is in
     *                   windowApp to pass song data to each row
     */
    public final void initTrackList(ArrayList<Song> songs, String playlistName, int playlistID, Window windowApp) {
        trackList = new JPanel();
        trackList.setBackground(Color.green);
        trackList.setBorder(null);
        trackList.setBounds(
            0, actionBarRow.getHeight(), Threshold.contentArea.width, 0
        );
        trackList.setLayout(null);
        trackList.setOpaque(false);
        trackList.setPreferredSize(trackList.getSize());

        if(playlistStyle == LIKED_PLAYLIST || playlistStyle == QUEUE_PLAYLIST) {
            dupColumnHeader = new ColumnHeader(windowApp, trackList, CUSTOM_PLAYLIST);

            if(playlistStyle == QUEUE_PLAYLIST) {
                dupColumnHeader.trackDateAddedHeader.setText("");
            }
        } else {
            dupColumnHeader = new ColumnHeader(windowApp, trackList, playlistStyle);
        }

        headerWrapper = new JPanel();
        headerWrapper.add(dupColumnHeader);
        headerWrapper.setBackground(new Color(24, 24, 24));
        headerWrapper.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 40, 40)));
        headerWrapper.setBounds(
            0, windowApp.getContentDraggable().getHeight(), trackList.getWidth(), 37
        );
        headerWrapper.setLayout(null);
        headerWrapper.setOpaque(true);
        headerWrapper.setPreferredSize(headerWrapper.getSize());
        headerWrapper.setVisible(false);

        if(playlistStyle == LIKED_PLAYLIST || playlistStyle == QUEUE_PLAYLIST) {
            columnHeader = new ColumnHeader(windowApp, trackList, CUSTOM_PLAYLIST);

            if(playlistStyle == QUEUE_PLAYLIST) {
                columnHeader.trackDateAddedHeader.setText("");
            }
        } else {
            columnHeader = new ColumnHeader(windowApp, trackList, playlistStyle);
        }

        trackTableList = new JPanel();
        trackTableList.setBackground(Color.ORANGE);
        trackTableList.setBorder(null);

        int gapSize;

        if(Threshold.currentSize.width >= 1280) {
            gapSize = 16;
        } else {
            gapSize = 10;
        }

        trackTableList.setBounds(
            paddingX + 1, columnHeader.getHeight() + gapSize, columnHeader.getWidth(), 0
        );
        trackTableList.setLayout(new GridBagLayout());
        trackTableList.setOpaque(false);
        trackTableList.setPreferredSize(trackTableList.getSize());
        trackTableList.setVisible(true);

        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.anchor = GridBagConstraints.NORTH;
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 1;
        gridConstraints.gridx = 0;

        windowApp.currentPlaylistTrackCount = songs.size();

        for(int i = 0; i < songs.size(); i++) {
            gridConstraints.gridy = trackTableList.getHeight();

            TrackRow tempRow;

            if(playlistStyle == LIKED_PLAYLIST || playlistStyle == QUEUE_PLAYLIST) {
                if(playlistStyle == LIKED_PLAYLIST) {
                    System.out.println("PASS 1");
                    this.setName("LIKED_PLAYLIST");
                }

                tempRow = new TrackRow(
                    windowApp, trackTableList.getWidth(), CUSTOM_PLAYLIST, i + 1, songs.get(i), playlistName, playlistID
                );

                if(playlistStyle == QUEUE_PLAYLIST) {
                    tempRow.trackDateAddedLabel.setText("");
                }
            } else {
                tempRow = new TrackRow(
                    windowApp, trackTableList.getWidth(), playlistStyle, i + 1, songs.get(i), playlistName, playlistID
                );
            }

            trackTableList.add(tempRow, gridConstraints, -1);
            trackTableList.setPreferredSize(new Dimension(
                columnHeader.getWidth(), trackTableList.getHeight() + trackTableList.getComponent(i).getHeight()
            ));
            trackTableList.setSize(trackTableList.getPreferredSize());
        }

        trackTableList.revalidate();
        trackTableList.repaint();

        /**
         *  Regardless this panel is for publisher and copyright holder info
         *  but due to inconsistent data provided this has been re-proposed as
         *  empty space panel just for bottom margin filling.
         */
        copyrightHolderInfo = new JPanel();
        copyrightHolderInfo.setBackground(Color.GREEN);
        copyrightHolderInfo.setBounds(
            0, columnHeader.getHeight() + gapSize + trackTableList.getHeight(), Threshold.contentArea.width, 32
        );
        copyrightHolderInfo.setBorder(null);
        copyrightHolderInfo.setLayout(null);
        copyrightHolderInfo.setOpaque(false);
        copyrightHolderInfo.setPreferredSize(copyrightHolderInfo.getSize());

        trackList.setPreferredSize(new Dimension(
            Threshold.contentArea.width, columnHeader.getHeight() + gapSize +
            trackTableList.getHeight() + copyrightHolderInfo.getHeight()
        ));
        trackList.setSize(trackList.getPreferredSize());

        trackList.add(columnHeader);
        trackList.add(trackTableList);
        trackList.add(copyrightHolderInfo);
    }

    /**
     *  The size of art image (width & height) is:
     *  Maximum: 232px, Minimum: 192px
     *
     *  @param imagePath Relative path to imageFile
     *
     *  @return ImageIcon that are fit imageHolder button size
     */
    public final ImageIcon getArtImage(String imagePath) {
        int imageWidth, imageHeight;

        if(imagePath.equals("./src/com/spotify/assets/icons/music_icon.png")) {
            if(Threshold.currentSize.width >= 1280) {
                imageWidth = 60;
                imageHeight = 60;
            } else {
                imageWidth = 48;
                imageHeight = 48;
            }
        } else {
            if(Threshold.currentSize.width >= 1280) {
                imageWidth = 232;
                imageHeight = 232;
            } else {
                imageWidth = 192;
                imageHeight = 192;
            }
        }

        return scaleImageIcon(imagePath, imageWidth, imageHeight);
    }

    /**
     *  Get playlist stamp as formatted String.
     *
     *  @param dateCreated Years of album release
     *  @param totalLikes
     *  @param totalSongs Total songs on album
     *  @param totalDurations Total durations on album
     *
     *  @return Results in certain format regarding playlistType
     */
    public final String getStampString(String[] dateCreated, int totalLikes, int totalSongs, int totalDurations) {
        StringBuilder stringBuilder = new StringBuilder("");

        int minutes = totalDurations / 60;
        int seconds = totalDurations % 60;

        if(playlistStyle == LIKED_PLAYLIST) {
            stringBuilder.append(" • ").append(totalSongs).append(" songs");
        } else if(playlistStyle == QUEUE_PLAYLIST) {
            stringBuilder.append("");
        } else if(playlistStyle == NATIVE_PLAYLIST) {
            stringBuilder.append(" • ").append(dateCreated[3]).append(" • ").append(totalSongs)
            .append(" songs, ").append(minutes).append(" min ").append(seconds).append(" sec");
        } else if(playlistStyle == CUSTOM_PLAYLIST) {
            /* if(totalLikes == 0) {
                stringBuilder.append(" • ").append(totalSongs).append(" songs, ")
                .append(minutes).append(" min ").append(seconds).append(" sec");
            } else {
                String tempStr;

                if(totalLikes == 1) {
                    tempStr = " like • ";
                } else {
                    tempStr = " likes • ";
                }

                stringBuilder.append(" • ").append(totalLikes).append(tempStr).append(totalSongs)
                .append(" songs, ").append(minutes).append(" min ").append(seconds).append(" sec");
            } */
            stringBuilder.append(" • ").append(totalSongs).append(" songs, ")
            .append(minutes).append(" min ").append(seconds).append(" sec");
        }

        return stringBuilder.toString();
    }

    public int getPlaylistStyle() {
        return playlistStyle;
    }

    public int getPlaylistID() {
        return playlistID;
    }

    public boolean isSummonEMH() {
        return summonEMH;
    }

    public void setSummonEMH(boolean summonEMH) {
        this.summonEMH = summonEMH;
    }

    public int getPaddingX() {
        return paddingX;
    }

    public void setPaddingX(int paddingX) {
        this.paddingX = paddingX;
    }

    public int getPaddingY() {
        return paddingY;
    }

    public void setPaddingY(int paddingY) {
        this.paddingY = paddingY;
    }

    public JPanel getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(JPanel mainInfo) {
        this.mainInfo = mainInfo;
    }

    public LinearGradient getInfoGradientPanel() {
        return infoGradientPanel;
    }

    public void setInfoGradientPanel(LinearGradient infoGradientPanel) {
        this.infoGradientPanel = infoGradientPanel;
    }

    public JPanel getInnerPanel() {
        return innerPanel;
    }

    public void setInnerPanel(JPanel innerPanel) {
        this.innerPanel = innerPanel;
    }

    public JButton getImageHolder() {
        return imageHolder;
    }

    public void setImageHolder(JButton imageHolder) {
        this.imageHolder = imageHolder;
    }

    public JPanel getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(JPanel infoPanel) {
        this.infoPanel = infoPanel;
    }

    public JLabel getPlaylistType() {
        return playlistType;
    }

    public void setPlaylistType(JLabel playlistType) {
        this.playlistType = playlistType;
    }

    public JLabel getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(JLabel playlistTitle) {
        this.playlistTitle = playlistTitle;
    }

    public JButton getAlbumOwner() {
        return albumOwner;
    }

    public void setAlbumOwner(JButton albumOwner) {
        this.albumOwner = albumOwner;
    }

    public JLabel getPlaylistInfo() {
        return playlistInfo;
    }

    public void setPlaylistInfo(JLabel playlistInfo) {
        this.playlistInfo = playlistInfo;
    }

    public int getSolidColoredPanelHeightConstant() {
        return solidColoredPanelHeightConstant;
    }

    public void setSolidColoredPanelHeightConstant(int solidColoredPanelHeightConstant) {
        this.solidColoredPanelHeightConstant = solidColoredPanelHeightConstant;
    }

    public LinearGradient getLinearGradientPanel() {
        return linearGradientPanel;
    }

    public void setLinearGradientPanel(LinearGradient linearGradientPanel) {
        this.linearGradientPanel = linearGradientPanel;
    }

    public JPanel getSolidColoredPanel() {
        return solidColoredPanel;
    }

    public void setSolidColoredPanel(JPanel solidColoredPanel) {
        this.solidColoredPanel = solidColoredPanel;
    }

    public JLayeredPane getMainList() {
        return mainList;
    }

    public void setMainList(JLayeredPane mainList) {
        this.mainList = mainList;
    }

    public LinearGradient getRootPaneHeaderJacket() {
        return rootPaneHeaderJacket;
    }

    public void setRootPaneHeaderJacket(LinearGradient rootPaneHeaderJacket) {
        this.rootPaneHeaderJacket = rootPaneHeaderJacket;
    }

    public JPanel getRootPaneHeader() {
        return rootPaneHeader;
    }

    public void setRootPaneHeader(JPanel rootPaneHeader) {
        this.rootPaneHeader = rootPaneHeader;
    }

    public int getRootPaneOpacity() {
        return rootPaneOpacity;
    }

    public void setRootPaneOpacity(int rootPaneOpacity) {
        this.rootPaneOpacity = rootPaneOpacity;
    }

    public JPanel getInnerHolder() {
        return innerHolder;
    }

    public void setInnerHolder(JPanel innerHolder) {
        this.innerHolder = innerHolder;
    }

    public JScrollPane getContentHolder() {
        return contentHolder;
    }

    public void setContentHolder(JScrollPane contentHolder) {
        this.contentHolder = contentHolder;
    }

    public JScrollBar getScrollBar() {
        return scrollBar;
    }

    public void setScrollBar(JScrollBar scrollBar) {
        this.scrollBar = scrollBar;
    }

    public JPanel getScrollBarHolder() {
        return scrollBarHolder;
    }

    public void setScrollBarHolder(JPanel scrollBarHolder) {
        this.scrollBarHolder = scrollBarHolder;
    }

    public JPanel getStickyHeaderRow() {
        return stickyHeaderRow;
    }

    public void setStickyHeaderRow(JPanel stickyHeaderRow) {
        this.stickyHeaderRow = stickyHeaderRow;
    }

    public JLayeredPane getInfoSectionWrapper() {
        return infoSectionWrapper;
    }

    public void setInfoSectionWrapper(JLayeredPane infoSectionWrapper) {
        this.infoSectionWrapper = infoSectionWrapper;
    }

    public JPanel getActionBarRow() {
        return actionBarRow;
    }

    public void setActionBarRow(JPanel actionBarRow) {
        this.actionBarRow = actionBarRow;
    }

    public JButton getPlayAllBtn() {
        return playAllBtn;
    }

    public void setPlayAllBtn(JButton playAllBtn) {
        this.playAllBtn = playAllBtn;
    }

    public JButton getAddLibraryBtn() {
        return addLibraryBtn;
    }

    public void setAddLibraryBtn(JButton addLibraryBtn) {
        this.addLibraryBtn = addLibraryBtn;
    }

    public JButton getExpandedMenuBtn() {
        return expandedMenuBtn;
    }

    public void setExpandedMenuBtn(JButton expandedMenuBtn) {
        this.expandedMenuBtn = expandedMenuBtn;
    }

    public JPanel getTrackList() {
        return trackList;
    }

    public void setTrackList(JPanel trackList) {
        this.trackList = trackList;
    }

    public JPanel getHeaderWrapper() {
        return headerWrapper;
    }

    public void setHeaderWrapper(JPanel headerWrapper) {
        this.headerWrapper = headerWrapper;
    }

    public JPanel getTrackTableList() {
        return trackTableList;
    }

    public void setTrackTableList(JPanel trackTableList) {
        this.trackTableList = trackTableList;
    }

    public JPanel getCopyrightHolderInfo() {
        return copyrightHolderInfo;
    }

    public void setCopyrightHolderInfo(JPanel copyrightHolderInfo) {
        this.copyrightHolderInfo = copyrightHolderInfo;
    }

    public ColumnHeader getColumnHeader() {
        return columnHeader;
    }

    public void setColumnHeader(ColumnHeader columnHeader) {
        this.columnHeader = columnHeader;
    }

    public ColumnHeader getDupColumnHeader() {
        return dupColumnHeader;
    }

    public void setDupColumnHeader(ColumnHeader dupColumnHeader) {
        this.dupColumnHeader = dupColumnHeader;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
}
