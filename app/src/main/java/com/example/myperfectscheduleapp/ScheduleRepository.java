package com.example.myperfectscheduleapp;

import java.util.ArrayList;
import java.util.List;

public class ScheduleRepository {

    private static ScheduleRepository instance;

    private final List<ScheduleItem> items = new ArrayList<>();

    private ScheduleRepository() {}

    public static ScheduleRepository getInstance() {

        if (instance == null) {
            instance = new ScheduleRepository();
        }

        return instance;
    }

    public void add(ScheduleItem item) {
        items.add(item);
    }

    public List<ScheduleItem> getByDay(String day) {

        List<ScheduleItem> result = new ArrayList<>();

        for (ScheduleItem item : items) {

            // הגנה מפני null
            if (item.getDay() != null && item.getDay().equals(day)) {
                result.add(item);
            }
        }

        return result;
    }

    public String exportSchedule() {

        StringBuilder builder = new StringBuilder();

        for (ScheduleItem item : items) {

            builder.append(item.getDay())
                    .append(" ")
                    .append(item.getSubject())
                    .append(" ")
                    .append(item.getStartTime())
                    .append("-")
                    .append(item.getEndTime())
                    .append("\n");
        }

        return builder.toString();
    }
}