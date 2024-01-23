// CollectionAdapter.java
package com.example.ntasks.rents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder> {

    private List<Collection> collectionList;

    public CollectionAdapter(List<Collection> collectionList) {
        this.collectionList = collectionList;
    }

    @NonNull
    @Override
    public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
        Collection collection = collectionList.get(position);

        // Bind data to the views in the item_collection.xml layout
        holder.tvPropertyName.setText(collection.getPropertyName()); // Set property name
        holder.tvCollectionName.setText(collection.getTenantName());
        holder.tvCollectionAmount.setText(String.valueOf(collection.getRentAmount()));
        holder.tvDate.setText(collection.getSelectedDate()); // Set selected date
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder {
        TextView tvPropertyName; // New field for property name
        TextView tvCollectionName;
        TextView tvCollectionAmount;
        TextView tvDate; // New field for selected date

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPropertyName = itemView.findViewById(R.id.tvPropertyName); // Initialize the new field
            tvCollectionName = itemView.findViewById(R.id.tvCollectionName);
            tvCollectionAmount = itemView.findViewById(R.id.tvCollectionAmount);
            tvDate = itemView.findViewById(R.id.tvdate); // Initialize the new field
        }
    }
}
