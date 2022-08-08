/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spotify.ui.LoginRegister;

import com.spotify.app.Account;
import com.spotify.app.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author KEVIN
 */
public class FresetPassword extends javax.swing.JFrame {

    /**
     * Creates new form FresetPassword
     */
    public FresetPassword(ArrayList<Account> accounts, User user) {
        initComponents(accounts, user);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(ArrayList<Account> accounts, User user) {

        questionverify = new javax.swing.JPanel();
        lbtitle = new javax.swing.JLabel();
        lblogoreg = new javax.swing.JLabel();
        lbregisterEmail = new javax.swing.JLabel();
        lblogin = new javax.swing.JLabel();
        lbconfirmpassword = new javax.swing.JLabel();
        lbexit = new javax.swing.JLabel();
        lbpassword_no = new javax.swing.JLabel();
        lbpassword_see = new javax.swing.JLabel();
        pfresetpassword = new javax.swing.JPasswordField();
        lbpassword_no2 = new javax.swing.JLabel();
        lbpassword_see2 = new javax.swing.JLabel();
        pflconfirm = new javax.swing.JPasswordField();
        lbMsg = new javax.swing.JLabel();
        lbresetpassword = new javax.swing.JLabel();
        this.setAutoRequestFocus(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        questionverify.setBackground(new java.awt.Color(18, 18, 18));
        questionverify.setForeground(new java.awt.Color(255, 255, 255));
        questionverify.setPreferredSize(new java.awt.Dimension(500, 550));
        questionverify.setLayout(null);

        lbtitle.setFont(new java.awt.Font("Gotham", 1, 30));
        lbtitle.setForeground(new java.awt.Color(255, 255, 255));
        lbtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbtitle.setText("Reset Password.");
        questionverify.add(lbtitle);
        lbtitle.setBounds(100, 90, 290, 53);

        lblogoreg.setBackground(new java.awt.Color(51, 51, 51));
        lblogoreg.setForeground(new java.awt.Color(255, 255, 255));
        lblogoreg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/spotify/assets/loginRegister/logo_Login_Spotify.png"))); // NOI18N
        lblogoreg.setText("logo");
        questionverify.add(lblogoreg);
        lblogoreg.setBounds(170, 40, 160, 50);

        lbregisterEmail.setForeground(new java.awt.Color(255, 255, 255));
        lbregisterEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/spotify/assets/loginRegister/register_btn.png"))); // NOI18N
        lbregisterEmail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        questionverify.add(lbregisterEmail);
        lbregisterEmail.setBounds(70, 330, 350, 50);
        lbregisterEmail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String password = new String(pfresetpassword.getPassword());
                String confirmPassword = new String(pflconfirm.getPassword());

                boolean passwordIsCorrect = false;
                if (password.equals(confirmPassword)) {
                    passwordIsCorrect = true;
                }

                if (passwordIsCorrect) {
                    user.setPassword(password);
                    JOptionPane.showMessageDialog(null, "Password reset is success!");
                    getThis().setVisible(false);
                    Flogin f = new Flogin(accounts);
                    f.setVisible(true);
                }
                else if (!passwordIsCorrect) {
                    JOptionPane.showMessageDialog(null, "Password and confirm password is not equals!");
                }

            }
        });

