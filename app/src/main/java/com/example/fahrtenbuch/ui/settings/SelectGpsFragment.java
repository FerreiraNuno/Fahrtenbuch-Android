package com.example.fahrtenbuch.ui.settings;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentSelectGpsBinding;
import com.example.fahrtenbuch.db.Database;

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
        binding.cardEinkauf.setOnClickListener(this);
        binding.cardSonstiges.setOnClickListener(this);

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction= getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, new MapFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        requireActivity().getSupportFragmentManager().setFragmentResultListener("requestKey", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Database db = new Database(binding.getRoot().getContext());
                db.insertLocation();
            }
        });


        if (view == binding.cardZuhause) {
        }
        else if (view == binding.cardEinkauf) {
        }
        else if (view == binding.cardUni) {
        }
        else if (view == binding.cardArbeit) {
        }
        else if (view == binding.cardSonstiges) {
        }
    }
}
