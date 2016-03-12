package com.example.holic.calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by holic on 16/3/5.
 */
public class actionEvents extends RecyclerView.Adapter<actionEvents.ViewHolder>
        implements Comparator<dbData> {
    private ArrayList<dbData> taskList = null;

    //build in for comparator class
    public int getItemCount() {
        return taskList == null?0:taskList.size();
    }
    @Override
    public int compare(dbData first, dbData second) {
        return first.getDate().compareTo(second.getDate());
    }

    public ArrayList<dbData> getCurrentEvents() {
        return taskList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.keep_event_record, parent, false);

        ViewHolder newone = new ViewHolder(view);
        return newone;
    }

    public void addEvent(dbData data) {
        if(taskList != null) {
            taskList.add(data);
            if(taskList.size() > 1)
                Collections.sort(taskList, this);
        }
    }

    //new holder implementation
    public class ViewHolder extends RecyclerView.ViewHolder{
        public View view;

        public ViewHolder(View views){
            super(views);
            view = views;
            view.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("SURE TO DELETE?");
                    //IF CHOOSE TO DELETE
                    builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            taskList.remove(getAdapterPosition());
                            //sync database
                            notifyItemRemoved(getAdapterPosition());
                        }
                    });
                    //if STILL WANT TO SAVE
                    builder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }

            });
        }
    }
    public actionEvents(ArrayList<dbData>datas){
        taskList = new ArrayList<dbData>();
        for(dbData data : datas){
            taskList.add(data);
            Collections.sort(taskList, this);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int current) {
        dbData curEvent = taskList.get(current);
        TextView namePos = (TextView)holder.view.findViewById(R.id.taskName);
        namePos.setText(curEvent.getName());

    }





}//end of class