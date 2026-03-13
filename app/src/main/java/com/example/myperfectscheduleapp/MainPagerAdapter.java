package com.example.myperfectscheduleapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TodayScheduleFragment();
            case 1:
                return new TasksFragment();
            case 2:
                return new WeeklyScheduleFragment();
            case 3:
                return new AfterSchoolScheduleFragment();
            default:
                return new TodayScheduleFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public String getTabTitle(int position) {
        switch (position) {
            case 0:
                return "היום";
            case 1:
                return "משימות";
            case 2:
                return "מערכת שעות";
            case 3:
                return "אחרי ביה״ס";
            default:
                return "";
        }
    }
}
