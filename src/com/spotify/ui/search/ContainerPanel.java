package com.spotify.ui.search;

import com.spotify.ui.Threshold;
import com.spotify.ui.Window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ContainerPanel extends JPanel {
    private JButton panelTitle;
    private JButton seeAll;
    private JPanel content;

    public ContainerPanel(String panelTitle) {
        super();
        this.setBackground(new Color(18, 18, 18));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setPreferredSize(new Dimension(Threshold.contentArea.width - 60, 274));
        this.setSize(this.getPreferredSize());

        this.panelTitle = new JButton(panelTitle);
        Window.initializeButton(this.panelTitle);
        this.panelTitle.setContentAreaFilled(false);
//        this.panelTitle.setPreferredSize(new Dimension(this.getPreferredSize().width / 2, 60));

        this.seeAll = new JButton("SEE ALL");
        Window.initializeButton(this.seeAll);
        this.seeAll.setContentAreaFilled(false);
        this.seeAll.setForeground(new Color(179, 179, 179));
//        this.seeAll.setPreferredSize(new Dimension(this.getPreferredSize().width / 2, 60));

        this.add(this.panelTitle);
        this.add(Box.createRigidArea(new Dimension(
            (int)(this.getPreferredSize().getWidth() - (2 * seeAll.getPreferredSize().width)),
            (int)(seeAll.getPreferredSize().getHeight()))
        ));
        this.add(seeAll);

        this.content = new JPanel();
        this.content.setBackground(new Color(18, 18, 18));
        this.content.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.content.setPreferredSize(new Dimension(
            (int)this.getPreferredSize().getWidth(),
            this.getPreferredSize().height - seeAll.getPreferredSize().height
        ));

        this.add(content);
    }

    public JButton getPanelTitle() {
        return panelTitle;
    }

    public void setPanelTitle(JButton panelTitle) {
        this.panelTitle = panelTitle;
    }

    public JButton getSeeAll() {
        return seeAll;
    }

    public void setSeeAll(JButton seeAll) {
        this.seeAll = seeAll;
    }

    public JPanel getContent() {
        return content;
    }

    public void setContent(JPanel content) {
        this.content = content;
    }
}
