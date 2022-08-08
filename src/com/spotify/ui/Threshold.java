package com.spotify.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

public class Threshold {
    public static Point currentPosition;
    public static Dimension minimumSize;
    public static Dimension maximumSize;
    public static Dimension currentSize;
    public static Dimension sidebarDraggable;
    public static Dimension contentDraggable;
    public static Dimension playerArea;
    public static Dimension sidebarArea;
    public static Dimension contentArea;

    /**
     *  000000 - Pure Black
     *  1db954 - Green
     *  1ed760 - Light Green
     *  282828 - Dark Grey
     *  535353 - Grey
     *  b3b3b3 - Light Grey
     *  ffffff - Pure White
     */
    public final static Color palette_000000 = new Color(0, 0, 0);
    public final static Color palette_1db954 = new Color(29, 185, 84);
    public final static Color palette_1ed760 = new Color(30, 215, 96);
    public final static Color palette_535353 = new Color(83, 83, 83);
    public final static Color palette_b3b3b3 = new Color(179, 179, 179);
    public final static Color palette_ffffff = new Color(255, 255, 255);

    static {
        currentPosition = new Point(0, 0);
        minimumSize = new Dimension(800, 600);
        maximumSize = new Dimension(
            (int)GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth(),
            (int)GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight()
        );
        currentSize = maximumSize;
        sidebarDraggable = new Dimension(200, 48);
        contentDraggable = new Dimension(
            (int)(currentSize.getWidth() - sidebarDraggable.getWidth()), 60
        );
        playerArea = new Dimension(
            (int)currentSize.getWidth(), 90
        );
        sidebarArea = new Dimension(
            (int)sidebarDraggable.getWidth(), (int)(currentSize.getHeight() - playerArea.getHeight())
        );
        contentArea = new Dimension(
            (int)(currentSize.getWidth() - sidebarArea.getWidth()),
            (int)(currentSize.getHeight() - playerArea.getHeight())
        );

        try {
            Font Gotham_Medium = Font.createFont(
                Font.TRUETYPE_FONT, new File("src/com/spotify/assets/fonts/Gotham Medium.otf")
            );
            Font Gotham_Ultra = Font.createFont(
                Font.TRUETYPE_FONT, new File("src/com/spotify/assets/fonts/Gotham Ultra.otf")
            );

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Gotham_Medium);
            ge.registerFont(Gotham_Ultra);
        } catch(IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}
