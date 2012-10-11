package com.drappierc.sqlite;

public class Score {
    private int id;
    private long score;
    private int type;

    public Score() {}

    public Score(long score,int type) {
        this.score = score;
        this.type = type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getScore() {
        return this.score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
