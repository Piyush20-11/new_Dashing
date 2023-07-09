package com.example.new_dashing;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationSelectionActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private Marker selectedLocationMarker;
    private ActivityResultLauncher<Intent> locationSelectionLauncher;

    private EditText etSelectedLocation;
    private LatLng selectedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);

        Button btnConfirmLocation = findViewById(R.id.btnConfirmLocation);
        etSelectedLocation=findViewById(R.id.etSelectedLocation);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_container);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btnConfirmLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLocation != null) {
                    // Return the selected location to the calling activity
                    LatLng selectedLatLng = selectedLocationMarker.getPosition();
                    String address = getAddressFromLatLng(selectedLatLng);
                    if (address != null) {
//                        etSelectedLocation.setText(address);
                        // Return the selected location and address to the calling activity
                        Intent intent = new Intent();
                        intent.putExtra("latitude", selectedLatLng.latitude);
                        intent.putExtra("longitude", selectedLatLng.longitude);
                        intent.putExtra("address", address);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LocationSelectionActivity.this, "Failed to get address", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LocationSelectionActivity.this, "Please select a location", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Set up map options
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        // Move the camera to a default location (e.g., city center)
        LatLng defaultLocation = new LatLng(40.7128, -74.0060); // New York City coordinates
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 12.0f));

        // Add marker and update the selected location when the user clicks on the map
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                selectedLocation = latLng;
                if (selectedLocationMarker != null) {

                    selectedLocationMarker.remove();
                }
                selectedLocationMarker = googleMap.addMarker(new MarkerOptions().position(latLng));
                Log.d("addre", "onMapClick: ");


            }
        });
    }
    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(LocationSelectionActivity.this);
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            Log.d("Addr", addresses.get(0).getAddressLine(0));
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i));
                    if (i < address.getMaxAddressLineIndex()) {
                        sb.append(", ");
                    }
                }
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

