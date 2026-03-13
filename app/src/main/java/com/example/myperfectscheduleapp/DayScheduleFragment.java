package com.example.myperfectscheduleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class DayScheduleFragment extends Fragment {

    private static final String ARG_DAY = "day";
    private String dayName;
    private RecyclerView recyclerView;
    private LessonAdapter adapter;
    private List<ScheduleItem> lessonList = new ArrayList<>();
    private TextView tvEmpty;

    public static DayScheduleFragment newInstance(String day) {
        DayScheduleFragment fragment = new DayScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dayName = getArguments().getString(ARG_DAY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_schedule, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewLessons);
        tvEmpty      = view.findViewById(R.id.tvEmpty);

        adapter = new LessonAdapter(lessonList, item -> deleteLessonItem(item));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        loadLessons();
        return view;
    }

    private void loadLessons() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        ScheduleRepository.getInstance().getScheduleByDay(
                user.getUid(),
                dayName,
                items -> {
                    lessonList.clear();
                    lessonList.addAll(items);
                    adapter.notifyDataSetChanged();

                    if (lessonList.isEmpty()) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                },
                e -> tvEmpty.setVisibility(View.VISIBLE)
        );
    }

    private void deleteLessonItem(ScheduleItem item) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        ScheduleRepository.getInstance().deleteScheduleItem(
                user.getUid(),
                item.getDocumentId(),
                aVoid -> loadLessons(),
                e -> {}
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLessons(); // טעינה מחדש כל פעם שחוזרים לטאב
    }
}
