package com.example.ja.goldminer;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Ja on 2016-08-05.
 */
public class Rock extends GameObject {

    private Bitmap image;
    private Type type;
    private boolean catched;
    private int cost;
    private double weight;

    public enum Type {SROCK, MROCK, BROCK, SGOLD, MGOLD, BGOLD, DIAMOND}

    public Rock(int x, int y, Bitmap img, Rock.Type type) {
        super(x, y, img.getWidth(), img.getHeight());
        this.type = type;
        this.image = img;
        catched = false;
        createRock(type, img);
    }

    private void createRock(Rock.Type type, Bitmap img) {
        switch (type) {
            case SROCK:
                cost = 10;
                weight = 7;
                break;
            case MROCK:
                cost = 25;
                weight = 9;
                break;
            case BROCK:
                cost = 50;
                weight = 9.5;
                break;
            case SGOLD:
                cost = 50;
                weight = 6;
                break;
            case MGOLD:
                cost = 200;
                weight = 9;
                break;
            case BGOLD:
                cost = 500;
                weight = 9.5;
                break;
            case DIAMOND:
                cost = 600;
                weight = 3;
                break;
        }
    }

    public void update(int x, int y) {
        if (catched) {
            this.x = x - width / 2;
            this.y = y - height / 2;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void setCatched(boolean catched) {
        this.catched = catched;
    }

    public boolean isCatched() {
        return catched;
    }

    public double getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
}
