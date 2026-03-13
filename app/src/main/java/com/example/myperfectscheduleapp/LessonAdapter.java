package com.example.myperfectscheduleapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    public interface OnDeleteClickListener {
        void onDelete(ScheduleItem item);
    }

    private final List<ScheduleItem> items;
    private final OnDeleteClickListener deleteListener;

    public LessonAdapter(List<ScheduleItem> items, OnDeleteClickListener listener) {
        this.items          = items;
        this.deleteListener = listener;
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
        ScheduleItem item = items.get(position);

        holder.tvSubject.setText(item.getSubject());
        holder.tvTime.setText(item.getStartTime() + " - " + item.getEndTime());
        holder.tvRoom.setText("חדר: " + (item.getRoom() != null
                && !item.getRoom().isEmpty() ? item.getRoom() : "לא צוין"));
        holder.tvPeriod.setText("שיעור " + item.getPeriod());

        holder.btnDelete.setOnClickListener(v -> deleteListener.onDelete(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubject, tvTime, tvRoom, tvPeriod;
        ImageButton btnDelete;

        LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject  = itemView.findViewById(R.id.tvSubject);
            tvTime     = itemView.findViewById(R.id.tvTime);
            tvRoom     = itemView.findViewById(R.id.tvRoom);
            tvPeriod   = itemView.findViewById(R.id.tvPeriod);
            btnDelete  = itemView.findViewById(R.id.btnDelete);
        }
    }
}
