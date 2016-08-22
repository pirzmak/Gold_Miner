package com.example.ja.goldminer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.Log;

/**
 * bel ble
 * Created by Ja on 2016-08-05.
 */
public class Hand extends GameObject {

    private static  final int R=40;
    private double v;
    private double alpha;
    private double scale;
    private double a;
    private double b;
    Paint paint;
    Paint paint2;
    private int t;
    private int startX;
    private int startY;
    private Status status;
    Bitmap[] image;

    public enum Status {STOPED,DOWN,UP,UPBIG}

    public Hand(int x, int y, int w, int h, Bitmap res, int numFrames, double scale) {
        super(x - w/2, y + R, w, h);

        image = new Bitmap[numFrames];

        for(int i=0;i<numFrames;i++)
            image[i] = Bitmap.createBitmap(res, i*width, 0, width, height);

        startX = x;
        startY = y;
        v=0.05;
        t=0;

        setPaint();

        status = Status.STOPED;
        this.scale=scale;
    }

    private void setPaint()
    {
        paint = new Paint();
        paint.setStrokeWidth(2f);
        paint.setColor(Color.parseColor("#D0B325"));

        paint2 = new Paint();
        paint2.setStrokeWidth(2f);
        paint2.setColor(Color.BLACK);
        paint2.setPathEffect(new DashPathEffect(new float[] {2,5}, 0));
    }

    public void update(){

        if(status==Status.STOPED)
        {
            x=(int)(R*Math.cos(v*t)+startX);
            y=(int)(R*Math.sin(v*t)+startY);
            if(y<startY)
                y=(int)(-R*Math.sin(v*t)+startY);

            t++;
        }
        else if(status==Status.DOWN)
        {
            y+=v*Math.sin(alpha);
            x=(int)((y-b)/a);

            if(x>=GamePanel.WIDTH*scale || y>=GamePanel.HEiGHT*scale|| x<=0)
                up(0);
        }
        else if(status==Status.UP)
        {
            y+=v*Math.sin(alpha);
            x=(int)((y-b)/a);
            if(y<=startY&&(x+20>startX&&x-20<startX))
            {
                status=Status.STOPED;
                v=0.05;
            }

        }
    }

    public void draw(Canvas canvas){
        int hx,hy;
        Bitmap himage;
        hx=x - width / 2;
        hy=y;

        himage=image[1];
        if(startX-x>15) {
            himage=image[0];
            hx= x - width + 10;
            hy= y - 5;
        }
        else if(startX-x<-15){
            himage=image[2];
            hx= x - 10;
            hy= y - 5;
        }

        canvas.drawBitmap(himage, hx, hy, null);
        canvas.drawLine(startX, startY, x, y, paint);
        canvas.drawLine(startX, startY, x, y, paint2);
    }

    public void down(){
        status = Status.DOWN;
        alpha=alpha(x,y);
        t=0;
        v=8;
    }

    public void stop()
    {
        status = Status.STOPED;
        v=0.05;
    }

    public Status getStatus() {
        return status;
    }

    public double alpha(double x, double y){
        a=(y-startY)/(x-startX);
        b=startY - startX*a;
        return Math.acos(-(startX - x)/
                Math.sqrt((startX - x)*(startX - x)+(startY - y)*(startY - y)));
    }

    public void up(double get){
        status = Status.UP;
        alpha=alpha(x,y);
        v=-10 + get;
    }
}
