package com.spotify.ui;

import com.spotify.app.Song;
import com.spotify.ui.playlist.PlaylistUI;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayButtonEvent implements com.spotify.app.Utility {
    protected Player audioPlayer_Ref;
    protected JButton playButtonPlayer_Ref;
    protected PlaylistUI playlistUI_Ref;
    protected JButton playButtonABR_Ref;
    protected JPanel trackTableList_Ref;

    public PlayButtonEvent(Window windowApp) {
        audioPlayer_Ref = windowApp.audioPlayer;
        playButtonPlayer_Ref = windowApp.audioPlayer.playBtn;

        if(windowApp.getContentPanel().getViewport().getView() instanceof PlaylistUI) {
            playlistUI_Ref = (PlaylistUI)windowApp.getContentPanel().getViewport().getView();
            playButtonABR_Ref = playlistUI_Ref.getPlayAllBtn();
            trackTableList_Ref = playlistUI_Ref.getTrackTableList();
        }
    }

    public final void changePlayerIcon(Window windowApp) {
        if(playButtonPlayer_Ref.getName().equalsIgnoreCase("Play_Button")) {
            playButtonPlayer_Ref.setIcon(
                scaleImageIcon("src/com/spotify/assets/icons/player/pause_black_rounded.png", 32, 32)
            );
            playButtonPlayer_Ref.setName("Pause_Button");
            playButtonPlayer_Ref.setToolTipText("Pause");

            windowApp.audioPlayer.audioHandler.startAudio();
        } else if(playButtonPlayer_Ref.getName().equalsIgnoreCase("Pause_Button")) {
            playButtonPlayer_Ref.setIcon(
                scaleImageIcon("src/com/spotify/assets/icons/player/play_black_rounded.png", 32, 32)
            );
            playButtonPlayer_Ref.setName("Play_Button");
            playButtonPlayer_Ref.setToolTipText("Play");

            windowApp.audioPlayer.audioHandler.stopAudio();
        }
    }

    public final void changePlaylistIcon(Window windowApp) {
        if(playButtonABR_Ref.getName().equalsIgnoreCase("Play_Button")) {
            playButtonABR_Ref.setIcon(scaleImageIcon(
                "src/com/spotify/assets/icons/pause_white_rounded.png", 56, 56
            ));
            playButtonABR_Ref.setName("Pause_Button");
        } else if(playButtonABR_Ref.getName().equalsIgnoreCase("Pause_Button")) {
            playButtonABR_Ref.setIcon(scaleImageIcon(
                "src/com/spotify/assets/icons/play_white_rounded.png", 56, 56
            ));
            playButtonABR_Ref.setName("Play_Button");
        }
    }

    public void setPlayerPlayedSong(Window windowApp, String playlistName, int playlistID, int trackNumber, Song song) {
        if((windowApp.activePlaylistName.equals(playlistName) && windowApp.activeTrackNumber != trackNumber) ||
        !windowApp.activePlaylistName.equals(playlistName)) {
            windowApp.audioPlayer.currentSong = song;

            windowApp.remove(windowApp.audioPlayer.playedSong);

            windowApp.audioPlayer.initPlayedSong(playlistID);
            windowApp.audioPlayer.initNotFirstTime();

            windowApp.add(windowApp.audioPlayer.playedSong);

            windowApp.audioPlayer.playedSong.revalidate();
            windowApp.audioPlayer.playedSong.repaint();

            windowApp.revalidate();
            windowApp.repaint();

            windowApp.audioPlayer.setAudioHandler();
        }
    }
}
