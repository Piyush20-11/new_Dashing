package com.example.new_dashing;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ambulanceBook extends AppCompatActivity {

    private static final int LOCATION_SELECTION_REQUEST_CODE = 1;

    private EditText patientNameEditText;

    private EditText etSelectedLocation;
    private Button locationEditText;
    private Button bookingButton;
    private ActivityResultLauncher<Intent> locationSelectionLauncher;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_book);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Find views by ID
        patientNameEditText = findViewById(R.id.patientName);
        locationEditText = findViewById(R.id.location);
        bookingButton = findViewById(R.id.booking);
        etSelectedLocation=findViewById(R.id.etSelectedLocation);

        locationSelectionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            double latitude = data.getDoubleExtra("latitude", 0.0);
                            double longitude = data.getDoubleExtra("longitude", 0.0);
                            String address = data.getStringExtra("address");
                            etSelectedLocation.setText(address);
                        }
                    }
                }
        );
        locationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ambulanceBook.this, LocationSelectionActivity.class);
                locationSelectionLauncher.launch(intent);
            }
        });

        // Handle booking button click
        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values
                String patientName = patientNameEditText.getText().toString();
                String etSelected = etSelectedLocation.getText().toString();

                // Create a new document in Firestore
                firestore.collection("ambulance_bookings")
                        .add(new bookModal(patientName, etSelected))
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Clear input fields after successful booking
                                patientNameEditText.setText("");
                                etSelectedLocation.setText("");

                                // Show a success message or perform any further actions
                                // after the booking is successfully added to Firestore
                            }
                        });
            }
        });
    }
}
