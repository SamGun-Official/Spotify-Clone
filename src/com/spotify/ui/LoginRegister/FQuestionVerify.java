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
import java.util.ArrayList;

/**
 *
 * @author KEVIN
 */
public class FQuestionVerify extends javax.swing.JFrame {

    /**
     * Creates new form FresetPassword
     */
    public FQuestionVerify(ArrayList<Account> accounts) {
        initComponents(accounts);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(ArrayList<Account> accounts) {
        questionverify = new javax.swing.JPanel();
        lbtitlelogin1 = new javax.swing.JLabel();
        lbemail = new javax.swing.JLabel();
        lblogoreg = new javax.swing.JLabel();
        lbregisterEmail = new javax.swing.JLabel();
        lblogin = new javax.swing.JLabel();
        lbMsg = new javax.swing.JLabel();
        tfemail = new javax.swing.JTextField();
        tfconfirmanswer = new javax.swing.JTextField();
        tfconfirmquestion = new javax.swing.JTextField();
        lbexit = new javax.swing.JLabel();
        this.setAutoRequestFocus(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        questionverify.setBackground(new java.awt.Color(18, 18, 18));
        questionverify.setForeground(new java.awt.Color(255, 255, 255));
        questionverify.setPreferredSize(new java.awt.Dimension(500, 600));
        questionverify.setLayout(null);

        lbtitlelogin1.setFont(new java.awt.Font("Gotham", 1, 30));
        lbtitlelogin1.setForeground(new java.awt.Color(255, 255, 255));
        lbtitlelogin1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbtitlelogin1.setText("Recover Account.");
        questionverify.add(lbtitlelogin1);
        lbtitlelogin1.setBounds(100, 90, 290, 53);

        lbemail.setBackground(new java.awt.Color(0, 0, 0));
        lbemail.setForeground(new java.awt.Color(255, 255, 255));
        lbemail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/spotify/assets/loginRegister/email_icon.png"))); // NOI18N
        questionverify.add(lbemail);
        lbemail.setBounds(375, 160, 50, 40);

        lblogoreg.setBackground(new java.awt.Color(51, 51, 51));
        lblogoreg.setForeground(new java.awt.Color(255, 255, 255));
        lblogoreg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/spotify/assets/loginRegister/logo_Login_Spotify.png"))); // NOI18N
        lblogoreg.setText("logo");
        questionverify.add(lblogoreg);
        lblogoreg.setBounds(170, 40, 160, 50);

        lbregisterEmail.setForeground(new java.awt.Color(255, 255, 255));
        lbregisterEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/spotify/assets/loginRegister/register_btn.png"))); // NOI18N
        lbregisterEmail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbregisterEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbregisterEmailMouseClicked(evt, accounts);
            }
        });
        questionverify.add(lbregisterEmail);
        lbregisterEmail.setBounds(70, 360, 350, 50);

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
        lblogin.setBounds(340, 440, 60, 20);

        lbMsg.setFont(new java.awt.Font("Gotham", 1, 15));
        lbMsg.setForeground(new java.awt.Color(179, 179, 179));
        lbMsg.setText("Do you remember your password?");
        lbMsg.setToolTipText("");
        questionverify.add(lbMsg);
        lbMsg.setBounds(90, 440, 320, 20);

