package com.example.medilinkapplogin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medilinkapplogin.userDataModel.BloodRequestModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class userDashBoard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView headerName,headerEmail,headerPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_dash_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");
        View headerView = navigationView.getHeaderView(0);
        headerName = headerView.findViewById(R.id.headerName);
        headerEmail = headerView.findViewById(R.id.headerEmail);
        headerPhone = headerView.findViewById(R.id.headerPhone);
        headerName.setText(name);
        headerEmail.setText(email);
        headerPhone.setText(phone);


        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        loadFragment(new HomeFragment());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id == R.id.optNotes)
                {

                }
                else if(id == R.id.optHome)
                {
                    loadFragment(new HomeFragment());
                }
                else if(id==R.id.optGallary)
                {

                }
                else {

                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });





    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public static class BloodRequestAdapter extends RecyclerView.Adapter<BloodRequestAdapter.ViewHolder> {
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
                donorPhone = itemView.findViewById(R.id.donorPhone);
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
}