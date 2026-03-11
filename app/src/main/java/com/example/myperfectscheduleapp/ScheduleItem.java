package com.example.myperfectscheduleapp;

public class ScheduleItem {
    private String subject;
    private String day;
    private String startTime;
    private String endTime;

    // קונסטרקטור ריק חובה עבור Firebase
    public ScheduleItem() {}

    public ScheduleItem(String subject, String day, String startTime, String endTime) {
        this.subject = subject;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters ו-Setters (חובה עבור Firebase)
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}