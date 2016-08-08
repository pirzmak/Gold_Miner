package com.example.ja.goldminer;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Ja on 2016-08-05.
 */
public class Map {

    private int x;
    private int y;
    private int minerY;
    private int minerX;
    private Bitmap background;

    public Map(int minerX, int minerY, Bitmap background) {
        this.minerY = minerY;
        this.minerX = minerX;
        x=y=0;
        this.background = background;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(background,x,y,null);
    }

    public int getMinerY() {
        return minerY;
    }

    public int getMinerX() {
        return minerX;
    }
}
