package com.example.medilinkapplogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.medilinkapplogin.Database.DBHelper;
import com.example.medilinkapplogin.user.userInfo;

public class MainActivity extends AppCompatActivity {
    EditText loginEmail,loginPassword;
    Button loginButton;
    TextView redirectToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        redirectToSignUp = findViewById(R.id.redirectToSignUp);
        redirectToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,signUpActivity.class);
                startActivity(intent);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEmail.getText().toString().trim();
                String pass = loginPassword.getText().toString().trim();
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                userInfo user = dbHelper.getUserInfo(email);

                if(dbHelper.userExists(email))
                {
                    if(dbHelper.checkPassword(pass,email))
                    {
                        Intent intent = new Intent(MainActivity.this,userDashBoard.class);
                    intent.putExtra("name",user.getName());
                        intent.putExtra("email",user.getEmail());
                        intent.putExtra("phone",user.getPhone());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "email and pass don't match", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "user doesn't exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}