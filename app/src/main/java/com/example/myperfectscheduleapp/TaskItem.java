package com.example.myperfectscheduleapp;

import com.google.firebase.Timestamp;

public class TaskItem {
    private String taskName;
    private String subject;
    private Timestamp dueDate;
    private String status;

    // קונסטרקטור ריק חובה עבור Firebase
    public TaskItem() {}

    public TaskItem(String taskName, String subject, Timestamp dueDate, String status) {
        this.taskName = taskName;
        this.subject = subject;
        this.dueDate = dueDate;
        this.status = status;
    }

    // Getters ו-Setters
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public Timestamp getDueDate() { return dueDate; }
    public void setDueDate(Timestamp dueDate) { this.dueDate = dueDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}