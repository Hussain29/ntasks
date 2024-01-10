package com.example.ntasks.rents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;
import com.example.ntasks.rents.Vendor; // Make sure to import the correct Vendor class

import java.util.ArrayList;

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.VendorViewHolder> {

    private Context context;
    private ArrayList<Vendor> vendorList;

    public VendorAdapter(Context context, ArrayList<Vendor> vendorList) {
        this.context = context;
        this.vendorList = vendorList;
    }

    @NonNull
    @Override
    public VendorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vendoritem, parent, false);
        return new VendorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendorViewHolder holder, int position) {
        Vendor vendor = vendorList.get(position);

        holder.textViewVendorName.setText(vendor.getVendorName());
        holder.textViewVendorPhone.setText(vendor.getVendorPhone1());
    }

    @Override
    public int getItemCount() {
        return vendorList.size();
    }

    public static class VendorViewHolder extends RecyclerView.ViewHolder {
        TextView textViewVendorName;
        TextView textViewVendorPhone;

        public VendorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewVendorName = itemView.findViewById(R.id.textViewTaskName); // Corrected to match the actual ID
            textViewVendorPhone = itemView.findViewById(R.id.textViewTaskDescription); // Corrected to match the actual ID
        }
    }
}
