package com.spotify.ui.search;

import com.spotify.app.Account;
import com.spotify.app.Admin;
import com.spotify.app.AlbumSearch;
import com.spotify.app.Artist;
import com.spotify.app.Genre;
import com.spotify.app.Playlist;
import com.spotify.app.PlaylistSearch;
import com.spotify.app.Song;
import com.spotify.app.User;
import com.spotify.ui.Content;
import com.spotify.ui.ContentDraggable;
import com.spotify.ui.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class TitleBarContentSearch extends ContentDraggable {
    private JTextField searchBar;

    public TitleBarContentSearch(Window frame, User user, ArrayList<Account> accounts) {
        super(frame,user);
        int height = 40;
        int width = 260;

        searchBar = new JTextField();
        searchBar.setBounds(
            super.getNextButton().getX() + super.getNextButton().getWidth() + 30,
            (this.getHeight() - height) / 2, width, height
        );
        searchBar.setText("Search Here");
        searchBar.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        searchBar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                if(searchBar.getText().equals("Search Here")) {
                    searchBar.setText("");
                } else {
                    searchBar.selectAll();
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if(searchBar.getText().trim().equals("") || searchBar.getText().equals("Search Here")) {
                    searchBar.setText("Search Here");
                }
            }
        });
        searchBar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("searchBar.getText() = " + searchBar.getText());
                if(searchBar.getText().equals("SHOW ALL")) {
                    showAll(accounts, frame, user);
                } else {
                    user.search(accounts, searchBar.getText(), frame, e.getKeyChar() == '\n', user);
                }
            }
        });

        this.add(searchBar);
    }

    public void showAll(ArrayList<Account> accounts, Window frame, User CurrentUser) {
//        frame.getContentPanel().remove(frame.getContentPanel().getContent());
        frame.remove(frame.getContentPanel());

        ArrayList<Song> searchedSong = new ArrayList<>();
        ArrayList<Artist> searchedArtist = new ArrayList<>();
        ArrayList<User> searchedUser = new ArrayList<>();
        ArrayList<Genre> searchedGenre = new ArrayList<>();
        ArrayList<PlaylistSearch> searchedPlaylist = new ArrayList<>();
        ArrayList<AlbumSearch> searchedAlbum = new ArrayList<>();

        Admin admin = (Admin)accounts.get(0);
        searchedSong.addAll(admin.getSongs());
        searchedArtist.addAll(admin.getArtists());
        searchedGenre.addAll(admin.getGenres());
        for(Account account : accounts) {
            if(account instanceof Admin && ((Admin)account).getPlaylists().size() > 0) {
                for(Playlist playlist : ((Admin)account).getPlaylists()) {
                    searchedPlaylist.add(new PlaylistSearch(playlist, account));
                }
            }
            if(account instanceof User) {
                searchedUser.add((User)account);
                if(((User)account).getPlaylists().size() > 0) {
                    for(Playlist playlist : ((User)account).getPlaylists()) {
                        searchedPlaylist.add(new PlaylistSearch(playlist, account));
                    }
                }
            }
        }
        for(Artist artist : ((Admin) accounts.get(0)).getArtists()) {
            for(Playlist playlist : artist.getAlbum()) {
                searchedAlbum.add(new AlbumSearch(playlist, artist));
            }
        }

        ContentSearch contentSearch = new ContentSearch(searchedSong, searchedArtist, searchedUser, searchedGenre,
        searchedPlaylist, searchedAlbum, frame, CurrentUser);

        frame.setContentPanel(new Content(contentSearch));
        frame.add(frame.getContentPanel());

        frame.repaint();
        frame.revalidate();
        frame.invalidate();

//        frame.getContentPanel().setContent(new Content(contentSearch));
//        frame.getContentPanel().add(frame.getContentPanel().getContent());
//        frame.getContentPanel().repaint();
//        frame.getContentPanel().revalidate();
//        frame.getContentPanel().invalidate();
    }

    public JTextField getSearchBar() {
        return searchBar;
    }

    public void setSearchBar(JTextField searchBar) {
        this.searchBar = searchBar;
    }
}
