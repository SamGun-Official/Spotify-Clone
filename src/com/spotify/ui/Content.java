package com.spotify.ui;

import com.spotify.app.Admin;
import com.spotify.ui.graphics.TranslucentScrollBarUI;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class Content extends JScrollPane {
    public Content() {
        super();
        this.setBackground(new Color(18, 18, 18));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(40, 40, 40)));
        this.setBounds(
            0, Threshold.contentDraggable.height, Threshold.contentArea.width, Threshold.contentArea.height
        );
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(14);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                setPreferredSize(new Dimension(
                    Threshold.currentSize.width - Threshold.sidebarArea.width,
                    Threshold.currentSize.height - Threshold.playerArea.height
                ));
                setSize(getPreferredSize());
            }
        });
        initScrollBar();
        this.setVisible(true);
    }

    public Content(JPanel panel, Admin admin) {
        super(panel);
        this.setBackground(new Color(18, 18, 18));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(40, 40, 40)));
        this.setBounds(
                0, Threshold.contentDraggable.height, Threshold.contentArea.width, Threshold.contentArea.height
        );
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(14);
//        this.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent ce) {
//                setPreferredSize(new Dimension(
//                        Threshold.currentSize.width - Threshold.sidebarArea.width,
//                        Threshold.currentSize.height - Threshold.playerArea.height
//                ));
//                setSize(getPreferredSize());
//            }
//        });
        initScrollBar();
        this.setVisible(true);
    }

    public Content(JPanel panel) {
        super(panel);
        this.setBackground(new Color(18, 18, 18));
//        this.setBackground(Color.yellow);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(40, 40, 40)));
        this.setBounds(
            0, Threshold.contentDraggable.height, Threshold.contentArea.width, Threshold.contentArea.height
        );
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(14);
//        this.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent ce) {
//                setPreferredSize(new Dimension(
//                    Threshold.currentSize.width - Threshold.sidebarArea.width,
//                    Threshold.currentSize.height - Threshold.playerArea.height
//                ));
//                setSize(getPreferredSize());
//            }
//        });
        initScrollBar();
        this.setVisible(true);
    }

    public Content(JPanel panel, boolean setBorder) {
        super(panel);
        this.setBackground(new Color(18, 18, 18));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, new Color(40, 40, 40)));
        this.setBounds(
            0, 0, Threshold.contentArea.width, Threshold.contentArea.height
        );
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        this.getVerticalScrollBar().setUnitIncrement(14);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent ce) {
                setPreferredSize(new Dimension(
                    Threshold.currentSize.width - Threshold.sidebarArea.width,
                    Threshold.currentSize.height - Threshold.playerArea.height
                ));
                setSize(getPreferredSize());
            }
        });
        initScrollBar();
        this.setVisible(true);
    }

    public void initScrollBar() {
        this.getVerticalScrollBar().setUI(new TranslucentScrollBarUI(new Color(255, 255, 255, 77)) {
            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D)g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setPaint(color);
                g2.fillRect(r.x, r.y, r.width - 2, r.height);
                g2.dispose();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
                Color specificColor = new Color(18, 18, 18);
                Graphics2D g2 = (Graphics2D)g.create();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(specificColor);
                g2.setPaint(specificColor);
                g2.fillRect(r.x, r.y, r.width, r.height);
                g2.dispose();
            }
        });
    }
}
