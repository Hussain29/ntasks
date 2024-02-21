package com.example.ntasks.PO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.PO.PurchaseOrder;
import com.example.ntasks.R;

import java.util.List;

public class POAdapter extends RecyclerView.Adapter<POAdapter.POViewHolder> {

    private Context context;
    private List<PurchaseOrder> poList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(PurchaseOrder po);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public POAdapter(Context context, List<PurchaseOrder> poList) {
        this.context = context;
        this.poList = poList;
    }

    @NonNull
    @Override
    public POViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.po_item, parent, false);
        return new POViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull POViewHolder holder, int position) {
        PurchaseOrder po = poList.get(position);

        holder.tvPOSubject.setText(po.getPoSubject());
        holder.tvAssignedBy.setText("A.By: " + po.getAssigner());
        holder.tvAssignedTo.setText("A.To.: " + po.getAssignedUser());

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(po);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return poList.size();
    }

    public static class POViewHolder extends RecyclerView.ViewHolder {
        TextView tvPOSubject;
        TextView tvAssignedBy;
        TextView tvAssignedTo;

        public POViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPOSubject = itemView.findViewById(R.id.tvposubject);
            tvAssignedBy = itemView.findViewById(R.id.tvassignedby);
            tvAssignedTo = itemView.findViewById(R.id.tvassignedto);
        }
    }


}
