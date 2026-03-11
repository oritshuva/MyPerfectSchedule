package com.example.myperfectscheduleapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SchedulePagerAdapter extends FragmentStateAdapter {

    public SchedulePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new TodayFragment();
            case 1: return new ScheduleFragment();
            case 2: return new TasksFragment();
            case 3: return new AfterSchoolFragment();
            default: return new TodayFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}