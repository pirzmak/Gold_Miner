package com.example.ja.goldminer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

/**
 * bel ble
 * Created by Ja on 2016-08-05.
 */
public class Hand extends GameObject {

    private static  final int R=40;
    private double v;
    private double a;
    Paint paint;
    Paint paint2;
    private int t;
    private int startX;
    private int startY;
    private Status status;
    Bitmap[] image;

    public enum Status {STOPED,DOWN,UP}

    public Hand(int x, int y, int w, int h, Bitmap res, int numFrames) {
        super(x - w/2, y + R, w, h);

        image = new Bitmap[numFrames];
        for(int i=0;i<numFrames;i++)
        {
            image[i] = Bitmap.createBitmap(res, i*width, 0, width, height);
        }

        startX = x;
        startY = y;
        v=0.05;
        t=0;

        setPaint();

        status = Status.STOPED;
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

            System.out.println(x + " " + startX);
            t++;
        }
        else if(status==Status.DOWN)
        {
            x+=v*Math.cos(a);
            y+=v*Math.sin(a);

            if(x>=GamePanel.WIDTH || y>=GamePanel.HEiGHT || x<=0)
                up(0);
        }
        else if(status==Status.UP)
        {
            x+=v*Math.cos(a);
            y+=v*Math.sin(a);
            if(y<=startY)
            {
                status=Status.STOPED;
                v=0.05;
            }
        }
    }

    public void draw(Canvas canvas){
        int
        if(startX-x>10)
            canvas.drawBitmap(image[1],x,y,null);
        else if(startX-x<-10)
            canvas.drawBitmap(image[0],x,y,null);
        else
            canvas.drawBitmap(image[2],x,y,null);

        canvas.drawLine(startX, startY, x + width/2, y, paint);
        canvas.drawLine(startX, startY, x + width/2, y, paint2);
    }

    public void down(){
       if(status != Status.DOWN)
           status = Status.DOWN;
        a=alpha(x,y);
        t=0;
        v=8;
    }

    public Status getStatus() {
        return status;
    }

    public double alpha(double x, double y){
        return Math.acos(-(startX - x)/
                Math.sqrt((startX - x)*(startX - x)+(startY - y)*(startY - y)));
    }

    public void up(int get){
        status = Status.UP;
        a=alpha(x,y);
        v=-8 + get;
    }
}
