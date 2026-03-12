package com.example.myperfectscheduleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DayScheduleFragment extends Fragment {

    private static final String ARG_DAY = "day";

    private RecyclerView recyclerLessons;
    private TextView textCurrentTime;
    private ProgressBar progressBar;
    private LessonAdapter adapter;
    private List<ScheduleItem> lessonsList = new ArrayList<>();
    private ScheduleViewModel viewModel;

    public static DayScheduleFragment newInstance(String day) {
        DayScheduleFragment fragment = new DayScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerLessons = view.findViewById(R.id.recyclerLessons);
        textCurrentTime = view.findViewById(R.id.textCurrentTime);
        progressBar = view.findViewById(R.id.progressBar);

        adapter = new LessonAdapter(lessonsList);
        recyclerLessons.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerLessons.setAdapter(adapter);

        String day = getArguments() != null ? getArguments().getString(ARG_DAY) : "ראשון";

        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        progressBar.setVisibility(View.VISIBLE);

        viewModel.loadScheduleForDay(uid, day).observe(getViewLifecycleOwner(), items -> {
            if (items != null) {
                lessonsList.clear();
                lessonsList.addAll(items);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
