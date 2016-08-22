package com.example.ja.goldminer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;

/**
 * Created by Ja on 2016-08-13.
 */
public class Time extends GameObject{

    private long delay;
    private long startTime;
    private Paint paint;
    private Animation animation;
    Bitmap[] image;

    public Time(int x, int y, int w, int h, Bitmap res, int numFrames) {
        super(x, y, w, h);

        image= new Bitmap[numFrames];

        for(int i=0;i<numFrames;i++)
        {
            image[i] = Bitmap.createBitmap(res, i*width, 0, width, height);
        }

        delay=60;
        startTime = System.nanoTime();

        animation = new Animation(image,delay);

        paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.parseColor("#49DF23"));
    }

    public void update(){
        animation.update();
    }

    public void draw(Canvas canvas){
        int t = (int) (delay - (System.nanoTime()-startTime)/1000000000);
        Bitmap himage = image[0];
        if(t<45)
            himage=image[1];
        if(t<30)
            himage=image[2];
        if(t<15)
            himage=image[3];

        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(Color.parseColor("#49DF23"));
        if(run()){
            canvas.drawText(String.valueOf(t),x+width/2 -15 ,y+height + 25,paint);
            canvas.drawBitmap(himage,x,y,null);
        }
        else {
            canvas.drawText("0",x+width/2 -10,y+height + 25,paint);
            canvas.drawBitmap(image[4],x,y,null);
        }
    }

    public boolean run()
    {
        return ((System.nanoTime()-startTime)/1000000000)<delay;
    }

    public void reset(){
        startTime = System.nanoTime();

    }
}
