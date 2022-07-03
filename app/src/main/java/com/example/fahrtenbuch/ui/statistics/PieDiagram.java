package com.example.fahrtenbuch.ui.statistics;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.PieDiagramBinding;

import org.eazegraph.lib.models.PieModel;

public class PieDiagram extends Diagrams {

    private PieDiagramBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = PieDiagramBinding.inflate(inflater,container,false);

        setData();

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void setData()
    {
        // Set the percentage
        binding.tvArbeit.setText(Integer.toString(20));
        binding.tvUni.setText(Integer.toString(20));
        binding.tvSport.setText(Integer.toString(20));
        binding.tvEinkaufen.setText(Integer.toString(20));
        binding.tvSonstiges.setText(Integer.toString(20));

        System.out.println(Integer.toHexString(R.color.arbeit));

        // Set the data and color to the pie chart
        binding.piechart.addPieSlice(
                new PieModel(
                        "Arbeit",
                        Integer.parseInt(binding.tvArbeit.getText().toString()), Color.parseColor("#76E3F1")));
        binding.piechart.addPieSlice(
                new PieModel(
                        "Uni",
                        Integer.parseInt(binding.tvUni.getText().toString()),Color.parseColor("#6EA0C8")));
        binding.piechart.addPieSlice(
                new PieModel(
                        "Sport",
                        Integer.parseInt(binding.tvSport.getText().toString()),Color.parseColor("#5D24C3")));
        binding.piechart.addPieSlice(
                new PieModel(
                        "Einkaufen",
                        Integer.parseInt(binding.tvEinkaufen.getText().toString()),Color.parseColor("#AE8AED")));
        binding.piechart.addPieSlice(
                new PieModel(
                        "Sonstiges",
                        Integer.parseInt(binding.tvSonstiges.getText().toString()),Color.parseColor("#129C8F")));

        // To animate the pie chart
        binding.piechart.startAnimation();
    }
}


