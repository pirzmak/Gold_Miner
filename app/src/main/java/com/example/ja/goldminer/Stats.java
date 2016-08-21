package com.example.ja.goldminer;

/**
 * Created by Ja on 2016-08-19.
 */
public interface Stats {
    String TABLE_NAME = "stats";

    interface Columns {
        String STAT_ID = "_id";
        String STAT_NR = "stat_nr";
        String STAT_ROCKS = "stat_rocks";
    }
}
