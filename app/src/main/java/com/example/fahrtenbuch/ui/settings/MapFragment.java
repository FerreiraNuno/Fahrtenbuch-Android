package com.example.fahrtenbuch.ui.settings;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.fahrtenbuch.databinding.FragmentMapBinding;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements View.OnClickListener {
    private FragmentMapBinding binding;
    LatLng markerLocation;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);

        binding.saveLocation.setOnClickListener(this);


        startMapFragment(savedInstanceState);

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if (view == binding.saveLocation) {
            if (markerLocation != null) {
                Bundle result = new Bundle();
                result.putString("bundleKey", markerLocation.latitude + "," + markerLocation.longitude);
                Navigation.findNavController(binding.getRoot()).getPreviousBackStackEntry().getSavedStateHandle().set("key", result);
                Navigation.findNavController(binding.getRoot()).navigateUp();
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startMapFragment(Bundle savedInstanceState) {
        MapsInitializer.initialize(binding.getRoot().getContext());
        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.onResume();
        binding.mapView.getMapAsync(googleMap -> {
            // Maps Settings
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2);
            }
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},3);
            } else {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
            }

            // Get current location
            String[] latLngString =  "50.58560003799547, 8.674005783881551".split(",");
            double latitude = Double.parseDouble(latLngString[0]);
            double longitude = Double.parseDouble(latLngString[1]);
            LatLng latLng = new LatLng(latitude, longitude);

            // Marker pin in the moving map with selected position
            Marker marker = googleMap.addMarker(new MarkerOptions().
                    position(latLng). //marker position
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))); //marker icon

            // Set Position in Map
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

            // Move Listener
            googleMap.setOnCameraMoveListener(() -> {
                LatLng midLatLng = googleMap.getCameraPosition().target;
                if (marker!=null) {
                    marker.setPosition(midLatLng);
                    markerLocation = marker.getPosition();
                }
            });
        });
    }
}