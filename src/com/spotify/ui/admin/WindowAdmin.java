package com.spotify.ui.admin;

import com.spotify.app.Account;
import com.spotify.app.Admin;
import com.spotify.app.User;
import com.spotify.ui.admin.form.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WindowAdmin extends JFrame implements ActionListener {
    ArrayList<JButton> playlistsButton = new ArrayList<>();
    private TitleBarSideBar titleBarSideBar;
    private TitleBarContent titleBarContent;
    private JPanel sidebar;
    private JPanel player;
    private ContentAdmin content;
    private JPanel navbar;
    private NavbarButtonAdmin CreateArtist;
    private NavbarButtonAdmin UploadSong;
    private NavbarButtonAdmin CreateAds;
    private NavbarButtonAdmin CreateAlbum;
    private NavbarButtonAdmin UpdateArtist;
    private NavbarButtonAdmin UpdateSong;
    private NavbarButtonAdmin UpdateAds;
    private NavbarButtonAdmin UpdateAlbum;
    private NavbarButtonAdmin DeleteArtist;
    private NavbarButtonAdmin DeleteSong;
    private NavbarButtonAdmin DeleteAds;
    private NavbarButtonAdmin DeleteAlbum;
    private NavbarButtonAdmin Logout;
    private NavbarButtonAdmin DeleteUser;
    private NavbarButtonAdmin VerifyUser;
    private Admin admin;
    private ArrayList<Account> accounts;
    private User user;

    public WindowAdmin(Admin admin, ArrayList<Account> accounts) {
        this.admin = admin;
        this.accounts = accounts;
        this.user = user;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBackground(Color.black);
        this.setSize(1920,1040);
        this.setUndecorated(true);

        this.titleBarSideBar = new TitleBarSideBar(this);
        this.titleBarSideBar.setBackground(Color.black);
        this.add(titleBarSideBar);

        this.sidebar = new JPanel();
        this.player = new JPanel();
        this.content = new ContentAdmin();
        JPanel panel = new JPanel();
        panel.setBackground(new Color(18,18,18));
        this.content = new ContentAdmin(panel);
        this.sidebar.setBounds(0,0, 200, (int) size.getHeight());
        this.sidebar.setBackground(new Color(0,0,0));
        this.player.setBounds(0, (int) size.getHeight() - 150, (int) size.getWidth(), 150);
        this.player.setBackground(new Color(24,24,24));
        this.content.setBounds(sidebar.getWidth(), 0, (int) size.getWidth() - sidebar.getWidth(),
                (int) size.getHeight() - 100);
        this.content.setBackground(new Color(24,24,24));

        this.sidebar.setLayout(null);
        navbar = new JPanel();
        navbar.setBounds(0,0,sidebar.getWidth(), 700);
        navbar.setOpaque(false);
        navbar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        Dimension sizeNavbar = new Dimension(navbar.getWidth() - 20, 38);
        
        CreateArtist = new NavbarButtonAdmin("Create Artist", sizeNavbar, this, "src/com/spotify/assets/icons_admin/02_user.png");
        CreateAds = new NavbarButtonAdmin("Create Ads", sizeNavbar, this, "src/com/spotify/assets/icons_admin/13_image.png");
        CreateAlbum = new NavbarButtonAdmin("Create Album", sizeNavbar, this, "src/com/spotify/assets/icons_admin/30_new.png");
        UpdateArtist = new NavbarButtonAdmin("Update Artist", sizeNavbar, this, "src/com/spotify/assets/icons_admin/update.png");
        UpdateSong = new NavbarButtonAdmin("Update Song", sizeNavbar, this, "src/com/spotify/assets/icons_admin/update.png");
        UpdateAds = new NavbarButtonAdmin("Update Ads", sizeNavbar, this, "src/com/spotify/assets/icons_admin/update.png");
        UpdateAlbum = new NavbarButtonAdmin("Update Album", sizeNavbar, this, "src/com/spotify/assets/icons_admin/update.png");
        DeleteArtist = new NavbarButtonAdmin("Delete Artist", sizeNavbar, this, "src/com/spotify/assets/icons_admin/09_close.png");
        DeleteSong = new NavbarButtonAdmin("Delete Song", sizeNavbar, this, "src/com/spotify/assets/icons_admin/09_close.png");
        DeleteAds = new NavbarButtonAdmin("Delete Ads", sizeNavbar, this, "src/com/spotify/assets/icons_admin/09_close.png");
        DeleteAlbum = new NavbarButtonAdmin("Delete Album", sizeNavbar, this, "src/com/spotify/assets/icons_admin/09_close.png");
        UploadSong = new NavbarButtonAdmin("Upload Song", sizeNavbar, this, "src/com/spotify/assets/icons_admin/10_music.png");
        VerifyUser = new NavbarButtonAdmin("Verify User",sizeNavbar,this,"src/com/spotify/assets/icons_admin/02_user.png");
        DeleteUser = new NavbarButtonAdmin("Delete User",sizeNavbar,this,"src/com/spotify/assets/icons_admin/09_close.png");
        Logout = new NavbarButtonAdmin("Logout", sizeNavbar, this, "src/com/spotify/assets/icons_admin/25_prohibited.png");

        navbar.add(Box.createRigidArea(new Dimension((int) sizeNavbar.getWidth(), (int) sizeNavbar.getHeight() - 30)));
        navbar.add(CreateArtist);
        navbar.add(UploadSong);
        navbar.add(CreateAds);
        navbar.add(CreateAlbum);
        navbar.add(Box.createRigidArea(sizeNavbar));
        navbar.add(UpdateArtist);
        navbar.add(UpdateSong);
//        navbar.add(UpdateAds);
        navbar.add(UpdateAlbum);
        navbar.add(Box.createRigidArea(sizeNavbar));
        navbar.add(DeleteArtist);
        navbar.add(DeleteSong);
//        navbar.add(DeleteAds);
        navbar.add(DeleteAlbum);
        navbar.add(Box.createRigidArea(sizeNavbar));
        navbar.add(VerifyUser);
        navbar.add(DeleteUser);
        navbar.add(Logout);

        JPanel linePanel = new JPanel();
        linePanel.setPreferredSize(new Dimension(navbar.getWidth() - 30, 1));
        linePanel.setSize(linePanel.getPreferredSize());
        linePanel.setMinimumSize(linePanel.getPreferredSize());
        linePanel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, new Color(30, 30, 30)));
        navbar.add(linePanel);
        this.sidebar.add(navbar);
        this.add(sidebar);
        this.add(player);

        this.titleBarContent = new TitleBarContent(this);
        this.add(titleBarContent);

        this.add(content);

        this.setVisible(true);
    }

    static ImageIcon resizeIcon(String filepath, int width, int height) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filepath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (img != null) {
            Image dimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(dimg);
        }
        else {
            return null;
        }
    }
    public static void initializeButton (JButton button) {
        button.setFont(new Font("Gotham", Font.PLAIN, 18));
        button.setFocusable(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
//        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setForeground(Color.white);
        button.setBackground(new Color(48,48,48));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CreateArtist.clicked(e);
        CreateAds.clicked(e);
        CreateAlbum.clicked(e);
        UpdateArtist.clicked(e);
        UpdateSong.clicked(e);
        UpdateAds.clicked(e);
        UpdateAlbum.clicked(e);
        DeleteArtist.clicked(e);
        DeleteSong.clicked(e);
        DeleteAds.clicked(e);
        DeleteAlbum.clicked(e);
        UploadSong.clicked(e);
        VerifyUser.clicked(e);
        DeleteUser.clicked(e);
        Logout.clicked(e);
        if (e.getSource() == CreateArtist) {
            this.remove(content);
            content = new ContentAdmin(new CreateArtistForm(admin));
            this.add(content);
            this.repaint();
            this.validate();
        }
        else if (e.getSource() == UploadSong) {
            this.remove(content);
            content = new ContentAdmin(new UploadSongForm(admin));
            this.add(content);
            this.repaint();
            this.validate();
        }
        else if (e.getSource() == CreateAds) {
            this.remove(content);
            content = new ContentAdmin(new CreateAdsForm(admin));
            this.add(content);
            this.repaint();
            this.validate();
        }
        else if (e.getSource() == CreateAlbum) {
            this.remove(content);
            content = new ContentAdmin(new CreateAlbumForm(admin));
            this.add(content);
            this.repaint();
            this.validate();
        }
        if (e.getSource() == UpdateArtist) {
            this.remove(content);
            content = new ContentAdmin(new UpdateArtistForm(admin));
            this.add(content);
            this.repaint();
            this.validate();
        }
        else if (e.getSource() == UpdateSong) {
            this.remove(content);
            content = new ContentAdmin(new UpdateSongForm(admin));
            this.add(content);
            this.repaint();
            this.validate();
        }
//        else if (e.getSource() == UpdateAds) {
//            System.out.println("CRUD ALBUM");
//        }
        else if (e.getSource() == UpdateAlbum) {
            this.remove(content);
            content = new ContentAdmin(new UpdateAlbumForm(admin));
            this.add(content);
            this.repaint();
            this.validate();
        }
        if (e.getSource() == DeleteArtist) {
            this.remove(content);
            content = new ContentAdmin(new DeleteArtistForm(admin));
            this.add(content);
            this.repaint();
            this.validate();
        }
        else if (e.getSource() == DeleteSong) {
            this.remove(content);
            content = new ContentAdmin(new DeleteSongForm(admin));
            this.add(content);
            this.repaint();
            this.validate();
        }
//        else if (e.getSource() == DeleteAds) {
//            System.out.println("CRUD ALBUM");
//        }
        else if (e.getSource() == DeleteAlbum) {
            this.remove(content);
            content = new ContentAdmin(new DeleteAlbumForm(admin));
            this.add(content);
            this.repaint();
            this.validate();
        }
        else if (e.getSource() == VerifyUser) {
            this.remove(content);
            content = new ContentAdmin(new VerifiyUserForm(admin,accounts));
            this.add(content);
            this.repaint();
            this.validate();
        }
        else if (e.getSource() == DeleteUser) {
            this.remove(content);
            content = new ContentAdmin(new DeleteUserForm(admin, accounts));
            this.add(content);
            this.repaint();
            this.validate();
        }
        else if (e.getSource() == Logout) {
            this.setVisible(false);
        }
    }
    public TitleBarSideBar getTitleBarSideBar() {
        return titleBarSideBar;
    }

    public void setTitleBarSideBar(TitleBarSideBar titleBarSideBar) {
        this.titleBarSideBar = titleBarSideBar;
    }
}
