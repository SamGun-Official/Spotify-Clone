package com.spotify.ui;

import com.spotify.app.Song;
import com.spotify.ui.graphics.PaintSlider;
import com.spotify.ui.playlist.PlaylistUI;
import com.spotify.ui.playlist.TrackRow;
import com.spotify.ui.thread.AudioHandler;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

public class Player extends javax.swing.JPanel implements com.spotify.app.Utility {
    private final Color backgroundColor;
    private final Dimension playerArea;
    private final Dimension currentSize;
    private final Window windowApp;

    protected JPanel playedSong;
    protected JButton albumArt;
    protected JPanel songInfo;
    protected JButton songTitle;
    protected JButton songArtist;
    protected JButton likeButton;

    protected AudioHandler audioHandler;
    protected JPanel musicPlayer;
    protected JPanel controlPanel;
    protected JButton shuffleBtn;
    protected JButton previousBtn;
    protected JButton playBtn;
    protected JButton nextBtn;
    protected JButton repeatBtn;
    protected JLayeredPane progressBarLayeredPane;
    protected JPanel playbackPanel;
    protected JPanel progressBarGlassPanel;
    protected JLabel currentPosition;
    protected JProgressBar progressBar;
    protected JSlider progressBarSlider;
    protected JLabel maximumDuration;

    protected JPanel playerControl;
    protected JButton lyricBtn;
    protected JButton queueBtn;
    protected JButton muteBtn;
    protected JLayeredPane volumeBarLayeredPane;
    protected JPanel volumePanel;
    protected JPanel volumeBarGlassPanel;
    protected JProgressBar volumeBar;
    protected JSlider volumeBarSlider;

    protected Song currentSong;
    protected String filepath;

    protected boolean isShuffle;
    protected int loopingID;
    protected float tempVolumeOnMute = 0.0f;
    protected int volumePercentage = 4;

    /** The multithread code had a little poor behavior and sometimes causing bugs.
     *  Several source on the internet recommend to use queue to make it more robust.
     */
    /* final Queue<AudioHandler> thePlayerQueue = new ArrayDeque<>(); */

