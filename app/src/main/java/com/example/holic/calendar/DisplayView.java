package com.example.holic.calendar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by holic on 16/3/5.
 */

public class DisplayView extends AppCompatActivity
        implements newEvents.eventDialog{
    private int year;
    private int month;
    private int dom;
    private dbHelper myDb;
    private Button addEvent;
    private ArrayList<dbData> taskList;

    private RecyclerView recyclerView;
    private actionEvents eventUpdate;
    private RecyclerView.LayoutManager layoutMng;

    private TextView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        Bundle passInfo = getIntent().getExtras();
        year = passInfo.getInt("YEAR");month = passInfo.getInt("MONTH");dom = passInfo.getInt("DAY");

        //implement button
        addEvent = (Button)findViewById(R.id.newEvent);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                newEvents newEventDialog = new newEvents();
                newEventDialog.storeData(year, month, dom);
                newEventDialog.show(fragmentManager, "New Event");
            }

        });
        recyclerView = (RecyclerView)findViewById(R.id.loopview);
        view = (TextView)findViewById(R.id.noEvent);
        myDb = new dbHelper(getApplicationContext());
        layoutMng = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutMng);
    }//




    private class DbReader extends AsyncTask<Void, Void, ArrayList<dbData>> {
        @Override
        protected ArrayList<dbData> doInBackground(Void... params) {
            SQLiteDatabase mydb = myDb.getReadableDatabase();
            ArrayList<dbData> dataList = new ArrayList<>();
            //dbHelper db1 = new new dbHelper(getApplicationContext());
            //dbHelper = new dbHelper(getApplicationContext());
            String[] projection = {dbTable.BUILD._ID,
                    dbTable.BUILD.NAME, dbTable.BUILD.HOUR, dbTable.BUILD.MINUTE};
            String selection = dbTable.BUILD.MONTH + "= ? AND " + dbTable.BUILD.YEAR + " = ? AND " + dbTable.BUILD.DAY + " = ?";
            String[] selectionArgs = {Integer.toString(month), Integer.toString(year), Integer.toString(dom)};
            Cursor cursor = mydb.query(dbTable.BUILD.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                dbData datas = new dbData();
                datas.setName(cursor.getString(cursor.getColumnIndex(dbTable.BUILD.NAME)));
                datas.setId(Integer.toString(cursor.getInt(cursor.getColumnIndex(dbTable.BUILD._ID))));
                Date cellD = new Date();
                cellD.setYear(year);cellD.setMonth(month);cellD.setDate(dom);
                cellD.setHours(cursor.getInt(cursor.getColumnIndex(dbTable.BUILD.HOUR)));
                cellD.setMinutes(cursor.getInt(cursor.getColumnIndex(dbTable.BUILD.MINUTE)));datas.setDate(cellD);
                dataList.add(datas);
                cursor.moveToNext();
            }
            return dataList;
        }

        @Override
        protected void onPostExecute(ArrayList<dbData> eventDetails) {
            taskList = eventDetails;
            if(taskList == null || taskList.size() == 0) {
                recyclerView.setVisibility(View.GONE);
            }
            eventUpdate = new actionEvents(taskList);
            recyclerView.setAdapter(eventUpdate);
            super.onPostExecute(eventDetails);
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private class DbWriter extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            ArrayList<dbData> currentList = eventUpdate.getCurrentEvents();
            SQLiteDatabase mydb = myDb.getWritableDatabase();
            for(dbData data : currentList) {
                if(data.getId() == null) {
                    ContentValues cell = new ContentValues();
                    cell.put(dbTable.BUILD.NAME, data.getName());cell.put(dbTable.BUILD.YEAR, data.getDate().getYear());
                    cell.put(dbTable.BUILD.MONTH, data.getDate().getMonth());cell.put(dbTable.BUILD.DAY, data.getDate().getDate());
                    cell.put(dbTable.BUILD.MINUTE, data.getDate().getMinutes());cell.put(dbTable.BUILD.HOUR, data.getDate().getHours());
                }
            }
            boolean empty;
            for(dbData data : taskList) {
                 empty = false;
                for(dbData data2 : currentList) {
                    if(data.getId() == data2.getId()) {
                        empty = true;
                        break;
                    }
                }
                if(!empty) {
                    String selection = dbTable.BUILD._ID + " is";
                    String[] selectionArgs = {data.getId()};
                    mydb.delete(dbTable.BUILD.TABLE_NAME, selection, selectionArgs);
                }
            }

            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }//end of write to db

    @Override
    protected void onResume() {
        DbReader dbReader = new DbReader();
        dbReader.execute();
        super.onResume();
    }

    @Override
    protected void onPause() {
        DbWriter dbWriter = new DbWriter();
        dbWriter.execute();
        super.onPause();
    }


    public void added(dbData eventDetails) {
        if(eventDetails != null) {
            eventUpdate.addEvent(eventDetails);
            eventUpdate.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
        }
    }


}
