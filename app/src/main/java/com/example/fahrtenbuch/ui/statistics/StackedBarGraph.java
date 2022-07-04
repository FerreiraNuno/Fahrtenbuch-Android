package com.example.fahrtenbuch.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fahrtenbuch.databinding.ActivityStackedBarGraphBinding;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

public class StackedBarGraph extends Diagrams {
    private ActivityStackedBarGraphBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = ActivityStackedBarGraphBinding.inflate(inflater,container,false);

        setData();

        return binding.getRoot();
    }

    private void setData() {
        StackedBarChart stackedBarChart = binding.stackedbarchart;

        StackedBarModel s1 = new StackedBarModel("12.4");
        s1.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s1.addBar(new BarModel(2.3f, 0xFF56B7F1));
        s1.addBar(new BarModel(2.3f, 0xFFCDA67F));

        StackedBarModel s2 = new StackedBarModel("13.4");
        s2.addBar(new BarModel(1.1f, 0xFF63CBB0));
        s2.addBar(new BarModel(2.7f, 0xFF56B7F1));
        s2.addBar(new BarModel(0.7f, 0xFFCDA67F));

        StackedBarModel s3 = new StackedBarModel("14.4");

        s3.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s3.addBar(new BarModel(2.f, 0xFF56B7F1));
        s3.addBar(new BarModel(3.3f, 0xFFCDA67F));

        StackedBarModel s4 = new StackedBarModel("15.4");
        s4.addBar(new BarModel(1.f, 0xFF63CBB0));
        s4.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s4.addBar(new BarModel(2.1f, 0xFFCDA67F));

        stackedBarChart.addBar(s1);
        stackedBarChart.addBar(s2);
        stackedBarChart.addBar(s3);
        stackedBarChart.addBar(s4);

        stackedBarChart.startAnimation();
    }
}