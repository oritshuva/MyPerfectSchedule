package com.example.myperfectscheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private RecyclerView recyclerSchedule;
    private Button btnLogout, btnAddLesson;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private ScheduleAdapter adapter;
    private List<ScheduleItem> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvWelcome = findViewById(R.id.tvWelcome);
        recyclerSchedule = findViewById(R.id.recyclerSchedule);
        btnLogout = findViewById(R.id.btnLogout);
        btnAddLesson = findViewById(R.id.btnAddLesson);

        // בדיקה אם המשתמש מחובר
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            goToLogin();
            return;
        }

        // הצגת שם המשתמש
        String email = currentUser.getEmail();
        tvWelcome.setText("Welcome, " + email);

        // הגדרת RecyclerView
        scheduleList = new ArrayList<>();
        adapter = new ScheduleAdapter(scheduleList);
        recyclerSchedule.setLayoutManager(new LinearLayoutManager(this));
        recyclerSchedule.setAdapter(adapter);

        // טעינת מערכת השעות
        loadSchedule();

        // כפתור יציאה
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            goToLogin();
        });

        // כפתור הוספת שיעור
        btnAddLesson.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SetupScheduleActivity.class));
        });
    }

    private void loadSchedule() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) return;

        String uid = currentUser.getUid();

        db.collection("schedules")
                .whereEqualTo("userId", uid)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    scheduleList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        ScheduleItem item = document.toObject(ScheduleItem.class);
                        scheduleList.add(item);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MainActivity.this, "Error loading schedule: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void goToLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}