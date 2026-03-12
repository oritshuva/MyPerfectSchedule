package com.example.myperfectscheduleapp;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WeeklyScheduleActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fabAdd;
    private FirebaseFirestore db;

    private final String[] days = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);

        db = FirebaseFirestore.getInstance();

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        fabAdd = findViewById(R.id.fabAdd);

        SchedulePagerAdapter pagerAdapter = new SchedulePagerAdapter(this, days);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(days[position])
        ).attach();

        // ✅ כפתור FAB פותח דיאלוג להוספת שיעור
        fabAdd.setOnClickListener(v -> showAddScheduleDialog());
    }

    // ✅ דיאלוג להוספת שיעור חדש
    private void showAddScheduleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_schedule, null);
        builder.setView(dialogView);

        EditText editSubject = dialogView.findViewById(R.id.editSubject);
        EditText editStartTime = dialogView.findViewById(R.id.editStartTime);
        EditText editEndTime = dialogView.findViewById(R.id.editEndTime);
        Spinner spinnerDay = dialogView.findViewById(R.id.spinnerDay);
        Button btnSave = dialogView.findViewById(R.id.btnSaveSchedule);
        Button btnCancel = dialogView.findViewById(R.id.btnCancelSchedule);

        // הגדרת Spinner עם ימי השבוע
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapter);

        // בחירת שעת התחלה
        editStartTime.setOnClickListener(v -> {
            showTimePicker((hour, minute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                editStartTime.setText(time);
            });
        });

        // בחירת שעת סיום
        editEndTime.setOnClickListener(v -> {
            showTimePicker((hour, minute) -> {
                String time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
                editEndTime.setText(time);
            });
        });

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(v -> {
            String subject = editSubject.getText().toString().trim();
            String startTime = editStartTime.getText().toString().trim();
            String endTime = editEndTime.getText().toString().trim();
            String day = spinnerDay.getSelectedItem().toString();

            if (subject.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            saveScheduleToFirebase(subject, day, startTime, endTime);
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    // ✅ בורר זמן
    private void showTimePicker(OnTimeSelectedListener listener) {
        TimePickerDialog picker = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            listener.onTimeSelected(hourOfDay, minute);
        }, 8, 0, true);
        picker.show();
    }

    // ✅ שמירת שיעור ב-Firebase
    private void saveScheduleToFirebase(String subject, String day, String startTime, String endTime) {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        Map<String, Object> scheduleData = new HashMap<>();
        scheduleData.put("userId", uid);
        scheduleData.put("subject", subject);
        scheduleData.put("day", day);
        scheduleData.put("startTime", startTime);
        scheduleData.put("endTime", endTime);

        db.collection("schedules")
                .add(scheduleData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Schedule added!", Toast.LENGTH_SHORT).show();
                    // רענון המסך
                    recreate();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // ממשק לבורר זמן
    interface OnTimeSelectedListener {
        void onTimeSelected(int hour, int minute);
    }

    @Override
    public void onBackPressed() {
        // ✅ כשלוחצים Back, חוזרים ל-MainActivity
        super.onBackPressed();
        startActivity(new Intent(WeeklyScheduleActivity.this, MainActivity.class));
        finish();
    }
}