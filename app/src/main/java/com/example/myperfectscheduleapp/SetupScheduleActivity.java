package com.example.myperfectscheduleapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetupScheduleActivity extends AppCompatActivity {

    private Spinner spinnerDays, spinnerSubjects;
    private EditText editStartTime, editEndTime;
    private Button btnSaveLesson, btnAddCustomSubject;
    private FirebaseFirestore db;
    private List<String> subjectsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_schedule);

        db = FirebaseFirestore.getInstance();

        // אתחול רכיבים - IDs התואמים בדיוק ל-XML
        spinnerDays = findViewById(R.id.spinnerDays);
        spinnerSubjects = findViewById(R.id.spinnerSubjects);
        editStartTime = findViewById(R.id.editStartTime);
        editEndTime = findViewById(R.id.editEndTime);
        btnSaveLesson = findViewById(R.id.btnSaveLesson);
        btnAddCustomSubject = findViewById(R.id.btnAddCustomSubject);

        setupSpinners();

        btnSaveLesson.setOnClickListener(v -> saveLesson());
    }

    private void setupSpinners() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, days);
        spinnerDays.setAdapter(daysAdapter);

        String[] initialSubjects = {"מתמטיקה", "אנגלית", "היסטוריה", "לשון", "מדעים"};
        subjectsList = new ArrayList<>(Arrays.asList(initialSubjects));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjectsList);
        spinnerSubjects.setAdapter(adapter);
    }

    private void saveLesson() {
        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        ScheduleItem lesson = new ScheduleItem(
                spinnerSubjects.getSelectedItem().toString(),
                spinnerDays.getSelectedItem().toString(),
                editStartTime.getText().toString(),
                editEndTime.getText().toString()
        );

        db.collection("users").document(uid).collection("schedule")
                .add(lesson)
                .addOnSuccessListener(ref -> Toast.makeText(this, "השיעור נשמר", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "שגיאה בשמירה", Toast.LENGTH_SHORT).show());
    }
}