package com.example.fahrtenbuch.ui.statistics;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.StackedBarGraphBinding;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class StackedBarGraph extends Diagrams {
    private StackedBarGraphBinding binding;

    String title;
    List<Float> values0, values1, values2;

    String name0, name1, name2, name3, name4;

    StackedBarGraph() {}

    StackedBarGraph(String title, List<Float> values0, List<Float> values1, List<Float> values2, String name0, String name1, String name2, String name3, String name4) {
        this.title = title;
        this.values0 = values0;
        this.values1 = values1;
        this.values2 = values2;
        this.name0 = name0;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.name4 = name4;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = StackedBarGraphBinding.inflate(inflater,container,false);

        binding.titleStackedBarChart.setText(title);

        setData();

        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setData() {
        StackedBarChart stackedBarChart = binding.stackedbarchart;

        int color0 = Color.parseColor("#76E3F1");
        int color1 = Color.parseColor("#6EA0C8");
        int color2 = Color.parseColor("#5D24C3");
        int color3 = Color.parseColor("#AE8AED");
        int color4 = Color.parseColor("#129C8F");

        int id0 = 0, id1 = 0, id2 = 0, id3 = 0, id4 = 0;
        try {
            id0 = R.drawable.class.getField(name0.toLowerCase(Locale.ROOT)).getInt(null);
            id1 = R.drawable.class.getField(name1.toLowerCase(Locale.ROOT)).getInt(null);
            id2 = R.drawable.class.getField(name2.toLowerCase(Locale.ROOT)).getInt(null);
            id3 = R.drawable.class.getField(name3.toLowerCase(Locale.ROOT)).getInt(null);
            id4 = R.drawable.class.getField(name4.toLowerCase(Locale.ROOT)).getInt(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        binding.bimage0.setImageResource(id0);
        binding.bimage1.setImageResource(id1);
        binding.bimage2.setImageResource(id2);
        binding.bimage3.setImageResource(id3);
        binding.bimage4.setImageResource(id4);

        StackedBarModel stackedBarModel0 = new StackedBarModel(values0.stream().mapToDouble(a -> a).sum() + "");
        StackedBarModel stackedBarModel1 = new StackedBarModel(values1.stream().mapToDouble(a -> a).sum() + "");
        StackedBarModel stackedBarModel2 = new StackedBarModel(values2.stream().mapToDouble(a -> a).sum() + "");


        stackedBarModel0.addBar(new BarModel(values0.get(0),color0));
        stackedBarModel0.addBar(new BarModel(values0.get(1),color1));
        stackedBarModel0.addBar(new BarModel(values0.get(2),color2));
        stackedBarModel0.addBar(new BarModel(values0.get(3),color3));
        stackedBarModel0.addBar(new BarModel(values0.get(4),color4));

        stackedBarModel1.addBar(new BarModel(values1.get(0),color0));
        stackedBarModel1.addBar(new BarModel(values1.get(1),color1));
        stackedBarModel1.addBar(new BarModel(values1.get(2),color2));
        stackedBarModel1.addBar(new BarModel(values1.get(3),color3));
        stackedBarModel1.addBar(new BarModel(values1.get(4),color4));

        stackedBarModel2.addBar(new BarModel(values2.get(0),color0));
        stackedBarModel2.addBar(new BarModel(values2.get(1),color1));
        stackedBarModel2.addBar(new BarModel(values2.get(2),color2));
        stackedBarModel2.addBar(new BarModel(values2.get(3),color3));
        stackedBarModel2.addBar(new BarModel(values2.get(4),color4));


        stackedBarChart.addBar(stackedBarModel2);
        stackedBarChart.addBar(stackedBarModel1);
        stackedBarChart.addBar(stackedBarModel0);

        stackedBarChart.startAnimation();
    }
}