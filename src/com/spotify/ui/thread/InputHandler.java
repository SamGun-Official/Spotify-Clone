package com.spotify.ui.thread;

import java.io.InputStream;

public class InputHandler extends Thread {
    private final InputStream inputStream;

    public InputHandler(InputStream inputStream, String name) {
        super(name);
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try {
            int c;
            while((c = inputStream.read()) != -1) {
                System.out.write(c);
            }
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
}
