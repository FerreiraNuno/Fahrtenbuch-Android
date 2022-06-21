package com.example.fahrtenbuch.ui.rides;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Date;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fahrtenbuch.databinding.FragmentRidesBinding;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RidesFragment extends Fragment {
    private FragmentRidesBinding binding;
    private ArrayList<FahrtItem> eintraege_liste;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRidesBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.fahrtenView;
        recyclerView.setHasFixedSize(true);

        // Erzeugen von ListItems
        Random random = new Random();
        eintraege_liste = new ArrayList<>();
        for (int i=0; i<150; i++){
            Date startDate = new Date("01/01/2021 15:05:24");
            Date endDate = new Date("06/21/2022 13:25:12");
            long randDate = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
            Date date = new Date(randDate);
            eintraege_liste.add(new FahrtItem(date, "Frankfurt", "Gießen", random.nextInt(120)+5));
        }
        Collections.sort(eintraege_liste, (x, y) -> y.getDatumBeginn().compareTo(x.getDatumBeginn()));

        // KM Anzahl diesen Monat aufsummieren
        int summeKm30Tage = 0;
        Date currentDate = new Date();
        for (FahrtItem item : eintraege_liste) {
            if ((currentDate.getTime() - item.getDatumBeginn().getTime())/(1000*60*60*24) < 30) {
                summeKm30Tage += item.getKm();
            }
        }
        binding.topCardRight.setText(summeKm30Tage + "km");



        LinearLayoutManager layoutManager = new LinearLayoutManager(binding.getRoot().getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapter(eintraege_liste));

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}