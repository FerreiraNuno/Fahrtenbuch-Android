package com.example.fahrtenbuch.ui.statistics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AdvancedAdapter extends ArrayAdapter<Diagrams> {
    public AdvancedAdapter(@NonNull Context context, ArrayList<Diagrams> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View element = null;

        if (element == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            PieDiagram pieDiagram = new PieDiagram();
            element = pieDiagram.onCreateView(inflater,parent,new Bundle());
        }



        return element;
    }
}
