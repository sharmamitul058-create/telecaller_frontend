package com.example.telecallerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecentActivityAdapter
        extends RecyclerView.Adapter<RecentActivityAdapter.ViewHolder> {

    private final ArrayList<String[]> list;

    public RecentActivityAdapter(@NonNull ArrayList<String[]> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] item = list.get(position);

        holder.tvName.setText(item[0]);
        holder.tvAction.setText(item[1]);
        holder.tvTime.setText(item[2]);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvAction, tvTime;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAction = itemView.findViewById(R.id.tvAction);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
