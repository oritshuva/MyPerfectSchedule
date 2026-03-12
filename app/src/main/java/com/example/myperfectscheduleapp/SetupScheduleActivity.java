package com.example.myperfectscheduleapp;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import java.util.*;

public class SetupScheduleActivity extends AppCompatActivity {

    private Spinner spinnerDay, spinnerPeriod;
    private EditText etSubject;
    private Button btnStartTime, btnEndTime, btnSaveClass, btnFinishSetup;
    private RecyclerView rvClasses;
    private SetupLessonAdapter adapter;
    private final List<Lesson> lessons = new ArrayList<>();

    private FirebaseFirestore db;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_schedule);

        // init Firebase
        db  = FirebaseFirestore.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // find views
        spinnerDay      = findViewById(R.id.spinnerDay);
        spinnerPeriod   = findViewById(R.id.spinnerPeriod);
        etSubject       = findViewById(R.id.etSubject);
        btnStartTime    = findViewById(R.id.btnStartTime);
        btnEndTime      = findViewById(R.id.btnEndTime);
        btnSaveClass    = findViewById(R.id.btnSaveClass);
        btnFinishSetup  = findViewById(R.id.btnFinishSetup);
        rvClasses       = findViewById(R.id.rvClasses);

        // set up spinners
        List<String> days    = Arrays.asList("Sunday","Monday","Tuesday","Wednesday","Thursday");
        List<String> periods = Arrays.asList("1","2","3","4","5","6","7","8");
        spinnerDay.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, days));
        spinnerPeriod.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, periods));

        // setup RecyclerView
        rvClasses.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SetupLessonAdapter(lessons);
        rvClasses.setAdapter(adapter);

        // load existing lessons for the selected day
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                loadLessons(days.get(pos));
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // time pickers
        View.OnClickListener tp = v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this,
                    (tpView, h, m) -> ((Button)v).setText(String.format(Locale.getDefault(), "%02d:%02d", h, m)),
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    true
            ).show();
        };
        btnStartTime.setOnClickListener(tp);
        btnEndTime.setOnClickListener(tp);

        // save new lesson
        btnSaveClass.setOnClickListener(v -> {
            String day     = spinnerDay.getSelectedItem().toString();
            int period     = Integer.parseInt(spinnerPeriod.getSelectedItem().toString());
            String subject = etSubject.getText().toString().trim();
            String start   = btnStartTime.getText().toString();
            String end     = btnEndTime.getText().toString();

            if (subject.isEmpty()) {
                etSubject.setError("Enter subject");
                return;
            }

            Lesson L = new Lesson(uid, day, period, subject, start, end);
            db.collection("lessons")
                    .add(L)
                    .addOnSuccessListener(docRef -> {
                        // clear inputs
                        etSubject.setText("");
                        btnStartTime.setText("08:00");
                        btnEndTime.setText("08:45");
                        // reload
                        loadLessons(day);
                    });
        });

        // finish setup
        btnFinishSetup.setOnClickListener(v -> {
            // כאן תניוטו למסך הראשי
            finish();
        });
    }

    private void loadLessons(String day) {
        db.collection("lessons")
                .whereEqualTo("userId", uid)
                .whereEqualTo("day", day)
                .orderBy("period", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(q -> {
                    lessons.clear();
                    for (QueryDocumentSnapshot doc : q) {
                        Lesson L = doc.toObject(Lesson.class);
                        L.setId(doc.getId());
                        lessons.add(L);
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}