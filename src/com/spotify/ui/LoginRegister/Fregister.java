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
import java.util.regex.Pattern;

/**
 *
 * @author KEVIN
 */
public class Fregister extends javax.swing.JFrame {

    /**
     * Creates new form test
     * @param accounts
     */
    public Fregister(ArrayList<Account> accounts) {
        initComponents(accounts);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents(ArrayList<Account> accounts) {
        register = new javax.swing.JPanel();
        lbtitlelogin1 = new javax.swing.JLabel();
        lbemail = new javax.swing.JLabel();
        lblogoreg = new javax.swing.JLabel();
        lbpassword_no = new javax.swing.JLabel();
        lbpassword_see = new javax.swing.JLabel();
        pflogin = new javax.swing.JPasswordField();
        lbregisterEmail = new javax.swing.JLabel();
        lblogin = new javax.swing.JLabel();
        lbMsg = new javax.swing.JLabel();
        tfemail = new javax.swing.JTextField();
        tfusername = new javax.swing.JTextField();
        lbtitlelogin2 = new javax.swing.JLabel();
        tfconfirmanswer = new javax.swing.JTextField();
        tfconfirmquestion = new javax.swing.JTextField();
        lbusericon = new javax.swing.JLabel();
        lbexit = new javax.swing.JLabel();
        tffirstname= new javax.swing.JTextField();
        tflastname= new javax.swing.JTextField();
        this.setAutoRequestFocus(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        register.setBackground(new java.awt.Color(18, 18, 18));
        register.setForeground(new java.awt.Color(255, 255, 255));
        register.setPreferredSize(new java.awt.Dimension(500, 600));
        register.setLayout(null);

        lbtitlelogin1.setFont(new java.awt.Font("Gotham", 1, 30));
        lbtitlelogin1.setForeground(new java.awt.Color(255, 255, 255));
        lbtitlelogin1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbtitlelogin1.setText("Sign up for free");
        register.add(lbtitlelogin1);
        lbtitlelogin1.setBounds(100, 90, 290, 53);

        lbemail.setBackground(new java.awt.Color(0, 0, 0));
        lbemail.setForeground(new java.awt.Color(255, 255, 255));
        lbemail.setIcon(new javax.swing.ImageIcon(
            getClass().getResource("/com/spotify/assets/loginRegister/email_icon.png")
        )); // NOI18N
        register.add(lbemail);
        lbemail.setBounds(365, 290, 50, 40);

        lblogoreg.setBackground(new java.awt.Color(51, 51, 51));
        lblogoreg.setForeground(new java.awt.Color(255, 255, 255));
        lblogoreg.setIcon(new javax.swing.ImageIcon(
            getClass().getResource("/com/spotify/assets/loginRegister/logo_Login_Spotify.png")
        )); // NOI18N
        lblogoreg.setText("logo");
        register.add(lblogoreg);
        lblogoreg.setBounds(170, 40, 160, 50);

        lbpassword_no.setForeground(new java.awt.Color(255, 255, 255));
        lbpassword_no.setIcon(new javax.swing.ImageIcon(
            getClass().getResource("/com/spotify/assets/loginRegister/pass_no_icon.png")
        )); // NOI18N
        lbpassword_no.setText("pass");
        lbpassword_no.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbpassword_noMouseClicked(evt);
            }
        });
        register.add(lbpassword_no);
        lbpassword_no.setBounds(362, 338, 50, 40);

