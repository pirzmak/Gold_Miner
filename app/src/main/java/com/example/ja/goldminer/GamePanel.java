package com.example.ja.goldminer;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Glowny panel z gra. Tworzenie gry uaktualnianie
 * ekranu, punktacji, stanu gry.
 * Created by pirzmowski on 05.08.161.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public double SCALE = getResources().getDisplayMetrics().density;
    public static final int HEiGHT = 600;
    public static final int WIDTH = 480;

    private MainThread thread;
    private Map map;
    private Miner miner;
    private Hand hand;
    private ArrayList<Rock> rocks;
    private MoneyProgress progres;
    private Time time;
    private Stat stat;
    private StatDAO statDAO;
    final Dialog dialog;


    public GamePanel(Context context,Stat stat){
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        this.stat = stat;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.level_end_dialog);
        statDAO = new StatDAO(context);

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

        createMap();
        miner = new Miner(map.getMinerX(),map.getMinerY(),(int)(80*SCALE),(int)(80*SCALE),
                BitmapFactory.decodeResource(getResources(),R.drawable.miner),3,1100);
        hand = new Hand(map.getMinerX(),map.getMinerY(),(int)(20*SCALE),(int)(15*SCALE),
                BitmapFactory.decodeResource(getResources(),R.drawable.hand),3,SCALE);

        thread.setRunning(true);
        thread.start();

        setFocusable(true);
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(!time.run())
            {

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("YOU LOSE");
                if(progres.win())
                {
                    Stat pom= statDAO.getNoteById(1);
                    pom.setNr(pom.getNr()+1);
                    text.setText("YOU WIN");
                    dialogButton.setBackground(getResources().getDrawable(R.drawable.next));
                }

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(progres.win())
                            reset(stat.getId()+1);
                        else
                            reset(stat.getId());
                    }
                });

                Button denyButton = (Button) dialog.findViewById(R.id.dialogButtonDeny);
                denyButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        ((Activity) getContext()).finish();

                    }
                });

                dialog.show();
            }
            if(hand.getStatus()==Hand.Status.STOPED&&time.run())
                        hand.down();
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void createMap()
    {
        map = new Map((int)((WIDTH/2)*SCALE),(int)(122*SCALE),BitmapFactory.decodeResource(getResources(),R.drawable.bg));
        rocks = new ArrayList<>();

        int type;
        int tmpx;
        int tmpy;
        int j=0;
        int u;

        for(int i=0;i<stat.getNr();i++)
        {
            type = Integer.parseInt(stat.getRocks().substring(j, ++j));
            j++;

            u=j;
            while(stat.getRocks().charAt(u)!='/')
                u++;
            tmpx = Integer.parseInt(stat.getRocks().substring(j, u++));

            j=u;
            while(stat.getRocks().charAt(u)!='/')
                u++;
            tmpy = Integer.parseInt(stat.getRocks().substring(j, u++));

            j=u;

            whatRock(type,(int)(SCALE*tmpx),(int)(SCALE*tmpy));
        }

        progres = new MoneyProgress((int)(430*SCALE),(int)(6.5*SCALE),(int)(30*SCALE),(int)(30*SCALE),500,BitmapFactory.decodeResource(getResources(),R.drawable.money));
        time = new Time((int)(40*SCALE),(int)(66.7*SCALE),(int)(30*SCALE),(int)(33*SCALE),BitmapFactory.decodeResource(getResources(),R.drawable.time),5);

    }

    public void whatRock(int n,int x,int y) {
        switch (n) {
            case 0:
                rocks.add(new Rock(x,y,BitmapFactory.decodeResource(getResources(),R.drawable.srock),Rock.Type.SROCK));
                break;
            case 1:
                rocks.add(new Rock(x,y,BitmapFactory.decodeResource(getResources(),R.drawable.mrock),Rock.Type.MROCK));
                break;
            case 2:
                rocks.add(new Rock(x,y,BitmapFactory.decodeResource(getResources(),R.drawable.brock),Rock.Type.BROCK));
                break;
            case 3:
                rocks.add(new Rock(x,y,BitmapFactory.decodeResource(getResources(),R.drawable.sgold),Rock.Type.SGOLD));
                break;
            case 4:
                rocks.add(new Rock(x,y,BitmapFactory.decodeResource(getResources(),R.drawable.mgold),Rock.Type.MGOLD));
                break;
            case 5:
                rocks.add(new Rock(x,y,BitmapFactory.decodeResource(getResources(),R.drawable.bgold),Rock.Type.BGOLD));
                break;
            case 6:
                rocks.add(new Rock(x,y,BitmapFactory.decodeResource(getResources(),R.drawable.diamond),Rock.Type.DIAMOND));
                break;
        }
    }

    public void update(){

        if(time.run()) {
            hand.update();
            for (int i = 0; i < rocks.size(); ++i) {
                rocks.get(i).update(hand.getX(), hand.getY());

                if (collision(rocks.get(i), hand) && hand.getStatus() == Hand.Status.DOWN) {
                    rocks.get(i).setCatched(true);
                    hand.up(rocks.get(i).getWeight());
                }

                if (rocks.get(i).isCatched() && hand.getY() <= map.getMinerY()) {
                    progres.update(rocks.get(i).getCost());
                    rocks.remove(i);
                    hand.stop();
                }
            }
            miner.update();
            time.update();
        }
    }

    public boolean collision(GameObject a, GameObject b){
        return Rect.intersects(a.getRectangle(),b.getRectangle());
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final float scaleFactorX = (float)(getWidth()/(WIDTH*SCALE));
        final float scaleFactorY = (float)(getHeight()/(HEiGHT*SCALE));

        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            map.draw(canvas);
            miner.draw(canvas);
            hand.draw(canvas);
            for(int i=0; i<rocks.size(); ++i)
                rocks.get(i).draw(canvas);

            progres.draw(canvas);
            time.draw(canvas);

            if(!time.run())getHolder().unlockCanvasAndPost(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    private void reset(int id){
        rocks.clear();
        time.reset();
        hand.stop();
        progres.reset();
        Log.d("Reset","tak");
        stat = statDAO.getNoteById(id);
        Log.d("Reset","tak2 "+ stat.getNr());
        createMap();
        dialog.dismiss();
    }
}
