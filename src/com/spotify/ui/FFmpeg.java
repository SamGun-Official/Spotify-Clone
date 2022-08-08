package com.spotify.ui;

import com.spotify.ui.thread.InputHandler;
import java.io.IOException;

public final class FFmpeg {
    public FFmpeg(String origpath, String origname, String filepath, String filename) throws IOException {
        start(origpath, origname, filepath, filename);
    }

    public void start(String origpath, String origname, String filepath, String filename) throws IOException {
        String[] ffmpeg = new String[] {
            "./src/com/spotify/ffmpeg/bin/ffmpeg", "-i", ("" + origpath + "\\" + origname),
            "-sample_fmt", "s16", "-ar", "44100", ("" + filepath + "\\" + filename)
        };
        Process process = Runtime.getRuntime().exec(ffmpeg);

        InputHandler errorHandler = new InputHandler(process.getErrorStream(), "Error Stream");
        errorHandler.start();

        InputHandler inputHandler = new InputHandler(process.getInputStream(), "Output Stream");
        inputHandler.start();

        try {
            process.waitFor();
        } catch(InterruptedException e) {
            throw new IOException("Process Interrupted.");
        }

        System.out.println("Exit Code: " + process.exitValue());
    }
}