    /**
     *  @param windowApp
     *  @param currentSong
     *  @param isFirstTime
     */
    public Player(Window windowApp, Song currentSong, boolean isFirstTime) {
        this.backgroundColor = new Color(24, 24, 24);
        this.currentSize = Threshold.currentSize;
        this.playerArea = Threshold.playerArea;
        this.currentSong = currentSong;
        this.windowApp = windowApp;

        this.initPlayedSong(-1);
        this.initMusicPlayer();
        this.initPlayerControl();

        this.windowApp.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                resizeMiddlePanel();
            }
        });
    }

    //<editor-fold defaultstate="collapsed" desc="Final method to get and display currently played song.">
    public final void initPlayedSong(int playlistID) {
        playedSong = new JPanel();
        playedSong.setBackground(this.backgroundColor);
        playedSong.setBounds(
            0, (int)(Threshold.currentSize.height - playerArea.getHeight()),
            (int)(Threshold.currentSize.width * 30 / 100) + 6, (int)playerArea.getHeight()
        );
        playedSong.setLayout(new BoxLayout(playedSong, BoxLayout.X_AXIS));
        playedSong.setPreferredSize(playedSong.getSize());
    }

    public final void initNotFirstTime() {
        if(windowApp.isFirstTime == false) {
            for(int i = 0; i < playedSong.getComponentCount(); i++) {
                playedSong.remove(playedSong.getComponent(i));
            }
        }

        windowApp.isFirstTime = false;

        if(currentSong.getAlbumArt().equals("./src/com/spotify/assets/icons/music_icon.png")) {
            albumArt = new JButton(scaleImageIcon(currentSong.getAlbumArt(), 18, 18));
        } else {
            albumArt = new JButton(scaleImageIcon(currentSong.getAlbumArt(), 56, 56));
        }

        albumArt.setBackground(new Color(40, 40, 40));
        albumArt.setBorder(null);
        albumArt.setBorderPainted(false);
        albumArt.setContentAreaFilled(false);
        /* albumArt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); */
        albumArt.setFocusable(false);
        albumArt.setFocusPainted(false);
        albumArt.setMaximumSize(new Dimension(56, 56));
        albumArt.setMinimumSize(new Dimension(56, 56));
        albumArt.setOpaque(true);
        albumArt.setPreferredSize(albumArt.getMinimumSize());
        albumArt.setSize(albumArt.getPreferredSize());

        songInfo = new JPanel();
        songInfo.setBackground(this.backgroundColor);
        songInfo.setLayout(new BoxLayout(songInfo, BoxLayout.Y_AXIS));
        songInfo.setPreferredSize(new Dimension(56, 56));
        songInfo.setSize(songInfo.getPreferredSize());

        songTitle = new JButton(currentSong.getTitle());
        songTitle.setBackground(this.backgroundColor);
        songTitle.setBorder(null);
        songTitle.setBorderPainted(false);
        songTitle.setContentAreaFilled(false);
        /* songTitle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); */
        songTitle.setFocusable(false);
        songTitle.setFocusPainted(false);
        songTitle.setFont(new Font("Gotham", Font.BOLD, 13));
        songTitle.setForeground(Threshold.palette_ffffff);
        songTitle.setHorizontalAlignment(SwingConstants.LEFT);
        songTitle.setOpaque(true);
        songTitle.setVerticalAlignment(SwingConstants.BOTTOM);
        /* songTitle.addActionListener((ActionEvent ae) -> {
            windowApp.getLayeredPane().remove(windowApp.getContentPanel());

            PlaylistUI panel = new PlaylistUI(
                windowApp.admin.getPlaylists().get(playlistID), windowApp,
                PlaylistUI.CUSTOM_PLAYLIST, playlistID
            );

            windowApp.setContentPanel(new Content(panel, true));
            windowApp.getLayeredPane().add(windowApp.getContentPanel());

            windowApp.revalidate();
            windowApp.repaint();
        });
        songTitle.addMouseListener(new MouseAdapter() {
            Font font = songTitle.getFont();
            Map attributes = font.getAttributes();

            @Override
            public void mouseEntered(MouseEvent me) {
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                songTitle.setFont(font.deriveFont(attributes));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                attributes.put(TextAttribute.UNDERLINE, null);
                songTitle.setFont(font.deriveFont(attributes));
            }
        }); */

        songArtist = new JButton(currentSong.getArtist().getName());
        songArtist.setBackground(this.backgroundColor);
        songArtist.setBorder(null);
        songArtist.setBorderPainted(false);
        songArtist.setContentAreaFilled(false);
        songArtist.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        songArtist.setFocusable(false);
        songArtist.setFocusPainted(false);
        songArtist.setFont(new Font("Gotham", Font.BOLD, 11));
        songArtist.setForeground(Threshold.palette_b3b3b3);
        songArtist.setHorizontalAlignment(SwingConstants.LEFT);
        songArtist.setOpaque(true);
        songArtist.setVerticalAlignment(SwingConstants.TOP);
        songArtist.addMouseListener(new MouseAdapter() {
            Font font = songArtist.getFont();
            Map attributes = font.getAttributes();

            @Override
            public void mouseEntered(MouseEvent me) {
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                songArtist.setFont(font.deriveFont(attributes));
            }

            @Override
            public void mouseExited(MouseEvent me) {
                attributes.put(TextAttribute.UNDERLINE, null);
                songArtist.setFont(font.deriveFont(attributes));
            }
        });

        songInfo.add(Box.createRigidArea(new Dimension(0, -1)));
        songInfo.add(Box.createRigidArea(new Dimension(0, -1)));
        songInfo.add(songTitle);
        songInfo.add(Box.createRigidArea(new Dimension(0, -1)));
        songInfo.add(songArtist);

        likeButton = new JButton(scaleImageIcon(
            "./src/com/spotify/assets/icons/likes_off.png", 16, 16
        ));
        likeButton.setBackground(this.backgroundColor);
        likeButton.setBorder(null);
        likeButton.setBorderPainted(false);
        likeButton.setBounds((int)playedSong.getSize().getWidth() - 18, 0, 16, 16);
        likeButton.setContentAreaFilled(false);
        likeButton.setFocusable(false);
        likeButton.setFocusPainted(false);
        likeButton.setMaximumSize(new Dimension(16, 16));
        likeButton.setMinimumSize(new Dimension(16, 16));
        likeButton.setName("Likes_Off");
        likeButton.setOpaque(true);
        likeButton.setPreferredSize(likeButton.getMaximumSize());
        likeButton.setSize(likeButton.getPreferredSize());
        likeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if(likeButton.getName().equals("Likes_Off")) {
                    likeButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_on_hover.png", 16, 16
                    ));
                    likeButton.setName("Likes_On");
                } else if(likeButton.getName().equals("Likes_On")) {
                    likeButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_off_hover.png", 16, 16
                    ));
                    likeButton.setName("Likes_Off");
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                if(likeButton.getName().equals("Likes_Off")) {
                    likeButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_off_hover.png", 16, 16
                    ));
                } else if(likeButton.getName().equals("Likes_On")) {
                    likeButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_on_hover.png", 16, 16
                    ));
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(likeButton.getName().equals("Likes_Off")) {
                    likeButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_off.png", 16, 16
                    ));
                } else if(likeButton.getName().equals("Likes_On")) {
                    likeButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_on.png", 16, 16
                    ));
                }
            }
        });

        if(windowApp.user.getLikedSongs().getSongs().size() > 0) {
            for(int i = 0; i < windowApp.user.getLikedSongs().getSongs().size(); i++) {
                if(windowApp.user.getLikedSongs().getSongs().get(i).getTitle().equals(currentSong.getTitle())) {
                    likeButton.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/likes_on.png", 16, 16
                    ));
                    likeButton.setName("Likes_On");
                }
            }
        }

        playedSong.add(Box.createRigidArea(new Dimension(16, 0)));
        playedSong.add(albumArt);
        playedSong.add(Box.createRigidArea(new Dimension(14, 0)));
        playedSong.add(songInfo);
        playedSong.add(Box.createRigidArea(new Dimension(22, 0)));
        playedSong.add(likeButton);
        playedSong.add(Box.createRigidArea(new Dimension(2, 0)));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Final method to construct audio player.">
    public final void initMusicPlayer() {
        musicPlayer = new JPanel();
        musicPlayer.setBackground(this.backgroundColor);
        musicPlayer.setBounds(
            (int)playedSong.getWidth(), (int)(currentSize.getHeight() - playerArea.getHeight()),
            ((int)currentSize.getWidth() - (int)(currentSize.getWidth() * 30 / 100) * 2) - 12,
            (int)playerArea.getHeight()
        );
        musicPlayer.setLayout(new BoxLayout(musicPlayer, BoxLayout.Y_AXIS));
        musicPlayer.setPreferredSize(musicPlayer.getSize());
        musicPlayer.setSize(musicPlayer.getPreferredSize());
        musicPlayer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                resizeMiddlePanel();
            }
        });

        progressBarLayeredPane = new JLayeredPane();
        progressBarLayeredPane.setPreferredSize(new Dimension(
            (int)musicPlayer.getSize().getWidth(), 16
        ));
        progressBarLayeredPane.setSize(progressBarLayeredPane.getPreferredSize());

        controlPanel = new JPanel();
        controlPanel.setBackground(this.backgroundColor);
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 15));
        controlPanel.setPreferredSize(new Dimension(
            (int)musicPlayer.getSize().getWidth(), (int)musicPlayer.getSize().getHeight() / 2
        ));
        controlPanel.setSize(controlPanel.getPreferredSize());

        playbackPanel = new JPanel();
        playbackPanel.setBackground(this.backgroundColor);
        playbackPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        playbackPanel.setPreferredSize(new Dimension(
            (int)musicPlayer.getSize().getWidth(), 16
        ));
        playbackPanel.setSize(playbackPanel.getPreferredSize());

        shuffleBtn = new JButton(
            scaleImageIcon("src/com/spotify/assets/icons/player/shuffle_off.png", 16, 16)
        );
        shuffleBtn.setBackground(this.backgroundColor);
        shuffleBtn.setBorder(null);
        shuffleBtn.setBorderPainted(false);
        shuffleBtn.setContentAreaFilled(false);
        shuffleBtn.setFocusable(false);
        shuffleBtn.setFocusPainted(false);
        shuffleBtn.setMaximumSize(new Dimension(32, 32));
        shuffleBtn.setMinimumSize(new Dimension(32, 32));
        shuffleBtn.setName("Shuffle_Off");
        shuffleBtn.setOpaque(true);
        shuffleBtn.setPreferredSize(shuffleBtn.getMaximumSize());
        shuffleBtn.setSize(shuffleBtn.getPreferredSize());
        shuffleBtn.setToolTipText("Enable shuffle");
        //<editor-fold defaultstate="collapsed" desc="MouseEvent handler for shuffle button.">
        shuffleBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if(shuffleBtn.getName().equalsIgnoreCase("Shuffle_Off")) {
                    windowApp.countOnShuffle = 0;
                    windowApp.countOnShuffle += 1;

                    shuffleBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/shuffle_on_hover.png", 16, 16)
                    );
                    shuffleBtn.setName("Shuffle_On");
                    shuffleBtn.setToolTipText("Disable shuffle");

                    isShuffle = true;
                    windowApp.shuffleOnDemand = new ArrayList<>();

                    for(int i = 0; i < windowApp.currentPlaylistTrackCount; i++) {
                        if(i != windowApp.activeTrackNumber - 1) {
                            windowApp.shuffleOnDemand.add(i + 1);
                        }
                    }

                    Collections.shuffle(windowApp.shuffleOnDemand);
                    windowApp.shuffleOnDemand.add(0, windowApp.activeTrackNumber);

                    /*
                    System.out.print("\nShuffle Order (Fallback): ");
                    for(int i = 0; i < windowApp.currentPlaylistTrackCount; i++) {
                        System.out.print(windowApp.shuffleOnDemand.get(i));

                        if(i != windowApp.currentPlaylistTrackCount - 1) {
                            System.out.print(", ");
                        } else {
                            System.out.println("");
                        }
                    }
                    System.out.println("Count On Shuffle: " + windowApp.countOnShuffle);
                    System.out.println("=============================================");
                    */
                } else if(shuffleBtn.getName().equalsIgnoreCase("Shuffle_On")) {
                    shuffleBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/shuffle_off_hover.png", 16, 16)
                    );
                    shuffleBtn.setName("Shuffle_Off");
                    shuffleBtn.setToolTipText("Enable shuffle");

                    isShuffle = false;
                }

                if(audioHandler != null) {
                    audioHandler.setShuffle(isShuffle);
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                if(shuffleBtn.getName().equalsIgnoreCase("Shuffle_Off")) {
                    shuffleBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/shuffle_off_hover.png", 16, 16)
                    );
                    shuffleBtn.setToolTipText("Enable shuffle");
                } else if(shuffleBtn.getName().equalsIgnoreCase("Shuffle_On")) {
                    shuffleBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/shuffle_on_hover.png", 16, 16)
                    );
                    shuffleBtn.setToolTipText("Disable shuffle");
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(shuffleBtn.getName().equalsIgnoreCase("Shuffle_Off")) {
                    shuffleBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/shuffle_off.png", 16, 16)
                    );
                } else if(shuffleBtn.getName().equalsIgnoreCase("Shuffle_On")) {
                    shuffleBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/shuffle_on.png", 16, 16)
                    );
                }
            }
        });
        //</editor-fold>

        previousBtn = new JButton(
            scaleImageIcon("src/com/spotify/assets/icons/player/previous_default.png", 16, 16)
        );
        previousBtn.setBackground(this.backgroundColor);
        previousBtn.setBorder(null);
        previousBtn.setBorderPainted(false);
        previousBtn.setContentAreaFilled(false);
        previousBtn.setFocusable(false);
        previousBtn.setFocusPainted(false);
        previousBtn.setMaximumSize(new Dimension(32, 32));
        previousBtn.setMinimumSize(new Dimension(32, 32));
        previousBtn.setName("Previous_Button");
        previousBtn.setOpaque(true);
        previousBtn.setPreferredSize(previousBtn.getMaximumSize());
        previousBtn.setSize(previousBtn.getPreferredSize());
        previousBtn.setToolTipText("Previous");
        //<editor-fold defaultstate="collapsed" desc="MouseEvent handler for previous button.">
        previousBtn.addActionListener((ActionEvent ae) -> {
            if(windowApp.getContentPanel().getViewport().getView() instanceof PlaylistUI) {
                PlaylistUI tempUI = (PlaylistUI)windowApp.getContentPanel().getViewport().getView();

//                if(windowApp.activePlaylistName.equals(tempUI.getPlaylistTitle().getText())) {
                    int tempIndex;

                    if(isShuffle == false) {
                        if(windowApp.activeTrackNumber - 2 < 0) {
                            tempIndex = windowApp.currentPlaylistTrackCount - 1;
                        } else {
                            tempIndex = windowApp.activeTrackNumber - 2;
                        }

                        TrackRow tempRow = (TrackRow)tempUI.getTrackTableList().getComponent(tempIndex);
                        tempRow.playButtonAction(windowApp);
                    } else {
                        if(windowApp.countOnShuffle - 2 < 0) {
                            tempIndex = windowApp.shuffleOnDemand.get(0);
                        } else {
                            tempIndex = windowApp.shuffleOnDemand.get(windowApp.countOnShuffle - 2) - 1;
                        }

                        /*
                        System.out.println(windowApp.countOnShuffle);
                        */

                        if(windowApp.countOnShuffle - 2 < 0) {
                            audioHandler.startOver();
                        } else {
                            TrackRow tempRow = (TrackRow)tempUI.getTrackTableList().getComponent(tempIndex);
                            tempRow.playButtonAction(windowApp);

                            windowApp.countOnShuffle -= 2;
                        }
                    }
//                } else {
//                    int playlistID = 0;
//
//                    for(int i = 0; i < windowApp.admin.getPlaylists().size(); i++) {
//                        if(windowApp.admin.getPlaylists().get(i).getName().equals(windowApp.activePlaylistName)) {
//                            playlistID = windowApp.admin.getPlaylists().get(i).getPlaylistID();
//                        }
//                    }
//
//                    PlaylistUI panel = new PlaylistUI(
//                        windowApp.accounts, windowApp.admin.getPlaylists().get(playlistID), windowApp,
//                        PlaylistUI.NATIVE_PLAYLIST, -1
//                    );
//                    windowApp.activeTrackNumber -= 1;
//                    TrackRow tempRow = (TrackRow)panel.getTrackTableList()
//                    .getComponent(windowApp.activeTrackNumber - 1);
//                    tempRow.playButtonAction(windowApp);
//
//                    int tempIndex;
//                    int playlistID = 0;
//
//                    for(int i = 0; i < windowApp.admin.getPlaylists().size(); i++) {
//                        if(windowApp.admin.getPlaylists().get(i).getName().equals(windowApp.activePlaylistName)) {
//                            playlistID = windowApp.admin.getPlaylists().get(i).getPlaylistID();
//                        }
//                    }
//
//                    if(windowApp.activeTrackNumber - 2 < 0) {
//                        tempIndex = windowApp.currentPlaylistTrackCount - 1;
//                        windowApp.activeTrackNumber = windowApp.currentPlaylistTrackCount;
//                    } else {
//                        if(windowApp.activeTrackNumber == 0) {
//                            tempIndex = 0;
//                        } else {
//                            tempIndex = windowApp.activeTrackNumber - 2;
//                            windowApp.activeTrackNumber -= 1;
//                        }
//                    }
//
//                    Song song = windowApp.admin.getPlaylists().get(playlistID).getSongs().get(tempIndex);
//
//                    TrackRow tempRow = new TrackRow(windowApp, 0, TrackRow.NATIVE_PLAYLIST,
//                    windowApp.activeTrackNumber - 1, song, windowApp.activePlaylistName, playlistID);
//                    tempRow.playButtonAction(windowApp);
//                }
            }
        });
        previousBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                previousBtn.setIcon(
                    scaleImageIcon("src/com/spotify/assets/icons/player/previous_hover.png", 16, 16)
                );
            }

            @Override
            public void mouseExited(MouseEvent me) {
                previousBtn.setIcon(
                    scaleImageIcon("src/com/spotify/assets/icons/player/previous_default.png", 16, 16)
                );
            }
        });
        //</editor-fold>

        playBtn = new JButton(
            scaleImageIcon("src/com/spotify/assets/icons/player/play_black_rounded.png", 32, 32)
        );
        playBtn.setBackground(this.backgroundColor);
        playBtn.setBorder(null);
        playBtn.setBorderPainted(false);
        playBtn.setContentAreaFilled(false);
        playBtn.setFocusable(false);
        playBtn.setFocusPainted(false);
        playBtn.setMaximumSize(new Dimension(32, 32));
        playBtn.setMinimumSize(new Dimension(32, 32));
        playBtn.setName("Play_Button");
        playBtn.setOpaque(true);
        playBtn.setPreferredSize(playBtn.getMaximumSize());
        playBtn.setSize(playBtn.getPreferredSize());
        playBtn.setToolTipText("Play");
        //<editor-fold defaultstate="collapsed" desc="MouseEvent handler for play button.">
        playBtn.addActionListener((ActionEvent ae) -> {
            if(windowApp.playButtonEvent != null) {
                if(windowApp.getContentPanel().getViewport().getView() instanceof PlaylistUI) {
                    PlaylistUI tempUI = (PlaylistUI)windowApp.getContentPanel().getViewport().getView();
                    TrackRow tempRow = (TrackRow)tempUI.getTrackTableList().getComponent(
                        windowApp.activeTrackNumber - 1
                    );
                    tempRow.playButtonAction(windowApp);
                }
            }
        });
        playBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                if(playBtn.getName().equalsIgnoreCase("Play_Button")) {
                    playBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/play_black_rounded.png", 32, 32)
                    );
                } else if(playBtn.getName().equalsIgnoreCase("Pause_Button")) {
                    playBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/pause_black_rounded.png", 32, 32)
                    );
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(playBtn.getName().equalsIgnoreCase("Play_Button")) {
                    playBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/play_black_rounded.png", 32, 32)
                    );
                } else if(playBtn.getName().equalsIgnoreCase("Pause_Button")) {
                    playBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/pause_black_rounded.png", 32, 32)
                    );
                }
            }
        });
        //</editor-fold>

        nextBtn = new JButton(
            scaleImageIcon("src/com/spotify/assets/icons/player/next_default.png", 16, 16)
        );
        nextBtn.setBackground(this.backgroundColor);
        nextBtn.setBorder(null);
        nextBtn.setBorderPainted(false);
        nextBtn.setContentAreaFilled(false);
        nextBtn.setFocusable(false);
        nextBtn.setFocusPainted(false);
        nextBtn.setMaximumSize(new Dimension(32, 32));
        nextBtn.setMinimumSize(new Dimension(32, 32));
        nextBtn.setName("Next_Button");
        nextBtn.setOpaque(true);
        nextBtn.setPreferredSize(nextBtn.getMaximumSize());
        nextBtn.setSize(nextBtn.getPreferredSize());
        nextBtn.setToolTipText("Next");
        //<editor-fold defaultstate="collapsed" desc="MouseEvent handler for next button.">
        nextBtn.addActionListener((ActionEvent ae) -> {
            if(windowApp.getContentPanel().getViewport().getView() instanceof PlaylistUI) {
                PlaylistUI tempUI = (PlaylistUI)windowApp.getContentPanel().getViewport().getView();

                int tempIndex;

                if(isShuffle == false) {
                    if(windowApp.activeTrackNumber > windowApp.currentPlaylistTrackCount - 1) {
                        tempIndex = 0;
                    } else {
                        tempIndex = windowApp.activeTrackNumber;
                    }

                    TrackRow tempRow = (TrackRow)tempUI.getTrackTableList().getComponent(tempIndex);
                    tempRow.playButtonAction(windowApp);
                } else {
                    if(windowApp.countOnShuffle > windowApp.currentPlaylistTrackCount - 1) {
                        windowApp.countOnShuffle = 0;

                        tempIndex = new Random().nextInt(windowApp.currentPlaylistTrackCount);
                    } else {
                        tempIndex = windowApp.shuffleOnDemand.get(windowApp.countOnShuffle) - 1;
                    }

                    TrackRow tempRow = (TrackRow)tempUI.getTrackTableList().getComponent(tempIndex);
                    tempRow.playButtonAction(windowApp);
                }
            }
        });
        nextBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                nextBtn.setIcon(
                    scaleImageIcon("src/com/spotify/assets/icons/player/next_hover.png", 16, 16)
                );
            }

            @Override
            public void mouseExited(MouseEvent me) {
                nextBtn.setIcon(
                    scaleImageIcon("src/com/spotify/assets/icons/player/next_default.png", 16, 16)
                );
            }
        });
        //</editor-fold>

        repeatBtn = new JButton(
            scaleImageIcon("src/com/spotify/assets/icons/player/repeat_off.png", 16, 16)
        );
        repeatBtn.setBackground(this.backgroundColor);
        repeatBtn.setBorder(null);
        repeatBtn.setBorderPainted(false);
        repeatBtn.setContentAreaFilled(false);
        repeatBtn.setFocusable(false);
        repeatBtn.setFocusPainted(false);
        repeatBtn.setMaximumSize(new Dimension(32, 32));
        repeatBtn.setMinimumSize(new Dimension(32, 32));
        repeatBtn.setName("Loop_Off");
        repeatBtn.setOpaque(true);
        repeatBtn.setPreferredSize(repeatBtn.getMaximumSize());
        repeatBtn.setSize(repeatBtn.getPreferredSize());
        repeatBtn.setToolTipText("Enable repeat");
        //<editor-fold defaultstate="collapsed" desc="MouseEvent handler for repeat button.">
        repeatBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if(repeatBtn.getName().equalsIgnoreCase("Loop_Off")) {
                    repeatBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/repeat_on.png", 16, 16)
                    );
                    repeatBtn.setName("Loop_All");
                    repeatBtn.setToolTipText("Enable repeat one");
                    loopingID = 1;
                } else if(repeatBtn.getName().equalsIgnoreCase("Loop_All")) {
                    repeatBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/repeat_one.png", 16, 16)
                    );
                    repeatBtn.setName("Loop_Once");
                    repeatBtn.setToolTipText("Disable repeat");
                    loopingID = 2;
                } else if(repeatBtn.getName().equalsIgnoreCase("Loop_Once")) {
                    repeatBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/repeat_off.png", 16, 16)
                    );
                    repeatBtn.setName("Loop_Off");
                    repeatBtn.setToolTipText("Enable repeat");
                    loopingID = 0;
                }

                if(audioHandler != null) {
                    audioHandler.setLoopingID(loopingID);
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                if(repeatBtn.getName().equalsIgnoreCase("Loop_Off")) {
                    repeatBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/repeat_off_hover.png", 16, 16)
                    );
                    repeatBtn.setToolTipText("Enable repeat");
                } else if(repeatBtn.getName().equalsIgnoreCase("Loop_All")) {
                    repeatBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/repeat_on_hover.png", 16, 16)
                    );
                    repeatBtn.setToolTipText("Enable repeat one");
                } else if(repeatBtn.getName().equalsIgnoreCase("Loop_Once")) {
                    repeatBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/repeat_one_hover.png", 16, 16)
                    );
                    repeatBtn.setToolTipText("Disable repeat");
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(repeatBtn.getName().equalsIgnoreCase("Loop_Off")) {
                    repeatBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/repeat_off.png", 16, 16)
                    );
                } else if(repeatBtn.getName().equalsIgnoreCase("Loop_All")) {
                    repeatBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/repeat_on.png", 16, 16)
                    );
                } else if(repeatBtn.getName().equalsIgnoreCase("Loop_Once")) {
                    repeatBtn.setIcon(
                        scaleImageIcon("src/com/spotify/assets/icons/player/repeat_one.png", 16, 16)
                    );
                }
            }
        });
        //</editor-fold>

        controlPanel.add(shuffleBtn);
        controlPanel.add(previousBtn);
        controlPanel.add(playBtn);
        controlPanel.add(nextBtn);
        controlPanel.add(repeatBtn);

        currentPosition = new JLabel("0:00");
        currentPosition.setBackground(this.backgroundColor);
        currentPosition.setForeground(Threshold.palette_b3b3b3);
        currentPosition.setHorizontalAlignment(SwingConstants.CENTER);
        currentPosition.setMinimumSize(new Dimension(40, (int)currentPosition.getPreferredSize().getHeight()));
        currentPosition.setOpaque(true);
        currentPosition.setPreferredSize(new Dimension(40, (int)currentPosition.getPreferredSize().getHeight()));

        maximumDuration = new JLabel("0:00");
        maximumDuration.setBackground(this.backgroundColor);
        maximumDuration.setForeground(Threshold.palette_b3b3b3);
        maximumDuration.setHorizontalAlignment(SwingConstants.CENTER);
        maximumDuration.setMinimumSize(new Dimension(40, (int)currentPosition.getPreferredSize().getHeight()));
        maximumDuration.setOpaque(true);
        maximumDuration.setPreferredSize(new Dimension(40, (int)currentPosition.getPreferredSize().getHeight()));

        progressBar = new JProgressBar();
        progressBar.setBackground(Threshold.palette_535353);
        progressBar.setBorder(null);
        progressBar.setBorderPainted(false);
        progressBar.setFocusable(false);
        progressBar.setForeground(Threshold.palette_b3b3b3);
        progressBar.setPreferredSize(new Dimension(
            (int)playbackPanel.getPreferredSize().getWidth() - (int)currentPosition.getPreferredSize().getWidth() -
            (int)maximumDuration.getPreferredSize().getWidth(), 4
        ));
        progressBar.setValue(0);
        progressBar.setVisible(true);
        progressBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                progressBarGlassPanel.setVisible(true);
                progressBarGlassPanel.requestFocusInWindow();
                progressBarGlassPanel.setFocusTraversalKeysEnabled(false);
                progressBar.setForeground(Threshold.palette_1db954);
            }
        });

        progressBarSlider = new JSlider();
        progressBarSlider.setBorder(null);
        progressBarSlider.setFocusable(false);
        progressBarSlider.setOpaque(false);
        progressBarSlider.setPaintTrack(false);
        progressBarSlider.setPreferredSize(new Dimension(
            (int)playbackPanel.getPreferredSize().getWidth() - (int)currentPosition.getPreferredSize().getWidth() -
            (int)maximumDuration.getPreferredSize().getWidth() + 12, 16
        ));
        progressBarSlider.setUI(new PaintSlider(progressBarSlider));
        progressBarSlider.setValue(0);
        progressBarSlider.addMouseListener(new MouseAdapter() {
            boolean isVisible = false;

            @Override
            public void mouseClicked(MouseEvent me) {
                isVisible = true;
                progressBarGlassPanel.setVisible(isVisible);
                progressBarSlider.setValue(progressBarSlider.getValue());
                progressBar.setValue(progressBarSlider.getValue());
            }

            @Override
            public void mousePressed(MouseEvent me) {
                isVisible = true;
                progressBarGlassPanel.setVisible(isVisible);
                progressBarSlider.setValue(progressBarSlider.getValue());
                progressBar.setValue(progressBarSlider.getValue());
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if(isVisible == true) {
                    isVisible = false;
                    progressBarGlassPanel.setVisible(isVisible);
                    progressBar.setForeground(Threshold.palette_b3b3b3);
                }

                progressBarSlider.setValue(progressBarSlider.getValue());
                progressBar.setValue(progressBarSlider.getValue());
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                isVisible = true;
                progressBarGlassPanel.setVisible(isVisible);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(isVisible == false) {
                    progressBarGlassPanel.setVisible(isVisible);
                    progressBar.setForeground(Threshold.palette_b3b3b3);
                }
            }
        });
        progressBarSlider.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                progressBarSlider.setValue(progressBarSlider.getValue());
                progressBar.setValue(progressBarSlider.getValue());
            }
        });

        playbackPanel.add(currentPosition);
        playbackPanel.add(progressBar);
        playbackPanel.add(maximumDuration);

        progressBarGlassPanel = new JPanel();
        progressBarGlassPanel.setFocusable(true);
        progressBarGlassPanel.setLayout(new GridBagLayout());
        progressBarGlassPanel.setOpaque(false);
        progressBarGlassPanel.setPreferredSize(new Dimension(
            (int)musicPlayer.getSize().getWidth(), 16
        ));
        progressBarGlassPanel.setSize(progressBarGlassPanel.getPreferredSize());
        progressBarGlassPanel.setVisible(false);

        progressBarGlassPanel.add(progressBarSlider);

        progressBarLayeredPane.add(progressBarGlassPanel, JLayeredPane.PALETTE_LAYER);
        progressBarLayeredPane.add(playbackPanel, JLayeredPane.DEFAULT_LAYER);

        musicPlayer.add(controlPanel);
        musicPlayer.add(progressBarLayeredPane);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Final method to get volume control, queue list, and display lyrics.">
    public final void initPlayerControl() {
        int localWidth = 90, localHeight = 16;

        playerControl = new JPanel();
        playerControl.setBackground(this.backgroundColor);
        playerControl.setBounds((int)(playedSong.getWidth() + musicPlayer.getWidth()),
            (int)(currentSize.getHeight() - playerArea.getHeight()),
            (int)(currentSize.getWidth() * 30 / 100) + 6, (int)playerArea.getHeight()
        );
        playerControl.setLayout(new GridBagLayout());

        GridBagConstraints gridConstraints = new GridBagConstraints();

        /*
        lyricBtn = new JButton(scaleImageIcon(
            "src/com/spotify/assets/icons/player/lyrics_off.png", 16, 16
        ));
        lyricBtn.setBackground(this.backgroundColor);
        lyricBtn.setBorder(null);
        lyricBtn.setBorderPainted(false);
        lyricBtn.setContentAreaFilled(false);
        lyricBtn.setFocusable(false);
        lyricBtn.setFocusPainted(false);
        lyricBtn.setMaximumSize(new Dimension(32, 32));
        lyricBtn.setMinimumSize(new Dimension(32, 32));
        lyricBtn.setName("Lyric_Button_Off");
        lyricBtn.setOpaque(false);
        lyricBtn.setPreferredSize(lyricBtn.getMaximumSize());
        lyricBtn.setSize(lyricBtn.getPreferredSize());
        lyricBtn.setToolTipText("Lyrics");
        //<editor-fold defaultstate="collapsed" desc="MouseEvent handler for lyric button.">
        lyricBtn.addActionListener((ActionEvent ae) -> {
            if(lyricBtn.getName().equals("Lyric_Button_Off")) {
                lyricBtn.setIcon(
                    scaleImageIcon("./src/com/spotify/assets/icons/player/lyrics_on_hover.png", 16, 16)
                );
                lyricBtn.setName("Lyric_Button_On");

                if(queueBtn.getName().equals("Queue_Button_On")) {
                    queueBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/queue_off.png", 32, 32)
                    );
                    queueBtn.setName("Queue_Button_Off");
                }
            } else if(lyricBtn.getName().equals("Lyric_Button_On")) {
                lyricBtn.setIcon(
                    scaleImageIcon("./src/com/spotify/assets/icons/player/lyrics_off_hover.png", 16, 16)
                );
                lyricBtn.setName("Lyric_Button_Off");
            }
        });
        lyricBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if(lyricBtn.getName().equals("Lyric_Button_Off")) {
                    lyricBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/lyrics_off.png", 16, 16)
                    );
                } else if(lyricBtn.getName().equals("Lyric_Button_On")) {
                    lyricBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/lyrics_on.png", 16, 16)
                    );
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                if(lyricBtn.getName().equals("Lyric_Button_Off")) {
                    lyricBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/lyrics_off_hover.png", 16, 16)
                    );
                } else if(lyricBtn.getName().equals("Lyric_Button_On")) {
                    lyricBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/lyrics_on_hover.png", 16, 16)
                    );
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(lyricBtn.getName().equals("Lyric_Button_Off")) {
                    lyricBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/lyrics_off.png", 16, 16)
                    );
                } else if(lyricBtn.getName().equals("Lyric_Button_On")) {
                    lyricBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/lyrics_on.png", 16, 16)
                    );
                }
            }
        });
        //</editor-fold>
        */

        queueBtn = new JButton(scaleImageIcon(
            "src/com/spotify/assets/icons/player/queue_off.png", 32, 32
        ));
        queueBtn.setBackground(this.backgroundColor);
        queueBtn.setBorder(null);
        queueBtn.setBorderPainted(false);
        queueBtn.setContentAreaFilled(false);
        queueBtn.setFocusable(false);
        queueBtn.setFocusPainted(false);
        queueBtn.setMaximumSize(new Dimension(32, 32));
        queueBtn.setMinimumSize(new Dimension(32, 32));
        queueBtn.setName("Queue_Button_Off");
        queueBtn.setOpaque(false);
        queueBtn.setPreferredSize(queueBtn.getMaximumSize());
        queueBtn.setSize(queueBtn.getPreferredSize());
        queueBtn.setToolTipText("Queue");
        //<editor-fold defaultstate="collapsed" desc="MouseEvent handler for queue button.">
        queueBtn.addActionListener((ActionEvent ae) -> {
//            if(queueBtn.getName().equals("Queue_Button_Off")) {
//                queueBtn.setIcon(
//                    scaleImageIcon("./src/com/spotify/assets/icons/player/queue_on_hover.png", 32, 32)
//                );
//                queueBtn.setName("Queue_Button_On");

                PlaylistUI panel = new PlaylistUI(
                    windowApp.accounts, windowApp.user.getQueueList(), windowApp, PlaylistUI.QUEUE_PLAYLIST, -1
                );
                windowApp.changeContent(
                    new Content(panel, true), new ContentDraggable(windowApp, windowApp.user), windowApp.user,
                    windowApp.accounts
                );

                /*
                if(lyricBtn.getName().equals("Lyric_Button_On")) {
                    lyricBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/lyrics_off.png", 16, 16)
                    );
                    lyricBtn.setName("Lyric_Button_Off");
                }
                */
//            } else if(queueBtn.getName().equals("Queue_Button_On")) {
//                queueBtn.setIcon(
//                    scaleImageIcon("./src/com/spotify/assets/icons/player/queue_off_hover.png", 32, 32)
//                );
//                queueBtn.setName("Queue_Button_Off");
//            }
        });
        queueBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if(queueBtn.getName().equals("Queue_Button_Off")) {
                    queueBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/queue_off.png", 32, 32)
                    );
                } else if(queueBtn.getName().equals("Queue_Button_On")) {
                    queueBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/queue_on.png", 32, 32)
                    );
                }
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                if(queueBtn.getName().equals("Queue_Button_Off")) {
                    queueBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/queue_off_hover.png", 32, 32)
                    );
                } else if(queueBtn.getName().equals("Queue_Button_On")) {
                    queueBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/queue_on_hover.png", 32, 32)
                    );
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(queueBtn.getName().equals("Queue_Button_Off")) {
                    queueBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/queue_off.png", 32, 32)
                    );
                } else if(queueBtn.getName().equals("Queue_Button_On")) {
                    queueBtn.setIcon(
                        scaleImageIcon("./src/com/spotify/assets/icons/player/queue_on.png", 32, 32)
                    );
                }
            }
        });
        //</editor-fold>

        muteBtn = new JButton(scaleImageIcon(
            "src/com/spotify/assets/icons/player/volume_icon_99_default.png", 16, 16
        ));
        muteBtn.setBackground(this.backgroundColor);
        muteBtn.setBorder(null);
        muteBtn.setBorderPainted(false);
        muteBtn.setContentAreaFilled(false);
        muteBtn.setFocusable(false);
        muteBtn.setFocusPainted(false);
        muteBtn.setMaximumSize(new Dimension(32, 32));
        muteBtn.setMinimumSize(new Dimension(32, 32));
        muteBtn.setName("Volume99_Default");
        muteBtn.setOpaque(false);
        muteBtn.setPreferredSize(muteBtn.getMaximumSize());
        muteBtn.setSize(muteBtn.getPreferredSize());
        muteBtn.setToolTipText("Mute");
        //<editor-fold defaultstate="collapsed" desc="MouseEvent handler for mute button.">
        muteBtn.addActionListener((ActionEvent ae) -> {
            if(volumeBarSlider.getValue() != volumeBarSlider.getMinimum()) {
                tempVolumeOnMute = windowApp.audioVolume;
                volumeBarSlider.setValue(volumeBarSlider.getMinimum());
                volumeBar.setValue(volumeBarSlider.getMinimum());
            } else {
                volumeBarSlider.setValue((int)tempVolumeOnMute * 100);
                volumeBar.setValue((int)tempVolumeOnMute * 100);
                tempVolumeOnMute = 0.0f;
            }
        });
        muteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                if(muteBtn.getName().equals("Volume99_Default")) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_99_hover.png", 16, 16
                    ));
                } else if(muteBtn.getName().equals("Volume66_Default")) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_66_hover.png", 16, 16
                    ));
                } else if(muteBtn.getName().equals("Volume33_Default")) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_33_hover.png", 16, 16
                    ));
                } else if(muteBtn.getName().equals("VolumeMuted_Default")) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_muted_hover.png", 16, 16
                    ));
                }
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if(muteBtn.getName().equals("Volume99_Default")) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_99_default.png", 16, 16
                    ));
                } else if(muteBtn.getName().equals("Volume66_Default")) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_66_default.png", 16, 16
                    ));
                } else if(muteBtn.getName().equals("Volume33_Default")) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_33_default.png", 16, 16
                    ));
                } else if(muteBtn.getName().equals("VolumeMuted_Default")) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_muted_default.png", 16, 16
                    ));
                }
            }
        });
        //</editor-fold>

        volumePanel = new JPanel();
        volumePanel.setBackground(this.backgroundColor);
        volumePanel.setLayout(new GridBagLayout());
        volumePanel.setPreferredSize(new Dimension(localWidth + 12, localHeight));
        volumePanel.setSize(volumePanel.getPreferredSize());

        volumeBar = new JProgressBar();
        volumeBar.setBackground(Threshold.palette_535353);
        volumeBar.setBorder(null);
        volumeBar.setBorderPainted(false);
        volumeBar.setFocusable(false);
        volumeBar.setForeground(Threshold.palette_b3b3b3);
        volumeBar.setPreferredSize(new Dimension(localWidth, 4));
        volumeBar.setMinimum((int)-80.0f * 100);
        volumeBar.setMaximum((int)0.0f * 100);
        volumeBar.setValue((int)0.0f * 100);
        volumeBar.setVisible(true);
        volumeBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                volumeBarGlassPanel.setVisible(true);
                volumeBarGlassPanel.requestFocusInWindow();
                volumeBarGlassPanel.setFocusTraversalKeysEnabled(false);
                volumeBar.setForeground(Threshold.palette_1db954);
            }
        });

        volumeBarSlider = new JSlider();
        volumeBarSlider.setBackground(Color.ORANGE);
        volumeBarSlider.setBorder(null);
        volumeBarSlider.setFocusable(false);
        volumeBarSlider.setOpaque(false);
        volumeBarSlider.setPaintTrack(false);
        volumeBarSlider.setPreferredSize(new Dimension(
            localWidth + 12, localHeight
        ));
        volumeBarSlider.setUI(new PaintSlider(volumeBarSlider));
        volumeBarSlider.setMinimum((int)-80.0f * 100);
        volumeBarSlider.setMaximum((int)0.0f * 100);
        volumeBarSlider.setValue((int)0.0f * 100);
        volumeBarSlider.addChangeListener((ChangeEvent ce) -> {
            if(volumeBarSlider.getValue() >= -2640) {
                if(volumePercentage != 4) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_99_default.png", 16, 16
                    ));
                    muteBtn.setName("Volume99_Default");
                    volumePercentage = 4;
                }
            } else if(volumeBarSlider.getValue() >= -2640 * 2) {
                if(volumePercentage != 3) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_66_default.png", 16, 16
                    ));
                    muteBtn.setName("Volume66_Default");
                    volumePercentage = 3;
                }
            } else if(volumeBarSlider.getValue() >= -2640 * 3) {
                if(volumePercentage != 2) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_33_default.png", 16, 16
                    ));
                    muteBtn.setName("Volume33_Default");
                    volumePercentage = 2;
                }
            } else {
                if(volumePercentage != 1) {
                    muteBtn.setIcon(scaleImageIcon(
                        "src/com/spotify/assets/icons/player/volume_icon_muted_default.png", 16, 16
                    ));
                    muteBtn.setName("VolumeMuted_Default");
                    volumePercentage = 1;
                }
            }

            windowApp.audioVolume = volumeBarSlider.getValue() / 100.0f;
        });
        volumeBarSlider.addMouseListener(new MouseAdapter() {
            boolean isVisible = false;

            @Override
            public void mouseClicked(MouseEvent me) {
                isVisible = true;
                volumeBarGlassPanel.setVisible(isVisible);
                volumeBarSlider.setValue(volumeBarSlider.getValue());
                volumeBar.setValue(volumeBar.getValue());
            }

            @Override
            public void mousePressed(MouseEvent me) {
                isVisible = true;
                volumeBarGlassPanel.setVisible(isVisible);
                volumeBarSlider.setValue(volumeBarSlider.getValue());
                volumeBar.setValue(volumeBarSlider.getValue());
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if(isVisible == true) {
                    isVisible = false;
                    volumeBarGlassPanel.setVisible(isVisible);
                    volumeBar.setForeground(Threshold.palette_b3b3b3);
                }

                volumeBarSlider.setValue(volumeBarSlider.getValue());
                volumeBar.setValue(volumeBarSlider.getValue());
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                isVisible = true;
                volumeBarGlassPanel.setVisible(isVisible);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(isVisible == false) {
                    volumeBarGlassPanel.setVisible(isVisible);
                    volumeBar.setForeground(Threshold.palette_b3b3b3);
                }
            }
        });
        volumeBarSlider.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                volumeBarSlider.setValue(volumeBarSlider.getValue());
                volumeBar.setValue(volumeBarSlider.getValue());
            }
        });

        gridConstraints.anchor = GridBagConstraints.CENTER;
        gridConstraints.insets = new Insets(0, 6, 0, 6);
        gridConstraints.weightx = 1;
        gridConstraints.weighty = 1;

        volumePanel.add(volumeBar, gridConstraints);

        volumeBarGlassPanel = new JPanel();
        volumeBarGlassPanel.setBackground(Color.ORANGE);
        volumeBarGlassPanel.setFocusable(true);
        volumeBarGlassPanel.setLayout(new GridBagLayout());
        volumeBarGlassPanel.setOpaque(false);
        volumeBarGlassPanel.setPreferredSize(new Dimension(localWidth + 12, localHeight));
        volumeBarGlassPanel.setSize(volumeBarGlassPanel.getPreferredSize());
        volumeBarGlassPanel.setVisible(false);

        volumeBarGlassPanel.add(volumeBarSlider);

        volumeBarLayeredPane = new JLayeredPane();
        volumeBarLayeredPane.setPreferredSize(new Dimension(localWidth + 12, localHeight));
        volumeBarLayeredPane.setSize(volumeBarLayeredPane.getPreferredSize());

        volumeBarLayeredPane.add(volumeBarGlassPanel, JLayeredPane.PALETTE_LAYER);
        volumeBarLayeredPane.add(volumePanel, JLayeredPane.DEFAULT_LAYER);

        /* gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.insets = new Insets(0, 0, 0, 0);
        gridConstraints.weightx = 1;
        playerControl.add(lyricBtn, gridConstraints); */

        gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.insets = new Insets(0, 0, 0, 0);
        /* gridConstraints.weightx = 0; */
        gridConstraints.weightx = 1;
        playerControl.add(queueBtn, gridConstraints);

        gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.insets = new Insets(0, 0, 0, -6);
        gridConstraints.weightx = 0;
        playerControl.add(muteBtn, gridConstraints);

        gridConstraints.anchor = GridBagConstraints.EAST;
        gridConstraints.insets = new Insets(0, 0, 0, 10);
        gridConstraints.weightx = 0;
        playerControl.add(volumeBarLayeredPane, gridConstraints);
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Final method to resize components.">
    public final void resizeLeftPanel() {
        likeButton.setLocation((int)playedSong.getSize().getWidth() - 16, 0);
        likeButton.setPreferredSize(new Dimension(16, 16));
        likeButton.setSize(likeButton.getPreferredSize());
        likeButton.revalidate();
        likeButton.repaint();
    }

    public final void resizeMiddlePanel() {
        progressBarLayeredPane.setPreferredSize(new Dimension(
            (int)musicPlayer.getSize().getWidth(), 16
        ));
        progressBarLayeredPane.setSize(progressBarLayeredPane.getPreferredSize());
        progressBarLayeredPane.revalidate();
        progressBarLayeredPane.repaint();

        playbackPanel.setPreferredSize(new Dimension(
            (int)musicPlayer.getSize().getWidth(), 16
        ));
        playbackPanel.setSize(playbackPanel.getPreferredSize());
        playbackPanel.revalidate();
        playbackPanel.repaint();

        progressBarGlassPanel.setPreferredSize(new Dimension(
            (int)musicPlayer.getSize().getWidth(), 16
        ));
        progressBarGlassPanel.setSize(progressBarGlassPanel.getPreferredSize());
        progressBarGlassPanel.revalidate();
        progressBarGlassPanel.repaint();

        progressBar.setPreferredSize(new Dimension(
            (int)playbackPanel.getPreferredSize().getWidth() -
            (int)currentPosition.getPreferredSize().getWidth() -
            (int)maximumDuration.getPreferredSize().getWidth(), 4
        ));
        progressBar.setSize(progressBar.getPreferredSize());
        progressBar.revalidate();
        progressBar.repaint();

        progressBarSlider.setPreferredSize(new Dimension(
            (int)playbackPanel.getPreferredSize().getWidth() -
            (int)currentPosition.getPreferredSize().getWidth() -
            (int)maximumDuration.getPreferredSize().getWidth() + 12, 16
        ));
        progressBarSlider.setSize(progressBarSlider.getPreferredSize());
        progressBarSlider.revalidate();
        progressBarSlider.repaint();
    }
    //</editor-fold>

    public final void setAudioHandler() {
        if(windowApp.activePlayer == true) {
            audioHandler.resetAll(true);
            audioHandler = null;
            System.gc();
        }

        filepath = currentSong.getAudioFile();
        audioHandler = new AudioHandler(this, windowApp);
    }

    /*
    // This shouldn't be placed here. It is outside the EDT that's why
    // Swing-Shell blocked the current thread. Possible memory leak.
    public void loadAudioFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser();

        Action detailView = fileChooser.getActionMap().get("viewTypeDetails");
        detailView.actionPerformed(null);

        fileChooser.setPreferredSize(new Dimension(640, 480));
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);

        int returnVal = fileChooser.showOpenDialog(this.windowApp);

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            String origpath = selectedFile.getParent(), origname = selectedFile.getName();
            String filepath = "./db/Yuko Suzuhana/Cradle of Eternity/", filename = "";
            String[] splitter = origname.split("\\.");

            for(int i = 0; i < splitter.length - 1; i++) {
                filename += splitter[i] + ".";
            } filename += "wav";

            File comparedFile = new File(filepath);

            if(comparedFile.exists() == false) {
                comparedFile.mkdirs();
            }

            if(new File(filepath + "\\" + filename).exists() == false) {
                FFmpeg converter = new FFmpeg(origpath, origname, filepath, filename);
            }
        }

        // Update audioFile in Song.java
    }
    */

    public JPanel getPlayedSong() {
        return playedSong;
    }

    public void setPlayedSong(JPanel playedSong) {
        this.playedSong = playedSong;
    }

    public JPanel getMusicPlayer() {
        return musicPlayer;
    }

    public void setMusicPlayer(JPanel audioPlayer) {
        this.musicPlayer = audioPlayer;
    }

    public JPanel getPlayerControl() {
        return playerControl;
    }

    public void setPlayerControl(JPanel playerControl) {
        this.playerControl = playerControl;
    }

    public JButton getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(JButton albumArt) {
        this.albumArt = albumArt;
    }

    public JPanel getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(JPanel songInfo) {
        this.songInfo = songInfo;
    }

    public JButton getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(JButton songTitle) {
        this.songTitle = songTitle;
    }

    public JButton getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(JButton songArtist) {
        this.songArtist = songArtist;
    }

    public JButton getLikeButton() {
        return likeButton;
    }

    public void setLikeButton(JButton likeButton) {
        this.likeButton = likeButton;
    }

    public AudioHandler getAudioHandler() {
        return audioHandler;
    }

    public void setAudioHandler(AudioHandler audioHandler) {
        this.audioHandler = audioHandler;
    }

    public JPanel getControlPanel() {
        return controlPanel;
    }

    public void setControlPanel(JPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public JButton getShuffleBtn() {
        return shuffleBtn;
    }

    public void setShuffleBtn(JButton shuffleBtn) {
        this.shuffleBtn = shuffleBtn;
    }

    public JButton getPreviousBtn() {
        return previousBtn;
    }

    public void setPreviousBtn(JButton previousBtn) {
        this.previousBtn = previousBtn;
    }

    public JButton getPlayBtn() {
        return playBtn;
    }

    public void setPlayBtn(JButton playBtn) {
        this.playBtn = playBtn;
    }

    public JButton getNextBtn() {
        return nextBtn;
    }

    public void setNextBtn(JButton nextBtn) {
        this.nextBtn = nextBtn;
    }

    public JButton getRepeatBtn() {
        return repeatBtn;
    }

    public void setRepeatBtn(JButton repeatBtn) {
        this.repeatBtn = repeatBtn;
    }

    public JLayeredPane getProgressBarLayeredPane() {
        return progressBarLayeredPane;
    }

    public void setProgressBarLayeredPane(JLayeredPane progressBarLayeredPane) {
        this.progressBarLayeredPane = progressBarLayeredPane;
    }

    public JPanel getPlaybackPanel() {
        return playbackPanel;
    }

    public void setPlaybackPanel(JPanel playbackPanel) {
        this.playbackPanel = playbackPanel;
    }

    public JPanel getProgressBarGlassPanel() {
        return progressBarGlassPanel;
    }

    public void setProgressBarGlassPanel(JPanel progressBarGlassPanel) {
        this.progressBarGlassPanel = progressBarGlassPanel;
    }

    public JLabel getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(JLabel currentPosition) {
        this.currentPosition = currentPosition;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public JSlider getProgressBarSlider() {
        return progressBarSlider;
    }

    public void setProgressBarSlider(JSlider progressBarSlider) {
        this.progressBarSlider = progressBarSlider;
    }

    public JLabel getMaximumDuration() {
        return maximumDuration;
    }

    public void setMaximumDuration(JLabel maximumDuration) {
        this.maximumDuration = maximumDuration;
    }

    public JButton getLyricBtn() {
        return lyricBtn;
    }

    public void setLyricBtn(JButton lyricBtn) {
        this.lyricBtn = lyricBtn;
    }

    public JButton getQueueBtn() {
        return queueBtn;
    }

    public void setQueueBtn(JButton queueBtn) {
        this.queueBtn = queueBtn;
    }

    public JButton getMuteBtn() {
        return muteBtn;
    }

    public void setMuteBtn(JButton muteBtn) {
        this.muteBtn = muteBtn;
    }

    public JLayeredPane getVolumeBarLayeredPane() {
        return volumeBarLayeredPane;
    }

    public void setVolumeBarLayeredPane(JLayeredPane volumeBarLayeredPane) {
        this.volumeBarLayeredPane = volumeBarLayeredPane;
    }

    public JPanel getVolumePanel() {
        return volumePanel;
    }

    public void setVolumePanel(JPanel volumePanel) {
        this.volumePanel = volumePanel;
    }

    public JPanel getVolumeBarGlassPanel() {
        return volumeBarGlassPanel;
    }

    public void setVolumeBarGlassPanel(JPanel volumeBarGlassPanel) {
        this.volumeBarGlassPanel = volumeBarGlassPanel;
    }

    public JProgressBar getVolumeBar() {
        return volumeBar;
    }

    public void setVolumeBar(JProgressBar volumeBar) {
        this.volumeBar = volumeBar;
    }

    public JSlider getVolumeBarSlider() {
        return volumeBarSlider;
    }

    public void setVolumeBarSlider(JSlider volumeBarSlider) {
        this.volumeBarSlider = volumeBarSlider;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setShuffle(boolean isShuffle) {
        this.isShuffle = isShuffle;
    }

    public int getLoopingID() {
        return loopingID;
    }

    public void setLoopingID(int loopingID) {
        this.loopingID = loopingID;
    }
}
