package com.example.fahrtenbuch.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.BarGraphBinding;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.time.LocalDate;
import java.util.List;

public class BarGraph extends Diagrams {

    String title;
    List<Float> values;

    BarGraph(String title, List<Float> values){
        this.title = title;
        this.values = values;
    }

    BarGraph() {}

    private BarGraphBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = BarGraphBinding.inflate(inflater,container,false);
        binding.titleBarGraph.setText(title);
        setData();
        return binding.getRoot();
    }

    private void setData() {
        BarChart barChart = binding.barchart;

        int i = values.size()-1;
        for (float value: values) {
            barChart.addBar(new BarModel( LocalDate.now().getYear() - i + "", value, R.color.purple_200));
            i--;
        }

        barChart.startAnimation();
    }
}