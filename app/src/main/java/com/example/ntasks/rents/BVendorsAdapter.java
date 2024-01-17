package com.example.ntasks.rents;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;

import java.util.ArrayList;
public class BVendorsAdapter extends RecyclerView.Adapter<BVendorsAdapter.BVendorsViewHolder> implements Filterable {

    private Context context;
    private ArrayList<BusinessVendor> vendorList;
    private ArrayList<BusinessVendor> filteredList;
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
        this.filteredList = new ArrayList<>(vendorList);
    }

    @NonNull
    @Override
    public BVendorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.bivendoritem, parent, false);
        return new BVendorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BVendorsViewHolder holder, int position) {
        BusinessVendor vendor = filteredList.get(position);

        holder.tvVendorName.setText(vendor.getCompanyName());
        holder.tvVendorProducts.setText(vendor.getCompanyProducts());

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(filteredList.get(adapterPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void setDataList(ArrayList<BusinessVendor> dataList) {
        this.vendorList = dataList;
        this.filteredList = new ArrayList<>(dataList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                if (TextUtils.isEmpty(filterPattern)) {
                    filteredList.clear();
                    filteredList.addAll(vendorList);
                } else {
                    filteredList.clear();
                    for (BusinessVendor vendor : vendorList) {
                        if (vendor.getCompanyPocName().toLowerCase().contains(filterPattern) ||vendor.getCompanyName().toLowerCase().contains(filterPattern) ||
                                vendor.getCompanyTelephone().contains(filterPattern)||vendor.getCompanyProducts().toLowerCase().contains(filterPattern)) {
                            filteredList.add(vendor);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
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
