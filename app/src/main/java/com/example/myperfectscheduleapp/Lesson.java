package com.example.myperfectscheduleapp;

public class Lesson {
    private String id, userId, day, subject, startTime, endTime;
    private int period;

    public Lesson() {}  // דרוש ל-Firebase

    public Lesson(String userId, String day, int period, String subject, String startTime, String endTime) {
        this.userId = userId;
        this.day = day;
        this.period = period;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }
    public int getPeriod() { return period; }
    public void setPeriod(int period) { this.period = period; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}