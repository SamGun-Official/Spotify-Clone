package com.spotify.ui.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class TranslucentScrollBarUI extends BasicScrollBarUI {
    protected final Color color;

    /**
     *  Apply transparent and custom LAF of scrollbar.
     *
     *  @param color Thumb color
     */
    public TranslucentScrollBarUI(Color color) {
        this.color = color;
    }

    /**
     *  "Hide" arrow button by default.
     *
     *  @return
     */
    protected JButton createZeroButton() {
        JButton jbutton = new JButton();
        jbutton.setMaximumSize(new Dimension(0, 0));
        jbutton.setMinimumSize(new Dimension(0, 0));
        jbutton.setPreferredSize(new Dimension(0, 0));

        return jbutton;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D)g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setPaint(color);
        g2.fillRect(r.x, r.y, r.width, r.height);
        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        // Track must not contain anything as we are trying
        // to create transparent and custom background LAF.
    }

    @Override
    protected void setThumbBounds(int x, int y, int width, int height) {
        super.setThumbBounds(x, y, width, height);

        scrollbar.revalidate();
        scrollbar.repaint();
    }
}
