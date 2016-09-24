package com.example.ja.goldminer;

import android.graphics.Bitmap;

/**
 * Animacje
 * Created by Ja on 2016-08-05.
 */
public class Animation {
    private Bitmap[] frames;
    private int actualFrame;
    private int znak;
    private long delay;
    private long startTime;

    public Animation(Bitmap[] frames, long delay) {
        this.frames = frames;
        this.delay = delay;
        znak=1;
        actualFrame = 0;
        startTime = System.nanoTime();
    }

    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;

        if(elapsed>delay)
        {
            actualFrame+=znak;
            startTime = System.nanoTime();
        }
        if(actualFrame>=frames.length)
        {
            znak=-1;
            actualFrame=actualFrame-1;
        }
        if(actualFrame<=0)
        {
            znak=1;
            actualFrame=0;
        }
    }

    public Bitmap getImage(){
        return frames[actualFrame];
    }

    public int getActualFrame() {
        return actualFrame;
    }

    public void setActualFrame(int actualFrame) {
        this.actualFrame = actualFrame;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

}
