package com.spotify.ui.admin;

import javax.swing.*;
import java.awt.*;

public class ContentAdmin extends JScrollPane {
    public ContentAdmin() {
        super();
        this.setBounds(ASSET.sideBar.width, ASSET.titleBarContent.height, ASSET.content.width, ASSET.content.height+150);
        this.setSize(ASSET.content.width, ASSET.content.height);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(14);
        this.setBackground(new Color(18, 18, 18));
        this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        this.setVisible(true);
    }

    public ContentAdmin(JPanel panel) {
        super(panel);
        this.setBounds(ASSET.sideBar.width, ASSET.titleBarContent.height, ASSET.content.width, ASSET.content.height+150);
        this.setSize(ASSET.content.width, ASSET.content.height);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(14);
        this.setBackground(new Color(18, 18, 18));
        this.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        this.setVisible(true);
    }
}
