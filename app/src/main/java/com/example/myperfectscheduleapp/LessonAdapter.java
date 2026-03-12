package com.example.myperfectscheduleapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private final List<ScheduleItem> lessons;

    public LessonAdapter(List<ScheduleItem> lessons) {
        this.lessons = lessons;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lesson, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        ScheduleItem lesson = lessons.get(position);

        // Period
        holder.periodTextView.setText(
                holder.itemView.getContext().getString(R.string.period_placeholder, lesson.getPeriod())
        );

        // Subject
        holder.subjectTextView.setText(lesson.getSubject());

        // Time
        holder.timeTextView.setText(
                holder.itemView.getContext().getString(R.string.time_placeholder,
                        lesson.getStartTime(), lesson.getEndTime())
        );

        // Room
        holder.roomTextView.setText(
                holder.itemView.getContext().getString(R.string.room_placeholder, lesson.getRoom())
        );
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView periodTextView;
        TextView subjectTextView;
        TextView timeTextView;
        TextView roomTextView;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            periodTextView = itemView.findViewById(R.id.periodTextView);
            subjectTextView = itemView.findViewById(R.id.subjectTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            roomTextView = itemView.findViewById(R.id.roomTextView);
        }
    }
}
