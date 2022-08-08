package com.spotify.app;

import com.spotify.ui.Content;
import com.spotify.ui.search.*;
import com.spotify.ui.Threshold;
import com.spotify.ui.Window;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

class NoResultFoundPanel extends JPanel {
    public NoResultFoundPanel(String text) {
        super();

        this.setPreferredSize(new Dimension(
            Threshold.contentArea.width, Threshold.contentArea.height
        ));
        this.setBackground(new Color(18, 18, 18));
        this.setSize(this.getPreferredSize());
        this.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        this.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        this.setLayout(null);

        int middleY = (int)this.getPreferredSize().getHeight() / 2;

        JLabel title = new JLabel("No results found for \"" + text + "\" ");
        title.setBounds(0, middleY - 50, (int)this.getPreferredSize().getWidth(), 50);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Gotham", Font.PLAIN, 26));
        title.setForeground(Color.white);

        JLabel subtitle = new JLabel("Please make sure your words are spelled correctly or use "
        + "less or different keywords");
        subtitle.setBounds(0, middleY, (int)this.getPreferredSize().getWidth(), 50);
        subtitle.setHorizontalTextPosition(JLabel.CENTER);
        subtitle.setHorizontalAlignment(JLabel.CENTER);
        subtitle.setFont(new Font("Gotham", Font.PLAIN, 22));
        subtitle.setForeground(new Color(200, 200, 200));

        this.add(title);
        this.add(subtitle);
    }
}

public class User extends Account implements Serializable {
    private String email;
    private String firstName;
    private String lastName;
    private ArrayList<User> followerList;
    private ArrayList<User> followingList;
    private Image profilePicture;
    private Playlist queueList;
    private Playlist topTracks;
    private Playlist likedSongs;
    private ArrayList<Playlist> playlists;
    private String securityQuestions;
    private String securityAnswer;
    private ArrayList<ContentSearch> recentSearches;
    private ArrayList<Playlist> recentlyPlayed;
    private boolean premium, response;
    private boolean requestVerification;

    public User(String username, String password, String firstName, String lastName, String securityQuestions,
    String securityAnswer, String email) {
        super(username, password);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.followerList = new ArrayList<>();
        this.followingList = new ArrayList<>();
        this.profilePicture = null;
        this.queueList = new Playlist(
            new Color(18, 18, 18), new ArrayList<>(), "Queue", "Queue", "-",
            (firstName + " " + lastName), "01/01/1970", "default", -1, 0, 0
        );
        this.topTracks = new Playlist(
            new Color(0, 0, 0), new ArrayList<>(), "NaN", "NaN", "-", "NaN", "01/01/1970", "default", -1, -1, -1
        );
        this.likedSongs = new Playlist(
            new Color(80, 56, 160), new ArrayList<>(), "Playlist", "Liked Songs", "-",
            (firstName + " " + lastName), "01/01/1970", "./src/com/spotify/assets/icons/likedSongs_cover.png", -1, 0, 0
        );
        this.playlists = new ArrayList<>();
        this.securityQuestions = securityQuestions;
        this.securityAnswer = securityAnswer;
        this.recentSearches = new ArrayList<>();
        this.recentlyPlayed = new ArrayList<>();
        this.premium = false;
        this.requestVerification = false;
        this.response = false;
    }

