package com.example.medilinkapplogin;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medilinkapplogin.Database.DBHelper;
import com.example.medilinkapplogin.PatientParameter.parameters1;
import com.example.medilinkapplogin.user.UserSession;
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
        holder.sendRequestButton.setOnClickListener(v -> {
           // SQLiteDatabase db = context.openOrCreateDatabase("user_info", MODE_PRIVATE, null);
            DBHelper dbHelper = new DBHelper(context);

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            try {
                ContentValues values = new ContentValues();
                values.put(parameters1.KEY_PATIENT_NAME, UserSession.getInstance().getName());
                values.put(parameters1.KEY_PATIENT_PHONE, UserSession.getInstance().getPhone());
                values.put(parameters1.KEY_PATIENT_BLOOD_GROUP, doner.getBloodGroup());
                values.put(parameters1.KEY_DONOR_PHONE, doner.getPhone());

                long result = db.insert(parameters1.PATIENT_INFO_TABLE, null, values);
                if (result != -1) {
                    Toast.makeText(context, "Request Sent!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to send request", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        holder.btnSeeMedicalCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo donor = doners.get(position); // or whatever your list is called

                String message = "Control less Diabetes: " + donor.getDiabetes() + "\n" +
                        "High Blood Pressure: " + donor.getBloodPressure() + "\n" +
                        "Hepatitis: " + donor.getHepatitis() + "\n" +
                        "Major Operation: " + donor.getOperation() + "\n" +
                        "Vaccinated in last 14 days: " + donor.getVaccine() + "\n" +
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
        Button sendRequestButton,btnSeeMedicalCondition;
        public ViewHolder(View itemView)
        {
            super(itemView);
            donerName = itemView.findViewById(R.id.donerName);
            donerPhone = itemView.findViewById(R.id.donerPhone);
            donerBloodGroup = itemView.findViewById(R.id.donerBloodGroup);
            sendRequestButton = itemView.findViewById(R.id.sendRequestButton);
            btnSeeMedicalCondition = itemView.findViewById(R.id.btnSeeMedicalCondition);

        }

    }
}
