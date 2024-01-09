package com.example.ntasks.rents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;
import com.example.ntasks.rents.Owner;

import java.util.ArrayList;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.OwnerViewHolder> {

    private Context context;
    private ArrayList<Owner> ownerList;

    public OwnerAdapter(Context context, ArrayList<Owner> ownerList) {
        this.context = context;
        this.ownerList = ownerList;
    }

    @NonNull
    @Override
    public OwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.owneritem, parent, false);
        return new OwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerViewHolder holder, int position) {
        Owner owner = ownerList.get(position);

        holder.textViewTaskName.setText(owner.getOwnerName());
        holder.textViewTaskDescription.setText(owner.getOwnerPhone1());
    }

    @Override
    public int getItemCount() {
        return ownerList.size();
    }

    public static class OwnerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTaskName;
        TextView textViewTaskDescription;

        public OwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTaskName = itemView.findViewById(R.id.textViewTaskName);
            textViewTaskDescription = itemView.findViewById(R.id.textViewTaskDescription);
        }
    }
}