    public void search(ArrayList<Account> accounts, String strSearch, Window frame, boolean pressedEnter,
    User CurrentUser) {
//        frame.getContentPanel().remove(frame.getContentPanel().getContent());

        ArrayList<Song> searchedSong = new ArrayList<>();
        ArrayList<Artist> searchedArtist = new ArrayList<>();
        ArrayList<User> searchedUser = new ArrayList<>();
        ArrayList<Genre> searchedGenre = new ArrayList<>();
        ArrayList<PlaylistSearch> searchedPlaylist = new ArrayList<>();
        ArrayList<AlbumSearch> searchedAlbum = new ArrayList<>();

        Admin admin = (Admin)accounts.get(0);

        for(Song song : admin.getSongs()) {
            if(song.getTitle().toLowerCase().contains(strSearch.toLowerCase()) ||
            song.getGenre().getName().toLowerCase().contains(strSearch.toLowerCase())) {
                searchedSong.add(song);
            }
        }

        for(Artist artist : admin.getArtists()) {
            if(artist.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                searchedArtist.add(artist);
            }
        }

        for(Genre genre : admin.getGenres()) {
            if(genre.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                searchedGenre.add(genre);
            }
        }

        for(Account account : accounts) {
            if(account instanceof Admin && ((Admin)account).getPlaylists().size() > 0) {
                for(Playlist playlist : ((Admin)account).getPlaylists()) {
                    if(playlist.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                        searchedPlaylist.add(new PlaylistSearch(playlist, account));
                    }
                }
            }

            if(account instanceof User && (account.getUsername().toLowerCase().contains(strSearch.toLowerCase()) ||
            (((User)account).getFirstName() + " " + ((User)account).getLastName()).toLowerCase().contains(strSearch.
            toLowerCase()))) {
                searchedUser.add((User)account);

                if(((User)account).getPlaylists().size() > 0) {
                    for(Playlist playlist : ((User)account).getPlaylists()) {
                        if(playlist.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            searchedPlaylist.add(new PlaylistSearch(playlist, account));
                        }
                    }
                }
            }
        }

        for(Artist artist : ((Admin)accounts.get(0)).getArtists()) {
            for(Playlist playlist : artist.getAlbum()) {
                if(playlist.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                    searchedAlbum.add(new AlbumSearch(playlist, artist));
                }
            }
        }

        JPanel contentSearch;

        if(searchedSong.isEmpty() && searchedArtist.isEmpty() && searchedUser.isEmpty() &&
        searchedGenre.isEmpty() && searchedPlaylist.isEmpty() && searchedAlbum.isEmpty()) {
            contentSearch = new NoResultFoundPanel(strSearch);
        } else {
            contentSearch = new ContentSearch(searchedSong, searchedArtist, searchedUser, searchedGenre,
            searchedPlaylist, searchedAlbum, frame, CurrentUser);

            if(pressedEnter) {
                this.recentSearches.add((ContentSearch)contentSearch);
            }
        }

        frame.getLayeredPane().remove(frame.getContentPanel());
        frame.setContentPanel(new Content(contentSearch));
        frame.getLayeredPane().add(frame.getContentPanel());
        frame.repaint();
        frame.revalidate();
        frame.invalidate();
    }

    public boolean checkFollowedUser(String username) {
        for(int i = 0; i < followingList.size(); i++) {
            if(followingList.get(i).getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public void requestMembershipVerification() {
        if(premium == false) {
            requestVerification = true;
        }
    }

    public void verificationMembershipResponse() {
        if(requestVerification == true) {
            if(response == true) {
                requestVerification = false;
                premium = true;
            } else {
                requestVerification = false;
            }
        }
    }

    public Playlist getPlaylistIndex(int i){
        return playlists.get(i);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void Following(User otherUser) {
        followingList.add(otherUser);
    }

    public void Follower(User otherUser) {
        followerList.add(otherUser);
    }

    public void removeFollowing(User otherUser) {
        followingList.remove(otherUser);
    }

    public void removeFollower(User otherUser) {
        followerList.remove(otherUser);
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public boolean isRequestVerification() {
        return requestVerification;
    }

    public void setRequestVerification(boolean requestVerification) {
        this.requestVerification = requestVerification;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<User> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(ArrayList<User> followerList) {
        this.followerList = followerList;
    }

    public ArrayList<User> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(ArrayList<User> followingList) {
        this.followingList = followingList;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Playlist getQueueList() {
        return queueList;
    }

    public void setQueueList(Playlist queueList) {
        this.queueList = queueList;
    }

    public Playlist getTopTracks() {
        return topTracks;
    }

    public void setTopTracks(Playlist topTracks) {
        this.topTracks = topTracks;
    }

    public Playlist getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(Playlist likedSongs) {
        this.likedSongs = likedSongs;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public int getFollowerSize(){
        return followerList.size();
    }

    public int getFollowingSize(){
        return followingList.size();
    }

    public int getPlaylistsSize(){
        return playlists.size();
    }

    public String getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(String securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public ArrayList<ContentSearch> getRecentSearches() {
        return recentSearches;
    }

    public void setRecentSearches(ArrayList<ContentSearch> recentSearches) {
        this.recentSearches = recentSearches;
    }

    public ArrayList<Playlist> getRecentlyPlayed() {
        return recentlyPlayed;
    }

    public void setRecentlyPlayed(ArrayList<Playlist> recentlyPlayed) {
        this.recentlyPlayed = recentlyPlayed;
    }

    @Override
    public String toString() {
        return super.getUsername() + " - " + super.getPassword() + " - " + getFirstName() + " " + getLastName();
    }
}
