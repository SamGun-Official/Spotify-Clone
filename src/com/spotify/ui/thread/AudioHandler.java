package com.spotify.ui.thread;

import com.spotify.ui.Player;
import com.spotify.ui.Window;
import com.spotify.ui.playlist.PlaylistUI;
import com.spotify.ui.playlist.TrackRow;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

public class AudioHandler implements com.spotify.app.Utility {
    protected AudioFormat audioFormat;
    protected AudioInputStream audioStream;
    protected Clip musicClip;
    protected FloatControl volumeControl;

    protected boolean isDragged = false;
    protected boolean isPausing = false;
    protected boolean isPlaying = false;
    protected boolean isShuffle = false;

    protected int loopingID;

    protected long currentClipPosition;
    protected long maximumClipDuration;
    protected long totalFrames;

    protected JButton playBtn;
    protected JLabel currentPosition;
    protected JLabel maximumDuration;
    protected JProgressBar progressBar;
    protected JSlider progressBarSlider;

    protected JButton muteBtn;
    protected JProgressBar volumeBar;
    protected JSlider volumeBarSlider;

    public AudioHandler(Player audioPlayer, Window windowApp) {
        this.isPausing = true;
        this.isPlaying = false;
        this.isShuffle = audioPlayer.isShuffle();

        this.loopingID = audioPlayer.getLoopingID();

        this.playBtn = audioPlayer.getPlayBtn();
        this.currentPosition = audioPlayer.getCurrentPosition();
        this.maximumDuration = audioPlayer.getMaximumDuration();
        this.progressBar = audioPlayer.getProgressBar();
        this.progressBarSlider = audioPlayer.getProgressBarSlider();

        this.muteBtn = audioPlayer.getMuteBtn();
        this.volumeBar = audioPlayer.getVolumeBar();
        this.volumeBarSlider = audioPlayer.getVolumeBarSlider();

        this.loadMusic(audioPlayer.getFilepath(), windowApp);
    }

