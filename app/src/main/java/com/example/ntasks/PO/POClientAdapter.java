package com.example.ntasks.PO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ntasks.R;

import java.util.ArrayList;

public class POClientAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> clientNames;
    private ArrayList<Integer> pendingCounts;

    public POClientAdapter(Context context, ArrayList<String> clientNames, ArrayList<Integer> pendingCounts) {
        this.context = context;
        this.clientNames = clientNames;
        this.pendingCounts = pendingCounts;
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
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false);
            holder = new ViewHolder();
            holder.textViewClientName = convertView.findViewById(R.id.textViewClientName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the client name and pending count for the current position
        String clientName = clientNames.get(position);

        clientName = clientName.replaceAll("\\s*\\*", ""); // Remove '*' symbol and any surrounding spaces

        int pendingCount = pendingCounts.get(position);


        // Set the client name and pending count text
        holder.textViewClientName.setText(clientName + (pendingCount > 0 ? " (" + pendingCount + ")" : ""));

        // Set text color based on pending PO status
        if (pendingCount > 0) {
            holder.textViewClientName.setTextColor(context.getResources().getColor(R.color.pendingcolour));
        } else {
            holder.textViewClientName.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textViewClientName;
    }
}
