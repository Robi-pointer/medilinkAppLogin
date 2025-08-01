package com.example.medilinkapplogin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.medilinkapplogin.Database.DBHelper;
import com.example.medilinkapplogin.userDataModel.dataModel;

import java.util.ArrayList;
import java.util.Calendar;

public class signUpActivity extends AppCompatActivity {

    EditText signUpName,signUpEmail,signUpPass,confirmSignUpPass,signUpPhone;
    Spinner bloodGroupSpiner;
    EditText signUpDist,signUpProfession;
    Button signUpButton;
    TextView redirectToLoin,signUpBloodGroup;
    CheckBox checkboxDiabetes, checkboxPressure, checkboxHepatitis,checkboxVaccine,checkboxOperation;
    EditText editDonationDate;
    Calendar calendar;
    ArrayList<String>arrBloodGroupSpinner = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        signUpName = findViewById(R.id.signUpName);
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpButton = findViewById(R.id.signUpButton);

        signUpPhone = findViewById(R.id.signUpPhone);
        signUpBloodGroup = findViewById(R.id.signUpBloodGroup);
        signUpDist = findViewById(R.id.signUpDist);
        signUpProfession = findViewById(R.id.signUpProfession);
        signUpPass = findViewById(R.id.signUpPass);
        confirmSignUpPass = findViewById(R.id.confirmSignUpPass);
        redirectToLoin = findViewById(R.id.redirectToLoin);
        bloodGroupSpiner = findViewById(R.id.bloodGroupSpiner);
        arrBloodGroupSpinner.add("O+");
        arrBloodGroupSpinner.add("A+");
        arrBloodGroupSpinner.add("B+");
        arrBloodGroupSpinner.add("AB+");
        arrBloodGroupSpinner.add("O-");
        arrBloodGroupSpinner.add("A-");
        arrBloodGroupSpinner.add("B-");
        arrBloodGroupSpinner.add("AB-");
        checkboxDiabetes = findViewById(R.id.checkboxDiabetes);
        checkboxPressure = findViewById(R.id.checkboxHighPressure);
        checkboxHepatitis = findViewById(R.id.checkboxHepatitis);
        checkboxVaccine = findViewById(R.id.checkboxVaccine);
        checkboxOperation = findViewById(R.id.checkboxOperation);
        editDonationDate = findViewById(R.id.editDonationDate);
        calendar = Calendar.getInstance();
        ArrayAdapter<String>bloodGroupAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,arrBloodGroupSpinner);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupSpiner.setAdapter(bloodGroupAdapter);
        redirectToLoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        editDonationDate.setOnClickListener(view -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(signUpActivity.this,
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        editDonationDate.setText(date);
                    }, year, month, day);
            dialog.show();
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = signUpName.getText().toString().trim();
                String email = signUpEmail.getText().toString().trim();
                String phone = signUpPhone.getText().toString().trim();
                String password = signUpPass.getText().toString().trim();
                String confirmPassword = confirmSignUpPass.getText().toString().trim();
                String bloodGroup = bloodGroupSpiner.getSelectedItem().toString().trim();
                String dist = signUpDist.getText().toString().trim();
                String profession = signUpProfession.getText().toString().trim();
                String diabetes = checkboxDiabetes.isChecked() ? "Yes" : "No";
                String pressure = checkboxPressure.isChecked() ? "Yes" : "No";
                String hepatitis = checkboxHepatitis.isChecked() ? "Yes" : "No";
                String operation = checkboxOperation.isChecked() ? "Yes" : "No";
                String vaccine = checkboxVaccine.isChecked() ? "Yes" : "No";

                String donationDate = editDonationDate.getText().toString().trim();
                DBHelper dbHelper = new DBHelper(signUpActivity.this);
                if(dbHelper.userExists(email)) {
                    Toast.makeText(signUpActivity.this, "user already exits", Toast.LENGTH_SHORT).show();
                return;
                }
               else
                {
                    if(password != null && password.equals(confirmPassword))
                    {
                        dataModel dm = new dataModel();
                        dm.setName(name);
                        dm.setEmail(email);
                        dm.setPhone(phone);
                        dm.setPass(password);
                        dm.setBloodGroup(bloodGroup);
                        dm.setDistrict(dist);
                        dm.setProfession(profession);
                        dm.setDiabetes(diabetes);
                        dm.setHigh_blood_pressure(pressure);
                        dm.setVaccine(vaccine);
                        dm.setOperation(operation);
                        dm.setHepatitis(hepatitis);
                        dm.setDonation_date(donationDate);

                        dbHelper.addUserInfo(dm);
                    }
                    else {
                        Toast.makeText(signUpActivity.this, "password and confirm password don't match", Toast.LENGTH_SHORT).show();
                    }
                }




            }
        });
    }
}