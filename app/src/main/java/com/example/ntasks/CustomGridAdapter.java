package com.example.ntasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ntasks.R;

import java.util.ArrayList;

// CustomGridAdapter.java
public class CustomGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> clientNames;

    public CustomGridAdapter(Context context, ArrayList<String> clientNames) {
        this.context = context;
        this.clientNames = clientNames;
    }

    @Override
    public int getCount() {
        return clientNames.size();
    }

    @Override
    public Object getItem(int position) {
        return clientNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item_layout, null);
        }

        // Get the current client name
        String clientName = clientNames.get(position);

        // Set the client name to the TextView in the custom layout
        TextView textViewClientName = view.findViewById(R.id.textViewClientName);
        textViewClientName.setText(clientName);

        return view;
    }
}
