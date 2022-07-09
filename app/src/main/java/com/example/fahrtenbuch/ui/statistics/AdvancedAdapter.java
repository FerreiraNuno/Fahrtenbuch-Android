package com.example.fahrtenbuch.ui.statistics;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.db.Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AdvancedAdapter extends ArrayAdapter<Diagrams> {
    private List<Diagrams> list;
    Database database;
    public AdvancedAdapter(@NonNull Context context, ArrayList<Diagrams> list) {
        super(context, 0, list);
        this.list = list;
        database = new Database(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View element = null;

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (position == 0) {
            List<Integer> values = database.getKMInTime(LocalDate.now().minusYears(1).toString().replace("-"," "),LocalDate.now().toString().replace("-"," "));
            List<Float> valuesf = new ArrayList<>();

            for (int i:values) {
                valuesf.add((float) i);
            }
            PieDiagram pieDiagram = new PieDiagram("Deine Fahrten im letzten Jahr (KM)",valuesf.get(0),valuesf.get(1),valuesf.get(2),valuesf.get(3),valuesf.get(4),"Arbeit","Uni","Sport","Einkauf","Sonstiges");

            element = pieDiagram.onCreateView(inflater,parent,new Bundle());

        }
        if (position == 1) {
            List<Float> values = Arrays.asList(12.3f,234.5f,324.5f,321.5f,89.3f,80f,45f,87f,89.3f,47f,301f,381f);
            BarGraph barGraph = new BarGraph("Deine KM pro Monat",values);
            element = barGraph.onCreateView(inflater,parent,new Bundle());
        }
        if (position == 2) {
            List<Float> values = Arrays.asList(12.3f,234.5f,324.5f,321.5f,89.3f,80f,45f,87f,89.3f,47f,301f,381f);
            LineGraph lineGraph = new LineGraph("Deine KM pro Monat",values);
            element = lineGraph.onCreateView(inflater,parent,new Bundle());
        }
        if (position == 3) {
            List<Float> values0 = Arrays.asList(21f,32f,53f,95f,83f,45f,49f,57f,21f,15f,74f,46f);
            List<Float> values1 = Arrays.asList(31f,52f,83f,65f,93f,85f,79f,57f,41f,25f,44f,76f);
            List<Float> values2 = Arrays.asList(21f,32f,53f,95f,83f,45f,49f,57f,21f,15f,74f,46f);
            List<Float> values3 = Arrays.asList(31f,52f,83f,65f,93f,85f,79f,57f,41f,25f,44f,76f);
            List<Float> values4 = Arrays.asList(51f,72f,23f,35f,53f,15f,79f,47f,71f,45f,64f,16f);
            StackedBarGraph stackedBarGraph = new StackedBarGraph("Deine KM pro Kategorie pro Monat (1.1 - 31.12)",values0,values1,values2,values3,values4,"Arbeit","Einkauf","Sport","Uni","Sonstiges");
            element = stackedBarGraph.onCreateView(inflater,parent,new Bundle());
        }
        if (position == 4) {
            PieDiagram pieDiagram = new PieDiagram("Deine Ausgaben nach Kategorie",40,40,20,20,10,"Werkstatt","Steuer","Tanken","Versicherung","Sonstiges");

            element = pieDiagram.onCreateView(inflater,parent,new Bundle());

        }
        if (position == 5) {
            List<Float> values = Arrays.asList(12.3f,234.5f,324.5f,321.5f,89.3f,80f,45f,87f,89.3f,47f,301f,381f);
            BarGraph barGraph = new BarGraph("Deine Ausgaben pro Monat",values);
            element = barGraph.onCreateView(inflater,parent,new Bundle());
        }
        if (position == 6) {
            List<Float> values = Arrays.asList(12.3f,234.5f,324.5f,321.5f,89.3f,80f,45f,87f,89.3f,47f,301f,381f);
            LineGraph lineGraph = new LineGraph("Deine Ausgaben pro Monat",values);
            element = lineGraph.onCreateView(inflater,parent,new Bundle());
        }
        if (position == 7) {
            List<Float> values0 = Arrays.asList(21f,32f,53f,95f,83f,45f,49f,57f,21f,15f,74f,46f);
            List<Float> values1 = Arrays.asList(31f,52f,83f,65f,93f,85f,79f,57f,41f,25f,44f,76f);
            List<Float> values2 = Arrays.asList(21f,32f,53f,95f,83f,45f,49f,57f,21f,15f,74f,46f);
            List<Float> values3 = Arrays.asList(31f,52f,83f,65f,93f,85f,79f,57f,41f,25f,44f,76f);
            List<Float> values4 = Arrays.asList(51f,72f,23f,35f,53f,15f,79f,47f,71f,45f,64f,16f);
            StackedBarGraph stackedBarGraph = new StackedBarGraph("Deine Ausgaben pro Kategorie pro Monat (1.1 - 31.12)",values0,values1,values2,values3,values4,"Werkstatt","Steuer","Tanken","Versicherung","Sonstiges");
            element = stackedBarGraph.onCreateView(inflater,parent,new Bundle());
        }
        return element;
    }
}
