package com.example.holic.calendar;

import android.provider.BaseColumns;

/**
 * Created by holic on 16/3/6.
 */
public class dbTable {

    public static abstract class BUILD implements BaseColumns {
        public static final String TABLE_NAME = "events";
        public static final String YEAR = "year";
        public static final String MONTH = "month";
        public static final String DAY = "dom";
        public static final String NAME = "title";
        public static final String HOUR = "hour";
        public static final String MINUTE = "minute";
    }
    //query which create the table
    public static final String CREATE_TABLE = "CREATE TABLE " + BUILD.TABLE_NAME + "(" + BUILD._ID + " INTEGER PRiMARY KEY," +
            BUILD.YEAR + " INTEGER, " + BUILD.MONTH + " INTEGER, " + BUILD.DAY + " INTEGER, " +
            BUILD.NAME + " TEXT, " + BUILD.HOUR + " INTEGER, " + BUILD.MINUTE + " INTEGER " + " )";
    //delete query
    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + BUILD.TABLE_NAME;


    //default constructor
    public dbTable() {}
}

