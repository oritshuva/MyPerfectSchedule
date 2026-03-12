package com.example.myperfectscheduleapp;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

public class TaskItem {
    @DocumentId
    private String id;
    private String taskName;
    private String subject;
    private Timestamp dueDate;
    private String status;
    private String urgency; // "high", "medium", "low"

    // קונסטרקטור ריק חובה עבור Firebase
    public TaskItem() {
        this.urgency = "medium"; // ברירת מחדל
    }

    public TaskItem(String taskName, String subject, Timestamp dueDate, String status, String urgency) {
        this.taskName = taskName;
        this.subject = subject;
        this.dueDate = dueDate;
        this.status = status;
        this.urgency = urgency != null ? urgency : "medium";
    }

    // Getters ו-Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public Timestamp getDueDate() { return dueDate; }
    public void setDueDate(Timestamp dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }

    // פונקציה עזר לקבלת מספר דחיפות (למיון)
    public int getUrgencyLevel() {
        if (urgency == null) return 2;
        switch (urgency.toLowerCase()) {
            case "high": return 1;
            case "medium": return 2;
            case "low": return 3;
            default: return 2;
        }
    }
}
