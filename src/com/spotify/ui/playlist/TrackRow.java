package com.spotify.ui.playlist;

import com.spotify.app.Artist;
import com.spotify.app.Song;
import com.spotify.app.Utility;
import com.spotify.ui.Content;
import com.spotify.ui.ContentDraggable;
import com.spotify.ui.PlayButtonEvent;
import com.spotify.ui.Threshold;
import com.spotify.ui.Window;
import com.spotify.ui.profile.ArtistProfile;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TrackRow extends JPanel implements Utility {
    public static final int UNUSED_PLAYLIST = 0;
    public static final int NATIVE_PLAYLIST = 1;
    public static final int CUSTOM_PLAYLIST = 2;
    public static final int LIKED_PLAYLIST = 100;
    public static final int QUEUE_PLAYLIST = 101;

    public boolean hoveringPlayButton = false;
    public boolean isPlaying = false;

    protected int globalHeight = 56;
    protected int playlistID;
    protected int trackNumber;
    protected String playlistName;

    protected Song song;

    protected int playlistStyle;
    protected int titleColumnMaxWidth;
    protected int rigidBoxCount;

    protected JLabel trackNumberLabel;
    protected JButton playSongButton;
    protected JPanel buttonHolder;
    protected JPanel trackNumberColumn;
    protected JLayeredPane playButtonLayeredPane;

    protected ImageIcon artImageIcon;
    protected JLabel trackTitleLabel;
    protected JPanel trackTitleColumn;
    protected JButton contributorsName;

    protected JLabel trackPlaysCountLabel;
    protected JLabel playsCountRightMargin;
    protected JPanel trackPlaysCountColumn;

    protected JButton trackAlbumButton;
    protected JPanel trackAlbumColumn;

    protected JLabel trackDateAddedLabel;
    protected JLabel dateAddedRightMargin;
    protected JPanel trackDateAddedColumn;

    protected JButton likeSongButton;
    protected JButton expandedMenuBtn;
    protected JLabel trackDurationLabel;
    protected JPanel trackDurationColumn;

    public TrackRow(Window windowApp, int maximumWidth, int playlistStyle, int trackNumber, Song song,
    String playlistName, int playlistID) {
        this.song = song;
        this.playlistID = playlistID;
        this.playlistName = playlistName;
        this.trackNumber = trackNumber;
        this.playlistStyle = playlistStyle;
        this.titleColumnMaxWidth = Integer.MAX_VALUE;

        this.setBackground(Color.CYAN);
        this.setBackground(new Color(42, 42, 42));
        this.setBorder(null);
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(maximumWidth, globalHeight));
        this.setSize(this.getPreferredSize());
        this.setVisible(true);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                if(hoveringPlayButton == false && isPlaying == false) {
                    trackNumberColumn.setVisible(false);
                    trackNumberColumn.revalidate();
                    trackNumberColumn.repaint();

                    buttonHolder.setVisible(true);
                    buttonHolder.revalidate();
                    buttonHolder.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(hoveringPlayButton == false && isPlaying == false) {
                    trackNumberColumn.setVisible(true);
                    trackNumberColumn.revalidate();
                    trackNumberColumn.repaint();

                    buttonHolder.setVisible(false);
                    buttonHolder.revalidate();
                    buttonHolder.repaint();
                }
            }
        });

        this.initComponents(windowApp);
    }

    private void initComponents(Window windowApp) {
        GridBagConstraints gridConstraints = new GridBagConstraints();

        try {
            if(playlistStyle == NATIVE_PLAYLIST) {
                this.rigidBoxCount = 4;

                this.initTrackNumberColumn(windowApp, "" + trackNumber);
                this.initTrackPlaysCountColumn(song.getTotalPlays());
                this.initTrackDurationColumn(windowApp, song.getDuration());
                this.initTrackTitleColumn(windowApp, song.getTitle(), song.getArtist().getName(),
                song.getAlbumArt());

                this.add(playButtonLayeredPane, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
                this.add(trackTitleColumn, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
                this.add(trackPlaysCountColumn, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
                this.add(trackDurationColumn, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
            } else if(playlistStyle == CUSTOM_PLAYLIST) {
                this.rigidBoxCount = 4;

                this.initTrackNumberColumn(windowApp, "" + trackNumber);
                this.initTrackAlbumColumn(windowApp, song.getPlaylistName());
                this.initTrackDateAddedColumn(song.getDateAdded());
                this.initTrackDurationColumn(windowApp, song.getDuration());
                this.initTrackTitleColumn(windowApp, song.getTitle(), song.getArtist().getName(),
                song.getAlbumArt());

                this.add(playButtonLayeredPane, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
                this.add(trackTitleColumn, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
                this.add(trackAlbumColumn, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
                this.add(trackDateAddedColumn, gridConstraints, -1);
                this.add(trackDurationColumn, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(windowApp.activePlaylistName.equals(playlistName) && windowApp.activeTrackNumber == this.trackNumber) {
                trackTitleLabel.setForeground(Threshold.palette_1db954);

                if(windowApp.activePlayer == true) {
                    hoveringPlayButton = true;
                    isPlaying = true;

                    playSongButton.setIcon(scaleImageIcon(
                        "./src/com/spotify/assets/icons/pause_white.png", 16, 16
                    ));
                    playSongButton.setName("Pause_Button");

                    trackNumberColumn.setVisible(false);
                    trackNumberColumn.revalidate();
                    trackNumberColumn.repaint();

                    buttonHolder.setVisible(true);
                    buttonHolder.revalidate();
                    buttonHolder.repaint();
                }
            } else {
                trackTitleLabel.setForeground(Threshold.palette_ffffff);
            }
        }
    }

    private void initTrackNumberColumn(Window windowApp, String trackNumber) {
        trackNumberLabel = new JLabel(trackNumber, JLabel.RIGHT);
        trackNumberLabel.setBackground(Color.GREEN);
        trackNumberLabel.setFont(new Font("Gotham", Font.PLAIN, 14));
        trackNumberLabel.setForeground(Threshold.palette_b3b3b3);
        trackNumberLabel.setIcon(null);
        trackNumberLabel.setMaximumSize(new Dimension(
            32, (int)trackNumberLabel.getPreferredSize().getHeight()
        ));
        trackNumberLabel.setOpaque(false);

        trackNumberColumn = new JPanel();
        trackNumberColumn.add(trackNumberLabel);
        trackNumberColumn.setBackground(Color.MAGENTA);
        trackNumberColumn.setBorder(null);
        trackNumberColumn.setLayout(new BoxLayout(trackNumberColumn, BoxLayout.X_AXIS));
        trackNumberColumn.setMaximumSize(new Dimension(32, globalHeight));
        trackNumberColumn.setOpaque(false);
        trackNumberColumn.setPreferredSize(trackNumberColumn.getMaximumSize());
        trackNumberColumn.setSize(trackNumberColumn.getPreferredSize());
        trackNumberColumn.setVisible(true);

        playSongButton = new JButton(scaleImageIcon(
            "./src/com/spotify/assets/icons/play_white.png", 16, 16
        ));
        playSongButton.setBackground(Color.GREEN);
        playSongButton.setBorder(null);
        playSongButton.setBorderPainted(false);
        playSongButton.setContentAreaFilled(false);
        playSongButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        playSongButton.setFocusable(false);
        playSongButton.setFocusPainted(false);
        playSongButton.setFont(new Font("Gotham", Font.PLAIN, 16));
        playSongButton.setForeground(Threshold.palette_b3b3b3);
        playSongButton.setName("Play_Button");
        playSongButton.setOpaque(false);
        playSongButton.addActionListener((ActionEvent ae) -> {
            PlaylistUI tempUI = (PlaylistUI)windowApp.getContentPanel().getViewport().getView();

            if(!windowApp.activePlaylistName.equals(tempUI.getPlaylistTitle().getText()) ||
            (windowApp.activePlaylistName.equals(tempUI.getPlaylistTitle().getText()) && this.trackNumber !=
            windowApp.activeTrackNumber)) {
                windowApp.countOnShuffle = 0;
            }

            playButtonAction(windowApp);
        });
        playSongButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                if(isPlaying == false) {
                    hoveringPlayButton = true;

                    trackNumberColumn.setVisible(false);
                    trackNumberColumn.revalidate();
                    trackNumberColumn.repaint();

                    buttonHolder.setVisible(true);
                    buttonHolder.revalidate();
                    buttonHolder.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(isPlaying == false) {
                    hoveringPlayButton = false;
                }
            }
        });

        buttonHolder = new JPanel();
        buttonHolder.add(Box.createRigidArea(new Dimension(16, 0)));
        buttonHolder.add(playSongButton);
        buttonHolder.setBackground(Color.PINK);
        buttonHolder.setBorder(null);
        buttonHolder.setLayout(new BoxLayout(buttonHolder, BoxLayout.X_AXIS));
        buttonHolder.setMaximumSize(new Dimension(32, globalHeight));
        buttonHolder.setOpaque(false);
        buttonHolder.setPreferredSize(buttonHolder.getMaximumSize());
        buttonHolder.setSize(buttonHolder.getPreferredSize());
        buttonHolder.setVisible(false);

        playButtonLayeredPane = new JLayeredPane();
        playButtonLayeredPane.setName("Invisible_Panel");
        playButtonLayeredPane.setPreferredSize(new Dimension(32, globalHeight));
        playButtonLayeredPane.setSize(playButtonLayeredPane.getPreferredSize());

        playButtonLayeredPane.add(buttonHolder, JLayeredPane.PALETTE_LAYER);
        playButtonLayeredPane.add(trackNumberColumn, JLayeredPane.DEFAULT_LAYER);
    }

    private void initTrackTitleColumn(Window windowApp, String songTitle, String artistName, String albumArt) {
        artImageIcon = scaleImageIcon(albumArt, 40, 40);

        trackTitleLabel = new JLabel(songTitle, JLabel.LEFT);
        trackTitleLabel.setBackground(Color.GREEN);
        trackTitleLabel.setFont(new Font("Gotham", Font.BOLD, 15));
        trackTitleLabel.setForeground(Threshold.palette_ffffff);
        trackTitleLabel.setMaximumSize(new Dimension(
            Integer.MAX_VALUE, globalHeight / 2 + 1
        ));
        trackTitleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        trackTitleLabel.setOpaque(false);
        trackTitleLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        if(playlistStyle == NATIVE_PLAYLIST) {
            titleColumnMaxWidth = (
                trackNumberColumn.getWidth() + trackPlaysCountColumn.getWidth() + trackDurationColumn.getWidth()
            );
        } else if(playlistStyle == CUSTOM_PLAYLIST) {
            titleColumnMaxWidth = (
                trackNumberColumn.getWidth() + trackAlbumColumn.getWidth() +
                trackDateAddedColumn.getWidth() + trackDurationColumn.getWidth()
            );
        }

        contributorsName = new JButton(artistName);
        contributorsName.setBackground(Color.GREEN);
        contributorsName.setBorder(null);
        contributorsName.setBorderPainted(false);
        contributorsName.setContentAreaFilled(false);
        contributorsName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contributorsName.setFocusable(false);
        contributorsName.setFocusPainted(false);
        contributorsName.setFont(new Font("Gotham", Font.PLAIN, 13));
        contributorsName.setForeground(Threshold.palette_b3b3b3);
        contributorsName.setHorizontalAlignment(SwingConstants.LEFT);
        contributorsName.setOpaque(false);
        contributorsName.setVerticalAlignment(SwingConstants.TOP);
        contributorsName.addActionListener((ActionEvent ae) -> {
            Artist tempArtist = null;

            for(int i = 0; i < windowApp.admin.getArtists().size(); i++) {
                if(windowApp.admin.getArtists().get(i).getName().equals(artistName)) {
                    tempArtist = windowApp.admin.getArtists().get(i);
                }
            }

            ArtistProfile ap = new ArtistProfile(tempArtist, windowApp);
            windowApp.changeContent(
                new Content(ap, true), new ContentDraggable(windowApp, windowApp.user), windowApp.user,
                windowApp.accounts
            );
        });
        contributorsName.addMouseListener(new MouseAdapter() {
            Font font = contributorsName.getFont();
            Map attributes = font.getAttributes();

            @Override
            public void mouseEntered(MouseEvent me) {
                if(isPlaying == false) {
                    hoveringPlayButton = true;

                    trackNumberColumn.setVisible(false);
                    trackNumberColumn.revalidate();
                    trackNumberColumn.repaint();

                    buttonHolder.setVisible(true);
                    buttonHolder.revalidate();
                    buttonHolder.repaint();
                }

                contributorsName.setForeground(Threshold.palette_ffffff);
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                contributorsName.setFont(font.deriveFont(attributes));
                contributorsName.revalidate();
                contributorsName.repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(isPlaying == false) {
                    hoveringPlayButton = false;
                }

                contributorsName.setForeground(Threshold.palette_b3b3b3);
                attributes.put(TextAttribute.UNDERLINE, null);
                contributorsName.setFont(font.deriveFont(attributes));
                contributorsName.revalidate();
                contributorsName.repaint();
            }
        });

        if(playlistStyle == NATIVE_PLAYLIST) {
            JPanel tempHorizontalHolder = new JPanel();
            tempHorizontalHolder.add(trackTitleLabel);
            tempHorizontalHolder.add(Box.createRigidArea(new Dimension(0, 1)));
            tempHorizontalHolder.add(contributorsName);
            tempHorizontalHolder.setBackground(Color.MAGENTA);
            tempHorizontalHolder.setBorder(null);
            tempHorizontalHolder.setLayout(new BoxLayout(tempHorizontalHolder, BoxLayout.Y_AXIS));
            tempHorizontalHolder.setMaximumSize(new Dimension(
                this.getWidth() - (16 * rigidBoxCount) - titleColumnMaxWidth, globalHeight
            ));
            tempHorizontalHolder.setOpaque(false);
            tempHorizontalHolder.setPreferredSize(tempHorizontalHolder.getMaximumSize());
            tempHorizontalHolder.setSize(tempHorizontalHolder.getPreferredSize());

            trackTitleColumn = new JPanel();
            trackTitleColumn.add(tempHorizontalHolder);
            trackTitleColumn.setBackground(Color.MAGENTA);
            trackTitleColumn.setBorder(null);
            trackTitleColumn.setLayout(new BoxLayout(trackTitleColumn, BoxLayout.X_AXIS));
            trackTitleColumn.setMaximumSize(new Dimension(
                this.getWidth() - (16 * rigidBoxCount) - titleColumnMaxWidth, globalHeight
            ));
            trackTitleColumn.setOpaque(false);
            trackTitleColumn.setPreferredSize(trackTitleColumn.getMaximumSize());
            trackTitleColumn.setSize(trackTitleColumn.getPreferredSize());
        } else {
            JLabel tempImageHolder = new JLabel(artImageIcon, JLabel.LEFT);
            tempImageHolder.setBackground(Color.PINK);
            tempImageHolder.setMaximumSize(new Dimension(globalHeight, globalHeight));
            tempImageHolder.setMinimumSize(tempImageHolder.getMaximumSize());
            tempImageHolder.setOpaque(false);
            tempImageHolder.setPreferredSize(tempImageHolder.getMaximumSize());
            tempImageHolder.setSize(tempImageHolder.getMaximumSize());

            JPanel tempHorizontalHolder = new JPanel();
            tempHorizontalHolder.add(trackTitleLabel);
            tempHorizontalHolder.add(Box.createRigidArea(new Dimension(0, 1)));
            tempHorizontalHolder.add(contributorsName);
            tempHorizontalHolder.setBackground(Color.MAGENTA);
            tempHorizontalHolder.setBorder(null);
            tempHorizontalHolder.setLayout(new BoxLayout(tempHorizontalHolder, BoxLayout.Y_AXIS));
            tempHorizontalHolder.setMaximumSize(new Dimension(
                this.getWidth() - (16 * rigidBoxCount) - titleColumnMaxWidth - tempImageHolder.getWidth(), globalHeight
            ));
            tempHorizontalHolder.setOpaque(false);
            tempHorizontalHolder.setPreferredSize(tempHorizontalHolder.getMaximumSize());
            tempHorizontalHolder.setSize(tempHorizontalHolder.getPreferredSize());

            trackTitleColumn = new JPanel();
            trackTitleColumn.add(tempImageHolder);
            trackTitleColumn.add(tempHorizontalHolder);
            trackTitleColumn.setBackground(Color.MAGENTA);
            trackTitleColumn.setBorder(null);
            trackTitleColumn.setLayout(new BoxLayout(trackTitleColumn, BoxLayout.X_AXIS));
            trackTitleColumn.setMaximumSize(new Dimension(
                this.getWidth() - (16 * rigidBoxCount) - titleColumnMaxWidth, globalHeight
            ));
            trackTitleColumn.setOpaque(false);
            trackTitleColumn.setPreferredSize(trackTitleColumn.getMaximumSize());
            trackTitleColumn.setSize(trackTitleColumn.getPreferredSize());
        }
    }

    private void initTrackPlaysCountColumn(int totalPlays) {
        int lpadWidth, rpadWidth;

        if(Threshold.currentSize.width == Threshold.maximumSize.width) {
            if(Threshold.maximumSize.width >= 1600) {
                lpadWidth = 104;
                rpadWidth = 430;
            } else {
                lpadWidth = 104;
                rpadWidth = 230;
            }
        } else {
            lpadWidth = 104;
            rpadWidth = 40;
        }

        trackPlaysCountLabel = new JLabel(NumberFormat.getInstance().format(totalPlays), JLabel.RIGHT);
        trackPlaysCountLabel.setBackground(Color.GREEN);
        trackPlaysCountLabel.setFont(new Font("Gotham", Font.PLAIN, 14));
        trackPlaysCountLabel.setForeground(Threshold.palette_b3b3b3);
        trackPlaysCountLabel.setMaximumSize(new Dimension(
            lpadWidth, (int)trackPlaysCountLabel.getPreferredSize().getHeight()
        ));
        trackPlaysCountLabel.setOpaque(false);

        playsCountRightMargin = new JLabel();
        playsCountRightMargin.setBackground(Color.GREEN);
        playsCountRightMargin.setFont(new Font("Gotham", Font.PLAIN, 14));
        playsCountRightMargin.setForeground(Threshold.palette_b3b3b3);
        playsCountRightMargin.setMaximumSize(new Dimension(
            rpadWidth, (int)trackPlaysCountLabel.getPreferredSize().getHeight()
        ));
        playsCountRightMargin.setOpaque(false);
        playsCountRightMargin.setVisible(false);

        trackPlaysCountColumn = new JPanel();
        trackPlaysCountColumn.add(trackPlaysCountLabel);
        trackPlaysCountColumn.add(playsCountRightMargin);
        trackPlaysCountColumn.setBackground(Color.MAGENTA);
        trackPlaysCountColumn.setBorder(null);
        trackPlaysCountColumn.setLayout(new BoxLayout(trackPlaysCountColumn, BoxLayout.X_AXIS));
        trackPlaysCountColumn.setMaximumSize(new Dimension(lpadWidth + rpadWidth, globalHeight));
        trackPlaysCountColumn.setOpaque(false);
        trackPlaysCountColumn.setPreferredSize(trackPlaysCountColumn.getMaximumSize());
        trackPlaysCountColumn.setSize(trackPlaysCountColumn.getPreferredSize());
    }

    private void initTrackAlbumColumn(Window windowApp, String playlistName) {
        int padWidth;

        if(Threshold.currentSize.width == Threshold.maximumSize.width) {
            if(Threshold.maximumSize.width >= 1600) {
                padWidth = 428;
            } else {
                padWidth = 268;
            }
        } else {
            padWidth = 138;
        }

        trackAlbumButton = new JButton(playlistName);
        trackAlbumButton.setBackground(Color.GREEN);
        trackAlbumButton.setBorder(null);
        trackAlbumButton.setBorderPainted(false);
        trackAlbumButton.setContentAreaFilled(false);
        trackAlbumButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        trackAlbumButton.setFocusable(false);
        trackAlbumButton.setFocusPainted(false);
        trackAlbumButton.setFont(new Font("Gotham", Font.PLAIN, 14));
        trackAlbumButton.setForeground(Threshold.palette_b3b3b3);
        trackAlbumButton.setOpaque(false);
        trackAlbumButton.setHorizontalAlignment(SwingConstants.LEFT);
        trackAlbumButton.setVerticalAlignment(SwingConstants.BOTTOM);
        trackAlbumButton.addActionListener((ActionEvent ae) -> {
            int playlistID = 0;

            for(int i = 0; i < windowApp.admin.getPlaylists().size(); i++) {
                if(windowApp.admin.getPlaylists().get(i).getName().equals(song.getPlaylistName())) {
                    playlistID = windowApp.admin.getPlaylists().get(i).getPlaylistID();
                }
            }

            PlaylistUI panel = new PlaylistUI(
                windowApp.accounts, windowApp.admin.getPlaylists().get(playlistID), windowApp,
                PlaylistUI.NATIVE_PLAYLIST, -1
            );
            windowApp.changeContent(
                new Content(panel, true), new ContentDraggable(windowApp, windowApp.user), windowApp.user,
                windowApp.accounts
            );
        });
        trackAlbumButton.addMouseListener(new MouseAdapter() {
            Font font = trackAlbumButton.getFont();
            Map attributes = font.getAttributes();

            @Override
            public void mouseEntered(MouseEvent me) {
                if(isPlaying == false) {
                    hoveringPlayButton = true;

                    trackNumberColumn.setVisible(false);
                    trackNumberColumn.revalidate();
                    trackNumberColumn.repaint();

                    buttonHolder.setVisible(true);
                    buttonHolder.revalidate();
                    buttonHolder.repaint();
                }

                trackAlbumButton.setForeground(Threshold.palette_ffffff);
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                trackAlbumButton.setFont(font.deriveFont(attributes));
                trackAlbumButton.revalidate();
                trackAlbumButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(isPlaying == false) {
                    hoveringPlayButton = false;
                }

                trackAlbumButton.setForeground(Threshold.palette_b3b3b3);
                attributes.put(TextAttribute.UNDERLINE, null);
                trackAlbumButton.setFont(font.deriveFont(attributes));
                trackAlbumButton.revalidate();
                trackAlbumButton.repaint();
            }
        });

        trackAlbumColumn = new JPanel();
        trackAlbumColumn.add(trackAlbumButton);
        trackAlbumColumn.setBackground(Color.MAGENTA);
        trackAlbumColumn.setBorder(null);
        trackAlbumColumn.setLayout(new BoxLayout(trackAlbumColumn, BoxLayout.X_AXIS));
        trackAlbumColumn.setMaximumSize(new Dimension(padWidth, globalHeight));
        trackAlbumColumn.setOpaque(false);
        trackAlbumColumn.setPreferredSize(trackAlbumColumn.getMaximumSize());
        trackAlbumColumn.setSize(trackAlbumColumn.getPreferredSize());
    }

    private void initTrackDateAddedColumn(String dateAdded) {
        int lpadWidth, rpadWidth;

        if(Threshold.currentSize.width == Threshold.maximumSize.width) {
            if(Threshold.maximumSize.width >= 1600) {
                lpadWidth = 330;
                rpadWidth = 16;
            } else {
                lpadWidth = 220;
                rpadWidth = 16;
            }
        } else {
            lpadWidth = 0;
            rpadWidth = 0;
        }

        trackDateAddedLabel = new JLabel(dateAdded, JLabel.LEFT);
        trackDateAddedLabel.setBackground(Color.GREEN);
        trackDateAddedLabel.setFont(new Font("Gotham", Font.PLAIN, 14));
        trackDateAddedLabel.setForeground(Threshold.palette_b3b3b3);
        trackDateAddedLabel.setMaximumSize(new Dimension(
            lpadWidth, (int)trackDateAddedLabel.getPreferredSize().getHeight()
        ));
        trackDateAddedLabel.setOpaque(false);

        dateAddedRightMargin = new JLabel();
        dateAddedRightMargin.setBackground(Color.GREEN);
        dateAddedRightMargin.setFont(new Font("Gotham", Font.PLAIN, 14));
        dateAddedRightMargin.setForeground(Threshold.palette_b3b3b3);
        dateAddedRightMargin.setMaximumSize(new Dimension(
            rpadWidth, (int)trackDateAddedLabel.getPreferredSize().getHeight()
        ));
        dateAddedRightMargin.setOpaque(false);
        dateAddedRightMargin.setVisible(false);

        trackDateAddedColumn = new JPanel();
        trackDateAddedColumn.add(trackDateAddedLabel);
        trackDateAddedColumn.add(dateAddedRightMargin);
        trackDateAddedColumn.setBackground(Color.MAGENTA);
        trackDateAddedColumn.setBorder(null);
        trackDateAddedColumn.setLayout(new BoxLayout(trackDateAddedColumn, BoxLayout.X_AXIS));
        trackDateAddedColumn.setMaximumSize(new Dimension(lpadWidth + rpadWidth, globalHeight));
        trackDateAddedColumn.setOpaque(false);
        trackDateAddedColumn.setPreferredSize(trackDateAddedColumn.getMaximumSize());
        trackDateAddedColumn.setSize(trackDateAddedColumn.getPreferredSize());
    }

    private void initTrackDurationColumn(Window windowApp, int songDuration) {
        int minutes = songDuration / 60;
        int seconds = songDuration % 60;

        likeSongButton = new JButton(scaleImageIcon(
            "src/com/spotify/assets/icons/likes_off.png", 16, 16
        ));
        likeSongButton.setName("Likes_Off");

        for(int i = 0; i < windowApp.user.getLikedSongs().getSongs().size(); i++) {
            if(windowApp.user.getLikedSongs().getSongs().get(i).getTitle().equals(song.getTitle()) &&
            windowApp.user.getLikedSongs().getSongs().get(i).getPlaylistName().equals(song.getPlaylistName())) {
                likeSongButton.setIcon(scaleImageIcon(
                    "src/com/spotify/assets/icons/likes_on.png", 16, 16
                ));
                likeSongButton.setName("Likes_On");
                break;
            }
        }

        likeSongButton.setBackground(Color.CYAN);
        likeSongButton.setBorder(null);
        likeSongButton.setBorderPainted(false);
        likeSongButton.setContentAreaFilled(false);
        likeSongButton.setFocusable(false);
        likeSongButton.setFocusPainted(false);
        likeSongButton.setMaximumSize(new Dimension(16, 17));
        likeSongButton.setMinimumSize(new Dimension(16, 16));
        likeSongButton.setOpaque(false);
        likeSongButton.setPreferredSize(likeSongButton.getMaximumSize());
        likeSongButton.setSize(likeSongButton.getPreferredSize());
        likeSongButton.setVerticalAlignment(SwingConstants.BOTTOM);
        likeSongButton.addActionListener((ActionEvent ae) -> {
            if(likeSongButton.getName().equals("Likes_Off")) {
                likeSongButton.setIcon(scaleImageIcon(
                    "src/com/spotify/assets/icons/likes_on_hover.png", 16, 16
                ));
                likeSongButton.setName("Likes_On");
                song.setTotalLikes(song.getTotalLikes() + 1);

                System.out.println(song.getTotalLikes());

                windowApp.user.getLikedSongs().getSongs().add(song);
            } else if(likeSongButton.getName().equals("Likes_On")) {
                likeSongButton.setIcon(scaleImageIcon(
                    "src/com/spotify/assets/icons/likes_off_hover.png", 16, 16
                ));
                likeSongButton.setName("Likes_Off");
                song.setTotalLikes(song.getTotalLikes() - 1);

                System.out.println(song.getTotalLikes());

                for(int i = 0; i < windowApp.user.getLikedSongs().getSongs().size(); i++) {
                    if(windowApp.user.getLikedSongs().getSongs().get(i).getTitle().equals(song.getTitle())) {
                        windowApp.user.getLikedSongs().getSongs().remove(i);
                        break;
                    }
                }

                windowApp.getContentPanels().remove(windowApp.getContentPanels().get(windowApp.getContentPanels()
                .size() - 1));
                windowApp.setPage(windowApp.getPage() - 1);
                PlaylistUI panel = new PlaylistUI(
                    windowApp.accounts, windowApp.user.getLikedSongs(), windowApp, PlaylistUI.LIKED_PLAYLIST, -1
                );
                windowApp.changeContent(
                    new Content(panel, true), new ContentDraggable(windowApp, windowApp.user), windowApp.user,
                    windowApp.accounts
                );
                windowApp.activeUserPlaylist = -1;
                windowApp.changeUserPlaylistBtnColor();
            }
        });
        likeSongButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                if(isPlaying == false) {
                    hoveringPlayButton = true;

                    trackNumberColumn.setVisible(false);
                    trackNumberColumn.revalidate();
                    trackNumberColumn.repaint();

                    buttonHolder.setVisible(true);
                    buttonHolder.revalidate();
                    buttonHolder.repaint();
                }

                if(likeSongButton.getName().equals("Likes_Off")) {
                    likeSongButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_off_hover.png", 16, 16
                    ));
                } else if(likeSongButton.getName().equals("Likes_On")) {
                    likeSongButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_on_hover.png", 16, 16
                    ));
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(isPlaying == false) {
                    hoveringPlayButton = false;
                }

                if(likeSongButton.getName().equals("Likes_Off")) {
                    likeSongButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_off.png", 16, 16
                    ));
                } else if(likeSongButton.getName().equals("Likes_On")) {
                    likeSongButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_on.png", 16, 16
                    ));
                }
            }
        });

        trackDurationLabel = new JLabel(String.format("%d:%02d", minutes, seconds), JLabel.RIGHT);
        trackDurationLabel.setBackground(Color.GREEN);
        trackDurationLabel.setFont(new Font("Gotham", Font.PLAIN, 14));
        trackDurationLabel.setForeground(Threshold.palette_b3b3b3);
        trackDurationLabel.setMaximumSize(new Dimension(
            52, (int)trackDurationLabel.getPreferredSize().getHeight()
        ));
        trackDurationLabel.setOpaque(false);

        expandedMenuBtn = new JButton(scaleImageIcon(
            "./src/com/spotify/assets/icons/three_dots_default.png", 16, 16
        ));
        expandedMenuBtn.setBackground(Color.CYAN);
        expandedMenuBtn.setBorder(null);
        expandedMenuBtn.setBorderPainted(false);
        expandedMenuBtn.setContentAreaFilled(false);
        expandedMenuBtn.setFocusable(false);
        expandedMenuBtn.setFocusPainted(false);
        expandedMenuBtn.setMaximumSize(new Dimension(16, 17));
        expandedMenuBtn.setMinimumSize(new Dimension(16, 16));
        expandedMenuBtn.setOpaque(false);
        expandedMenuBtn.setPreferredSize(expandedMenuBtn.getMaximumSize());
        expandedMenuBtn.setSize(expandedMenuBtn.getPreferredSize());
        expandedMenuBtn.setVerticalAlignment(SwingConstants.BOTTOM);
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

                ExpandedMenu tempMenu = new ExpandedMenu(windowApp, this, playlistStyle, ExpandedMenu.TRACK_ROW);
                tempMenu.setLocation(
                    (int)expandedMenuBtn.getLocationOnScreen().getX() - Threshold.sidebarArea.width -
                    tempMenu.getWidth() + expandedMenuBtn.getWidth(), expandedMenuBtn.getHeight() +
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
                if(isPlaying == false) {
                    hoveringPlayButton = true;

                    trackNumberColumn.setVisible(false);
                    trackNumberColumn.revalidate();
                    trackNumberColumn.repaint();

                    buttonHolder.setVisible(true);
                    buttonHolder.revalidate();
                    buttonHolder.repaint();
                }

                expandedMenuBtn.setIcon(scaleImageIcon(
                    "src/com/spotify/assets/icons/three_dots_hover.png", 16, 16
                ));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(isPlaying == false) {
                    hoveringPlayButton = false;
                }

                expandedMenuBtn.setIcon(scaleImageIcon(
                    "src/com/spotify/assets/icons/three_dots_default.png", 16, 16
                ));
            }
        });

        JLabel rightMargin = new JLabel();
        rightMargin.setBackground(Color.CYAN);
        rightMargin.setFont(new Font("Gotham", Font.PLAIN, 14));
        rightMargin.setForeground(Threshold.palette_b3b3b3);
        rightMargin.setMaximumSize(new Dimension(
            16, (int)trackDurationLabel.getPreferredSize().getHeight()
        ));
        rightMargin.setOpaque(false);

        trackDurationColumn = new JPanel();
        trackDurationColumn.add(likeSongButton);
        trackDurationColumn.add(trackDurationLabel);
        trackDurationColumn.add(rightMargin);
        trackDurationColumn.add(expandedMenuBtn);
        trackDurationColumn.setBackground(Color.MAGENTA);
        trackDurationColumn.setBorder(null);
        trackDurationColumn.setLayout(new BoxLayout(trackDurationColumn, BoxLayout.X_AXIS));
        trackDurationColumn.setMaximumSize(new Dimension(100, globalHeight));
        trackDurationColumn.setOpaque(false);
        trackDurationColumn.setPreferredSize(trackDurationColumn.getMaximumSize());
        trackDurationColumn.setSize(trackDurationColumn.getPreferredSize());
    }

    public void resizeAll() {
        if(playlistStyle == NATIVE_PLAYLIST) {
            int lpadWidth, rpadWidth;

            if(Threshold.currentSize.width == Threshold.maximumSize.width) {
                if(Threshold.maximumSize.width >= 1600) {
                    lpadWidth = 104;
                    rpadWidth = 430;
                } else {
                    lpadWidth = 104;
                    rpadWidth = 230;
                }
            } else {
                lpadWidth = 104;
                rpadWidth = 40;
            }

            trackPlaysCountLabel.setMaximumSize(new Dimension(
                lpadWidth, (int)trackPlaysCountLabel.getPreferredSize().getHeight()
            ));
            trackPlaysCountLabel.revalidate();
            trackPlaysCountLabel.repaint();

            playsCountRightMargin.setMaximumSize(new Dimension(
                rpadWidth, (int)trackPlaysCountLabel.getPreferredSize().getHeight()
            ));
            playsCountRightMargin.revalidate();
            playsCountRightMargin.repaint();

            trackPlaysCountColumn.setMaximumSize(new Dimension(lpadWidth + rpadWidth, globalHeight));
            trackPlaysCountColumn.setPreferredSize(trackPlaysCountColumn.getMaximumSize());
            trackPlaysCountColumn.setSize(trackPlaysCountColumn.getPreferredSize());
            trackPlaysCountColumn.revalidate();
            trackPlaysCountColumn.repaint();

            titleColumnMaxWidth = (
                trackNumberColumn.getWidth() + trackPlaysCountColumn.getWidth() + trackDurationColumn.getWidth()
            );
        } else if(playlistStyle == CUSTOM_PLAYLIST) {
            int padWidth, lpadWidth, rpadWidth;

            if(Threshold.currentSize.width == Threshold.maximumSize.width) {
                if(Threshold.maximumSize.width >= 1600) {
                    padWidth = 428;
                    lpadWidth = 330;
                    rpadWidth = 16;
                } else {
                    padWidth = 268;
                    lpadWidth = 220;
                    rpadWidth = 16;
                }
            } else {
                padWidth = 138;
                lpadWidth = 0;
                rpadWidth = 0;
            }

            trackAlbumButton.setMaximumSize(new Dimension(
                padWidth, (int)trackAlbumButton.getPreferredSize().getHeight()
            ));
            trackAlbumButton.revalidate();
            trackAlbumButton.repaint();

            trackAlbumColumn.setMaximumSize(new Dimension(padWidth, globalHeight));
            trackAlbumColumn.setPreferredSize(trackAlbumColumn.getMaximumSize());
            trackAlbumColumn.setSize(trackAlbumColumn.getPreferredSize());
            trackAlbumColumn.revalidate();
            trackAlbumColumn.repaint();

            trackDateAddedLabel.setMaximumSize(new Dimension(
                lpadWidth, (int)trackDateAddedLabel.getPreferredSize().getHeight()
            ));
            trackDateAddedLabel.revalidate();
            trackDateAddedLabel.repaint();

            dateAddedRightMargin.setMaximumSize(new Dimension(
                rpadWidth, (int)trackDateAddedLabel.getPreferredSize().getHeight()
            ));
            dateAddedRightMargin.revalidate();
            dateAddedRightMargin.repaint();

            trackDateAddedColumn.setMaximumSize(new Dimension(lpadWidth + rpadWidth, globalHeight));
            trackDateAddedColumn.setPreferredSize(trackDateAddedColumn.getMaximumSize());
            trackDateAddedColumn.setSize(trackDateAddedColumn.getPreferredSize());
            trackDateAddedColumn.revalidate();
            trackDateAddedColumn.repaint();

            titleColumnMaxWidth = (
                trackNumberColumn.getWidth() + trackAlbumColumn.getWidth() +
                trackDateAddedColumn.getWidth() + trackDurationColumn.getWidth()
            );
        }

        trackTitleColumn.setMaximumSize(new Dimension(
            this.getWidth() - (16 * rigidBoxCount) - titleColumnMaxWidth, globalHeight
        ));
        trackTitleColumn.setPreferredSize(trackTitleColumn.getMaximumSize());
        trackTitleColumn.setSize(trackTitleColumn.getPreferredSize());
        trackTitleColumn.revalidate();
        trackTitleColumn.repaint();
    }

    public void playButtonAction(Window windowApp) {
        if(windowApp.audioPlayer.isShuffle() == true) {
            if(windowApp.countOnShuffle == 0) {
                windowApp.shuffleOnDemand = new ArrayList<>();

                for(int i = 0; i < windowApp.currentPlaylistTrackCount; i++) {
                    if(i != this.trackNumber - 1) {
                        windowApp.shuffleOnDemand.add(i + 1);
                    }
                }

                Collections.shuffle(windowApp.shuffleOnDemand);
                windowApp.shuffleOnDemand.add(0, this.trackNumber);

                /*
                System.out.print("\nShuffle Order: ");
                for(int i = 0; i < windowApp.currentPlaylistTrackCount; i++) {
                    System.out.print(windowApp.shuffleOnDemand.get(i));

                    if(i != windowApp.currentPlaylistTrackCount - 1) {
                        System.out.print(", ");
                    } else {
                        System.out.println("");
                    }
                }
                */
            }

            /*
            System.out.println("Count On Shuffle: " + windowApp.countOnShuffle);
            System.out.println("=============================================");
            */
        }

        /*
        System.out.println("Count On Shuffle: " + windowApp.countOnShuffle);
        System.out.println("=============================================");
        */

        if(playSongButton.getName().equalsIgnoreCase("Play_Button")) {
            hoveringPlayButton = true;
            isPlaying = true;

            trackNumberColumn.setVisible(false);
            trackNumberColumn.revalidate();
            trackNumberColumn.repaint();

            buttonHolder.setVisible(true);
            buttonHolder.revalidate();
            buttonHolder.repaint();

            playSongButton.setIcon(scaleImageIcon(
                "./src/com/spotify/assets/icons/pause_white.png", 16, 16
            ));
            playSongButton.setName("Pause_Button");

            trackTitleLabel.setForeground(Threshold.palette_1db954);
        } else if(playSongButton.getName().equalsIgnoreCase("Pause_Button")) {
            hoveringPlayButton = false;
            isPlaying = false;

            trackNumberColumn.setVisible(true);
            trackNumberColumn.revalidate();
            trackNumberColumn.repaint();

            buttonHolder.setVisible(false);
            buttonHolder.revalidate();
            buttonHolder.repaint();

            playSongButton.setIcon(scaleImageIcon(
                "./src/com/spotify/assets/icons/play_white.png", 16, 16
            ));
            playSongButton.setName("Play_Button");
        }

        windowApp.playButtonEvent = null;
        windowApp.playButtonEvent = new PlayButtonEvent(windowApp);

        windowApp.playButtonEvent.setPlayerPlayedSong(windowApp, playlistName, playlistID, trackNumber, song);
        windowApp.playButtonEvent.changePlayerIcon(windowApp);
        windowApp.playButtonEvent.changePlaylistIcon(windowApp);

        if(playSongButton.getName().equalsIgnoreCase("Pause_Button")) {
            if(windowApp.getContentPanel().getViewport().getView() instanceof PlaylistUI) {
                PlaylistUI tempUI = (PlaylistUI)windowApp.getContentPanel().getViewport().getView();

                tempUI.playAllBtn.setIcon(scaleImageIcon(
                    "./src/com/spotify/assets/icons/pause_white_rounded.png", 56, 56
                ));
                tempUI.playAllBtn.setName("Pause_Button");

                if(windowApp.activeTrackNumber != trackNumber && windowApp.activeTrackNumber != 0) {
                    TrackRow tempRow = (TrackRow)tempUI.trackTableList.getComponent(windowApp.activeTrackNumber - 1);

                    tempRow.hoveringPlayButton = false;
                    tempRow.isPlaying = false;

                    tempRow.playSongButton.setIcon(scaleImageIcon(
                        "./src/com/spotify/assets/icons/play_white.png", 16, 16
                    ));
                    tempRow.playSongButton.setName("Play_Button");

                    tempRow.trackTitleLabel.setForeground(Threshold.palette_ffffff);

                    tempRow.trackNumberColumn.setVisible(true);
                    tempRow.trackNumberColumn.revalidate();
                    tempRow.trackNumberColumn.repaint();

                    tempRow.buttonHolder.setVisible(false);
                    tempRow.buttonHolder.revalidate();
                    tempRow.buttonHolder.repaint();

                    tempRow.song.setTotalPlays(tempRow.song.getTotalPlays() + 1);

                    if(windowApp.audioPlayer.isShuffle() == true) {
                        windowApp.countOnShuffle += 1;
                    }

                    if(playlistStyle == NATIVE_PLAYLIST) {
                        tempRow.trackPlaysCountLabel.setText(NumberFormat.getInstance().format(
                            tempRow.song.getTotalPlays()
                        ));
                        tempRow.trackPlaysCountLabel.revalidate();
                        tempRow.trackPlaysCountLabel.repaint();
                    }
                }

                if(windowApp.audioPlayer.isShuffle() == true && windowApp.countOnShuffle == 0) {
                    windowApp.countOnShuffle += 1;
                }

                windowApp.activePlayer = true;
                windowApp.activeTrackNumber = trackNumber;
                windowApp.activePlaylistName = tempUI.getPlaylistTitle().getText();
            }
        } else {
            windowApp.activePlayer = false;
        }
    }

    public void changeHeartIconOnLiked(Window windowApp) {
        likeSongButton.setIcon(scaleImageIcon(
            "src/com/spotify/assets/icons/likes_on.png", 16, 16
        ));
        likeSongButton.setName("Likes_On");
        likeSongButton.revalidate();
        likeSongButton.repaint();
    }

    public boolean isHoveringPlayButton() {
        return hoveringPlayButton;
    }

    public void setHoveringPlayButton(boolean hoveringPlayButton) {
        this.hoveringPlayButton = hoveringPlayButton;
    }

    public boolean isIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public int getGlobalHeight() {
        return globalHeight;
    }

    public void setGlobalHeight(int globalHeight) {
        this.globalHeight = globalHeight;
    }

    public int getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public int getPlaylistStyle() {
        return playlistStyle;
    }

    public void setPlaylistStyle(int playlistStyle) {
        this.playlistStyle = playlistStyle;
    }

    public int getTitleColumnMaxWidth() {
        return titleColumnMaxWidth;
    }

    public void setTitleColumnMaxWidth(int titleColumnMaxWidth) {
        this.titleColumnMaxWidth = titleColumnMaxWidth;
    }

    public int getRigidBoxCount() {
        return rigidBoxCount;
    }

    public void setRigidBoxCount(int rigidBoxCount) {
        this.rigidBoxCount = rigidBoxCount;
    }

    public JLabel getTrackNumberLabel() {
        return trackNumberLabel;
    }

    public void setTrackNumberLabel(JLabel trackNumberLabel) {
        this.trackNumberLabel = trackNumberLabel;
    }

    public JButton getPlaySongButton() {
        return playSongButton;
    }

    public void setPlaySongButton(JButton playSongButton) {
        this.playSongButton = playSongButton;
    }

    public JPanel getButtonHolder() {
        return buttonHolder;
    }

    public void setButtonHolder(JPanel buttonHolder) {
        this.buttonHolder = buttonHolder;
    }

    public JPanel getTrackNumberColumn() {
        return trackNumberColumn;
    }

    public void setTrackNumberColumn(JPanel trackNumberColumn) {
        this.trackNumberColumn = trackNumberColumn;
    }

    public JLayeredPane getPlayButtonLayeredPane() {
        return playButtonLayeredPane;
    }

    public void setPlayButtonLayeredPane(JLayeredPane playButtonLayeredPane) {
        this.playButtonLayeredPane = playButtonLayeredPane;
    }

    public ImageIcon getArtImageIcon() {
        return artImageIcon;
    }

    public void setArtImageIcon(ImageIcon artImageIcon) {
        this.artImageIcon = artImageIcon;
    }

    public JLabel getTrackTitleLabel() {
        return trackTitleLabel;
    }

    public void setTrackTitleLabel(JLabel trackTitleLabel) {
        this.trackTitleLabel = trackTitleLabel;
    }

    public JPanel getTrackTitleColumn() {
        return trackTitleColumn;
    }

    public void setTrackTitleColumn(JPanel trackTitleColumn) {
        this.trackTitleColumn = trackTitleColumn;
    }

    public JButton getContributorsName() {
        return contributorsName;
    }

    public void setContributorsName(JButton contributorsName) {
        this.contributorsName = contributorsName;
    }

    public JLabel getTrackPlaysCountLabel() {
        return trackPlaysCountLabel;
    }

    public void setTrackPlaysCountLabel(JLabel trackPlaysCountLabel) {
        this.trackPlaysCountLabel = trackPlaysCountLabel;
    }

    public JLabel getPlaysCountRightMargin() {
        return playsCountRightMargin;
    }

    public void setPlaysCountRightMargin(JLabel playsCountRightMargin) {
        this.playsCountRightMargin = playsCountRightMargin;
    }

    public JPanel getTrackPlaysCountColumn() {
        return trackPlaysCountColumn;
    }

    public void setTrackPlaysCountColumn(JPanel trackPlaysCountColumn) {
        this.trackPlaysCountColumn = trackPlaysCountColumn;
    }

    public JButton getTrackAlbumButton() {
        return trackAlbumButton;
    }

    public void setTrackAlbumButton(JButton trackAlbumButton) {
        this.trackAlbumButton = trackAlbumButton;
    }

    public JPanel getTrackAlbumColumn() {
        return trackAlbumColumn;
    }

    public void setTrackAlbumColumn(JPanel trackAlbumColumn) {
        this.trackAlbumColumn = trackAlbumColumn;
    }

    public JLabel getTrackDateAddedLabel() {
        return trackDateAddedLabel;
    }

    public void setTrackDateAddedLabel(JLabel trackDateAddedLabel) {
        this.trackDateAddedLabel = trackDateAddedLabel;
    }

    public JLabel getDateAddedRightMargin() {
        return dateAddedRightMargin;
    }

    public void setDateAddedRightMargin(JLabel dateAddedRightMargin) {
        this.dateAddedRightMargin = dateAddedRightMargin;
    }

    public JPanel getTrackDateAddedColumn() {
        return trackDateAddedColumn;
    }

    public void setTrackDateAddedColumn(JPanel trackDateAddedColumn) {
        this.trackDateAddedColumn = trackDateAddedColumn;
    }

    public JButton getLikeSongButton() {
        return likeSongButton;
    }

    public void setLikeSongButton(JButton likeSongButton) {
        this.likeSongButton = likeSongButton;
    }

    public JButton getExpandedMenuBtn() {
        return expandedMenuBtn;
    }

    public void setExpandedMenuBtn(JButton expandedMenuBtn) {
        this.expandedMenuBtn = expandedMenuBtn;
    }

    public JLabel getTrackDurationLabel() {
        return trackDurationLabel;
    }

    public void setTrackDurationLabel(JLabel trackDurationLabel) {
        this.trackDurationLabel = trackDurationLabel;
    }

    public JPanel getTrackDurationColumn() {
        return trackDurationColumn;
    }

    public void setTrackDurationColumn(JPanel trackDurationColumn) {
        this.trackDurationColumn = trackDurationColumn;
    }
}
