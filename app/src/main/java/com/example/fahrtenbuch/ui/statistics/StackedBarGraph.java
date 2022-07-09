package com.example.fahrtenbuch.ui.statistics;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.StackedBarGraphBinding;

import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.util.List;
import java.util.Locale;

public class StackedBarGraph extends Diagrams {
    private StackedBarGraphBinding binding;

    String title;
    List<Float> values0, values1, values2, values3, values4;

    String name0, name1, name2, name3, name4;

    StackedBarGraph() {}

    StackedBarGraph(String title, List<Float> values0, List<Float> values1, List<Float> values2, List<Float> values3, List<Float> values4, String name0, String name1, String name2, String name3, String name4) {
        this.title = title;
        this.values0 = values0;
        this.values1 = values1;
        this.values2 = values2;
        this.values3 = values3;
        this.values4 = values4;
        this.name0 = name0;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.name4 = name4;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = StackedBarGraphBinding.inflate(inflater,container,false);

        binding.titleStackedBarChart.setText(title);

        setData();

        return binding.getRoot();
    }

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

        StackedBarModel jan = new StackedBarModel("Januar");
        StackedBarModel feb = new StackedBarModel("Februar");
        StackedBarModel mae = new StackedBarModel("März");
        StackedBarModel apr = new StackedBarModel("April");
        StackedBarModel mai = new StackedBarModel("Mai");
        StackedBarModel jun = new StackedBarModel("Juni");
        StackedBarModel jul = new StackedBarModel("Juli");
        StackedBarModel aug = new StackedBarModel("August");
        StackedBarModel sep = new StackedBarModel("September");
        StackedBarModel okt = new StackedBarModel("Oktober");
        StackedBarModel nov = new StackedBarModel("November");
        StackedBarModel dez = new StackedBarModel("Dezember");


        jan.addBar(new BarModel(values0.get(0),color0));
        jan.addBar(new BarModel(values1.get(0),color1));
        jan.addBar(new BarModel(values2.get(0),color2));
        jan.addBar(new BarModel(values3.get(0),color3));
        jan.addBar(new BarModel(values4.get(0),color4));

        feb.addBar(new BarModel(values0.get(1),color0));
        feb.addBar(new BarModel(values1.get(1),color1));
        feb.addBar(new BarModel(values2.get(1),color2));
        feb.addBar(new BarModel(values3.get(1),color3));
        feb.addBar(new BarModel(values4.get(1),color4));

        mae.addBar(new BarModel(values0.get(2),color0));
        mae.addBar(new BarModel(values1.get(2),color1));
        mae.addBar(new BarModel(values2.get(2),color2));
        mae.addBar(new BarModel(values3.get(2),color3));
        mae.addBar(new BarModel(values4.get(2),color4));

        apr.addBar(new BarModel(values0.get(3),color0));
        apr.addBar(new BarModel(values1.get(3),color1));
        apr.addBar(new BarModel(values2.get(3),color2));
        apr.addBar(new BarModel(values3.get(3),color3));
        apr.addBar(new BarModel(values4.get(3),color4));

        mai.addBar(new BarModel(values0.get(4),color0));
        mai.addBar(new BarModel(values1.get(4),color1));
        mai.addBar(new BarModel(values2.get(4),color2));
        mai.addBar(new BarModel(values3.get(4),color3));
        mai.addBar(new BarModel(values4.get(4),color4));

        jun.addBar(new BarModel(values0.get(5),color0));
        jun.addBar(new BarModel(values1.get(5),color1));
        jun.addBar(new BarModel(values2.get(5),color2));
        jun.addBar(new BarModel(values3.get(5),color3));
        jun.addBar(new BarModel(values4.get(5),color4));

        jul.addBar(new BarModel(values0.get(6),color0));
        jul.addBar(new BarModel(values1.get(6),color1));
        jul.addBar(new BarModel(values2.get(6),color2));
        jul.addBar(new BarModel(values3.get(6),color3));
        jul.addBar(new BarModel(values4.get(6),color4));

        aug.addBar(new BarModel(values0.get(7),color0));
        aug.addBar(new BarModel(values1.get(7),color1));
        aug.addBar(new BarModel(values2.get(7),color2));
        aug.addBar(new BarModel(values3.get(7),color3));
        aug.addBar(new BarModel(values4.get(7),color4));

        sep.addBar(new BarModel(values0.get(8),color0));
        sep.addBar(new BarModel(values1.get(8),color1));
        sep.addBar(new BarModel(values2.get(8),color2));
        sep.addBar(new BarModel(values3.get(8),color3));
        sep.addBar(new BarModel(values4.get(8),color4));

        okt.addBar(new BarModel(values0.get(9),color0));
        okt.addBar(new BarModel(values1.get(9),color1));
        okt.addBar(new BarModel(values2.get(9),color2));
        okt.addBar(new BarModel(values3.get(9),color3));
        okt.addBar(new BarModel(values4.get(9),color4));

        nov.addBar(new BarModel(values0.get(10),color0));
        nov.addBar(new BarModel(values1.get(10),color1));
        nov.addBar(new BarModel(values2.get(10),color2));
        nov.addBar(new BarModel(values3.get(10),color3));
        nov.addBar(new BarModel(values4.get(10),color4));

        dez.addBar(new BarModel(values0.get(11),color0));
        dez.addBar(new BarModel(values1.get(11),color1));
        dez.addBar(new BarModel(values2.get(11),color2));
        dez.addBar(new BarModel(values3.get(11),color3));
        dez.addBar(new BarModel(values4.get(11),color4));

        stackedBarChart.addBar(jan);
        stackedBarChart.addBar(feb);
        stackedBarChart.addBar(mae);
        stackedBarChart.addBar(apr);
        stackedBarChart.addBar(mai);
        stackedBarChart.addBar(jun);
        stackedBarChart.addBar(jul);
        stackedBarChart.addBar(aug);
        stackedBarChart.addBar(sep);
        stackedBarChart.addBar(okt);
        stackedBarChart.addBar(nov);
        stackedBarChart.addBar(dez);

        stackedBarChart.startAnimation();
    }
}