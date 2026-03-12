package com.example.myperfectscheduleapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SchedulePagerAdapter extends FragmentStateAdapter {

    private final String[] days;

    public SchedulePagerAdapter(@NonNull FragmentActivity fragmentActivity, String[] days) {
        super(fragmentActivity);
        this.days = days;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return DayScheduleFragment.newInstance(days[position]);
    }

    @Override
    public int getItemCount() {
        return days.length;
    }
}