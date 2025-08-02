package com.example.medilinkapplogin;

import static android.content.Context.MODE_PRIVATE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medilinkapplogin.DBParameters.parameters;
import com.example.medilinkapplogin.Database.DBHelper;
import com.example.medilinkapplogin.PatientParameter.parameters1;
import com.example.medilinkapplogin.user.UserSession;
import com.example.medilinkapplogin.userDataModel.BloodRequestModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link bloodRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bloodRequestFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<BloodRequestModel> requests = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public bloodRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment bloodRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static bloodRequestFragment newInstance(String param1, String param2) {
        bloodRequestFragment fragment = new bloodRequestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_blood_request, container, false);
        recyclerView = view.findViewById(R.id.bloodRequestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadRequests();
         return view;
    }
    private void loadRequests() {
        //SQLiteDatabase db = getContext().openOrCreateDatabase("user_info", MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(getContext());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + parameters1.PATIENT_INFO_TABLE + " WHERE " +
                        parameters1.KEY_DONOR_PHONE + "=?",
                new String[]{UserSession.getInstance().getPhone()});

        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndexOrThrow("patient_name");
            int bloodIndex = cursor.getColumnIndexOrThrow("patient_blood_group");
            int phoneIndex = cursor.getColumnIndexOrThrow("patient_phone");
            int donorPhoneIndex = cursor.getColumnIndexOrThrow("donor_phone");
            String patientName = cursor.getString(nameIndex);
            String bloodGroup = cursor.getString(bloodIndex);
            String phone = cursor.getString(phoneIndex);
            String donorPhone = cursor.getString(donorPhoneIndex);

            requests.add(new BloodRequestModel(patientName, bloodGroup, phone, donorPhone));
        }

        BloodRequestAdapter adapter = new BloodRequestAdapter(requests);
        recyclerView.setAdapter(adapter);
    }

}
