package com.example.myperfectscheduleapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SchedulePagerAdapter extends FragmentStateAdapter {

    private final ScheduleFragment scheduleFragment = new ScheduleFragment();
    private final AfterSchoolFragment afterSchoolFragment = new AfterSchoolFragment();
    private final TasksFragment tasksFragment = new TasksFragment();

    public SchedulePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return scheduleFragment;
        }
        else if (position == 1) {
            return afterSchoolFragment;
        }
        else {
            return tasksFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public TasksFragment getTasksFragment() {
        return tasksFragment;
    }
}