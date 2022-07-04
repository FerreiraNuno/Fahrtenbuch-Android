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
import androidx.navigation.Navigation;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentSelectGpsBinding;
import com.example.fahrtenbuch.db.Database;

import java.util.HashSet;
import java.util.function.Consumer;

public class SelectGpsFragment extends Fragment implements View.OnClickListener{
    private FragmentSelectGpsBinding binding;

    private String categorie = "";

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.R)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                getActivity().requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                getActivity().requestPermissions(new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 1);
            }
        }

        binding  = FragmentSelectGpsBinding.inflate(inflater,container,false);

        binding.cardZuhause.setOnClickListener(this);
        binding.cardUni.setOnClickListener(this);
        binding.cardArbeit.setOnClickListener(this);
        binding.cardEinkauf.setOnClickListener(this);
        binding.cardSport.setOnClickListener(this);
        binding.cardSonstiges.setOnClickListener(this);

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view == binding.cardZuhause) {
            categorie = "Zuhause";
        }
        else if (view == binding.cardEinkauf) {
            categorie = "Einkauf";
        }
        else if (view == binding.cardUni) {
            categorie = "Uni";
        }
        else if (view == binding.cardArbeit) {
            categorie = "Arbeit";
        }
        else if (view == binding.cardSonstiges) {
            categorie = "Sonstiges";
        }
        else if (view == binding.cardSport) {
            categorie = "Sport";
        }
        /*
        FragmentTransaction fragmentTransaction= getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container, new MapFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commit();
        */

        Navigation.findNavController(binding.getRoot()).getCurrentBackStackEntry().getSavedStateHandle().getLiveData("key").observe(getViewLifecycleOwner(), resultBundle -> {
            System.out.println(resultBundle);
            String[] latlng = resultBundle.toString().replace("Bundle[{bundleKey=", "").replace("}", "").replace("]", "").split(",");
            String latitude = latlng[0];
            String longitude = latlng[1];
            Database db = new Database(binding.getRoot().getContext());
            db.insertLocation(latitude, longitude, categorie);
        });
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_selectGpsFragment_to_mapFragment);
    }
}
