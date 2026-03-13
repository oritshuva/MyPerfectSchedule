package com.example.myperfectscheduleapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    private List<ScheduleItem> scheduleList;

    public ScheduleAdapter(List<ScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleItem item = scheduleList.get(position);

        // הצגת הנתונים
        holder.tvSubject.setText(item.getSubject());
        holder.tvTime.setText(item.getStartTime() + " - " + item.getEndTime());
        holder.tvRoom.setText("חדר: " + item.getRoom());
        holder.textPeriod.setText(String.valueOf(item.getPeriod()));
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubject, tvTime, tvRoom, textPeriod;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            textPeriod = itemView.findViewById(R.id.textPeriod);
        }
    }
}
