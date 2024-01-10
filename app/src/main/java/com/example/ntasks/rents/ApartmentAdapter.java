package com.example.ntasks.rents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;

import java.util.ArrayList;

public class ApartmentAdapter extends RecyclerView.Adapter<ApartmentAdapter.ApartmentViewHolder> {

    private Context context;
    private ArrayList<Apartment> apartmentList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Apartment apartment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ApartmentAdapter(Context context, ArrayList<Apartment> apartmentList) {
        this.context = context;
        this.apartmentList = apartmentList;
    }

    @NonNull
    @Override
    public ApartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.apart_item, parent, false);
        return new ApartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApartmentViewHolder holder, int position) {
        Apartment apartment = apartmentList.get(position);

        holder.tvApartmentName.setText(apartment.getAptName());
        holder.tvApartmentArea.setText(apartment.getAptAddress());

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(apartmentList.get(adapterPosition));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return apartmentList.size();
    }

    public static class ApartmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvApartmentName;
        TextView tvApartmentArea;

        public ApartmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvApartmentName = itemView.findViewById(R.id.tvapart);
            tvApartmentArea = itemView.findViewById(R.id.tvaptad);
        }
    }
}

