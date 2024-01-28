package com.example.ntasks.rents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IndependentAdapter extends RecyclerView.Adapter<IndependentAdapter.ViewHolder> {

    private final Context context;
    private final List<Independent> independentList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Independent independent);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public IndependentAdapter(Context context, List<Independent> independentList) {
        this.context = context;
        this.independentList = independentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.independentitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Independent independent = independentList.get(position);

        // Set values to views
        if (independent.getImgUrl() != null && !independent.getImgUrl().isEmpty()) {
            Picasso.get().load(independent.getImgUrl()).into(holder.imgIndp);
        }

        holder.tvIndpName.setText(independent.getIndpName());
        holder.tvIndpArea.setText("Area: " + independent.getIndpArea());

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(independentList.get(adapterPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return independentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIndp;
        TextView tvIndpName, tvIndpArea;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIndp = itemView.findViewById(R.id.ivindp);
            tvIndpName = itemView.findViewById(R.id.tvindpname);
            tvIndpArea = itemView.findViewById(R.id.tvindparea);

            // You can set onClickListener or other view-related operations here if needed
        }
    }
}
