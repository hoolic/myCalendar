package com.example.holic.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity {

    private CalendarView Mycalendar;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Mycalendar = (CalendarView)findViewById(R.id.calendarView);
        Mycalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int monthOfYear, int dayOfMonth) {
                Intent displayEvents = new Intent(getApplicationContext(), DisplayView.class);
                bundle.putInt("YEAR", year);
                bundle.putInt("MONTH", monthOfYear);
                bundle.putInt("DAY", dayOfMonth);
                displayEvents.putExtras(bundle);
                startActivity(displayEvents);
            }
        });
    }
}