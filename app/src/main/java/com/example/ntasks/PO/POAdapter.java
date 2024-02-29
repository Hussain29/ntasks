package com.example.ntasks.PO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;

import java.util.ArrayList;
import java.util.List;

public class POAdapter extends RecyclerView.Adapter<POAdapter.POViewHolder> implements Filterable {

    private Context context;
    private List<PurchaseOrder> poList;
    private List<PurchaseOrder> filteredList;
    private boolean isFiltering = false;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onBindViewHolder(@NonNull POViewHolder holder, int position);

        void onItemClick(PurchaseOrder po);

        boolean onQueryTextSubmit(String query);

        boolean onQueryTextChange(String newText);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public POAdapter(Context context, List<PurchaseOrder> poList) {
        this.context = context;
        this.poList = poList;
        this.filteredList = new ArrayList<>(poList);

    }


    public void filter(String query) {
        /*filteredList.clear();*/
        if (query.isEmpty()) {
            filteredList.clear();
/*
            isFiltering = false;
*/
            filteredList.addAll(poList);
        } else {
            filteredList.clear();
            isFiltering = true;
            query = query.toLowerCase().trim();
            for (PurchaseOrder po : poList) {
                if (po.getPoSubject().toLowerCase().contains(query)) {
                    filteredList.add(po);
                }
            }
        }
        notifyDataSetChanged();
    }
    public void setDataList(ArrayList<PurchaseOrder> poList) {
        this.poList = poList;
        this.filteredList = new ArrayList<>(poList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public POViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.po_item, parent, false);
        return new POViewHolder(view);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase().trim();
                List<PurchaseOrder> filteredList = new ArrayList<>();

                if (charString.isEmpty()) {
                    filteredList.addAll(poList);
                } else {
                    for (PurchaseOrder po : poList) {
                        if (po.getPoSubject().toLowerCase().contains(charString)) {
                            filteredList.add(po);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List<PurchaseOrder>) results.values);
                notifyDataSetChanged();
            }
        };
    }



    @Override
    public void onBindViewHolder(@NonNull POViewHolder holder, int position) {
        PurchaseOrder po = filteredList.get(position);

        holder.tvPOSubject.setText(po.getPoSubject());
        holder.tvAssignedBy.setText("A.By: " + po.getAssigner());
        holder.tvAssignedTo.setText("A.To.: " + po.getAssignedUser());
        holder.tvClientName.setText(po.getClient());

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(po);
                }
            }
        });

        String statuspo = po.getStatus();
        if (statuspo != null) {
            switch (statuspo.toLowerCase()) {
                case "completed":
                    holder.itemView.setBackgroundResource(R.drawable.done);
                    break;
                case "done":
                    holder.itemView.setBackgroundResource(R.drawable.done);
                    break;
                default:
                    holder.itemView.setBackgroundResource(R.drawable.assigned);
                    break;
            }
        } else {
            holder.itemView.setBackgroundResource(R.drawable.assigned);
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public PurchaseOrder getItem(int position) {
        return filteredList.get(position);
    }

    public static class POViewHolder extends RecyclerView.ViewHolder {
        TextView tvPOSubject;
        TextView tvAssignedBy;
        TextView tvAssignedTo;
        TextView tvClientName;

        public POViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPOSubject = itemView.findViewById(R.id.tvposubject);
            tvAssignedBy = itemView.findViewById(R.id.tvassignedby);
            tvAssignedTo = itemView.findViewById(R.id.tvassignedto);
            tvClientName = itemView.findViewById(R.id.clientname);
        }
    }
}
