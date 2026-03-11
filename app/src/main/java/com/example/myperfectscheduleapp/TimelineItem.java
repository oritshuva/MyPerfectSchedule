package com.example.myperfectscheduleapp;

public class TimelineItem {

    private String title;
    private String time;

    public TimelineItem(String title,String time){
        this.title = title;
        this.time = time;
    }

    public String getTitle(){
        return title;
    }

    public String getTime(){
        return time;
    }
}