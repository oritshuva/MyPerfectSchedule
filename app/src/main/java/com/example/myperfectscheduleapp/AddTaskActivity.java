package com.example.myperfectscheduleapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTaskName, editTaskDate;
    private Spinner spinnerTaskSubject, spinnerUrgency;
    private Button btnSaveTask;
    private Calendar calendar;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        db = FirebaseFirestore.getInstance();
        calendar = Calendar.getInstance();

        editTaskName = findViewById(R.id.editTaskName);
        editTaskDate = findViewById(R.id.editTaskDate);
        spinnerTaskSubject = findViewById(R.id.spinnerTaskSubject);
        spinnerUrgency = findViewById(R.id.spinnerUrgency);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        // בחירת תאריך
        editTaskDate.setOnClickListener(v -> {
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                editTaskDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        // רשימת מקצועות
        List<String> subjects = Arrays.asList("מתמטיקה", "אנגלית", "היסטוריה", "לשון", "מדעים");
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaskSubject.setAdapter(subjectAdapter);

        // רשימת דחיפות
        List<String> urgencyLevels = Arrays.asList("🔴 גבוה", "🟠 בינוני", "🟢 נמוך");
        ArrayAdapter<String> urgencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, urgencyLevels);
        urgencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUrgency.setAdapter(urgencyAdapter);
        spinnerUrgency.setSelection(1); // ברירת מחדל: בינוני

        btnSaveTask.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String name = editTaskName.getText().toString().trim();
        String subject = spinnerTaskSubject.getSelectedItem().toString();
        String urgencyText = spinnerUrgency.getSelectedItem().toString();
        String uid = FirebaseAuth.getInstance().getUid();

        if (name.isEmpty() || uid == null) {
            Toast.makeText(this, "נא למלא שם משימה", Toast.LENGTH_SHORT).show();
            return;
        }

        // המרת הטקסט לערך פשוט
        String urgency;
        if (urgencyText.contains("גבוה")) urgency = "high";
        else if (urgencyText.contains("נמוך")) urgency = "low";
        else urgency = "medium";

        Map<String, Object> task = new HashMap<>();
        task.put("taskName", name);
        task.put("subject", subject);
        task.put("dueDate", new Timestamp(calendar.getTime()));
        task.put("status", "pending");
        task.put("urgency", urgency);

        db.collection("users").document(uid).collection("tasks")
                .add(task)
                .addOnSuccessListener(ref -> {
                    Toast.makeText(this, "משימה נוספה!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "שגיאה בשמירה", Toast.LENGTH_SHORT).show());
    }
}
