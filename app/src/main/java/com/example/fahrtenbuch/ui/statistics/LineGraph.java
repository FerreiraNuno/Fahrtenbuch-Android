package com.example.fahrtenbuch.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.LineGraphBinding;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class LineGraph extends Diagrams{
    private LineGraphBinding binding;
    String title;
    List<Float> values;

    LineGraph(String title, List<Float> values){
        this.title = title;
        this.values = values;
    }

    LineGraph() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LineGraphBinding.inflate(inflater,container,false);
        binding.titleLineGraph.setText(title);
        setData();
        return binding.getRoot();
    }

    private void setData() {
        ValueLineChart valueLineChart = binding.cubiclinechart;
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(R.color.purple_200);

        series.addPoint(new ValueLinePoint("Jan", values.get(0)));
        series.addPoint(new ValueLinePoint("Feb", values.get(1)));
        series.addPoint(new ValueLinePoint("Mar", values.get(2)));
        series.addPoint(new ValueLinePoint("Apr", values.get(3)));
        series.addPoint(new ValueLinePoint("Mai", values.get(4)));
        series.addPoint(new ValueLinePoint("Jun", values.get(5)));
        series.addPoint(new ValueLinePoint("Jul", values.get(6)));
        series.addPoint(new ValueLinePoint("Aug", values.get(7)));
        series.addPoint(new ValueLinePoint("Sep", values.get(8)));
        series.addPoint(new ValueLinePoint("Oct", values.get(9)));
        series.addPoint(new ValueLinePoint("Nov", values.get(10)));
        series.addPoint(new ValueLinePoint("Dec", values.get(11)));

        valueLineChart.addSeries(series);
        valueLineChart.startAnimation();
    }
}
