package com.example.fahrtenbuch.ui.rides;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.fahrtenbuch.databinding.FragmentLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


public class LocationFragment extends Fragment implements View.OnClickListener {
    private static final int PERMISSIONS_FINE_LOCATION = 99;
    private static final int PERMISSIONS_BACKGROUND_LOCATION = 98;

    private FragmentLocationBinding binding;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocationBinding.inflate(inflater, container, false);

        if (ActivityCompat.checkSelfPermission(binding.getRoot().getContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_BACKGROUND_LOCATION);
        }

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                System.out.println("location changed");
                System.out.println(locationResult.getLastLocation().getLatitude());
                System.out.println(locationResult.getLastLocation().getLongitude());
                super.onLocationResult(locationResult);
                updateUi(locationResult.getLastLocation());
            }
        };
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        updateGPS();

        binding.swGps.setOnClickListener(this);
        binding.swLocationsupdates.setOnClickListener(this);
        return binding.getRoot();
    }

    public void updateGPS() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(binding.getRoot().getContext());
        if (ActivityCompat.checkSelfPermission(binding.getRoot().getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) binding.getRoot().getContext(), location -> updateUi(location));
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(binding.getRoot().getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(binding.getRoot().getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        updateGPS();
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        binding.tvLat.setText("Not tracking location");
        binding.tvLon.setText("Not tracking location");
        binding.tvAltitude.setText("Not tracking location");
        binding.tvAccuracy.setText("Not tracking location");
        binding.tvSpeed.setText("Not tracking location");
    }


    public void updateUi(Location location) {
        binding.tvLat.setText(String.valueOf(location.getLatitude()));
        binding.tvLon.setText(String.valueOf(location.getLongitude()));
        binding.tvAltitude.setText(String.valueOf(location.getAltitude()));
        binding.tvAccuracy.setText(String.valueOf(location.getAccuracy()));
        binding.tvSpeed.setText(String.valueOf(location.getSpeed()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0) {
            System.out.println("results were called too early");
            return;
        }
        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGPS();
                } else {
                    Toast.makeText(binding.getRoot().getContext(), "Permission was not granted, App Tracking not enabled" , Toast.LENGTH_SHORT).show();
                }
                break;
            case PERMISSIONS_BACKGROUND_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(binding.getRoot().getContext(), "Background Tracking enabled!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(binding.getRoot().getContext(), "Permission was not granted, Background Tracking not enabled", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == binding.swGps) {
            if (binding.swGps.isChecked()) {
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                binding.tvSensor.setText("Using GPS Sensors");
            } else {
                locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                binding.tvSensor.setText("Using Towers + WIFI");
            }
        } else if (view == binding.swLocationsupdates) {
            if (binding.swLocationsupdates.isChecked()) {
                binding.swLocationsupdates.setText("Location Updates On");
                startLocationUpdates();
            } else {
                binding.swLocationsupdates.setText("Location Updates Off");
                stopLocationUpdates();
            }
        }
    }
}