package com.example.medilinkapplogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medilinkapplogin.user.userInfo;
import com.example.medilinkapplogin.userDataModel.dataModel;

import java.util.ArrayList;

public class RecyclerDonerApapter extends RecyclerView.Adapter<RecyclerDonerApapter.ViewHolder> {
    private final Context context;
    private final ArrayList<userInfo> doners;
    RecyclerDonerApapter(Context context, ArrayList<userInfo> doners)
    {
        this.context = context;
        this.doners = doners;

    }
    @NonNull
    @Override
    public RecyclerDonerApapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doner_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDonerApapter.ViewHolder holder, int position) {
        userInfo doner = doners.get(position);
        holder.donerName.setText(doner.getName());
        holder.donerPhone.setText(doner.getPhone());
        holder.donerBloodGroup.setText(doner.getBloodGroup());
        holder.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "Blood request sent to " + doner.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });
       holder.btnSeeMedicalCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo donor = doners.get(position); // or whatever your list is called

                String message = "Diabetes: " + donor.getDiabetes() + "\n" +
                        "Pressure: " + donor.getBloodPressure() + "\n" +
                        "Hepatitis: " + donor.getHepatitis() + "\n" +
                        "Operation: " + donor.getOperation() + "\n" +
                        "Vaccine: " + donor.getVaccine() + "\n" +
                        "Last Donation: " + donor.getDonationDate();

                new AlertDialog.Builder(context)
                        .setTitle("Medical Condition")
                        .setMessage(message)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return doners.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView donerName,donerPhone,donerBloodGroup;
        Button requestButton,btnSeeMedicalCondition;
        public ViewHolder(View itemView)
        {
            super(itemView);
            donerName = itemView.findViewById(R.id.donerName);
            donerPhone = itemView.findViewById(R.id.donerPhone);
            donerBloodGroup = itemView.findViewById(R.id.donerBloodGroup);
            requestButton = itemView.findViewById(R.id.requestButton);
            btnSeeMedicalCondition = itemView.findViewById(R.id.btnSeeMedicalCondition);

        }

    }
}
