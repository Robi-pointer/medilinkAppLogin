package com.example.medilinkapplogin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.w3c.dom.Text;

import java.io.IOException;


public class HomeFragment extends Fragment {

   

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        EditText searchBloodGroup  = view.findViewById(R.id.searchBloodGroup);
        Button searchBloodGroupButton = view.findViewById(R.id.searchBloodGroupButton);
        LinearLayout medicineSearchLayout = view.findViewById(R.id.medicineSearchLayout);
        searchBloodGroupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String bloodGroup = searchBloodGroup.getText().toString().trim();
                searchResultFragment resultFragment = searchResultFragment.newInstance(bloodGroup);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, resultFragment) // R.id.container must be your FrameLayout in activity
                        .addToBackStack(null)              // So that back button goes back to HomeFragment
                        .commit();
            }
        });
        medicineSearchLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment medicineSearchFragment = new medicineSearchFragment();
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, medicineSearchFragment) // R.id.container must be your FrameLayout in activity
                        .addToBackStack(null)              // So that back button goes back to HomeFragment
                        .commit();

            }
        });


        return view;
    }


}