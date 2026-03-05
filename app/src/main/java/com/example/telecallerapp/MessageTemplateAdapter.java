package com.example.telecallerapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telecallerapp.Models.RecentActivityModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import android.content.Context;
import com.example.telecallerapp.Models.RecentActivityModel;

public class MessageTemplateAdapter extends RecyclerView.Adapter<MessageTemplateAdapter.ViewHolder> {

    private ArrayList<String> templates;
    private String leadName;
    private String leadPhone;

    public MessageTemplateAdapter(ArrayList<String> templates, String leadName, String leadPhone) {
        this.templates = templates;
        this.leadName = leadName;
        this.leadPhone = leadPhone;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_template, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] data = templates.get(position).split(" - ", 2);
        holder.tvTitle.setText(data[0]);
        holder.tvBody.setText(data[1]);

        holder.itemView.setOnClickListener(v -> {

            String templateText = templates.get(position);
            String finalMessage = templateText.replace("{{name}}", leadName);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:" + leadPhone));
            intent.putExtra("sms_body", finalMessage);

            v.getContext().startActivity(intent);

            addRecentActivity(v.getContext(), leadName, "Message Sent");
        });

    }
    private void addRecentActivity(Context context, String leadName, String action) {

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

    @Override
    public int getItemCount() {
        return templates.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvBody;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);
        }
    }
}
