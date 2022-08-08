package com.spotify.ui.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicSliderUI;

public class PaintSlider extends BasicSliderUI {
    private final BasicStroke stroke = new BasicStroke(
        4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f, new float[]{1f, 2f}, 0f
    );

    public PaintSlider(JSlider b) {
        super(b);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(12, 16);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();

        g2d.setStroke(stroke);
        g2d.setPaint(null);

        if(slider.getOrientation() == SwingConstants.HORIZONTAL) {
            g2d.drawLine(trackRect.x, trackRect.y + trackRect.height / 2,
            trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
        } else {
            g2d.drawLine(trackRect.x + trackRect.width / 2, trackRect.y,
            trackRect.x + trackRect.width / 2, trackRect.y + trackRect.height);
        }

        g2d.setStroke(old);
    }

    @Override
    public void paintThumb(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.fillOval(thumbRect.x, thumbRect.y + 2, 12, 12);
    }
}
