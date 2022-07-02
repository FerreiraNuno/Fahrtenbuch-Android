package com.example.fahrtenbuch.ui.settings;
import android.annotation.SuppressLint;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import androidx.fragment.app.Fragment;

import com.example.fahrtenbuch.databinding.FragmentLocationPickerBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class LocationPickerFragment extends Fragment implements View.OnClickListener {
    private FragmentLocationPickerBinding binding;
    LatLng nowLocation;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocationPickerBinding.inflate(inflater, container, false);
        binding.btPicker.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btPicker) {
            startLocationService();
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationService() {
        Dialog dialog = new Dialog(binding.getRoot().getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding.getRoot().removeAllViews();
        dialog.setContentView(binding.relativeLayout);
        dialog.show();
        MapsInitializer.initialize(binding.getRoot().getContext());
        binding.mapView.onCreate(dialog.onSaveInstanceState());
        binding.mapView.onResume();
        binding.mapView.getMapAsync(googleMap -> {
            String[] latLngString =  "50.58560003799547, 8.674005783881551".split(",");
            double latitude = Double.parseDouble(latLngString[0]);
            double longitude = Double.parseDouble(latLngString[1]);
            LatLng latLng = new LatLng(latitude, longitude);
            // Marker pin in the moving map
            Marker marker = googleMap.addMarker(new MarkerOptions().
                    position(latLng). //marker position
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))); //marker icon
            // Enable GPS marker in Map
            googleMap.setMyLocationEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(11), 500, null);
            googleMap.setOnCameraMoveListener(() -> {
                LatLng midLatLng = googleMap.getCameraPosition().target;
                if (marker!=null) {
                    marker.setPosition(midLatLng);
                    nowLocation = marker.getPosition();
                }
            });
        });
        //dialog.setCancelable(false);
        binding.saveLocation.setOnClickListener(v -> {
            if (nowLocation != null) {
                LatLng returnLocation = new LatLng(nowLocation.latitude, nowLocation.longitude);
            }
        });
    }
}
