package com.example.myperfectscheduleapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleRepository {
    private static ScheduleRepository instance;
    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    // Day schedules - organized by day
    private final Map<String, MutableLiveData<List<ScheduleItem>>> daySchedules = new HashMap<>();

    // Constructor פרטי
    private ScheduleRepository() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        initializeDaySchedules();
    }

    // Singleton pattern
    public static synchronized ScheduleRepository getInstance() {
        if (instance == null) {
            instance = new ScheduleRepository();
        }
        return instance;
    }

    // אתחול ה-LiveData עבור כל יום
    private void initializeDaySchedules() {
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (String day : days) {
            daySchedules.put(day, new MutableLiveData<>(new ArrayList<>()));
        }
    }

    // קבלת LiveData של לוח זמנים ליום מסוים
    public LiveData<List<ScheduleItem>> getScheduleForDay(String uid, String day) {
        MutableLiveData<List<ScheduleItem>> daySchedule = daySchedules.get(day);

        if (daySchedule != null) {
            loadScheduleFromFirestore(uid, day, daySchedule);
        }

        return daySchedule;
    }

    // טעינת נתונים מ-Firestore
    private void loadScheduleFromFirestore(String uid, String day, MutableLiveData<List<ScheduleItem>> liveData) {
        getSchedulesCollection(uid)
                .whereEqualTo("day", day)
                .orderBy("period")
                .addSnapshotListener((snapshots, error) -> {
                    if (error != null) {
                        return;
                    }

                    if (snapshots != null) {
                        List<ScheduleItem> items = new ArrayList<>();
                        for (DocumentSnapshot doc : snapshots.getDocuments()) {
                            ScheduleItem item = doc.toObject(ScheduleItem.class);
                            if (item != null) {
                                item.setDocumentId(doc.getId());
                                items.add(item);
                            }
                        }
                        liveData.postValue(items);
                    }
                });
    }

    // הוספת שיעור חדש
    public void addScheduleItem(ScheduleItem item, OnCompleteListener listener) {
        String uid = getCurrentUserId();
        if (uid == null) {
            if (listener != null) listener.onFailure("User not authenticated");
            return;
        }

        item.setUserId(uid);

        getSchedulesCollection(uid)
                .add(item)
                .addOnSuccessListener(documentReference -> {
                    if (listener != null) listener.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onFailure(e.getMessage());
                });
    }

    // עדכון שיעור
    public void updateScheduleItem(ScheduleItem item, OnCompleteListener listener) {
        String uid = getCurrentUserId();
        if (uid == null || item.getDocumentId() == null) {
            if (listener != null) listener.onFailure("Invalid update request");
            return;
        }

        getSchedulesCollection(uid)
                .document(item.getDocumentId())
                .set(item)
                .addOnSuccessListener(aVoid -> {
                    if (listener != null) listener.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onFailure(e.getMessage());
                });
    }

    // מחיקת שיעור
    public void deleteScheduleItem(String documentId, OnCompleteListener listener) {
        String uid = getCurrentUserId();
        if (uid == null || documentId == null) {
            if (listener != null) listener.onFailure("Invalid delete request");
            return;
        }

        getSchedulesCollection(uid)
                .document(documentId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    if (listener != null) listener.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (listener != null) listener.onFailure(e.getMessage());
                });
    }

    // קבלת collection של schedules למשתמש
    private CollectionReference getSchedulesCollection(String uid) {
        return db.collection("users").document(uid).collection("schedules");
    }

    // קבלת UID של המשתמש הנוכחי
    private String getCurrentUserId() {
        return auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;
    }

    // Interface ל-callbacks
    public interface OnCompleteListener {
        void onSuccess();
        void onFailure(String error);
    }
}
