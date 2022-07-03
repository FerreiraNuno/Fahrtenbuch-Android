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

        eintraegeListe.add(new PieDiagram());

        AdvancedAdapter advancedAdapter = new AdvancedAdapter(getContext(), eintraegeListe);
        lv.setAdapter(advancedAdapter);

        View root = binding.getRoot();
        return root;
    }

}