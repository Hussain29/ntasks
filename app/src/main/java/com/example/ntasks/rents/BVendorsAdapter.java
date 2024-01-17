package com.example.ntasks.rents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;

import java.util.ArrayList;

public class BVendorsAdapter extends RecyclerView.Adapter<BVendorsAdapter.BVendorsViewHolder> {

    private Context context;
    private ArrayList<BusinessVendor> vendorList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(BusinessVendor vendor);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BVendorsAdapter(Context context, ArrayList<BusinessVendor> vendorList) {
        this.context = context;
        this.vendorList = vendorList;
    }

    @NonNull
    @Override
    public BVendorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bivendoritem, parent, false);
        return new BVendorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BVendorsViewHolder holder, int position) {
        BusinessVendor vendor = vendorList.get(position);

        holder.tvVendorName.setText(vendor.getVendorName());
        holder.tvVendorProducts.setText(vendor.getProducts());

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(vendorList.get(adapterPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }

    public static class BVendorsViewHolder extends RecyclerView.ViewHolder {
        TextView tvVendorName;
        TextView tvVendorProducts;

        public BVendorsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVendorName = itemView.findViewById(R.id.tvbvendorname);
            tvVendorProducts = itemView.findViewById(R.id.tvbvendorprods);
        }
    }
}
