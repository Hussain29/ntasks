package com.example.ntasks.rents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.OwnerViewHolder> {

    private Context context;
    private ArrayList<Owner> ownerList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Owner owner);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

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

        holder.tvOwnerName.setText(owner.getOwnerName());
        holder.tvPhone.setText(owner.getOwnerPhone1());

        if (owner.getPhotoUrl() != null && !owner.getPhotoUrl().isEmpty()) {
            Picasso.get().load(owner.getPhotoUrl()).into(holder.ivOwner);
        }

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(ownerList.get(adapterPosition));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return ownerList.size();
    }

    public static class OwnerViewHolder extends RecyclerView.ViewHolder {
        TextView tvOwnerName;
        TextView tvPhone;
        ImageView ivOwner;

        public OwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOwnerName = itemView.findViewById(R.id.textViewTaskName);
            tvPhone = itemView.findViewById(R.id.textViewTaskDescription);
            ivOwner = itemView.findViewById(R.id.ivOwner);
        }
    }
}
