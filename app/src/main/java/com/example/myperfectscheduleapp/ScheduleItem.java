package com.example.myperfectscheduleapp;

public class ScheduleItem {

    private String subject;
    private String day;
    private String startTime;
    private String endTime;

    public ScheduleItem() {
    }

    public ScheduleItem(String subject, String day, String startTime, String endTime) {
        this.subject = subject;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}