package com.example.ja.goldminer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ja on 2016-08-19.
 */
class DBHelper extends SQLiteOpenHelper {
    private final static int DB_VERSION = 1;
    private final static String DB_NAME = "AppDB.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DROP TABLE IF EXISTS " + Stats.TABLE_NAME);
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + Stats.TABLE_NAME
                        + " ( "
                        + Stats.Columns.STAT_ID
                        + " integer primary key, "
                        + Stats.Columns.STAT_NR
                        + " integer, "
                        +Stats.Columns.STAT_ROCKS
                        + " text )"
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "
                        + Stats.TABLE_NAME
                        + " ( "
                        + Stats.Columns.STAT_ID
                        + " integer primary key, "
                        + Stats.Columns.STAT_NR
                        + " integer, "
                        +Stats.Columns.STAT_ROCKS
                        + " text )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // kasujemy bazę
        db.execSQL("DROP TABLE IF EXISTS " + Stats.TABLE_NAME);
        // tworzymy nową bazę
        onCreate(db);
    }
}
