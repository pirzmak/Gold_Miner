package com.example.ja.goldminer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Ja on 2016-08-12.
 */
public class MoneyProgress extends GameObject{

    private Bitmap money;
    private int goal;
    private int actualyMoney;
    private Paint paint;
    private int progres;

    public MoneyProgress(int x, int y, int width, int height, int goal, Bitmap money) {
        super(x, y, width, height);
        this.goal = goal;
        this.money = money;
        actualyMoney = 0;
        progres = 0;

        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.parseColor("#49DF23"));
        paint.setTextSize(30);
        paint.setFakeBoldText(true);
    }

    public void update(int m) {
        actualyMoney+=m;
        progres = x;
        if(m<goal)
            progres = x*(actualyMoney/goal);

    }

    public void reset(){
        actualyMoney=0;
        progres=0;
    }

    public boolean win(){
        return actualyMoney>=goal;
    }

    public void draw(Canvas canvas) {

        canvas.drawLine(10, y+height/2, progres+10, y+height/2, paint);
        if(x != progres)
            canvas.drawText(actualyMoney + "/" + goal,progres + 10,y+height/2+20,paint);
        else
            canvas.drawText(actualyMoney + "/" + goal,progres - 40,y+height + 25,paint);
        canvas.drawBitmap(money, x, y, null);
    }
}
