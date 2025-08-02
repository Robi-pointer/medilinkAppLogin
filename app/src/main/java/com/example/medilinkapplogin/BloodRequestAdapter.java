package com.example.medilinkapplogin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medilinkapplogin.R;
import com.example.medilinkapplogin.userDataModel.BloodRequestModel;

import java.util.ArrayList;

public class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.ViewHolder> {
    ArrayList<BloodRequestModel> list;

    public BloodRequestAdapter(ArrayList<BloodRequestModel> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, group, phone, donorPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.patientName);
            group = itemView.findViewById(R.id.bloodGroup);
            phone = itemView.findViewById(R.id.patientPhone);
             donorPhone= itemView.findViewById(R.id.donorPhone);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_request_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BloodRequestModel model = list.get(position);
        holder.name.setText(model.getPatientName());
        holder.group.setText(model.getBloodGroup());
        holder.phone.setText(model.getPhone());
        holder.donorPhone.setText(model.getDonorPhone());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
