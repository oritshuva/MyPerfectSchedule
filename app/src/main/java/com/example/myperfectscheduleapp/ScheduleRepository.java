package com.example.myperfectscheduleapp;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository {

    private static ScheduleRepository instance;
    private final FirebaseFirestore db;
    private ListenerRegistration listenerRegistration;

    // Singleton - רק instance אחד קיים
    public static synchronized ScheduleRepository getInstance() {
        if (instance == null) {
            instance = new ScheduleRepository();
        }
        return instance;
    }

    private ScheduleRepository() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * מחזיר את ה-path הנכון: users/{userId}/schedules
     */
    private String getCollectionPath(String userId) {
        return "users/" + userId + "/schedules";
    }

    /**
     * הוספת שיעור חדש
     */
    public void addScheduleItem(
            String userId,
            ScheduleItem item,
            OnSuccessListener<Void> onSuccess,
            OnFailureListener onFailure
    ) {
        db.collection(getCollectionPath(userId))
                .add(item)
                .addOnSuccessListener(documentReference -> {
                    // עדכון ה-documentId בתוך המסמך
                    String generatedId = documentReference.getId();
                    documentReference.update("documentId", generatedId)
                            .addOnSuccessListener(unused -> {
                                if (onSuccess != null) onSuccess.onSuccess(null);
                            })
                            .addOnFailureListener(e -> {
                                // גם אם העדכון נכשל, השיעור נוסף
                                if (onSuccess != null) onSuccess.onSuccess(null);
                            });
                })
                .addOnFailureListener(e -> {
                    if (onFailure != null) onFailure.onFailure(e);
                });
    }

    /**
     * קבלת שיעורים לפי יום
     */
    public void getScheduleByDay(
            String userId,
            String day,
            OnSuccessListener<List<ScheduleItem>> onSuccess,
            OnFailureListener onFailure
    ) {
        db.collection(getCollectionPath(userId))
                .whereEqualTo("day", day)
                .orderBy("startTime")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<ScheduleItem> items = new ArrayList<>();
                    for (com.google.firebase.firestore.DocumentSnapshot doc
                            : querySnapshot.getDocuments()) {
                        ScheduleItem item = doc.toObject(ScheduleItem.class);
                        if (item != null) {
                            item.setDocumentId(doc.getId());
                            items.add(item);
                        }
                    }
                    if (onSuccess != null) onSuccess.onSuccess(items);
                })
                .addOnFailureListener(e -> {
                    if (onFailure != null) onFailure.onFailure(e);
                });
    }

    /**
     * מחיקת שיעור
     */
    public void deleteScheduleItem(
            String userId,
            String documentId,
            OnSuccessListener<Void> onSuccess,
            OnFailureListener onFailure
    ) {
        db.collection(getCollectionPath(userId))
                .document(documentId)
                .delete()
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure);
    }

    /**
     * ניקוי listeners כשיוצאים מהמסך
     */
    public void cleanup() {
        if (listenerRegistration != null) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }
}
