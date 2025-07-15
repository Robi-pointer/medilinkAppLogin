package com.example.medilinkapplogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medilinkapplogin.Database.DBHelper;
import com.example.medilinkapplogin.user.userInfo;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link searchResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class searchResultFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BLOOD_GROUP = "blood_group";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public searchResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment searchResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static searchResultFragment newInstance(String bloodGroup) {
        searchResultFragment fragment = new searchResultFragment();
        Bundle args = new Bundle();
        args.putString(BLOOD_GROUP, bloodGroup);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(BLOOD_GROUP);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_search_result, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DBHelper dbHelper = new DBHelper(getContext());
       if(getArguments()!=null)
       {
           String bloodGroup = getArguments().getString(BLOOD_GROUP);
           Toast.makeText(getContext(), "blood find as" + bloodGroup, Toast.LENGTH_SHORT).show();

           ArrayList<userInfo> doners = dbHelper.getDonersByBloodGroup(bloodGroup);
           if(doners.isEmpty())
           {
               Toast.makeText(getContext(),
                       "No donors found for " + bloodGroup,
                       Toast.LENGTH_SHORT).show();
           }
           else
           {
               recyclerView.setAdapter(new RecyclerDonerApapter(getContext(),doners));
           }
       }


        return view;
    }
}