/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spotify.ui.profile;

/**
 *
 * @author KEVIN
 */
public class ContainerPlaylist extends javax.swing.JPanel {

    public ContainerPlaylist() {
        initComponents();
    }

    public void settitle(String name) {
        lbtitle.setText(name);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containerPanelProfile = new javax.swing.JPanel();
        lbtitle = new javax.swing.JLabel();

        setBackground(new java.awt.Color(44, 44, 44));

        containerPanelProfile.setBackground(new java.awt.Color(75, 75, 75));

        javax.swing.GroupLayout containerPanelProfileLayout = new javax.swing.GroupLayout(containerPanelProfile);
        containerPanelProfile.setLayout(containerPanelProfileLayout);
        containerPanelProfileLayout.setHorizontalGroup(
            containerPanelProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        containerPanelProfileLayout.setVerticalGroup(
            containerPanelProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );

        lbtitle.setFont(new java.awt.Font("Gotham", 1, 18)); // NOI18N
        lbtitle.setForeground(new java.awt.Color(255, 255, 255));
        lbtitle.setText("My Playlist");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(containerPanelProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 48, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(containerPanelProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbtitle, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerPanelProfile;
    private javax.swing.JLabel lbtitle;
    // End of variables declaration//GEN-END:variables
}
