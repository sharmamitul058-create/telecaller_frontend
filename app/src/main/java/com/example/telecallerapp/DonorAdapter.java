package com.example.telecallerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.ViewHolder> {


    ArrayList<Donor> donorList;

    public DonorAdapter(ArrayList<Donor> donorList) {
        this.donorList = donorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Donor donor = donorList.get(position);
        holder.tvName.setText(donor.name);
        holder.tvPhone.setText(donor.phone);
        holder.tvAmount.setText("₹ " + donor.amount);
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvAmount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}

