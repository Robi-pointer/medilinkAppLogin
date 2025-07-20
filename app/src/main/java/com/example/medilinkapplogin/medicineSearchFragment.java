package com.example.medilinkapplogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link medicineSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class medicineSearchFragment extends Fragment {
    RequestQueue requestQueue;
    AutoCompleteTextView searchMedicine;
    Button searchMedicineButton;
    TextView medicineDetails;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public medicineSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment medicineSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static medicineSearchFragment newInstance(String param1, String param2) {
        medicineSearchFragment fragment = new medicineSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_medicine_search, container, false);
        searchMedicine = view.findViewById(R.id.searchMedicine);
        searchMedicineButton = view.findViewById(R.id.searchMedicineButton);
         medicineDetails = view.findViewById(R.id.medicineDetails);
        requestQueue = Volley.newRequestQueue(getContext());
        // 🔍 Listen for user typing
        searchMedicine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 2) {
                    fetchSuggestions(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        searchMedicineButton.setOnClickListener(v -> {
            String query = searchMedicine.getText().toString().trim();
            if (!query.isEmpty()) {
                searchMedicineDetail(query);
            } else {
                Toast.makeText(getContext(), "Please enter a medicine name", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
    private void fetchSuggestions(final String query) {
        String url = "https://api.fda.gov/drug/label.json?search=openfda.generic_name:" + query + "&limit=10";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<String> suggestions = new ArrayList<>();

                        try {
                            JSONArray results = response.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject openfda = results.getJSONObject(i).optJSONObject("openfda");

                                if (openfda != null) {
                                    JSONArray names = openfda.optJSONArray("generic_name");

                                    if (names != null) {
                                        for (int j = 0; j < names.length(); j++) {
                                            String name = names.getString(j);
                                            if (!suggestions.contains(name)) {
                                                suggestions.add(name);
                                            }
                                        }
                                    }
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                    getContext(),
                                    android.R.layout.simple_dropdown_item_1line,
                                    suggestions
                            );

                            searchMedicine.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            searchMedicine.showDropDown();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // You can show a Toast or Log if needed
                        // Toast.makeText(getContext(), "Error fetching suggestions", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }
    private void searchMedicineDetail(final String query) {
        String url = "https://api.fda.gov/drug/label.json?search=openfda.generic_name:" + query + "&limit=1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");

                            if (results.length() > 0) {
                                JSONObject result = results.getJSONObject(0);

                                String description = "";
                                JSONArray descriptionArray = result.optJSONArray("description");

                                if (descriptionArray != null && descriptionArray.length() > 0) {
                                    description = descriptionArray.getString(0);
                                } else {
                                    description = "Description not available.";
                                }

                                medicineDetails.setText(description);
                            } else {
                                medicineDetails.setText("No result found.");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            medicineDetails.setText("Error parsing result.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        medicineDetails.setText("Error fetching data.");
                    }
                });

        requestQueue.add(request);
    }



}