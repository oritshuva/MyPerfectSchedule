package com.example.myperfectscheduleapp;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment {

    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private ArrayList<ScheduleItem> scheduleList;

    public ScheduleFragment() {
        // constructor required
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerSchedule);

        scheduleList = new ArrayList<>();

        adapter = new ScheduleAdapter(scheduleList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        Button btnAdd = view.findViewById(R.id.btnAddSchedule);

        btnAdd.setOnClickListener(v -> showAddScheduleDialog());
    }

    // חשוב: public ולא private
    public void showAddScheduleDialog() {

        Dialog dialog = new Dialog(requireContext());

        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_schedule, null);

        dialog.setContentView(dialogView);

        EditText etSubject = dialogView.findViewById(R.id.etSubjectName);
        EditText etDay = dialogView.findViewById(R.id.etDay);
        EditText etStart = dialogView.findViewById(R.id.etStartTime);
        EditText etEnd = dialogView.findViewById(R.id.etEndTime);

        Button btnSave = dialogView.findViewById(R.id.btnSaveSchedule);

        btnSave.setOnClickListener(v -> {

            String subject = etSubject.getText().toString();
            String day = etDay.getText().toString();
            String start = etStart.getText().toString();
            String end = etEnd.getText().toString();

            if (TextUtils.isEmpty(subject) ||
                    TextUtils.isEmpty(day) ||
                    TextUtils.isEmpty(start) ||
                    TextUtils.isEmpty(end)) {
                return;
            }

            ScheduleItem item = new ScheduleItem(subject, day, start, end);

            scheduleList.add(item);

            adapter.notifyDataSetChanged();

            dialog.dismiss();
        });

        dialog.show();
    }
}