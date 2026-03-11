package com.example.myperfectscheduleapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<ScheduleItem> scheduleList;

    public ScheduleAdapter(List<ScheduleItem> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleItem item = scheduleList.get(position);
        holder.textTitle.setText(item.getSubject());
        holder.textTime.setText(item.getStartTime() + " - " + item.getEndTime());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textTime;
        View statusCircle;
        ImageButton btnNote, btnAlarm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // IDs אלו חייבים להיות קיימים ב-item_schedule.xml
            textTitle = itemView.findViewById(R.id.textTitle);
            textTime = itemView.findViewById(R.id.textTime);
            statusCircle = itemView.findViewById(R.id.statusCircle);
            btnNote = itemView.findViewById(R.id.btnNote);
            btnAlarm = itemView.findViewById(R.id.btnAlarm);
        }
    }
}