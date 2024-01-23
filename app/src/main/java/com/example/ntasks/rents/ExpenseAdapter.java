package com.example.ntasks.rents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expenses> expenseList;

    public ExpenseAdapter(List<Expenses> expenseList) {
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expenses expense = expenseList.get(position);

        // Bind data to the views in the item_expense.xml layout
        holder.tvExpenseDate.setText(expense.getSelectedDate());
        holder.tvPropertyName.setText(expense.getPropertyName());
        holder.tvExpenseName.setText(expense.getParticular());
        holder.tvExpenseAmount.setText(expense.getExpenseAmount());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvExpenseDate;
        TextView tvPropertyName;
        TextView tvExpenseName;
        TextView tvExpenseAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpenseDate = itemView.findViewById(R.id.tvExpenseDate);
            tvPropertyName = itemView.findViewById(R.id.tvPropertyName);
            tvExpenseName = itemView.findViewById(R.id.tvExpenseName);
            tvExpenseAmount = itemView.findViewById(R.id.tvExpenseAmount);
        }
    }
}
