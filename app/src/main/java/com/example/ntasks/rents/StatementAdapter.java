package com.example.ntasks.rents;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntasks.R;

import java.util.List;

public class StatementAdapter extends RecyclerView.Adapter<StatementAdapter.ViewHolder> {
    private List<Statement> statementList;
    private Context context;

    public StatementAdapter(Context context, List<Statement> statementList) {
        this.context = context;
        this.statementList = statementList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statementitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Statement statement = statementList.get(position);

        holder.tvMonth.setText(statement.getMonth());
        holder.tvLink.setText(statement.getLink());

        // Set OnClickListener for tvLink
        holder.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the URL from the TextView and open it in a web browser
                String url = statement.getLink();
                if (url != null && !url.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return statementList.size();
    }

    public void setData(List<Statement> statementList) {
        this.statementList = statementList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMonth;
        TextView tvLink;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMonth = itemView.findViewById(R.id.tvmonth);
            tvLink = itemView.findViewById(R.id.tvlink);
            tvLink.setTextColor(Color.BLUE);
        }
    }
}
