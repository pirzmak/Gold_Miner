package com.example.ja.goldminer;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;


/**
 * Glowny panel z gra. Tworzenie gry uaktualnianie
 * ekranu, punktacji, stanu gry.
 * Created by pirzmowski on 05.08.161.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final double SCALE = 1.5;
    public static final int HEiGHT = 900;
    public static final int WIDTH = 720;
    private MainThread thread;
    private Map map;
    private Miner miner;
    private Hand hand;
    private ArrayList<Rock> rocks;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder , int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public void  surfaceCreated(SurfaceHolder holder){

        createMap(1);
        miner = new Miner(map.getMinerX(),(int)(map.getMinerY()/SCALE) - 80,(int)(80*SCALE),(int)(80*SCALE),
                BitmapFactory.decodeResource(getResources(),R.drawable.miner),3);
        hand = new Hand(map.getMinerX() + miner.getWidth()/2,map.getMinerY() - 28,(int)(20*SCALE),(int)(15*SCALE),
                BitmapFactory.decodeResource(getResources(),R.drawable.hand),3);

        thread.setRunning(true);
        thread.start();

        setFocusable(true);
    }

    private void createMap(int rockNum)
    {
        map = new Map((int)((WIDTH/2)/SCALE),220,BitmapFactory.decodeResource(getResources(),R.drawable.bg));
        rocks = new ArrayList<>();
        rocks.add(new Rock(240,500,BitmapFactory.decodeResource(getResources(),R.drawable.mrock),Rock.Type.MROCK));
        rocks.add(new Rock(340,550,BitmapFactory.decodeResource(getResources(),R.drawable.srock),Rock.Type.SROCK));
        rocks.add(new Rock(180,370,BitmapFactory.decodeResource(getResources(),R.drawable.brock),Rock.Type.BROCK));
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(hand.getStatus()==Hand.Status.STOPED)
                hand.down();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update(){
        hand.update();
        for(int i=0; i<rocks.size(); ++i)
        {
            rocks.get(i).update(hand.getX(),hand.getY());

            if(collision(rocks.get(i),hand)&&hand.getStatus() == Hand.Status.DOWN){
                rocks.get(i).setCatched(true);
                hand.up(rocks.get(i).getWeight());
            }

            if(rocks.get(i).getY()<=map.getMinerY() - 28)
                rocks.remove(i);
        }
        miner.update();
    }

    public boolean collision(GameObject a, GameObject b){

        if(Rect.intersects(a.getRectangle(),b.getRectangle()))return true;
        return false;

    }


    @Override
    public void draw(Canvas canvas) {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEiGHT*1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            map.draw(canvas);
            miner.draw(canvas);
            hand.draw(canvas);
            for(int i=0; i<rocks.size(); ++i)
            {
                rocks.get(i).draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }

    public void reset()
    {
    }

}