        tfemail.setBackground(new java.awt.Color(51, 51, 51));
        tfemail.setFont(new java.awt.Font("Gotham", 0, 14));
        tfemail.setForeground(new java.awt.Color(179, 179, 179));
        tfemail.setText("Email");
        tfemail.setCaretColor(Color.white);
        tfemail.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tfemail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfemailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfemailFocusLost(evt);
            }
        });
        questionverify.add(tfemail);
        tfemail.setBounds(80, 160, 300, 40);

        tfconfirmanswer.setBackground(new java.awt.Color(51, 51, 51));
        tfconfirmanswer.setFont(new java.awt.Font("Gotham", 0, 14));
        tfconfirmanswer.setForeground(new java.awt.Color(179, 179, 179));
        tfconfirmanswer.setText("Verification Answer");
        tfconfirmanswer.setCaretColor(Color.white);
        tfconfirmanswer.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tfconfirmanswer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfconfirmanswerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfconfirmanswerFocusLost(evt);
            }
        });
        questionverify.add(tfconfirmanswer);
        tfconfirmanswer.setBounds(80, 280, 335, 40);

        tfconfirmquestion.setBackground(new java.awt.Color(51, 51, 51));
        tfconfirmquestion.setFont(new java.awt.Font("Gotham", 0, 14));
        tfconfirmquestion.setForeground(new java.awt.Color(179, 179, 179));
        tfconfirmquestion.setText("Verification Question");
        tfconfirmquestion.setCaretColor(Color.WHITE);
        tfconfirmquestion.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tfconfirmquestion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfconfirmquestionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfconfirmquestionFocusLost(evt);
            }
        });
        questionverify.add(tfconfirmquestion);
        tfconfirmquestion.setBounds(80, 220, 335, 40);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(questionverify, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(questionverify, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
        );

        setBounds(650, 250, 500, 550);
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbloginMouseClicked(java.awt.event.MouseEvent evt, ArrayList<Account> accounts) {//GEN-FIRST
        // :event_lbloginMouseClicked
        this.setVisible(false);
        Flogin l=new Flogin(accounts);
        l.setVisible(true);
    }//GEN-LAST:event_lbloginMouseClicked

    private void tfemailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfemailFocusGained
        if (tfemail.getText().trim().equals("Email")) {
            tfemail.setText("");
        }
    }//GEN-LAST:event_tfemailFocusGained

    private void tfemailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfemailFocusLost
        if (tfemail.getText().trim().equals("")||tfemail.getText().trim().equals("Email")) {
            tfemail.setText("Email");
        }
    }//GEN-LAST:event_tfemailFocusLost

    private void tfconfirmanswerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfconfirmanswerFocusGained
        if (tfconfirmanswer.getText().trim().equals("Verification Answer")) {
            tfconfirmanswer.setText("");
        }
    }//GEN-LAST:event_tfconfirmanswerFocusGained

    private void tfconfirmanswerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfconfirmanswerFocusLost
        if (tfconfirmanswer.getText().trim().equals("Verification Answer")||tfconfirmanswer.getText().trim().equals("")) {
            tfconfirmanswer.setText("Verification Answer");
        }
    }//GEN-LAST:event_tfconfirmanswerFocusLost

    private void tfconfirmquestionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfconfirmquestionFocusGained
        if (tfconfirmquestion.getText().trim().equals("Verification Question")) {
            tfconfirmquestion.setText("");
        }
    }//GEN-LAST:event_tfconfirmquestionFocusGained

    private void tfconfirmquestionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfconfirmquestionFocusLost
        if (tfconfirmquestion.getText().trim().equals("Verification Question")||tfconfirmquestion.getText().trim().equals("")) {
            tfconfirmquestion.setText("Verification Question");
        }
    }//GEN-LAST:event_tfconfirmquestionFocusLost

    private void lbexitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbexitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lbexitMouseClicked

    private void lbregisterEmailMouseClicked(java.awt.event.MouseEvent evt, ArrayList<Account> accounts) {//GEN-FIRST
        // :event_lbregisterEmailMouseClicked
        String email = tfemail.getText();
        String question = tfconfirmquestion.getText();
        String answer = tfconfirmanswer.getText();

        boolean emailCorrect = false;
        boolean answerCorrect = false;
        User validUser = null;

        for (Account account : accounts) {
            if (account instanceof User) {
                User user = (User) account;
                if (user.getEmail().equals(email)) {
                    emailCorrect = true;
                    if (user.getSecurityQuestions().equals(question) && user.getSecurityAnswer().equals(question)) {
                        answerCorrect = true;
                        validUser = user;
                    }
                }
            }
        }

        if (emailCorrect && answerCorrect) {
            this.setVisible(false);
            FresetPassword reset=new FresetPassword(accounts, validUser);
            reset.setVisible(true);
        }
        else if (!emailCorrect) {
            JOptionPane.showMessageDialog(null, "Email doesn't exists!");
        }
        else if (!answerCorrect) {
            JOptionPane.showMessageDialog(null, "Wrong answer!");
        }



    }//GEN-LAST:event_lbregisterEmailMouseClicked

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
//            java.util.logging.Logger.getLogger(FQuestionVerify.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FQuestionVerify.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FQuestionVerify.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FQuestionVerify.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FQuestionVerify().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbMsg;
    private javax.swing.JLabel lbemail;
    private javax.swing.JLabel lbexit;
    private javax.swing.JLabel lblogin;
    private javax.swing.JLabel lblogoreg;
    private javax.swing.JLabel lbregisterEmail;
    private javax.swing.JLabel lbtitlelogin1;
    private javax.swing.JPanel questionverify;
    private javax.swing.JTextField tfconfirmanswer;
    private javax.swing.JTextField tfconfirmquestion;
    private javax.swing.JTextField tfemail;
    // End of variables declaration//GEN-END:variables
}
