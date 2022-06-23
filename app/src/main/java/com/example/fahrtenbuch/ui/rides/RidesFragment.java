package com.example.fahrtenbuch.ui.rides;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Collections;
import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentRidesBinding;
import com.example.fahrtenbuch.ui.settings.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RidesFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private FragmentRidesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRidesBinding.inflate(inflater, container, false);

        binding.plusButton.setOnClickListener(this);
        binding.topCardRight.setOnClickListener(this);

        // Put rides into array
        ArrayList<ListObject> eintraege_liste = createRidesArray();
        //Create Recycler view for rides
        RecyclerView recyclerView = binding.fahrtenView;
        recyclerView.setAdapter(new RecyclerViewAdapter(eintraege_liste));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if (view == binding.plusButton) {
            // Neues Fragment zur Fahrten bearbeitung hier starten
            FragmentTransaction fragmentTransaction= getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_fragment_container, new createRideFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (view == binding.topCardRight) {
            DatePickerDialog dialog = new DatePickerDialog(binding.getRoot().getContext(), this, 2022, 6, 23);
            dialog.show();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }


    private ArrayList<ListObject> createRidesArray() {
        //Erzeugen von ListItems (randomisiert)
        Random random = new Random();
        ArrayList<ListObject> eintraege_liste = new ArrayList<>();
        for (int i=0; i<150; i++){
            Date startDate = new Date("03/01/2022 15:05:24");
            Date endDate = new Date("06/22/2022 23:25:12");
            long randDate = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
            Date date = new Date(randDate);
            eintraege_liste.add(new FahrtItem(date, "Frankfurt", "Gießen", random.nextInt(80)+5));
        }

        Collections.sort(eintraege_liste, (x, y) -> y.getDatum().compareTo(x.getDatum()));
        //Datum eingruppieren und in Liste einfügen
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
                    summeKm30Tage += ((FahrtItem) item).getKm();
                }
            }
        }
        binding.topCardRight.setText(summeKm30Tage + "km");

        return eintraege_liste;
    }
}