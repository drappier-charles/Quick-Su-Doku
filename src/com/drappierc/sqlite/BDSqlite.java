package com.drappierc.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class BDSqlite extends SQLiteOpenHelper {

    private static final String TABLE_SCORES = "table_scores";
    private static final String COL_ID = "ID";
    private static final String COL_SCORE = "SCORE";
    private static final String COL_TYPE = "TYPE";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_SCORES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SCORE + " REAL NOT NULL,"
            + COL_TYPE + " INTEGER NOT NULL);";

    public BDSqlite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_SCORES + ";");
        onCreate(db);
    }

}