    private void loadMusic(String filepath, Window windowApp) {
        try {
            File audioFile = new File(filepath);

            if(audioFile.exists() == true) {
                audioStream = AudioSystem.getAudioInputStream(audioFile);
                audioFormat = audioStream.getFormat();
                totalFrames = audioStream.getFrameLength();

                musicClip = AudioSystem.getClip();
                musicClip.open(audioStream);
                musicClip.addLineListener((final LineEvent event) -> {
                    if(event.getType().equals(Type.STOP) && musicClip.getMicrosecondPosition() >=
                    musicClip.getMicrosecondLength() && loopingID != 2) {
                        if(windowApp.getContentPanel().getViewport().getView() instanceof PlaylistUI) {
                            PlaylistUI tempUI = (PlaylistUI)windowApp.getContentPanel().getViewport().getView();

                            if((windowApp.activeTrackNumber < windowApp.currentPlaylistTrackCount && isShuffle == false)
                            || (windowApp.countOnShuffle < windowApp.currentPlaylistTrackCount && isShuffle == true)) {
                                TrackRow tempRow;

                                if(isShuffle == true) {
                                    tempRow = (TrackRow)tempUI.getTrackTableList().getComponent(
                                        windowApp.shuffleOnDemand.get(windowApp.countOnShuffle) - 1
                                    );
                                } else {
                                    tempRow = (TrackRow)tempUI.getTrackTableList().getComponent(
                                        windowApp.activeTrackNumber
                                    );
                                }

                                tempRow.getTrackNumberColumn().setVisible(false);
                                tempRow.getTrackNumberColumn().revalidate();
                                tempRow.getTrackNumberColumn().repaint();

                                tempRow.getButtonHolder().setVisible(true);
                                tempRow.getButtonHolder().revalidate();
                                tempRow.getButtonHolder().repaint();

                                tempRow.hoveringPlayButton = true;
                                tempRow.isPlaying = true;

                                tempRow.playButtonAction(windowApp);
                            } else {
                                TrackRow tempRow;

                                if(isShuffle == true && loopingID == 1) {
                                    tempRow = (TrackRow)tempUI.getTrackTableList().getComponent(
                                        new Random().nextInt(14)
                                    );
                                } else {
                                    tempRow = (TrackRow)tempUI.getTrackTableList().getComponent(0);
                                }

                                tempRow.getTrackNumberColumn().setVisible(true);
                                tempRow.getTrackNumberColumn().revalidate();
                                tempRow.getTrackNumberColumn().repaint();

                                tempRow.getButtonHolder().setVisible(false);
                                tempRow.getButtonHolder().revalidate();
                                tempRow.getButtonHolder().repaint();

                                tempRow.hoveringPlayButton = false;
                                tempRow.isPlaying = false;

                                windowApp.countOnShuffle = 0;
                                tempRow.playButtonAction(windowApp);

                                if(loopingID == 0) {
                                    tempRow.playButtonAction(windowApp);
                                }
                            }
                        }
                    }
                });

                volumeControl = (FloatControl)musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(windowApp.audioVolume);

                currentClipPosition = 0;
                maximumClipDuration = musicClip.getMicrosecondLength();

                currentPosition.setText(getDuration((int)currentClipPosition));
                maximumDuration.setText(getDuration((int)maximumClipDuration / 1000000));

                progressBar.setMinimum(0);
                progressBar.setMaximum((int)(musicClip.getMicrosecondLength() / 1000));
                progressBar.setValue(0);

                progressBarSlider.setMinimum(0);
                progressBarSlider.setMaximum((int)(musicClip.getMicrosecondLength() / 1000));
                progressBarSlider.setValue(0);
                progressBarSlider.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent me) {
                        isDragged = true;
                    }

                    @Override
                    public void mouseReleased(MouseEvent me) {
                        isDragged = false;

                        progressBarSlider.setValue(progressBarSlider.getValue());
                        progressBar.setValue(progressBarSlider.getValue());

                        musicClip.setMicrosecondPosition((long)(progressBarSlider.getValue() * 1000));
                    }
                });
                progressBarSlider.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent me) {
                        progressBarSlider.setValue(progressBarSlider.getValue());
                        progressBar.setValue(progressBarSlider.getValue());
                        currentPosition.setText(
                            getDuration(progressBarSlider.getValue() / 1000)
                        );
                    }
                });

                volumeBar.setMinimum((int)volumeControl.getMinimum() * 100);
                volumeBar.setMaximum((int)0.0f * 100);
                volumeBar.setValue((int)windowApp.audioVolume * 100);

                volumeBarSlider.setMinimum((int)volumeControl.getMinimum() * 100);
                volumeBarSlider.setMaximum((int)0.0f * 100);
                volumeBarSlider.setValue((int)windowApp.audioVolume * 100);
                volumeBarSlider.addChangeListener((ChangeEvent ce) -> {
                    volumeControl.setValue(volumeBarSlider.getValue() / 100.0f);
                    windowApp.audioVolume = volumeControl.getValue();
                });
            }
        } catch(IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            throw new Error(e);
        }
    }

    public void startAudio() {
        currentClipPosition = musicClip.getMicrosecondPosition();
        isPlaying = true;
        isPausing = false;
        updateProgressBar();
        musicClip.start();
    }

    public void stopAudio() {
        currentClipPosition = musicClip.getMicrosecondPosition();
        isPlaying = false;
        isPausing = true;
        musicClip.stop();
    }

    private void updateProgressBar() {
        Timer playTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                int getMicrosecondPosition = (int)(musicClip.getMicrosecondPosition() / 1000);

                if(isDragged == false) {
                    progressBar.setValue(getMicrosecondPosition);
                    progressBarSlider.setValue(getMicrosecondPosition);
                    currentPosition.setText(getDuration(getMicrosecondPosition / 1000));
                }

                if(musicClip.getMicrosecondPosition() >= musicClip.getMicrosecondLength()) {
                    resetAll(false);

                    if(loopingID == 0 || loopingID == 1) {
                        isPlaying = false;
                        isPausing = true;

                        playBtn.setIcon(
                            scaleImageIcon("src/com/spotify/assets/icons/player/play_black_rounded.png", 32, 32)
                        );
                        playBtn.setName("Play_Button");
                        playBtn.setToolTipText("Play");

                        // Careful with this because timer may never returned.
                        playTimer.cancel();

                        // Need to stop audio right away.
                        stopAudio();

                        // If the playback ended at the end of the single item list
                        // then just close the clip in case want to play again.
                        // musicClip.close();
                    } else {
                        isPlaying = true;
                        isPausing = false;

                        // Also unnecessary action. Need to moderate but not ASAP.
                        playBtn.setIcon(
                            scaleImageIcon("src/com/spotify/assets/icons/player/pause_black_rounded.png", 32, 32)
                        );
                        playBtn.setName("Pause_Button");
                        playBtn.setToolTipText("Pause");

                        // Careful with this because timer may never returned.
                        playTimer.cancel();

                        // Despite Clip.LOOP_CONTINUOUSLY automatically starting a new thread,
                        // it is possible to just let the audio stop playing and do a restart.
                        startAudio();
                    }

                    // Make sure bug doesn't appear when user harshly dragging mouse.
                    // Possible unnecessary action because it is really a rare case.
                    resetAll(false);
                }

                if(isPlaying == false && isPausing == true) {
                    playTimer.cancel();
                }
            }
        };

        playTimer.scheduleAtFixedRate(timerTask, 0, 200);
    }

    public void resetAll(boolean cancelThread) {
        if(cancelThread == true) {
            isPlaying = false;
            isPausing = true;

            playBtn.setIcon(
                scaleImageIcon("src/com/spotify/assets/icons/player/play_black_rounded.png", 32, 32)
            );
            playBtn.setName("Play_Button");
            playBtn.setToolTipText("Play");

            stopAudio();

            try {
                Thread.sleep(100);

                if(musicClip.isOpen() == true) {
                    try {
                        musicClip.close();
                        audioStream.close();
                    } catch(IOException ex) {
                        System.out.println(ex);
                    }
                }
            } catch(InterruptedException ex) {
                System.out.println(ex);
            }
        }

        if(loopingID == 2) {
            progressBar.setValue(0);
            progressBarSlider.setValue(0);
            currentPosition.setText("0:00");
            musicClip.setMicrosecondPosition(0);
        }
    }

    public void startOver() {
        progressBar.setValue(0);
        progressBarSlider.setValue(0);
        currentPosition.setText("0:00");
        musicClip.setMicrosecondPosition(0);
    }

    private String getDuration(int secondsElapsed) {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;

        return String.format("%d:%02d", minutes, seconds);
    }

    public Clip getMusicClip() {
        return musicClip;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public boolean isPausing() {
        return isPausing;
    }

    public void setPausing(boolean isPausing) {
        this.isPausing = isPausing;
    }

    public int getLoopingID() {
        return loopingID;
    }

    public void setLoopingID(int loopingID) {
        this.loopingID = loopingID;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setShuffle(boolean isShuffle) {
        this.isShuffle = isShuffle;
    }
}
