/*
package com.example.ntasks;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Userlist> list;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(Context context, ArrayList<Userlist> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Userlist userlist = list.get(position);
        holder.taskNamedb.setText(userlist.getTaskName());
        holder.taskDescriptiondb.setText(userlist.getTaskDesc());
        holder.prioritydb.setText(userlist.getTaskprio());
        holder.deadlinedb.setText(userlist.getTaskdeadl());

        String priority = userlist.getTaskprio();

        switch (priority.toLowerCase()) {
            case "high":
                holder.prioritydb.setBackgroundResource(R.drawable.rounded_bg_priohigh);
                break;
            case "medium":
                holder.prioritydb.setBackgroundResource(R.drawable.rounded_bg_priomed);
                break;
            case "low":
                holder.prioritydb.setBackgroundResource(R.drawable.rounded_bg_priolow);
                break;
            default:
                holder.prioritydb.setBackgroundResource(R.drawable.rounded_bg);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Userlist userlist);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView taskNamedb, taskDescriptiondb, prioritydb, deadlinedb; // statusdb

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNamedb = itemView.findViewById(R.id.textViewTaskName);
            taskDescriptiondb = itemView.findViewById(R.id.textViewTaskDescription);
            prioritydb = itemView.findViewById(R.id.textViewPriority);
            deadlinedb = itemView.findViewById(R.id.textViewDeadline);

            // Add the following line if you've added a new TextView for displaying status
            // statusdb = itemView.findViewById(R.id.textViewStatus);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(list.get(position));
                }
            }
        }
    }
}*/
package com.example.ntasks;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Userlist> list;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(Context context, ArrayList<Userlist> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new MyViewHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Userlist userlist = list.get(position);
        holder.taskNamedb.setText(userlist.getTaskName());
        holder.taskDescriptiondb.setText(userlist.getTaskDesc());
        holder.prioritydb.setText(userlist.getTaskprio());
        holder.deadlinedb.setText(userlist.getTaskdeadl());

        String priority = userlist.getTaskprio();

        if (priority != null) {
            switch (priority.toLowerCase()) {
                case "high":
                    holder.prioritydb.setBackgroundResource(R.drawable.rounded_bg_priohigh);
                    break;
                case "medium":
                    holder.prioritydb.setBackgroundResource(R.drawable.rounded_bg_priomed);
                    break;
                case "low":
                    holder.prioritydb.setBackgroundResource(R.drawable.rounded_bg_priolow);
                    break;
                default:
                    holder.prioritydb.setBackgroundResource(R.drawable.rounded_bg);
                    break;
            }
        } else {
            // Handle the case where priority is null (optional, depending on your logic)
            // You might want to set a default background or handle it differently
            holder.prioritydb.setBackgroundResource(R.drawable.rounded_bg);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Userlist userlist);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView taskNamedb, taskDescriptiondb, prioritydb, deadlinedb; // statusdb
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            taskNamedb = itemView.findViewById(R.id.textViewTaskName);
            taskDescriptiondb = itemView.findViewById(R.id.textViewTaskDescription);
            prioritydb = itemView.findViewById(R.id.textViewPriority);
            deadlinedb = itemView.findViewById(R.id.textViewDeadline);

            // Add the following line if you've added a new TextView for displaying status
            // statusdb = itemView.findViewById(R.id.textViewStatus);

            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(list.get(position));
                }
            }
        }
    }
}
