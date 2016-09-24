package com.example.ja.goldminer;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * ble ble
 * Created by Ja on 2016-08-05.
 */
public class Miner extends GameObject{

    private Animation animation;

    public Miner(int x, int y, int width, int height, Bitmap res, int numFrames, int delay) {
        super(x-width/2, y - height, width, height);

        Bitmap[] image = new Bitmap[numFrames];

        for(int i=0;i<numFrames;i++)
        {
            image[i] = Bitmap.createBitmap(res, i*width, 0, width, height);
        }

        animation = new Animation(image,delay);

    }

    public void update(){
        animation.update();
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }
}
