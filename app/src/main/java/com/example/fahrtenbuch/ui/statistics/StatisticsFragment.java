package com.example.fahrtenbuch.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fahrtenbuch.databinding.FragmentStatisticsBinding;

import java.util.ArrayList;


public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStatisticsBinding.inflate(inflater, container, false);

        ListView lv = binding.statisticsView;

        ArrayList<Diagrams> eintraegeListe = new ArrayList<>();

        eintraegeListe.add(new PieDiagram());   //Fahrten nach Kategorie
        eintraegeListe.add(new BarGraph());     //KM pro Monat
        eintraegeListe.add(new LineGraph());    //KM pro Monat
        eintraegeListe.add(new StackedBarGraph());  //KM pro Kategorie pro Monat (1.1 - 31.12)
        eintraegeListe.add(new PieDiagram());   //Ausgaben nach Kategorie
        eintraegeListe.add(new BarGraph());     //Ausgaben pro Monat
        eintraegeListe.add(new LineGraph());     //Ausgaben pro Monat
        eintraegeListe.add(new StackedBarGraph());  //Ausgaben pro Kategorie pro Monat (1.1 - 31.12)

        AdvancedAdapter advancedAdapter = new AdvancedAdapter(getContext(), eintraegeListe);
        lv.setAdapter(advancedAdapter);

        View root = binding.getRoot();
        return root;
    }

}