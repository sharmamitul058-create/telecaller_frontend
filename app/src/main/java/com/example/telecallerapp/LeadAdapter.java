package com.example.telecallerapp;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import com.example.telecallerapp.Models.RecentActivityModel;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
public class LeadAdapter extends RecyclerView.Adapter<LeadAdapter.ViewHolder> {

    private ArrayList<Lead> leadList;

    public LeadAdapter(ArrayList<Lead> leadList) {
        this.leadList = leadList;
    }

    private void addRecentActivity(String leadName, String action) {

        String uid = FirebaseAuth.getInstance().getUid();
        if (uid == null) return;

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("Users")
                .child(uid)
                .child("RecentActivity");

        String key = ref.push().getKey();

        if (key == null) return;

        RecentActivityModel model =
                new RecentActivityModel(leadName, action, System.currentTimeMillis());

        ref.child(key).setValue(model);
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

        Lead lead = leadList.get(position);

        holder.tvName.setText(lead.name);
        holder.tvPhone.setText(lead.phone);
        holder.tvStatus.setText(lead.status);

        holder.layoutActions.setVisibility(View.GONE);

        if ("Fresh".equalsIgnoreCase(lead.status)) {
            holder.layoutActions.setVisibility(View.VISIBLE);
            holder.btnCall.setVisibility(View.VISIBLE);
            holder.btnConvert.setVisibility(View.GONE);
            holder.btnNurture.setVisibility(View.GONE);
            holder.btnSchedule.setVisibility(View.GONE);
            holder.btnUpdate.setVisibility(View.GONE);
            holder.btnMessage.setVisibility(View.GONE);
        } else if ("Contacted".equalsIgnoreCase(lead.status)) {
            holder.layoutActions.setVisibility(View.VISIBLE);
            holder.btnSchedule.setVisibility(View.VISIBLE);
            holder.btnMessage.setVisibility(View.VISIBLE);
            holder.btnCall.setVisibility(View.GONE);
            holder.btnViewDetail.setVisibility(View.GONE);
            holder.btnUpdate.setVisibility(View.GONE);
            holder.btnConvert.setVisibility(View.GONE);
            holder.btnNurture.setVisibility(View.GONE);

        } else if ("Interested".equalsIgnoreCase(lead.status)) {
            holder.layoutActions.setVisibility(View.VISIBLE);
            holder.btnConvert.setVisibility(View.VISIBLE);
            holder.btnNurture.setVisibility(View.VISIBLE);
            holder.btnSchedule.setVisibility(View.GONE);
            holder.btnCall.setVisibility(View.GONE);
            holder.btnUpdate.setVisibility(View.GONE);
            holder.btnMessage.setVisibility(View.GONE);
        }

        holder.btnCall.setOnClickListener(v -> {

            // Open dialer
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + lead.phone));
            v.getContext().startActivity(intent);

            // Add to Recent Activity
            addRecentActivity(lead.name, "Dialed Lead");

            // Update status to Contacted
            String userId = FirebaseAuth.getInstance().getUid();
            if (userId == null || lead.id == null) return;

            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("leads")
                    .child(userId)
                    .child(lead.id);

            ref.child("status").setValue("Contacted");
        });
        holder.btnMessage.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), MessageTemplatesActivity.class);
            intent.putExtra("leadName", lead.name);
            intent.putExtra("leadPhone", lead.phone);

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return leadList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone, tvStatus;
        Button btnCall, btnSchedule, btnConvert, btnNurture, btnViewDetail, btnUpdate, btnMessage;
        LinearLayout layoutActions;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnViewDetail = itemView.findViewById(R.id.btnViewDetail);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnSchedule = itemView.findViewById(R.id.btnSchedule);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnMessage = itemView.findViewById(R.id.btnMessage);
            btnConvert = itemView.findViewById(R.id.btnConvert);
            btnNurture = itemView.findViewById(R.id.btnNurture);
            layoutActions = itemView.findViewById(R.id.layoutActions);
        }
    }
}
