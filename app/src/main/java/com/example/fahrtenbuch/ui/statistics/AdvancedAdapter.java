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

import com.example.fahrtenbuch.MainActivity;
import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.db.Database;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AdvancedAdapter extends ArrayAdapter<Diagrams> {
    private List<Diagrams> list;
    Database database;
    public AdvancedAdapter(@NonNull Context context, ArrayList<Diagrams> list) {
        super(context, 0, list);
        this.list = list;
        database = MainActivity.db;
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
            while (valuesf.size() < 5) {
                valuesf.add(0.0f);
            }
            PieDiagram pieDiagram = new PieDiagram("Deine Fahrten im letzten Jahr (KM)",valuesf.get(0),valuesf.get(1),valuesf.get(2),valuesf.get(3),valuesf.get(4),"Arbeit","Uni","Sport","Einkauf","Sonstiges");

            element = pieDiagram.onCreateView(inflater,parent,new Bundle());

        }
        if (position == 1) {
            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                values.add(database.getKMPerYear(LocalDate.now().getYear() - i));
            }

            Collections.reverse(values);
            List<Float> valuesf = new ArrayList<>();

            for (int i:values) {
                valuesf.add((float) i);
            }

            BarGraph barGraph = new BarGraph("Deine gefahrenen KM in den letzten Jahren bis heute",valuesf);
            element = barGraph.onCreateView(inflater,parent,new Bundle());
        }
        if (position == 2) {
            List<Integer> values0 = database.getKMInTime(LocalDate.now().getYear() + " 01 01", LocalDate.now().getYear() + " 01 31");
            List<Integer> values1 = database.getKMInTime(LocalDate.now().getYear() + " 02 01", LocalDate.now().getYear() + " 02 31");
            List<Integer> values2 = database.getKMInTime(LocalDate.now().getYear() + " 03 01", LocalDate.now().getYear() + " 03 31");
            List<Integer> values3 = database.getKMInTime(LocalDate.now().getYear() + " 04 01", LocalDate.now().getYear() + " 04 31");
            List<Integer> values4 = database.getKMInTime(LocalDate.now().getYear() + " 05 01", LocalDate.now().getYear() + " 05 31");
            List<Integer> values5 = database.getKMInTime(LocalDate.now().getYear() + " 06 01", LocalDate.now().getYear() + " 06 31");
            List<Integer> values6 = database.getKMInTime(LocalDate.now().getYear() + " 07 01", LocalDate.now().getYear() + " 07 31");
            List<Integer> values7 = database.getKMInTime(LocalDate.now().getYear() + " 08 01", LocalDate.now().getYear() + " 08 31");
            List<Integer> values8 = database.getKMInTime(LocalDate.now().getYear() + " 09 01", LocalDate.now().getYear() + " 09 31");
            List<Integer> values9 = database.getKMInTime(LocalDate.now().getYear() + " 10 01", LocalDate.now().getYear() + " 10 31");
            List<Integer> values10 = database.getKMInTime(LocalDate.now().getYear() + " 11 01", LocalDate.now().getYear() + " 11 31");
            List<Integer> values11 = database.getKMInTime(LocalDate.now().getYear() + " 12 01", LocalDate.now().getYear() + " 12 31");

            float val0 = values0.stream().mapToInt(Integer::intValue).sum();
            float val1 = values1.stream().mapToInt(Integer::intValue).sum();
            float val2 = values2.stream().mapToInt(Integer::intValue).sum();
            float val3 = values3.stream().mapToInt(Integer::intValue).sum();
            float val4 = values4.stream().mapToInt(Integer::intValue).sum();
            float val5 = values5.stream().mapToInt(Integer::intValue).sum();
            float val6 = values6.stream().mapToInt(Integer::intValue).sum();
            float val7 = values7.stream().mapToInt(Integer::intValue).sum();
            float val8 = values8.stream().mapToInt(Integer::intValue).sum();
            float val9 = values9.stream().mapToInt(Integer::intValue).sum();
            float val10 = values10.stream().mapToInt(Integer::intValue).sum();
            float val11 = values11.stream().mapToInt(Integer::intValue).sum();

            List<Float> values = Arrays.asList(val0,val1,val2,val3,val4,val5,val6,val7,val8,val9,val10,val11);
            LineGraph lineGraph = new LineGraph("Deine gefahrenen KM in diesem Jahr",values);
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
            List<Integer> values = database.getAllExpensesPerTypeTimed(LocalDate.now().minusYears(1).toString().replace("-"," "),LocalDate.now().toString().replace("-"," "));
            List<Float> valuesf = new ArrayList<>();

            for (int i:values) {
                valuesf.add((float) i);
            }
            while (valuesf.size() < 5) {
                valuesf.add(0.0f);
            }
            PieDiagram pieDiagram = new PieDiagram("Deine Ausgaben im letzten Jahr (€)",valuesf.get(0),valuesf.get(1),valuesf.get(2),valuesf.get(3),valuesf.get(4),"Tanken","Versicherung","Steuer","Werkstatt","Sonstiges");

            element = pieDiagram.onCreateView(inflater,parent,new Bundle());

        }
        if (position == 5) {
            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                values.add(database.getExpensesInTime(LocalDate.now().getYear()-i + " 01 01", LocalDate.now().getYear()-i + " 12 31"));
            }

            Collections.reverse(values);

            List<Float> valuesf = new ArrayList<>();

            for (int i:values) {
                valuesf.add((float) i);
            }
            BarGraph barGraph = new BarGraph("Deine Ausgaben in den letzten Jahren bis heute",valuesf);
            element = barGraph.onCreateView(inflater,parent,new Bundle());
        }
        if (position == 6) {
            List<Integer> values = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                values.add(database.getExpensesInTime(LocalDate.now().getYear() + " 0" + i + " 01",LocalDate.now().getYear() + " 0" + i + " 31"));
            }
            values.add(database.getExpensesInTime(LocalDate.now().getYear() + " 10 01",LocalDate.now().getYear() + " 10 31"));
            values.add(database.getExpensesInTime(LocalDate.now().getYear() + " 11 01",LocalDate.now().getYear() + " 11 31"));
            values.add(database.getExpensesInTime(LocalDate.now().getYear() + " 12 01",LocalDate.now().getYear() + " 12 31"));


            List<Float> valuesf = new ArrayList<>();

            for (int i:values) {
                valuesf.add((float) i);
            }

            System.out.println(valuesf + "\n"+ values);

            LineGraph lineGraph = new LineGraph("Deine Ausgaben in diesem Jahr",valuesf);
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
