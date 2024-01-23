package com.example.ntasks.rents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private Context context;
    private ArrayList<Employee> employeeList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Employee employee);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public EmployeeAdapter(Context context, ArrayList<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hremployee, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);

        // Set employee data to the views
        holder.employeeNameTextView.setText(employee.getEmpName());
        holder.employeeContactTextView.setText(employee.getEmpEmCon1());

        // If you have an employee image, you can set it here
        if (employee.getPhotoUrl() != null && !employee.getPhotoUrl().isEmpty()) {
            Picasso.get().load(employee.getPhotoUrl()).into(holder.employeeImageView);
        }

        // Set click listener for the item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (mListener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(employeeList.get(adapterPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView employeeNameTextView;
        TextView employeeContactTextView;
        ImageView employeeImageView;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeNameTextView = itemView.findViewById(R.id.tvHRemployeeName);
            employeeContactTextView = itemView.findViewById(R.id.tvHRempContact);
            employeeImageView = itemView.findViewById(R.id.ivhremployeeImg);
        }
    }
}

