package com.example.ntasks.rents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;

import java.util.List;

public class RentAlertsAdapter extends RecyclerView.Adapter<RentAlertsAdapter.ViewHolder> {

    private Context context;
    private List<Tenant> itemList;

    public RentAlertsAdapter(Context context, List<Tenant> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ralertitems, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tenant tenant = itemList.get(position);

        holder.tvTenantName.setText("Name: " + tenant.getTenantName());
        holder.tvApartment.setText("Apartment: " + tenant.getPropertyName());
        holder.tvDateOfPayment.setText("Date Of Payment: " + tenant.getPayday());
        holder.tvRentAmount.setText("Amount: " + tenant.getTenantRent());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenantName, tvApartment, tvDateOfPayment, tvRentAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenantName = itemView.findViewById(R.id.tvTenantName);
            tvApartment = itemView.findViewById(R.id.tvapartment);
            tvDateOfPayment = itemView.findViewById(R.id.tvdop);
            tvRentAmount = itemView.findViewById(R.id.tvRentAmt);
        }
    }
}
