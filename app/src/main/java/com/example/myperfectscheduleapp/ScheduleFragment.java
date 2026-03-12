package com.example.myperfectscheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ScheduleFragment extends Fragment {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private final String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        viewPager = view.findViewById(R.id.viewPagerDays);
        tabLayout = view.findViewById(R.id.tabLayoutDays);
        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);

        SchedulePagerAdapter adapter = new SchedulePagerAdapter(requireActivity(), days);
        viewPager.setAdapter(adapter);

        // ✅ חיבור TabLayout עם ViewPager
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(days[position])
        ).attach();

        // ✅ כפתור FAB פותח את WeeklyScheduleActivity
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WeeklyScheduleActivity.class);
            startActivity(intent);
        });

        return view;
    }
}