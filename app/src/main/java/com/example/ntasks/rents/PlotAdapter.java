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

import java.util.List;

public class PlotAdapter extends RecyclerView.Adapter<PlotAdapter.ViewHolder> {

    private final Context context;
    private final List<Plot> plotList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Plot plot);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public PlotAdapter(Context context, List<Plot> plotList) {
        this.context = context;
        this.plotList = plotList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plotitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Plot plot = plotList.get(position);

        // Set values to views
        holder.imgPlot.setImageResource(R.drawable.plot);
        holder.tvPlotName.setText(plot.getPltName());
        holder.tvPlotArea.setText("Area: " + plot.getPltArea());

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(plotList.get(adapterPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return plotList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPlot;
        TextView tvPlotName, tvPlotArea;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlot = itemView.findViewById(R.id.imgplot);
            tvPlotName = itemView.findViewById(R.id.tvplotname);
            tvPlotArea = itemView.findViewById(R.id.tvplotadd);
        }
    }
}
