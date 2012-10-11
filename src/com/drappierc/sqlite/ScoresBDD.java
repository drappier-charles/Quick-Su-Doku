package com.drappierc.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ScoresBDD {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "sudoku.db";

    private static final String TABLE_SCORES = "table_scores";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_SCORE = "SCORE";
    private static final int NUM_COL_SCORE = 1;
    private static final String COL_TYPE = "TYPE";
    private static final int NUM_COL_TYPE= 2;

    private SQLiteDatabase bdd;

    private BDSqlite maBaseSQLite;

    public ScoresBDD(Context context){
        maBaseSQLite = new BDSqlite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertScore(Score score){
        ContentValues values = new ContentValues();
        values.put(COL_SCORE, score.getScore());
        values.put(COL_TYPE,score.getType());
        return bdd.insert(TABLE_SCORES, null, values);
    }

    public int updateScore(int id, Score score){
        ContentValues values = new ContentValues();
        values.put(COL_SCORE, score.getScore());
        values.put(COL_TYPE, score.getType());
        return bdd.update(TABLE_SCORES, values, COL_ID + " = " +id, null);
    }

    public int removeScoreWithID(int id){
        return bdd.delete(TABLE_SCORES, COL_ID + " = " +id, null);
    }

    private Score cursorToScore(Cursor c){
        if (c.getCount() == 0)
            return null;

        c.moveToFirst();
        Score score = new Score();
        score.setId(c.getInt(NUM_COL_ID));
        score.setScore(c.getLong(NUM_COL_SCORE));
        score.setType(c.getInt(NUM_COL_TYPE));
        c.close();

        return score;
    }

    public ArrayList<Score> getAll() {

        Cursor c = bdd.query(TABLE_SCORES, new String[] {COL_ID, COL_SCORE, COL_TYPE}, null, null, null, null , COL_TYPE+" ASC, "+COL_SCORE+" ASC");

        if (c.getCount() == 0)
            return null;

        ArrayList<Score> scores = new ArrayList<Score>();
        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    Score score = new Score();
                    score.setId(c.getInt(NUM_COL_ID));
                    score.setScore(c.getLong(NUM_COL_SCORE));
                    score.setType(c.getInt(NUM_COL_TYPE));

                    scores.add(score);
                }while (c.moveToNext());
            }
        }
        c.close();

        return scores;
    }
}