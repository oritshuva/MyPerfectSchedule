package com.example.myperfectscheduleapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SetupLessonAdapter extends RecyclerView.Adapter<SetupLessonAdapter.VH> {

    private final List<Lesson> lessons;

    public SetupLessonAdapter(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_setup_lesson_row, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Lesson L = lessons.get(position);
        holder.tvRowPeriod.setText(String.valueOf(L.getPeriod()));
        holder.tvRowSubject.setText(L.getSubject());
        holder.tvRowTime.setText(L.getStartTime() + " - " + L.getEndTime());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvRowPeriod, tvRowSubject, tvRowTime;
        public VH(@NonNull View itemView) {
            super(itemView);
            tvRowPeriod  = itemView.findViewById(R.id.tvRowPeriod);
            tvRowSubject = itemView.findViewById(R.id.tvRowSubject);
            tvRowTime    = itemView.findViewById(R.id.tvRowTime);
        }
    }
}