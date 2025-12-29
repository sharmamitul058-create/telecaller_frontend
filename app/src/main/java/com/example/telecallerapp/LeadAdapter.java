package com.example.telecallerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.ViewHolder> {

    private ArrayList<String> leads;

    public LeadAdapter(ArrayList<String> leads) {
        this.leads = leads;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lead, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String lead = leads.get(position);
        String[] data = lead.split(" - ");

        if (data.length < 3) return; // safety check

        String name = data[0];
        String phone = data[1];
        String status = data[2];

        holder.tvName.setText(name);
        holder.tvPhone.setText(phone);
        holder.tvStatus.setText(status);

        holder.layoutActions.setVisibility(View.GONE);
        holder.btnSchedule.setVisibility(View.GONE);

        // Show buttons ONLY for Fresh & Interested
        if (status.equalsIgnoreCase("Fresh")) {
            holder.layoutActions.setVisibility(View.VISIBLE);
            holder.btnSchedule.setVisibility(View.GONE);
            holder.btnCall.setVisibility(View.VISIBLE);
            holder.btnMessage.setVisibility(View.GONE);
            holder.btnViewDetail.setVisibility(View.VISIBLE);
            holder.btnUpdate.setVisibility(View.GONE);
            holder.btnConvert.setVisibility(View.GONE);
            holder.btnNurture.setVisibility(View.GONE);
        } else if (status.equalsIgnoreCase("Contacted")) {
            holder.layoutActions.setVisibility(View.VISIBLE);
            holder.btnSchedule.setVisibility(View.VISIBLE);
            holder.btnUpdate.setVisibility(View.VISIBLE);
            holder.btnMessage.setVisibility(View.GONE);
            holder.btnCall.setVisibility(View.GONE);
            holder.btnViewDetail.setVisibility(View.GONE);
            holder.btnConvert.setVisibility(View.GONE);
            holder.btnNurture.setVisibility(View.GONE);


        }else if(status.equalsIgnoreCase("Interested")){
            holder.layoutActions.setVisibility(View.VISIBLE);
            holder.btnSchedule.setVisibility(View.GONE);
            holder.btnCall.setVisibility(View.GONE);
            holder.btnMessage.setVisibility(View.GONE);
            holder.btnViewDetail.setVisibility(View.GONE);
            holder.btnUpdate.setVisibility(View.GONE);
            holder.btnConvert.setVisibility(View.VISIBLE);
            holder.btnNurture.setVisibility(View.VISIBLE);
        }

        else {
            holder.layoutActions.setVisibility(View.GONE);
        }

        // Frontend-only dummy actions
        holder.btnCall.setOnClickListener(v ->
                Toast.makeText(v.getContext(), "Calling " + phone, Toast.LENGTH_SHORT).show()
        );

        holder.btnMessage.setOnClickListener(v ->
                Toast.makeText(v.getContext(), "Messaging " + phone, Toast.LENGTH_SHORT).show()
        );
        holder.btnSchedule.setOnClickListener(v ->
                Toast.makeText(v.getContext(), "Scheduling"+ phone, Toast.LENGTH_SHORT).show());
        holder.btnViewDetail.setOnClickListener(v ->
        Toast.makeText(v.getContext(), "Viewing"+ phone, Toast.LENGTH_SHORT).show());
        holder.btnUpdate.setOnClickListener(v ->
                Toast.makeText(v.getContext(), "Updating..."+ phone, Toast.LENGTH_SHORT).show());
        holder.btnNurture.setOnClickListener(v ->
                Toast.makeText(v.getContext(),
                        "Nurturing " + holder.tvName.getText(),
                        Toast.LENGTH_SHORT).show()
        );

        holder.btnConvert.setOnClickListener(v ->
                Toast.makeText(v.getContext(),
                        "Converted " + holder.tvName.getText(),
                        Toast.LENGTH_SHORT).show()
        );


    }

    @Override
    public int getItemCount() {
        return leads.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone, tvStatus;
        Button btnCall, btnMessage, btnSchedule, btnViewDetail, btnUpdate, btnNurture, btnConvert;
        LinearLayout layoutActions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            btnCall = itemView.findViewById(R.id.btnCall);
            btnMessage = itemView.findViewById(R.id.btnMessage);
            btnSchedule = itemView.findViewById(R.id.btnSchedule);
            btnViewDetail = itemView.findViewById(R.id.btnViewDetail);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnNurture = itemView.findViewById(R.id.btnNurture);
            btnConvert = itemView.findViewById(R.id.btnConvert);
            layoutActions = itemView.findViewById(R.id.layoutActions);
        }
    }
}

