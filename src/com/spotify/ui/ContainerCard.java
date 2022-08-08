package com.spotify.ui;

import com.spotify.ui.Threshold;
import com.spotify.ui.Window;
import com.spotify.ui.search.ContentSearch;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ContainerCard extends JPanel {
    private JButton panelTitle;
    private JButton seeAll;
    private JPanel content;

    public ContainerCard(String panelTitle, Window frame) {
        super();

        MouseListener ml = new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent evt) {
                Component button = evt.getComponent();
                Font font = button.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                button.setFont(font.deriveFont(attributes));
            }

            @Override
            public void mouseExited(MouseEvent evt){
                System.out.println("Exited");
                Component button = evt.getComponent();
                Font font = button.getFont();
                Map attributes = font.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, null);
                button.setFont(font.deriveFont(attributes));
            }
        };

        this.setPreferredSize(new Dimension(Threshold.contentArea.width - 60, 314));
        this.setSize(this.getPreferredSize());
        this.setBackground(new Color(18, 18, 18));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        this.panelTitle = new JButton(panelTitle);
        Window.initializeButton(this.panelTitle);
        this.panelTitle.setContentAreaFilled(false);
//        this.panelTitle.setPreferredSize(new Dimension(this.getPreferredSize().width / 2, 60));
        this.panelTitle.addMouseListener(ml);

        this.seeAll = new JButton("SEE ALL");
        Window.initializeButton(this.seeAll);
        this.seeAll.setContentAreaFilled(false);
        this.seeAll.setForeground(new Color(179, 179, 179));
        this.seeAll.addMouseListener(ml);
//        this.seeAll.setPreferredSize(new Dimension(this.getPreferredSize().width / 2, 60));
        this.seeAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getThis().setPreferredSize(new Dimension((int) getThis().getPreferredSize().getWidth(),
                        frame.getContentPanel().getPreferredSize().height));
                getThis().getContent().setPreferredSize(new Dimension((int) getThis().getPreferredSize().getWidth(),
                        (int) (frame.getContentPanel().getPreferredSize().getHeight() - getThis().getSeeAll().getPreferredSize().getHeight())));
                ContentSearch panel = new ContentSearch();
                panel.add(getThis());
                frame.changeContent(new Content(panel), frame.getContentDraggable(), frame.user, frame.accounts);
            }
        });
        this.getPanelTitle().addActionListener(this.getSeeAll().getActionListeners()[0]);

        this.add(this.panelTitle);
        this.add(Box.createRigidArea(new Dimension(
                        (int) (this.getPreferredSize().getWidth() - (10 + this.panelTitle.getPreferredSize().width + seeAll.getPreferredSize().width)),
                        (int) (seeAll.getPreferredSize().getHeight()))
                )
        );
        this.add(seeAll);

        this.content = new JPanel();
        this.content.setPreferredSize(new Dimension((int) this.getPreferredSize().getWidth(),
                this.getPreferredSize().height - seeAll.getPreferredSize().height));
        this.content.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        this.content.setBackground(new Color(18, 18, 18));

        this.add(content);
    }

    public ContainerCard getThis() {
        return this;
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
