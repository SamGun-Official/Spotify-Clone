package com.spotify.ui.admin;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ASSET {
    public static Dimension fullscreen;
    public static Dimension titleBarContent;
    public static Dimension titleBarSideBar;
    public static Dimension player;
    public static Dimension sideBar;
    public static Dimension content;

    //constructor static
    static {
        fullscreen = new Dimension(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width,
                GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);
        player = new Dimension((int) fullscreen.getWidth(), 100);
        titleBarSideBar = new Dimension( 200, 50);
        titleBarContent = new Dimension((int) fullscreen.getWidth() - titleBarSideBar.width, 60);
        sideBar = new Dimension((int) titleBarSideBar.getWidth(),
                (int) (fullscreen.getHeight() - player.getHeight() - titleBarSideBar.getHeight()));
        content = new Dimension((int) (fullscreen.getWidth() - sideBar.getWidth()),
                (int) (fullscreen.getHeight() - player.getHeight() - titleBarContent.getHeight()));
    }
}
