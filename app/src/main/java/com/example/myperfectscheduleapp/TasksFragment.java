package com.example.myperfectscheduleapp;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class TasksFragment extends Fragment {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private ArrayList<TaskItem> taskList;

    public TasksFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recyclerTasks);

        taskList = new ArrayList<>();

        adapter = new TaskAdapter(taskList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    public void showAddTaskDialog() {

        Dialog dialog = new Dialog(requireContext());

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_add_task, null);

        dialog.setContentView(view);

        EditText title = view.findViewById(R.id.etTaskTitle);
        EditText description = view.findViewById(R.id.etTaskDescription);
        EditText date = view.findViewById(R.id.etTaskDate);

        Button save = view.findViewById(R.id.btnSaveTask);

        save.setOnClickListener(v -> {

            TaskItem task = new TaskItem(
                    title.getText().toString(),
                    description.getText().toString(),
                    date.getText().toString()
            );

            taskList.add(task);
            adapter.notifyDataSetChanged();

            // יצירת התראה
            Intent intent = new Intent(getContext(), ReminderReceiver.class);
            intent.putExtra("title", title.getText().toString());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getContext(),
                    (int) System.currentTimeMillis(),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager alarmManager =
                    (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);

            long triggerTime = System.currentTimeMillis() + 60000;

            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
            );

            dialog.dismiss();
        });

        dialog.show();
    }
}