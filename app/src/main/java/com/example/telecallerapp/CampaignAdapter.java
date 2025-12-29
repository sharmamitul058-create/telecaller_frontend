package com.example.telecallerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder> {

    private ArrayList<String> campaigns;

    public CampaignAdapter(ArrayList<String> campaigns) {
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
        String[] data = campaigns.get(position).split(" - ");
        holder.tvTitle.setText(data[0]);
        holder.tvStatus.setText(data[1]);
        holder.tvProgress.setText(data[2] + " Called");
    }

    @Override
    public int getItemCount() {
        return campaigns.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus, tvProgress;
        ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvProgress = itemView.findViewById(R.id.tvProgress);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}

