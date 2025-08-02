package com.example.medilinkapplogin;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;

import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import com.google.mlkit.vision.text.Text;


import java.io.IOException;


public class HomeFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_PICK = 102;
    private Uri imageUri;
   

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
        LinearLayout prescriptionLayout = view.findViewById(R.id.prescriptionLayout);
        LinearLayout BloodRequestLayout = view.findViewById(R.id.BloodRequestLayout);
        BloodRequestLayout.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.container, new bloodRequestFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        prescriptionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });
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
                searchBloodGroup.setText("");
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
    private void showImagePickerDialog() {
        final String[] options = {"Take Photo", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                } else {
                    Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickImage, REQUEST_IMAGE_PICK);
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            Bitmap imageBitmap = null;

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                imageBitmap = (Bitmap) data.getExtras().get("data");
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                try {
                    imageUri = data.getData();
                    imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (imageBitmap != null) {
                recognizeTextFromImage(imageBitmap);
            }
        }
    }
    private void recognizeTextFromImage(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        recognizer.process(image)
                .addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text result) {
                        String resultText = result.getText();

                        // Send to ResultFragment
                        Bundle bundle = new Bundle();
                        bundle.putString("recognized_text", resultText);

                        prescriptionReaderFragment resultFragment = new prescriptionReaderFragment();
                        resultFragment.setArguments(bundle);


                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, resultFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to recognize text", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
    }
}

