package com.example.fahrtenbuch.ui.statistics;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = LineGraphBinding.inflate(inflater,container,false);
        binding.titleLineGraph.setText(title);
        setData();
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setData() {
        ValueLineChart valueLineChart = binding.cubiclinechart;
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(R.color.purple_200);

        int month = LocalDate.now().getMonthValue();

        series.addPoint(new ValueLinePoint("Jan", values.get(0)));
        if(month > 1) series.addPoint(new ValueLinePoint("Feb", values.get(1)));
        if(month > 2) series.addPoint(new ValueLinePoint("Mar", values.get(2)));
        if(month > 3) series.addPoint(new ValueLinePoint("Apr", values.get(3)));
        if(month > 4) series.addPoint(new ValueLinePoint("Mai", values.get(4)));
        if(month > 5) series.addPoint(new ValueLinePoint("Jun", values.get(5)));
        if(month > 6) series.addPoint(new ValueLinePoint("Jul", values.get(6)));
        if(month > 7) series.addPoint(new ValueLinePoint("Aug", values.get(7)));
        if(month > 8) series.addPoint(new ValueLinePoint("Sep", values.get(8)));
        if(month > 9) series.addPoint(new ValueLinePoint("Oct", values.get(9)));
        if(month > 10) series.addPoint(new ValueLinePoint("Nov", values.get(10)));
        if(month > 11) series.addPoint(new ValueLinePoint("Dec", values.get(11)));

        valueLineChart.addSeries(series);
        valueLineChart.startAnimation();
    }
}
