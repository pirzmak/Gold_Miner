package com.example.ja.goldminer;

/**
 * Created by Ja on 2016-08-19.
 */
public class Stat {
    private static Integer id = 0;
    private Integer nr;
    private String rocks;

    public Stat() {
        this.id++;
    }

    public Stat(Integer nr, String rocks) {
        this.id++;
        this.nr = nr;
        this.rocks = rocks;
    }

    public static Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public String getRocks() {
        return rocks;
    }

    public void setRocks(String rocks) {
        this.rocks = rocks;
    }
}