        lblogin.setFont(new java.awt.Font("Gotham", 1, 15));
        lblogin.setForeground(new java.awt.Color(179, 179, 179));
        lblogin.setText("LOGIN");
        lblogin.setToolTipText("");
        lblogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbloginMouseClicked(evt, accounts);
            }
        });
        questionverify.add(lblogin);
        lblogin.setBounds(340, 410, 60, 20);

        lbconfirmpassword.setFont(new java.awt.Font("Gotham", 1, 15));
        lbconfirmpassword.setForeground(new java.awt.Color(179, 179, 179));
        lbconfirmpassword.setText("Confirm Password");
        lbconfirmpassword.setToolTipText("");
        questionverify.add(lbconfirmpassword);
        lbconfirmpassword.setBounds(80, 230, 320, 20);

        lbexit.setFont(new java.awt.Font("Gotham", 1, 20));
        lbexit.setForeground(new java.awt.Color(255, 255, 255));
        lbexit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbexit.setText("X");
        lbexit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbexit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbexitMouseClicked(evt);
            }
        });
        questionverify.add(lbexit);
        lbexit.setBounds(440, 10, 40, 50);

        lbpassword_no.setForeground(new java.awt.Color(255, 255, 255));
        lbpassword_no.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/spotify/assets/loginRegister/pass_no_icon.png"))); // NOI18N
        lbpassword_no.setText("pass");
        lbpassword_no.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbpassword_noMouseClicked(evt);
            }
        });
        questionverify.add(lbpassword_no);
        lbpassword_no.setBounds(370, 180, 50, 40);

        lbpassword_see.setBackground(new java.awt.Color(0, 0, 0));
        lbpassword_see.setForeground(new java.awt.Color(255, 255, 255));
        lbpassword_see.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/spotify/assets/loginRegister/pass_see_icon.png"))); // NOI18N
        lbpassword_see.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbpassword_seeMouseClicked(evt);
            }
        });
        questionverify.add(lbpassword_see);
        lbpassword_see.setBounds(370, 180, 50, 40);

        pfresetpassword.setEchoChar('*');
        pfresetpassword.setBackground(new java.awt.Color(51, 51, 51));
        pfresetpassword.setFont(new java.awt.Font("Gotham", 0, 14));
        pfresetpassword.setForeground(new java.awt.Color(179, 179, 179));
        pfresetpassword.setText("Password");
        pfresetpassword.setCaretColor(Color.white);
        pfresetpassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 10, 5));
        pfresetpassword.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        questionverify.add(pfresetpassword);
        pfresetpassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                char[] temp={'P','a','s','s','w','o','r','d'};
                if (Arrays.equals(pfresetpassword.getPassword(),temp)){
                    pfresetpassword.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                char[] temp={};
                if (Arrays.equals(pfresetpassword.getPassword(),temp)){
                    pfresetpassword.setText("Password");
                }
            }
        });
        pfresetpassword.setBounds(80, 180, 300, 40);

        lbpassword_no2.setForeground(new java.awt.Color(255, 255, 255));
        lbpassword_no2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/spotify/assets/loginRegister/pass_no_icon.png"))); // NOI18N
        lbpassword_no2.setText("pass");
        lbpassword_no2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbpassword_no2MouseClicked(evt);
            }
        });
        questionverify.add(lbpassword_no2);
        lbpassword_no2.setBounds(370, 250, 50, 40);

        lbpassword_see2.setBackground(new java.awt.Color(0, 0, 0));
        lbpassword_see2.setForeground(new java.awt.Color(255, 255, 255));
        lbpassword_see2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/spotify/assets/loginRegister/pass_see_icon.png"))); // NOI18N
        lbpassword_see2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbpassword_see2MouseClicked(evt);
            }
        });
        questionverify.add(lbpassword_see2);
        lbpassword_see2.setBounds(370, 250, 50, 40);

        pflconfirm.setEchoChar('*');
        pflconfirm.setBackground(new java.awt.Color(51, 51, 51));
        pflconfirm.setFont(new java.awt.Font("Gotham", 0, 14));
        pflconfirm.setForeground(new java.awt.Color(179, 179, 179));
        pflconfirm.setText("Password");
        pflconfirm.setCaretColor(Color.white);
        pflconfirm.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 10, 5));
        pflconfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pflconfirm.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                char[] temp={'P','a','s','s','w','o','r','d'};
                if (Arrays.equals(pflconfirm.getPassword(),temp)){
                    pflconfirm.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                char[] temp={};
                if (Arrays.equals(pflconfirm.getPassword(),temp)){
                    pflconfirm.setText("Password");
                }
            }
        });
        questionverify.add(pflconfirm);
        pflconfirm.setBounds(80, 250, 300, 40);

        lbMsg.setFont(new java.awt.Font("Gotham", 1, 15));
        lbMsg.setForeground(new java.awt.Color(179, 179, 179));
        lbMsg.setText("Do you remember your password?");
        lbMsg.setToolTipText("");
        questionverify.add(lbMsg);
        lbMsg.setBounds(90, 410, 320, 20);

        lbresetpassword.setFont(new java.awt.Font("Gotham", 1, 15));
        lbresetpassword.setForeground(new java.awt.Color(179, 179, 179));
        lbresetpassword.setText("Reset Password");
        lbresetpassword.setToolTipText("");
        questionverify.add(lbresetpassword);
        lbresetpassword.setBounds(80, 160, 320, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(questionverify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(questionverify, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(650, 250, 500, 550);
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private FresetPassword getThis() {
        return this;
    }

    private void lbloginMouseClicked(java.awt.event.MouseEvent evt, ArrayList<Account> accounts) {//GEN-FIRST
        // :event_lbloginMouseClicked
        this.setVisible(false);
        Flogin l=new Flogin(accounts);
        l.setVisible(true);
    }//GEN-LAST:event_lbloginMouseClicked

    private void lbexitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbexitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lbexitMouseClicked

    private void lbpassword_noMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbpassword_noMouseClicked
        pfresetpassword.setEchoChar((char)0);
        lbpassword_no.setVisible(false);
        lbpassword_see.setVisible(true);
    }//GEN-LAST:event_lbpassword_noMouseClicked

    private void lbpassword_seeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbpassword_seeMouseClicked
        pfresetpassword.setEchoChar('*');
        lbpassword_see.setVisible(false);
        lbpassword_no.setVisible(true);
    }//GEN-LAST:event_lbpassword_seeMouseClicked

    private void lbpassword_no2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbpassword_no2MouseClicked
        pflconfirm.setEchoChar((char)0);
        lbpassword_no2.setVisible(false);
        lbpassword_see2.setVisible(true);
    }//GEN-LAST:event_lbpassword_no2MouseClicked

    private void lbpassword_see2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbpassword_see2MouseClicked
        pflconfirm.setEchoChar('*');
        lbpassword_see2.setVisible(false);
        lbpassword_no2.setVisible(true);
    }//GEN-LAST:event_lbpassword_see2MouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FresetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FresetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FresetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FresetPassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FresetPassword().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbMsg;
    private javax.swing.JLabel lbconfirmpassword;
    private javax.swing.JLabel lbexit;
    private javax.swing.JLabel lblogin;
    private javax.swing.JLabel lblogoreg;
    private javax.swing.JLabel lbpassword_no;
    private javax.swing.JLabel lbpassword_no2;
    private javax.swing.JLabel lbpassword_see;
    private javax.swing.JLabel lbpassword_see2;
    private javax.swing.JLabel lbregisterEmail;
    private javax.swing.JLabel lbresetpassword;
    private javax.swing.JLabel lbtitle;
    private javax.swing.JPasswordField pflconfirm;
    private javax.swing.JPasswordField pfresetpassword;
    private javax.swing.JPanel questionverify;
    // End of variables declaration//GEN-END:variables
}