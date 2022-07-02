package com.example.fahrtenbuch.ui.settings;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.fahrtenbuch.databinding.FragmentSelectGpsBinding;

import java.util.HashSet;
import java.util.function.Consumer;

public class SelectGpsFragment extends Fragment implements View.OnClickListener {
    private FragmentSelectGpsBinding binding;

    //einzelne Sets um Fahrten Kategorien zuzuordnen
    public static HashSet<Location> orteZuhause = new HashSet<Location>() {};
    public static HashSet<Location> orteUni = new HashSet<Location>() {};
    public static HashSet<Location> orteArbeit = new HashSet<Location>() {};
    public static HashSet<Location> orteEinkauf = new HashSet<Location>() {};
    public static HashSet<Location> orteSonstiges = new HashSet<Location>() {};

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.R)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding  = FragmentSelectGpsBinding.inflate(inflater,container,false);

        binding.cardZuhause.setOnClickListener(this);
        binding.cardUni.setOnClickListener(this);
        binding.cardArbeit.setOnClickListener(this);
        binding.cardEinkauf.setOnClickListener(this);
        binding.cardSonstiges.setOnClickListener(this);

        detectCurrentLocation();

        View root = binding.getRoot();
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void detectCurrentLocation() {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                getActivity().requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            } else {
                LocationManager m = getActivity().getSystemService(LocationManager.class);
                m.getCurrentLocation(LocationManager.GPS_PROVIDER, null, getActivity().getMainExecutor(), new Consumer<Location>() {
                    @Override
                    public void accept(Location location) {
                        SettingsFragment.lastEndpointBluetoothBeacon = new Location(location);
                    }
                });
            }

    }

    @Override
    public void onClick(View view) {

        if (view == binding.cardZuhause) {
            orteZuhause.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
        }
        if (view == binding.cardUni) {
            orteUni.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
        }
        if (view == binding.cardArbeit) {
            orteArbeit.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
        }
        if (view == binding.cardEinkauf) {
            orteEinkauf.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
        }
        if (view == binding.cardSonstiges) {
            orteSonstiges.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
        }
    }
}
