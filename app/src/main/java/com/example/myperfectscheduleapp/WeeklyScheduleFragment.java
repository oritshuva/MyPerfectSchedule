package com.example.myperfectscheduleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WeeklyScheduleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks_placeholder, container, false);

        TextView textView = view.findViewById(R.id.placeholderText);
        textView.setText("מערכת שעות שבועית\n(בפיתוח)");

        return view;
    }
}
