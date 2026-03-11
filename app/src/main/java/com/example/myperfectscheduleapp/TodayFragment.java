package com.example.myperfectscheduleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodayFragment extends Fragment {

    private TextView textCurrentLesson, textTimeLeft, textNextLesson, textDateTitle;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today, container, false);

        textCurrentLesson = view.findViewById(R.id.textCurrentLesson);
        textTimeLeft = view.findViewById(R.id.textTimeLeft);
        textNextLesson = view.findViewById(R.id.textNextLesson);
        textDateTitle = view.findViewById(R.id.textDateTitle);

        db = FirebaseFirestore.getInstance();

        setCurrentDate();
        checkCurrentLesson();

        return view;
    }

    private void setCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMMM", new Locale("he", "IL"));
        textDateTitle.setText(sdf.format(new Date()));
    }

    private void checkCurrentLesson() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        Calendar calendar = Calendar.getInstance();
        String currentDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);

        // פורמט שעה נוכחי להשוואה (HH:mm)
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = timeFormat.format(new Date());

        db.collection("users").document(uid).collection("schedule")
                .whereEqualTo("day", currentDay)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!isAdded()) return;

                    if (queryDocumentSnapshots.isEmpty()) {
                        textCurrentLesson.setText("יום חופשי!");
                        return;
                    }

                    boolean found = false;
                    for (ScheduleItem item : queryDocumentSnapshots.toObjects(ScheduleItem.class)) {
                        if (currentTime.compareTo(item.getStartTime()) >= 0 && currentTime.compareTo(item.getEndTime()) <= 0) {
                            textCurrentLesson.setText(item.getSubject());
                            textTimeLeft.setText("מסתיים ב-" + item.getEndTime());
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        textCurrentLesson.setText("אין שיעור כרגע");
                        textTimeLeft.setText("");
                    }
                });
    }
}