        lbpassword_see.setBackground(new java.awt.Color(0, 0, 0));
        lbpassword_see.setForeground(new java.awt.Color(255, 255, 255));
        lbpassword_see.setIcon(new javax.swing.ImageIcon(
            getClass().getResource("/com/spotify/assets/loginRegister/pass_see_icon.png")
        )); // NOI18N
        lbpassword_see.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbpassword_seeMouseClicked(evt);
            }
        });
        register.add(lbpassword_see);
        lbpassword_see.setBounds(360, 338, 50, 40);

        pflogin.setEchoChar('*');
        pflogin.setBackground(new java.awt.Color(51, 51, 51));
        pflogin.setFont(new java.awt.Font("Gotham", 0, 14));
        pflogin.setForeground(new java.awt.Color(179, 179, 179));
        pflogin.setText("Password ");
        pflogin.setCaretColor(Color.white);
        pflogin.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 10, 5));
        pflogin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        pflogin.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                pfloginFocusGained(evt);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                pfloginFocusLost(evt);
            }
        });
        pflogin.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                char[] temp = {'P','a','s','s','w','o','r','d', ' '};
                if(Arrays.equals(pflogin.getPassword(),temp)){
                    pflogin.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                char[] temp={};
                if(Arrays.equals(pflogin.getPassword(),temp)){
                    pflogin.setText("Password ");
                }
            }
        });
        register.add(pflogin);
        pflogin.setBounds(70, 338, 300, 40);

        lbregisterEmail.setForeground(new java.awt.Color(255, 255, 255));
        lbregisterEmail.setIcon(new javax.swing.ImageIcon(
            getClass().getResource("/com/spotify/assets/loginRegister/register_btn.png")
        )); // NOI18N
        lbregisterEmail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        register.add(lbregisterEmail);
        lbregisterEmail.setBounds(60, 485, 350, 50);
        lbregisterEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                //over here ===========================================================================================
                String username = tfusername.getText();
                String password = new String(pflogin.getPassword());
                String firstName = tffirstname.getText();
                String lastName = tflastname.getText();
                String confirmQuestion = tfconfirmquestion.getText();
                String confirmAnswer = tfconfirmanswer.getText();
                String email = tfemail.getText();
                System.out.println("username = " + username);
                System.out.println("password = " + password);
                System.out.println("firstName = " + firstName);
                System.out.println("lastName = " + lastName);
                System.out.println("confirmQuestion = " + confirmQuestion);
                System.out.println("confirmAnswer = " + confirmAnswer);
                System.out.println("email = " + email);
                boolean allFieldFilled = true;
                boolean emailValid = isEmailValid(email);
                boolean usernameIsNew = true;
                boolean emailIsNew = true;
                for(Account account : accounts) {
                    if(account instanceof User) {
                        User user = (User) account;
                        if(user.getUsername().equals(username)) {
                            usernameIsNew = false;
                        }
                        if(user.getEmail().equals(username)) {
                            emailIsNew = false;
                        }
                    }
                }
                if(username.equalsIgnoreCase("admin")) {
                    usernameIsNew = false;
                }

                // Update: Punya Kenny isBlank(), punya Samuel isEmpty().
                // Mungkin beda JDK.
                /*
//                if(username.isBlank() || username.equals("Username ")
//                || password.isBlank() || password.equals("Password ")
//                || firstName.isBlank() || firstName.equals("First Name ")
//                || lastName.isBlank() || lastName.equals("Last Name ")
//                || confirmQuestion.isBlank() || confirmQuestion.equals("Verification Question ")
//                || confirmAnswer.isBlank() || confirmAnswer.equals("Verification Answer ")
//                || email.isBlank() || email.equals("Email ")) {
//                    allFieldFilled = false;
//                }
                */
                if(username.isEmpty() || username.equals("Username ")
                || password.isEmpty() || password.equals("Password ")
                || firstName.isEmpty() || firstName.equals("First Name ")
                || lastName.isEmpty() || lastName.equals("Last Name ")
                || confirmQuestion.isEmpty() || confirmQuestion.equals("Verification Question ")
                || confirmAnswer.isEmpty() || confirmAnswer.equals("Verification Answer ")
                || email.isEmpty() || email.equals("Email ")) {
                    allFieldFilled = false;
                }

                if(allFieldFilled && emailValid && usernameIsNew && emailIsNew) {
                    accounts.add(new User(
                        tfusername.getText(), new String(pflogin.getPassword()), tffirstname.getText(),
                        tflastname.getText(), tfconfirmquestion.getText(), tfconfirmanswer.getText(),
                        tfemail.getText()
                    ));
                    JOptionPane.showMessageDialog(null, "Register success");
                    lbloginMouseClicked(evt, accounts);
                }
                else if(!emailIsNew) {
                    JOptionPane.showMessageDialog(null, "Email already exists!");
                }
                else if(!usernameIsNew) {
                    JOptionPane.showMessageDialog(null, "Username already exists!");
                }
                else if(!emailValid) {
                    JOptionPane.showMessageDialog(null, "Email is invalid!");
                }
                else if(!allFieldFilled) {
                    JOptionPane.showMessageDialog(null, "Please fill all field!");
                }
            }
        });

        lblogin.setFont(new java.awt.Font("Gotham", 1, 15));
        lblogin.setForeground(new java.awt.Color(179, 179, 179));
        lblogin.setText("LOGIN");
        lblogin.setToolTipText("");
        lblogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbloginMouseClicked(evt, accounts);
            }
        });
        register.add(lblogin);
        lblogin.setBounds(290, 535, 60, 20);

        lbMsg.setFont(new java.awt.Font("Gotham", 1, 15));
        lbMsg.setForeground(new java.awt.Color(179, 179, 179));
        lbMsg.setText("Already have account?");
        lbMsg.setToolTipText("");
        register.add(lbMsg);
        lbMsg.setBounds(130, 535, 200, 20);

        tfemail.setBackground(new java.awt.Color(51, 51, 51));
        tfemail.setFont(new java.awt.Font("Gotham", 0, 14));
        tfemail.setForeground(new java.awt.Color(179, 179, 179));
        tfemail.setText("Email ");
        tfemail.setCaretColor(Color.white);
        tfemail.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tfemail.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfemailFocusGained(evt);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfemailFocusLost(evt);
            }
        });
        register.add(tfemail);
        tfemail.setBounds(70, 290, 300, 40);

        tfusername.setBackground(new java.awt.Color(51, 51, 51));
        tfusername.setFont(new java.awt.Font("Gotham", 0, 14));
        tfusername.setForeground(new java.awt.Color(179, 179, 179));
        tfusername.setText("Username ");
        tfusername.setCaretColor(Color.white);
        tfusername.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tfusername.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfusernameFocusGained(evt);
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfusernameFocusLost(evt);
            }
        });
        register.add(tfusername);
        tfusername.setBounds(70, 240, 300, 40);

        lbtitlelogin2.setFont(new java.awt.Font("Gotham", 1, 30));
        lbtitlelogin2.setForeground(new java.awt.Color(255, 255, 255));
        lbtitlelogin2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbtitlelogin2.setText("Spotify Account.");
        register.add(lbtitlelogin2);
        lbtitlelogin2.setBounds(100, 130, 290, 53);

        tffirstname.setBackground(new java.awt.Color(51, 51, 51));
        tffirstname.setFont(new java.awt.Font("Gotham", 0, 14));
        tffirstname.setForeground(new java.awt.Color(179, 179, 179));
        tffirstname.setText("First Name ");
        tffirstname.setCaretColor(Color.white);
        tffirstname.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tffirstname.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(tffirstname.getText().equals("First Name ")) {
                    tffirstname.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(tffirstname.getText().equals("First Name ")||tffirstname.getText().trim().equals("")) {
                    tffirstname.setText("First Name ");
                }
            }
        });

        register.add(tffirstname);
        tffirstname.setBounds(70, 190, 165, 40);
        tflastname.setBackground(new java.awt.Color(51, 51, 51));
        tflastname.setFont(new java.awt.Font("Gotham", 0, 14));
        tflastname.setForeground(new java.awt.Color(179, 179, 179));
        tflastname.setText("Last Name ");
        tflastname.setCaretColor(Color.white);
        tflastname.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        register.add(tflastname);
        tflastname.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(tflastname.getText().equals("Last Name ")) {
                    tflastname.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(tflastname.getText().equals("Last Name ")||tflastname.getText().trim().equals("")) {
                    tflastname.setText("Last Name ");
                }
            }
        });
        tflastname.setBounds(240, 190, 165, 40);

        tfconfirmanswer.setBackground(new java.awt.Color(51, 51, 51));
        tfconfirmanswer.setFont(new java.awt.Font("Gotham", 0, 14));
        tfconfirmanswer.setForeground(new java.awt.Color(179, 179, 179));
        tfconfirmanswer.setText("Verification Answer ");
        tfconfirmanswer.setCaretColor(Color.white);
        tfconfirmanswer.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tfconfirmanswer.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(tfconfirmanswer.getText().equals("Verification Answer ")) {
                    tfconfirmanswer.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(tfconfirmanswer.getText().equals("Verification Answer ")||tfconfirmanswer.getText().trim().equals("")) {
                    tfconfirmanswer.setText("Verification Answer ");
                }
            }
        });
        register.add(tfconfirmanswer);
        register.add(tfconfirmanswer);
        tfconfirmanswer.setBounds(70, 435, 335, 40);

        tfconfirmquestion.setBackground(new java.awt.Color(51, 51, 51));
        tfconfirmquestion.setFont(new java.awt.Font("Gotham", 0, 14));
        tfconfirmquestion.setForeground(new java.awt.Color(179, 179, 179));
        tfconfirmquestion.setText("Verification Question ");
        tfconfirmquestion.setCaretColor(Color.white);
        tfconfirmquestion.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        tfconfirmquestion.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(tfconfirmquestion.getText().equals("Verification Question ")) {
                    tfconfirmquestion.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(tfconfirmquestion.getText().equals("Verification Question ")||tfconfirmquestion.getText().trim().equals("")) {
                    tfconfirmquestion.setText("Verification Question ");
                }
            }
        });
        register.add(tfconfirmquestion);
        tfconfirmquestion.setBounds(70, 385, 335, 40);

        lbusericon.setForeground(new java.awt.Color(255, 255, 255));
        lbusericon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/spotify/assets/loginRegister/userst_icon.png"))); // NOI18N
        lbusericon.setText("icon");
        register.add(lbusericon);
        lbusericon.setBounds(360, 238, 50, 42);

        lbexit.setFont(new java.awt.Font("Gotham", 1, 20));
        lbexit.setForeground(new java.awt.Color(255, 255, 255));
        lbexit.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbexit.setText("X");
        lbexit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbexit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbexitMouseClicked(evt);
            }
        });
        register.add(lbexit);
        lbexit.setBounds(440, 10, 40, 50);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(register, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(register, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBounds(650, 250, 500, 600);
        setLocationRelativeTo(null);
    }// </editor-fold>

    private void lbpassword_noMouseClicked(java.awt.event.MouseEvent evt) {
        pflogin.setEchoChar((char) 0);
        lbpassword_no.setVisible(false);
        lbpassword_see.setVisible(true);
    }

    private void lbpassword_seeMouseClicked(java.awt.event.MouseEvent evt) {
        pflogin.setEchoChar('*');
        lbpassword_see.setVisible(false);
        lbpassword_no.setVisible(true);
    }

    private void pfloginFocusGained(java.awt.event.FocusEvent evt) {
    }

    private void pfloginFocusLost(java.awt.event.FocusEvent evt) {
    }

    private void tfemailFocusGained(java.awt.event.FocusEvent evt) {
        if(tfemail.getText().equals("Email ")) {
            tfemail.setText("");
        }
    }

    private void tfemailFocusLost(java.awt.event.FocusEvent evt) {
        if(tfemail.getText().trim().equals("") || tfemail.getText().equals("Email ")) {
            tfemail.setText("Email ");
        }
    }

    private void tfusernameFocusGained(java.awt.event.FocusEvent evt) {
        if(tfusername.getText().equals("Username ")) {
            tfusername.setText("");
        }
    }

    private void tfusernameFocusLost(java.awt.event.FocusEvent evt) {
        if(tfusername.getText().equals("Username ") || tfusername.getText().trim().equals("")) {
            tfusername.setText("Username ");
        }
    }

    private void lbloginMouseClicked(java.awt.event.MouseEvent evt, ArrayList<Account> accounts) {
        this.setVisible(false);
        Flogin l = new Flogin(accounts);
        l.setVisible(true);
    }

    private void lbexitMouseClicked(java.awt.event.MouseEvent evt) {
        System.exit(0);
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        if(email == null)
            return false;
        return pat.matcher(email).matches();
    }

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//         */
//        try {
//            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch(ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Fregister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch(InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Fregister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch(IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Fregister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch(javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Fregister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Fregister().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbMsg;
    private javax.swing.JLabel lbemail;
    private javax.swing.JLabel lbexit;
    private javax.swing.JLabel lblogin;
    private javax.swing.JLabel lblogoreg;
    private javax.swing.JLabel lbpassword_no;
    private javax.swing.JLabel lbpassword_see;
    private javax.swing.JLabel lbregisterEmail;
    private javax.swing.JLabel lbtitlelogin1;
    private javax.swing.JLabel lbtitlelogin2;
    private javax.swing.JLabel lbusericon;
    private javax.swing.JPasswordField pflogin;
    private javax.swing.JPanel register;
    private javax.swing.JTextField tfconfirmanswer;
    private javax.swing.JTextField tfconfirmquestion;
    private javax.swing.JTextField tfemail;
    private javax.swing.JTextField tfusername;
    private javax.swing.JTextField tffirstname;
    private javax.swing.JTextField tflastname;
    // End of variables declaration//GEN-END:variables
}
