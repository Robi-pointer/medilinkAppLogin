package com.example.medilinkapplogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


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
        return view;
    }

}