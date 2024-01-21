package com.example.ntasks.rents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;

import java.util.List;

public class TenantAdapter extends RecyclerView.Adapter<TenantAdapter.ViewHolder> {

    private final Context context;
    private final List<Tenant> tenantList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Tenant tenant);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TenantAdapter(Context context, List<Tenant> tenantList) {
        this.context = context;
        this.tenantList = tenantList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tenantitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tenant tenant = tenantList.get(position);

        // Set values to views
        /*holder.imgTenant.setImageResource(R.drawable.addpersonn);*/ // You can change this image based on your requirements
        holder.tvTenantName.setText(tenant.getTenantName());
        holder.tvTenantProperty.setText("FLAT/INDEPENDENT: " + tenant.getPropertyName());

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(tenantList.get(adapterPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tenantList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgTenant;
        TextView tvTenantName, tvTenantProperty;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /*imgTenant = itemView.findViewById(R.id.imgtenant);*/
            tvTenantName = itemView.findViewById(R.id.tvtenantname);
            tvTenantProperty = itemView.findViewById(R.id.tvtenantprop);

            // You can set onClickListener or other view-related operations here if needed
        }
    }
}
