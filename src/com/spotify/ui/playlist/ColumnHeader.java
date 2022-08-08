package com.spotify.ui.playlist;

import com.spotify.app.Utility;
import com.spotify.ui.Threshold;
import com.spotify.ui.Window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColumnHeader extends JPanel implements Utility {
    static final int UNUSED_PLAYLIST = 0;
    static final int NATIVE_PLAYLIST = 1;
    static final int CUSTOM_PLAYLIST = 2;
    static final int LIKED_PLAYLIST = 100;
    static final int QUEUE_PLAYLIST = 101;

    protected int paddingX, paddingY;
    protected int globalHeight = 36;

    protected int playlistStyle;
    protected int titleColumnMaxWidth;
    protected int rigidBoxCount;

    protected JLabel trackNumberHeader;
    protected JPanel trackNumberColumn;

    protected JLabel trackTitleHeader;
    protected JPanel trackTitleColumn;

    protected JLabel trackPlaysCountHeader;
    protected JLabel playsCountRightMargin;
    protected JPanel trackPlaysCountColumn;

    protected JLabel trackAlbumHeader;
    protected JPanel trackAlbumColumn;

    protected JLabel trackDateAddedHeader;
    protected JLabel dateAddedRightMargin;
    protected JPanel trackDateAddedColumn;

    protected JLabel trackDurationHeader;
    protected JPanel trackDurationColumn;

    public ColumnHeader(Window windowApp, JPanel trackList, int playlistStyle) {
        if(Threshold.currentSize.width >= 1280) {
            this.paddingX = 32;
            this.paddingY = 24;
        } else {
            this.paddingX = 16;
            this.paddingY = 24;
        }

        this.setBackground(Color.CYAN);
        if(playlistStyle != UNUSED_PLAYLIST) {
            this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 40, 40)));
        } else {
            this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(40, 40, 40)));
        }
        this.setBounds(
            paddingX + 1, 0, trackList.getWidth() - (paddingX * 2) - 2, globalHeight + 1
        );
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.setPreferredSize(this.getSize());
        this.setVisible(true);

        GridBagConstraints gridConstraints = new GridBagConstraints();

        try {
            this.playlistStyle = playlistStyle;
            this.titleColumnMaxWidth = Integer.MAX_VALUE;

            if(playlistStyle == NATIVE_PLAYLIST) {
                this.rigidBoxCount = 4;

                this.initTrackNumberColumn();
                this.initTrackPlaysCountColumn();
                this.initTrackDurationColumn();
                this.initTrackTitleColumn();

                this.add(trackNumberColumn, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
                this.add(trackTitleColumn, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
                this.add(trackPlaysCountColumn, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
                this.add(trackDurationColumn, gridConstraints, -1);
                this.add(Box.createRigidArea(new Dimension(16, 0)), gridConstraints, -1);
            } else if(playlistStyle == CUSTOM_PLAYLIST) {
                this.rigidBoxCount = 4;

                this.initTrackNumberColumn();
                this.initTrackAlbumColumn();
                this.initTrackDateAddedColumn();
                this.initTrackDurationColumn();
                this.initTrackTitleColumn();

                this.add(trackNumberColumn, gridConstraints, -1);
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
        }
    }

    protected final void initTrackNumberColumn() {
        trackNumberHeader = new JLabel("#", JLabel.RIGHT);
        trackNumberHeader.setBackground(Color.GREEN);
        trackNumberHeader.setFont(new Font("Gotham", Font.PLAIN, 16));
        trackNumberHeader.setForeground(Threshold.palette_b3b3b3);
        trackNumberHeader.setMaximumSize(new Dimension(
            32, (int)trackNumberHeader.getPreferredSize().getHeight()
        ));
        trackNumberHeader.setOpaque(false);

        trackNumberColumn = new JPanel();
        trackNumberColumn.add(trackNumberHeader);
        trackNumberColumn.setBackground(Color.MAGENTA);
        trackNumberColumn.setBorder(null);
        trackNumberColumn.setLayout(new BoxLayout(trackNumberColumn, BoxLayout.X_AXIS));
        trackNumberColumn.setMaximumSize(new Dimension(32, globalHeight));
        trackNumberColumn.setOpaque(false);
        trackNumberColumn.setPreferredSize(trackNumberColumn.getMaximumSize());
        trackNumberColumn.setSize(trackNumberColumn.getPreferredSize());
    }

    protected final void initTrackTitleColumn() {
        trackTitleHeader = new JLabel("TITLE", JLabel.LEFT);
        trackTitleHeader.setBackground(Color.GREEN);
        trackTitleHeader.setFont(new Font("Gotham", Font.PLAIN, 12));
        trackTitleHeader.setForeground(Threshold.palette_b3b3b3);
        trackTitleHeader.setMaximumSize(new Dimension(
            Integer.MAX_VALUE, (int)trackTitleHeader.getPreferredSize().getHeight()
        ));
        trackTitleHeader.setOpaque(false);

        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.07);
        trackTitleHeader.setFont(trackTitleHeader.getFont().deriveFont(attributes));

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

        trackTitleColumn = new JPanel();
        trackTitleColumn.add(trackTitleHeader);
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

    protected final void initTrackPlaysCountColumn() {
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

        trackPlaysCountHeader = new JLabel("PLAYS", JLabel.RIGHT);
        trackPlaysCountHeader.setBackground(Color.GREEN);
        trackPlaysCountHeader.setFont(new Font("Gotham", Font.PLAIN, 12));
        trackPlaysCountHeader.setForeground(Threshold.palette_b3b3b3);
        trackPlaysCountHeader.setMaximumSize(new Dimension(
            lpadWidth, (int)trackPlaysCountHeader.getPreferredSize().getHeight()
        ));
        trackPlaysCountHeader.setOpaque(false);

        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.07);
        trackPlaysCountHeader.setFont(trackPlaysCountHeader.getFont().deriveFont(attributes));

        playsCountRightMargin = new JLabel();
        playsCountRightMargin.setBackground(Color.GREEN);
        playsCountRightMargin.setFont(new Font("Gotham", Font.PLAIN, 12));
        playsCountRightMargin.setForeground(Threshold.palette_b3b3b3);
        playsCountRightMargin.setMaximumSize(new Dimension(
            rpadWidth, (int)trackPlaysCountHeader.getPreferredSize().getHeight()
        ));
        playsCountRightMargin.setOpaque(false);
        playsCountRightMargin.setVisible(false);

        trackPlaysCountColumn = new JPanel();
        trackPlaysCountColumn.add(trackPlaysCountHeader);
        trackPlaysCountColumn.add(playsCountRightMargin);
        trackPlaysCountColumn.setBackground(Color.MAGENTA);
        trackPlaysCountColumn.setBorder(null);
        trackPlaysCountColumn.setLayout(new BoxLayout(trackPlaysCountColumn, BoxLayout.X_AXIS));
        trackPlaysCountColumn.setMaximumSize(new Dimension(lpadWidth + rpadWidth, globalHeight));
        trackPlaysCountColumn.setOpaque(false);
        trackPlaysCountColumn.setPreferredSize(trackPlaysCountColumn.getMaximumSize());
        trackPlaysCountColumn.setSize(trackPlaysCountColumn.getPreferredSize());
    }

    protected final void initTrackAlbumColumn() {
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

        trackAlbumHeader = new JLabel("ALBUM", JLabel.LEFT);
        trackAlbumHeader.setBackground(Color.GREEN);
        trackAlbumHeader.setFont(new Font("Gotham", Font.PLAIN, 12));
        trackAlbumHeader.setForeground(Threshold.palette_b3b3b3);
        trackAlbumHeader.setMaximumSize(new Dimension(
            padWidth, (int)trackAlbumHeader.getPreferredSize().getHeight()
        ));
        trackAlbumHeader.setOpaque(false);

        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.07);
        trackAlbumHeader.setFont(trackAlbumHeader.getFont().deriveFont(attributes));

        trackAlbumColumn = new JPanel();
        trackAlbumColumn.add(trackAlbumHeader);
        trackAlbumColumn.setBackground(Color.MAGENTA);
        trackAlbumColumn.setBorder(null);
        trackAlbumColumn.setLayout(new BoxLayout(trackAlbumColumn, BoxLayout.X_AXIS));
        trackAlbumColumn.setMaximumSize(new Dimension(padWidth, globalHeight));
        trackAlbumColumn.setOpaque(false);
        trackAlbumColumn.setPreferredSize(trackAlbumColumn.getMaximumSize());
        trackAlbumColumn.setSize(trackAlbumColumn.getPreferredSize());
    }

    protected final void initTrackDateAddedColumn() {
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

        trackDateAddedHeader = new JLabel("DATE ADDED", JLabel.LEFT);
        trackDateAddedHeader.setBackground(Color.GREEN);
        trackDateAddedHeader.setFont(new Font("Gotham", Font.PLAIN, 12));
        trackDateAddedHeader.setForeground(Threshold.palette_b3b3b3);
        trackDateAddedHeader.setMaximumSize(new Dimension(
            lpadWidth, (int)trackDateAddedHeader.getPreferredSize().getHeight()
        ));
        trackDateAddedHeader.setOpaque(false);

        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.07);
        trackDateAddedHeader.setFont(trackDateAddedHeader.getFont().deriveFont(attributes));

        dateAddedRightMargin = new JLabel();
        dateAddedRightMargin.setBackground(Color.GREEN);
        dateAddedRightMargin.setFont(new Font("Gotham", Font.PLAIN, 12));
        dateAddedRightMargin.setForeground(Threshold.palette_b3b3b3);
        dateAddedRightMargin.setMaximumSize(new Dimension(
            rpadWidth, (int)trackDateAddedHeader.getPreferredSize().getHeight()
        ));
        dateAddedRightMargin.setOpaque(false);
        dateAddedRightMargin.setVisible(false);

        trackDateAddedColumn = new JPanel();
        trackDateAddedColumn.add(trackDateAddedHeader);
        trackDateAddedColumn.add(dateAddedRightMargin);
        trackDateAddedColumn.setBackground(Color.MAGENTA);
        trackDateAddedColumn.setBorder(null);
        trackDateAddedColumn.setLayout(new BoxLayout(trackDateAddedColumn, BoxLayout.X_AXIS));
        trackDateAddedColumn.setMaximumSize(new Dimension(lpadWidth + rpadWidth, globalHeight));
        trackDateAddedColumn.setOpaque(false);
        trackDateAddedColumn.setPreferredSize(trackDateAddedColumn.getMaximumSize());
        trackDateAddedColumn.setSize(trackDateAddedColumn.getPreferredSize());
    }

    protected final void initTrackDurationColumn() {
        trackDurationHeader = new JLabel(scaleImageIcon(
            "./src/com/spotify/assets/icons/duration_icon_default.png", 16, 16
        ), JLabel.RIGHT);
        trackDurationHeader.setBackground(Color.GREEN);
        trackDurationHeader.setFont(new Font("Gotham", Font.PLAIN, 12));
        trackDurationHeader.setForeground(Threshold.palette_b3b3b3);
        trackDurationHeader.setMaximumSize(new Dimension(
            68, (int)trackDurationHeader.getPreferredSize().getHeight()
        ));
        trackDurationHeader.setOpaque(false);

        JLabel rightMargin = new JLabel();
        rightMargin.setBackground(Color.GREEN);
        rightMargin.setFont(new Font("Gotham", Font.PLAIN, 12));
        rightMargin.setForeground(Threshold.palette_b3b3b3);
        rightMargin.setMaximumSize(new Dimension(
            32, (int)trackDurationHeader.getPreferredSize().getHeight()
        ));
        rightMargin.setOpaque(false);
        rightMargin.setVisible(false);

        trackDurationColumn = new JPanel();
        trackDurationColumn.add(trackDurationHeader);
        trackDurationColumn.add(rightMargin);
        trackDurationColumn.setBackground(Color.MAGENTA);
        trackDurationColumn.setBorder(null);
        trackDurationColumn.setLayout(new BoxLayout(trackDurationColumn, BoxLayout.X_AXIS));
        trackDurationColumn.setMaximumSize(new Dimension(100, globalHeight));
        trackDurationColumn.setOpaque(false);
        trackDurationColumn.setPreferredSize(trackDurationColumn.getMaximumSize());
        trackDurationColumn.setSize(trackDurationColumn.getPreferredSize());
    }

    public final void resizeAll() {
        if(playlistStyle != UNUSED_PLAYLIST) {
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

                trackPlaysCountHeader.setMaximumSize(new Dimension(
                    lpadWidth, (int)trackPlaysCountHeader.getPreferredSize().getHeight()
                ));
                trackPlaysCountHeader.revalidate();
                trackPlaysCountHeader.repaint();

                playsCountRightMargin.setMaximumSize(new Dimension(
                    rpadWidth, (int)trackPlaysCountHeader.getPreferredSize().getHeight()
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

                trackAlbumHeader.setMaximumSize(new Dimension(
                    padWidth, (int)trackAlbumHeader.getPreferredSize().getHeight()
                ));
                trackAlbumHeader.revalidate();
                trackAlbumHeader.repaint();

                trackAlbumColumn.setMaximumSize(new Dimension(padWidth, globalHeight));
                trackAlbumColumn.setPreferredSize(trackAlbumColumn.getMaximumSize());
                trackAlbumColumn.setSize(trackAlbumColumn.getPreferredSize());
                trackAlbumColumn.revalidate();
                trackAlbumColumn.repaint();

                trackDateAddedHeader.setMaximumSize(new Dimension(
                    lpadWidth, (int)trackDateAddedHeader.getPreferredSize().getHeight()
                ));
                trackDateAddedHeader.revalidate();
                trackDateAddedHeader.repaint();

                dateAddedRightMargin.setMaximumSize(new Dimension(
                    rpadWidth, (int)trackDateAddedHeader.getPreferredSize().getHeight()
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

    public int getGlobalHeight() {
        return globalHeight;
    }

    public void setGlobalHeight(int globalHeight) {
        this.globalHeight = globalHeight;
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

    public JLabel getTrackNumberHeader() {
        return trackNumberHeader;
    }

    public void setTrackNumberHeader(JLabel trackNumberHeader) {
        this.trackNumberHeader = trackNumberHeader;
    }

    public JPanel getTrackNumberColumn() {
        return trackNumberColumn;
    }

    public void setTrackNumberColumn(JPanel trackNumberColumn) {
        this.trackNumberColumn = trackNumberColumn;
    }

    public JLabel getTrackTitleHeader() {
        return trackTitleHeader;
    }

    public void setTrackTitleHeader(JLabel trackTitleHeader) {
        this.trackTitleHeader = trackTitleHeader;
    }

    public JPanel getTrackTitleColumn() {
        return trackTitleColumn;
    }

    public void setTrackTitleColumn(JPanel trackTitleColumn) {
        this.trackTitleColumn = trackTitleColumn;
    }

    public JLabel getTrackPlaysCountHeader() {
        return trackPlaysCountHeader;
    }

    public void setTrackPlaysCountHeader(JLabel trackPlaysCountHeader) {
        this.trackPlaysCountHeader = trackPlaysCountHeader;
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

    public JLabel getTrackAlbumHeader() {
        return trackAlbumHeader;
    }

    public void setTrackAlbumHeader(JLabel trackAlbumHeader) {
        this.trackAlbumHeader = trackAlbumHeader;
    }

    public JPanel getTrackAlbumColumn() {
        return trackAlbumColumn;
    }

    public void setTrackAlbumColumn(JPanel trackAlbumColumn) {
        this.trackAlbumColumn = trackAlbumColumn;
    }

    public JLabel getTrackDateAddedHeader() {
        return trackDateAddedHeader;
    }

    public void setTrackDateAddedHeader(JLabel trackDateAddedHeader) {
        this.trackDateAddedHeader = trackDateAddedHeader;
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

    public JLabel getTrackDurationHeader() {
        return trackDurationHeader;
    }

    public void setTrackDurationHeader(JLabel trackDurationHeader) {
        this.trackDurationHeader = trackDurationHeader;
    }

    public JPanel getTrackDurationColumn() {
        return trackDurationColumn;
    }

    public void setTrackDurationColumn(JPanel trackDurationColumn) {
        this.trackDurationColumn = trackDurationColumn;
    }
}
