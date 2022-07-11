package com.example.fahrtenbuch.ui.rides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fahrtenbuch.MainActivity;
import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentItemsBinding;
import com.example.fahrtenbuch.db.Database;
import com.example.fahrtenbuch.db.DateItem;
import com.example.fahrtenbuch.db.FahrtItem;
import com.example.fahrtenbuch.db.ListObject;
import com.example.fahrtenbuch.ui.rides.RidesFragmentDirections.ActionNavigationRidesToEditRideFragment;

import java.util.ArrayList;

public class RidesFragment extends Fragment implements View.OnClickListener, RecyclerViewAdapter.RecyclerviewOnClickListener {
    private FragmentItemsBinding binding;
    ArrayList<ListObject> eintraege_liste;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemsBinding.inflate(inflater, container, false);
        binding.plusButton.setOnClickListener(this);
        binding.topCardRight.setOnClickListener(this);

        eintraege_liste = getRidesArray();
        if (eintraege_liste.isEmpty()) {
            binding.recyclerView.setVisibility(View.INVISIBLE);
            binding.emptyText.setVisibility(View.VISIBLE);
        }
        else {
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.emptyText.setVisibility(View.INVISIBLE);
        }
        RecyclerView recyclerView = binding.recyclerView;
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, eintraege_liste);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if (view == binding.plusButton) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_rides_to_createRideFragment);

        }
    }



    private ArrayList<ListObject> getRidesArray() {
        Database db = MainActivity.db;
        ArrayList<FahrtItem> eintraege_fahrten = db.getAllRides();
        ArrayList<ListObject> eintraege_liste = new ArrayList<>(eintraege_fahrten);


        Collections.sort(eintraege_liste, (x, y) -> y.getDatum().compareTo(x.getDatum()));
        //Datum eingruppieren und in Liste einfügen
        if (eintraege_liste.size() == 0) {
            return eintraege_liste;
        }
        Date previousDate = eintraege_liste.get(0).getDatum();
        eintraege_liste.add(0, new DateItem(previousDate));
        for (int i=0; i<eintraege_liste.size(); i++){
            if (eintraege_liste.get(i).getType() == ListObject.TYPE_EINTRAG) {
                if (eintraege_liste.get(i).getDatum().getDate() != previousDate.getDate()) {
                    previousDate = eintraege_liste.get(i).getDatum();
                    eintraege_liste.add(i, new DateItem(previousDate));
                    i++;
                }
            }
        }
        // KM Anzahl diesen Monat aufsummieren
        int summeKm30Tage = 0;
        Date currentDate = new Date();
        for (ListObject item : eintraege_liste) {
            if ((currentDate.getTime() - item.getDatum().getTime())/(1000*60*60*24) < 30) {
                if (item.getType() == ListObject.TYPE_EINTRAG) {
                    summeKm30Tage += ((FahrtItem) item).getRideDistance();
                }
            }
        }
        binding.topCardRight.setText(summeKm30Tage + "km");

        return eintraege_liste;
    }

    @Override
    public void recyclerviewClick(int position) {
        int rideId = ((FahrtItem) eintraege_liste.get(position)).getRideId();
        ActionNavigationRidesToEditRideFragment action = RidesFragmentDirections.actionNavigationRidesToEditRideFragment();
        action.setRideId(rideId);
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }
}