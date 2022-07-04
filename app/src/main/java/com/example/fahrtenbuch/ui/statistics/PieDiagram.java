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

import java.util.Locale;

public class PieDiagram extends Diagrams {
    private float value0, value1, value2, value3, value4;
    private String name0, name1, name2, name3, name4;

    PieDiagram() {}

    PieDiagram(float value0, float value1, float value2, float value3, float value4, String name0, String name1, String name2, String name3, String name4) {
        this.value0 = value0;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.name0 = name0;
        this.name1 = name1;
        this.name2 = name2;
        this.name3 = name3;
        this.name4 = name4;
    }

    private PieDiagramBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        binding = PieDiagramBinding.inflate(inflater,container,false);

        setData();

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void setData()
    {
        // Set the percentage
        binding.value0.setText(Float.toString(value0));
        binding.value1.setText(Float.toString(value1));
        binding.value2.setText(Float.toString(value2));
        binding.value3.setText(Float.toString(value3));
        binding.value4.setText(Float.toString(value4));

        // Set Pictures
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
        binding.image0.setImageResource(id0);
        binding.image1.setImageResource(id1);
        binding.image2.setImageResource(id2);
        binding.image3.setImageResource(id3);
        binding.image4.setImageResource(id4);

        // Set the data and color to the pie chart
        binding.piechart.addPieSlice(
                new PieModel(
                        name0,
                        Float.parseFloat(binding.value0.getText().toString()), Color.parseColor("#76E3F1")));
        binding.piechart.addPieSlice(
                new PieModel(
                        name1,
                        Float.parseFloat(binding.value1.getText().toString()),Color.parseColor("#6EA0C8")));
        binding.piechart.addPieSlice(
                new PieModel(
                        name2,
                        Float.parseFloat(binding.value2.getText().toString()),Color.parseColor("#5D24C3")));
        binding.piechart.addPieSlice(
                new PieModel(
                        name3,
                        Float.parseFloat(binding.value3.getText().toString()),Color.parseColor("#AE8AED")));
        binding.piechart.addPieSlice(
                new PieModel(
                        name4,
                        Float.parseFloat(binding.value4.getText().toString()),Color.parseColor("#129C8F")));

        // To animate the pie chart
        binding.piechart.startAnimation();
    }
}


