package com.example.holic.calendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by holic on 16/3/5.
 */
public class dbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "calendar.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "EventTable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "YEAR";
    public static final String COL_3 = "MONTH";
    public static final String COL_4 = "DAY";
    public static final String COL_5 = "HOUR";
    public static final String COL_6 = "MINUTE";
    public static final String COL_7 = "NAME";

    /*public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            COL_1+ " INTEGER PRiMARY KEY," +
            COL_2 + " INTEGER, " +
            COL_3 + " INTEGER, " +
            COL_4+ " INTEGER, " +
            COL_5 + " INTEGER, " +
            COL_6 + " INTEGER " +
            COL_7 + " TEXT," + " )";*/


   //Constructor
    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase DB = this.getWritableDatabase();

    }
    String CREATE_TABLE = dbTable.CREATE_TABLE;
    String DELETE_TABLE = dbTable.DELETE_TABLE;

    @Override
    public void onCreate(SQLiteDatabase db) {
        //add create table query
        db.execSQL(CREATE_TABLE);
    }

        @Override
        public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
            if (newVersion > oldVersion) {
                db.execSQL(DELETE_TABLE);
                onCreate(db);
            }
        }
    }
