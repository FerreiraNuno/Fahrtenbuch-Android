package com.example.fahrtenbuch.ui.statistics;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fahrtenbuch.databinding.DiagramTextBinding;

public class TextDiagram extends Diagrams{
    private DiagramTextBinding binding;
    private float value;
    private String title;

    TextDiagram(){}

    TextDiagram(String title, float value) {
        this.value = value;
        this.title = title;
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DiagramTextBinding.inflate(inflater,container,false);

        binding.textView.setText(value + "€");
        binding.titleText.setText(title);

        return binding.getRoot();
    }
}
