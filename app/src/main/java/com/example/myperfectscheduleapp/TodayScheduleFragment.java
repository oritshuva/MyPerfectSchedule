package com.example.myperfectscheduleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TodayScheduleFragment extends Fragment {

    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private List<ScheduleItem> scheduleList;
    private FirebaseFirestore db;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        db = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        recyclerView = view.findViewById(R.id.scheduleRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        scheduleList = new ArrayList<>();
        adapter = new ScheduleAdapter(scheduleList);
        recyclerView.setAdapter(adapter);

        loadTodaySchedule();

        return view;
    }

    private void loadTodaySchedule() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("he"));
        String today = dayFormat.format(calendar.getTime());

        db.collection("users")
                .document(userId)
                .collection("schedules")
                .whereEqualTo("day", today)
                .orderBy("startTime", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    scheduleList.clear();
                    scheduleList.addAll(queryDocumentSnapshots.toObjects(ScheduleItem.class));
                    adapter.notifyDataSetChanged();
                });
    }
}
