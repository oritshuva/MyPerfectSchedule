package com.example.myperfectscheduleapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WeeklyScheduleActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private FloatingActionButton fabAdd;
    private SchedulePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);

        viewPager = findViewById(R.id.viewPager);
        fabAdd = findViewById(R.id.fabAdd);

        adapter = new SchedulePagerAdapter(this);
        viewPager.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {

            int page = viewPager.getCurrentItem();

            Fragment fragment =
                    getSupportFragmentManager().findFragmentByTag("f" + page);

            if (fragment == null) return;

            // לוז רגיל
            if (fragment instanceof ScheduleFragment) {
                ((ScheduleFragment) fragment).showAddScheduleDialog();
            }

            // משימות
            if (fragment instanceof TasksFragment) {
                ((TasksFragment) fragment).showAddTaskDialog();
            }

        });
    }
}