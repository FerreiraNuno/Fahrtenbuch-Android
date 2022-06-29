package com.example.fahrtenbuch.ui.rides;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentRidesBinding;
import com.example.fahrtenbuch.db.Database;

import java.util.ArrayList;

public class RidesFragment extends Fragment implements View.OnClickListener {
    private FragmentRidesBinding binding;
    private RecyclerViewAdapter recyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRidesBinding.inflate(inflater, container, false);
        binding.plusButton.setOnClickListener(this);
        binding.topCardRight.setOnClickListener(this);

        ArrayList<ListObject> eintraege_liste = getRidesArray();
        RecyclerView recyclerView = binding.fahrtenView;
        recyclerViewAdapter = new RecyclerViewAdapter(eintraege_liste);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if (view == binding.plusButton) {
            FragmentTransaction fragmentTransaction= getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, new CreateRideFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (view == binding.topCardRight) {
            Database db = new Database(binding.getRoot().getContext());
            Date today = new Date();
            db.insert(today.getTime(), 80, 5);
        }
    }



    private ArrayList<ListObject> getRidesArray() {
        Database db = new Database(binding.getRoot().getContext());
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
            if (eintraege_liste.get(i).getType() == ListObject.TYPE_FAHRT) {
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
                if (item.getType() == ListObject.TYPE_FAHRT) {
                    summeKm30Tage += ((FahrtItem) item).getRideDistance();
                }
            }
        }
        binding.topCardRight.setText(summeKm30Tage + "km");

        return eintraege_liste;
    }
}