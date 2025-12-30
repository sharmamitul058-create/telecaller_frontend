package com.example.telecallerapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder> {

    private ArrayList<String[]> campaigns;

    public CampaignAdapter(ArrayList<String[]> campaigns) {
        this.campaigns = campaigns;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_campaign, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String[] data = campaigns.get(position);

        String title = data[0];
        String status = data[1];
        String audience = data[2];
        String progress = data[3];
        String start = data[4];
        String end = data[5];

        holder.tvTitle.setText(title);
        holder.tvStatus.setText(status);
        holder.tvAudience.setText("Audience: " + audience);
        holder.tvProgress.setText(progress + " Called");
        holder.tvStart.setText("Started: " + start);
        holder.tvEnd.setText("Due: " + end);

        if (status.equalsIgnoreCase("Active")) {
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50"));
        } else if (status.equalsIgnoreCase("Paused")) {
            holder.tvStatus.setTextColor(Color.parseColor("#FF9800"));
        } else {
            holder.tvStatus.setTextColor(Color.parseColor("#9E9E9E"));
        }
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus, tvProgress, tvAudience, tvStart, tvEnd;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            progressBar = itemView.findViewById(R.id.progressBar);
            tvAudience = itemView.findViewById(R.id.tvAudience);
            tvStart = itemView.findViewById(R.id.tvStart);
            tvEnd = itemView.findViewById(R.id.tvEnd);
        }
    }
}
