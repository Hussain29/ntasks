package com.example.ntasks.rents;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ntasks.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// CollectionAdapter.java
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
        holder.tvCollectionName.setText(collection.getTenantName());
        holder.tvCollectionAmount.setText(String.valueOf(collection.getRentAmount()));
    }

    @Override
    public int getItemCount() {
        return collectionList.size();
    }

    public static class CollectionViewHolder extends RecyclerView.ViewHolder {
        TextView tvCollectionName;
        TextView tvCollectionAmount;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCollectionName = itemView.findViewById(R.id.tvCollectionName);
            tvCollectionAmount = itemView.findViewById(R.id.tvCollectionAmount);
        }
    }
}

