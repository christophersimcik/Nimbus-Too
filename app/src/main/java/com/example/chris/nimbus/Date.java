package com.example.chris.nimbus;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.TimeZone;

public class Date {
    DateTimeZone dz = DateTimeZone.forTimeZone(TimeZone.getDefault());
    DateTime dt = new DateTime().withZone(dz);
    public Date(){
    }
    public String getTime(){
        String time;
        time = dt.toString("hh:mm:ss");
        if(time.charAt(0) == '0'){
            time = time.substring(1,time.length());
        }
        return time;
    }
    public String getDay(){
        String day;
        day = dt.dayOfWeek().getAsText();
        return day;
    }
    public String getMonth(){
        String month;
        month = dt.monthOfYear().getAsShortText();
        return month;
    }
    public String getMeridiem(){
        String meridiem;
        meridiem = dt.toString("a");
        return meridiem;
    }
    public int getDayOfMonth(){
        int date;
        date = dt.getDayOfMonth();
        return date;
    }
    public int getYear(){
        int year;
        year = dt.getYear();
        return year;
    }
    public int getHourOfDay(){
        int hour;
        hour = dt.getHourOfDay();
        return hour;
    }
    public int getMinOfHour(){
        int min;
        min = dt.getMinuteOfHour();
        return min;
    }
    public int getSecOfMin(){
        int sec;
        sec = dt.getSecondOfMinute();
        return sec;
    }
    public String checkTime(){
        String st;
        st = dt.toString();
        return st;
    }
    public String getTimeZone(){
        String tz;
        tz = dt.getZone().getID();
        return tz;
    }
}
