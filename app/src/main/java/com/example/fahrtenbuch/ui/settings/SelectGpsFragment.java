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
import androidx.fragment.app.FragmentTransaction;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentSelectGpsBinding;

import java.util.HashSet;
import java.util.function.Consumer;

public class SelectGpsFragment extends Fragment implements View.OnClickListener{
    private FragmentSelectGpsBinding binding;

    //einzelne Sets um Fahrten Kategorien zuzuordnen
    public static HashSet<Location> orteZuhause = new HashSet<Location>() {};
    public static HashSet<Location> orteUni = new HashSet<Location>() {};
    public static HashSet<Location> orteArbeit = new HashSet<Location>() {};
    public static HashSet<Location> orteFreunde = new HashSet<Location>() {};
    public static HashSet<Location> orteSonstiges = new HashSet<Location>() {};

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.R)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding  = FragmentSelectGpsBinding.inflate(inflater,container,false);

        binding.cardZuhause.setOnClickListener(this);
        binding.cardUni.setOnClickListener(this);
        binding.cardArbeit.setOnClickListener(this);
        binding.cardFreunde.setOnClickListener(this);
        binding.cardSonstiges.setOnClickListener(this);

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view == binding.cardZuhause) {
            FragmentTransaction fragmentTransaction= getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, new LocationPickerFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            orteZuhause.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
            orteZuhause.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
        }
        else if (view == binding.cardFreunde) {
            orteFreunde.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
        }
        else if (view == binding.cardUni) {
            FragmentTransaction fragmentTransaction= getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, new MapFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            orteUni.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
        }
        else if (view == binding.cardArbeit) {
            orteArbeit.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
        }
        else if (view == binding.cardSonstiges) {
            orteSonstiges.add(new Location(SettingsFragment.lastEndpointBluetoothBeacon));
        }
    }
}
