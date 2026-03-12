package com.example.myperfectscheduleapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class ScheduleViewModel extends ViewModel {
    private final ScheduleRepository repository;

    // Constructor - singleton משתמש ב-ScheduleRepository
    public ScheduleViewModel() {
        repository = ScheduleRepository.getInstance();
    }

    // טעינת לוח זמנים ליום מסוים
    public LiveData<List<ScheduleItem>> loadScheduleForDay(String uid, String day) {
        return repository.getScheduleForDay(uid, day);
    }
}
