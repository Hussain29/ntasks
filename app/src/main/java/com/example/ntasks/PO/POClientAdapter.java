package com.example.ntasks.PO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ntasks.R;

import java.util.ArrayList;

public class POClientAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> clientNames;

    public POClientAdapter(Context context, ArrayList<String> clientNames) {
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

        // Check if the client name contains '*' symbol (indicating pending POs)
        if (clientName.contains("*")) {
            // If yes, set the text with different color or style to highlight
            clientName = clientName.replaceAll("\\s*\\*", ""); // Remove '*' symbol and any surrounding spaces
            textViewClientName.setText(clientName);
            textViewClientName.setTextColor(context.getResources().getColor(R.color.pendingcolour));

            // You can also set a different color or style for the '*' symbol
        } else {
            // If no pending POs, display the client name as it is
            textViewClientName.setText(clientName);
        }

        return view;
    }
}
