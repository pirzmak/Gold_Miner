package com.example.ja.goldminer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.List;

/**
 * Created by Ja on 2016-08-19.
 */
public class StatDAO {

    // obiekt umożliwiający dostęp do bazy danych
    private DBHelper dbHelper;

    public StatDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    // wstawienie nowej notatki do bazy danych
    public void insertNote(final Stat stat) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Stats.Columns.STAT_NR, stat.getNr());
        contentValues.put(Stats.Columns.STAT_ROCKS, stat.getRocks());

        dbHelper.getWritableDatabase().insert(Stats.TABLE_NAME, null, contentValues);
    }

    // pobranie notatki na podstawie jej id
    public Stat getNoteById(final int id) {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("select * from " + Stats.TABLE_NAME + " where " + Stats.Columns.STAT_ID + " = " + id, null);
        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            return mapCursorToStat(cursor);
        }
        return null;
    }


    // aktualizacja notatki w bazie
    public void updateNote(final Stat stat) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Stats.Columns.STAT_NR, stat.getNr());
        contentValues.put(Stats.Columns.STAT_ROCKS, stat.getRocks());

        dbHelper.getWritableDatabase().update(Stats.TABLE_NAME,
                contentValues,
                " " + Stats.Columns.STAT_ID + " = ? ",
                new String[]{stat.getId().toString()}
        );
    }

    // usunięcie notatki z bazy
    public void delete() {
        dbHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + Stats.TABLE_NAME);

    }



    // zamiana cursora na obiekt notatki
    private Stat mapCursorToStat(final Cursor cursor) {
        int idColumnId = cursor.getColumnIndex(Stats.Columns.STAT_ID);
        int nrColumnId = cursor.getColumnIndex(Stats.Columns.STAT_NR);
        int rocksColumnId = cursor.getColumnIndex(Stats.Columns.STAT_ROCKS);

        Stat stat = new Stat();
        stat.setId(cursor.getInt(idColumnId));
        stat.setNr(cursor.getInt(nrColumnId));
        stat.setRocks(cursor.getString(rocksColumnId));

        return stat;
    }
}
