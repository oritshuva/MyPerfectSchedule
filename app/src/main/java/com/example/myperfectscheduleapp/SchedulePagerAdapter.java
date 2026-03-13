package com.example.myperfectscheduleapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SchedulePagerAdapter extends FragmentStateAdapter {

    private final int numPages;

    private final String[] DAYS = {
            "ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי"
    };

    public SchedulePagerAdapter(@NonNull FragmentActivity activity, int numPages) {
        super(activity);
        this.numPages = numPages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // שולח את שם היום ל-Fragment
        return DayScheduleFragment.newInstance(DAYS[position]);
    }

    @Override
    public int getItemCount() {
        return numPages;
    }
}
