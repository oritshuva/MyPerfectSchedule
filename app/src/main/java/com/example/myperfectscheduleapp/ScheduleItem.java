package com.example.myperfectscheduleapp;

public class ScheduleItem {

    private String day;
    private int periodNumber;
    private String subjectName;
    private String startTime;
    private String endTime;

    public ScheduleItem() {
    }

    public ScheduleItem(String day, int periodNumber, String subjectName, String startTime, String endTime) {
        this.day = day;
        this.periodNumber = periodNumber;
        this.subjectName = subjectName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public int getPeriodNumber() {
        return periodNumber;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setPeriodNumber(int periodNumber) {
        this.periodNumber = periodNumber;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}