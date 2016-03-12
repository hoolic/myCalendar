package com.example.holic.calendar;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by holic on 16/3/5.
 */
public class dbData {
    public String id = null;
    private String name;
    private Date date;

    //setter
    public void setName(String name){
        this.name = name;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setDate(Date date){
        this.date = date;
    }

    //getter
    public String getName(){
        return name;
    }
    public String getId(){
        return id;
    }
    public Date getDate(){
        return date;
    }
     //print format
     String output="";
    public String toString(){
        DateFormat [] format = new DateFormat[] {
                DateFormat.getDateInstance(),
                DateFormat.getDateTimeInstance(),
                DateFormat.getTimeInstance(),
        };
        for (DateFormat df : format) {
            output = df.format(date);
            //System.out.println(df.format(new Date(0)));
            //df.setTimeZone(TimeZone.getTimeZone("UTC"));
            //System.out.println(df.format(new Date(0)));
        }
        return "\"" + name + " " + output + "\"";
    }




}
