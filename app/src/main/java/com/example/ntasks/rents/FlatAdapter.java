package com.example.ntasks.rents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;
import com.example.ntasks.rents.Flats;

import java.util.ArrayList;

public class FlatAdapter extends RecyclerView.Adapter<FlatAdapter.FlatViewHolder> {

    private Context context;
    private ArrayList<Flats> flatList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Flats flats);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public FlatAdapter(Context context, ArrayList<Flats> flatList) {
        this.context = context;
        this.flatList = flatList;
    }

    @NonNull
    @Override
    public FlatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.flat_item, parent, false);
        return new FlatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlatViewHolder holder, int position) {
        Flats flats = flatList.get(position);

        holder.tvFlatNo.setText(flats.getFlatNo());
        holder.tvFlatArea.setText(flats.getApartmentName());

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(flatList.get(adapterPosition));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return flatList.size();
    }

    public static class FlatViewHolder extends RecyclerView.ViewHolder {
        TextView tvFlatNo;
        TextView tvFlatArea;

        public FlatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFlatNo = itemView.findViewById(R.id.tvflatno);
            tvFlatArea = itemView.findViewById(R.id.tvflatadd);
        }
    }
}
