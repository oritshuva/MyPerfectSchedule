package com.example.myperfectscheduleapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<TaskItem> taskList;

    public TaskAdapter(List<TaskItem> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TaskItem task = taskList.get(position);
        holder.textName.setText(task.getTaskName());
        holder.textSub.setText(task.getSubject());

        if (task.getDueDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault());
            holder.textDate.setText(sdf.format(task.getDueDate().toDate()));
        }

        // 🎨 צביעה לפי דחיפות
        String urgency = task.getUrgency() != null ? task.getUrgency() : "medium";
        switch (urgency.toLowerCase()) {
            case "high":
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFEBEE")); // אדום בהיר
                holder.urgencyIndicator.setBackgroundColor(Color.parseColor("#D32F2F"));
                break;
            case "low":
                holder.cardView.setCardBackgroundColor(Color.parseColor("#E8F5E9")); // ירוק בהיר
                holder.urgencyIndicator.setBackgroundColor(Color.parseColor("#388E3C"));
                break;
            default: // medium
                holder.cardView.setCardBackgroundColor(Color.parseColor("#FFF3E0")); // כתום בהיר
                holder.urgencyIndicator.setBackgroundColor(Color.parseColor("#F57C00"));
                break;
        }
    }

    @Override
    public int getItemCount() { return taskList.size(); }

    public void updateTasks(List<TaskItem> newTasks) {
        this.taskList = newTasks;
        notifyDataSetChanged();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        View urgencyIndicator;
        TextView textName, textSub, textDate;

        public TaskViewHolder(View v) {
            super(v);
            cardView = (CardView) v;
            urgencyIndicator = v.findViewById(R.id.urgencyIndicator);
            textName = v.findViewById(R.id.textTaskName);
            textSub = v.findViewById(R.id.textTaskSubject);
            textDate = v.findViewById(R.id.textTaskDate);
        }
    }
}
