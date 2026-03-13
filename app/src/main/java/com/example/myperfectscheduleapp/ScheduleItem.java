package com.example.myperfectscheduleapp;

import com.google.firebase.firestore.Exclude;

public class ScheduleItem {

    private String period;
    private String subject;
    private String startTime;
    private String endTime;
    private String room;
    private String day;
    private String documentId;
    private String userId;

    // Firebase חייב constructor ריק
    public ScheduleItem() {}

    public ScheduleItem(
            String period,
            String subject,
            String startTime,
            String endTime,
            String room,
            String day,
            String documentId,
            String userId
    ) {
        this.period     = period;
        this.subject    = subject;
        this.startTime  = startTime;
        this.endTime    = endTime;
        this.room       = room;
        this.day        = day;
        this.documentId = documentId;
        this.userId     = userId;
    }

    // Getters
    public String getPeriod()     { return period; }
    public String getSubject()    { return subject; }
    public String getStartTime()  { return startTime; }
    public String getEndTime()    { return endTime; }
    public String getRoom()       { return room; }
    public String getDay()        { return day; }
    public String getUserId()     { return userId; }

    // documentId לא נשמר ב-Firebase (זה ה-ID של הdocument עצמו)
    @Exclude
    public String getDocumentId() { return documentId; }

    // Setters
    public void setPeriod(String period)         { this.period = period; }
    public void setSubject(String subject)       { this.subject = subject; }
    public void setStartTime(String startTime)   { this.startTime = startTime; }
    public void setEndTime(String endTime)       { this.endTime = endTime; }
    public void setRoom(String room)             { this.room = room; }
    public void setDay(String day)               { this.day = day; }
    public void setDocumentId(String documentId) { this.documentId = documentId; }
    public void setUserId(String userId)         { this.userId = userId; }
}
