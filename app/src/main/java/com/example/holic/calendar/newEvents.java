package com.example.holic.calendar;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by holic on 16/3/5.
 */


public class newEvents extends DialogFragment{
    private eventDialog triger = null;
    private EditText nameInput;
    private TimePicker timeInput;
    private Date dateInput;
    //private int store_year = -1;

    public interface eventDialog{
        void added(dbData data);
    }

    public void storeData(int year, int month, int dom) {
        dateInput = new Date();
        dateInput.setYear(year);
        dateInput.setMonth(month);
        dateInput.setDate(dom);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            triger = (eventDialog)activity;
        } catch (ClassCastException cce) {

        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Calendar getInput = Calendar.getInstance();

       // Button btn = (Button)dialogView.findViewById(R.id.addEvent);
        View dialogView = inflater.inflate(R.layout.activity_event, null);
        builder.setView(dialogView);
        //get task name input
        nameInput = (EditText)dialogView.findViewById(R.id.nameInput);
        nameInput.setText("New Task");
        //store time setting updates
        timeInput = (TimePicker)dialogView.findViewById(R.id.timeInput);
        timeInput.setCurrentHour(getInput.get(Calendar.HOUR));
        timeInput.setCurrentMinute(getInput.get(Calendar.MINUTE));


        //implement button click activity
        Button btn = (Button)dialogView.findViewById(R.id.addEvent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //set time
                dateInput.setMinutes(timeInput.getCurrentMinute());
                dateInput.setHours(timeInput.getCurrentHour());

                //update the task stack
                if (triger != null) {
                    dbData data = new dbData();
                    String taskName = nameInput.getText().toString();
                    if (taskName.isEmpty()) {
                        taskName = "New Task";
                    } else {
                        data.setName(taskName);
                    }
                    data.setDate(dateInput);
                    triger.added(data);
                    dismiss();
                }
            }
        });

        return builder.create();
    }

}
