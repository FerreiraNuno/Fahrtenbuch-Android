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
            List<Float> values = database.getPricePerKm(LocalDate.now().minusYears(1).toString().replace("-"," "),LocalDate.now().toString().replace("-"," "),12);

            float value = (values.get(0) + values.get(1)) / 2;

            TextDiagram textDiagram = new TextDiagram("Preis pro km in den letzten 365 Tage",value);
            element = textDiagram.onCreateView(inflater,parent,new Bundle());
        }
        if (position == 1) {
            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                values.add(database.getKMPerYear(LocalDate.now().getYear() - i));
            }

            Collections.reverse(values);
            List<Float> valuesf = new ArrayList<>();

            for (int value : values) {
                valuesf.add((float) value);
            }

            BarGraph barGraph = new BarGraph("Deine gefahrenen km in den letzten Jahren",valuesf);
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
            LineGraph lineGraph = new LineGraph("Deine gefahrenen km in diesem Jahr",values);
            element = lineGraph.onCreateView(inflater,parent,new Bundle());
        }
        if (position == 3) {
            List<Integer> values0 =  database.getKMInTime(LocalDate.now().minusMonths(1).toString().replace("-", " "),LocalDate.now().toString().replace("-", " "));
            List<Integer> values1 =  database.getKMInTime(LocalDate.now().minusMonths(2).toString().replace("-", " "),LocalDate.now().minusMonths(1).toString().replace("-", " "));
            List<Integer> values2 =  database.getKMInTime(LocalDate.now().minusMonths(3).toString().replace("-", " "),LocalDate.now().minusMonths(2).toString().replace("-", " "));

            while (values0.size() < 5) {
                values0.add(0);
            }
            while (values1.size() < 5) {
                values1.add(0);
            }
            while (values2.size() < 5) {
                values2.add(0);
            }

            List<Float> valuesf0 = new ArrayList<>();

            for (int i:values0) {
                valuesf0.add((float) i);
            }

            List<Float> valuesf1 = new ArrayList<>();

            for (int i:values1) {
                valuesf1.add((float) i);
            }

            List<Float> valuesf2 = new ArrayList<>();

            for (int i:values2) {
                valuesf2.add((float) i);
            }


            StackedBarGraph stackedBarGraph = new StackedBarGraph("Deine gefahrenen km in den letzten 3 Monaten nach Kategorie",valuesf0,valuesf1,valuesf2,"Arbeit","Uni","Sport","Einkauf","Auto");
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
            PieDiagram pieDiagram = new PieDiagram("Deine Ausgaben im letzten Jahr nach Kategorie",valuesf.get(0),valuesf.get(1),valuesf.get(2),valuesf.get(3),valuesf.get(4),"Tanken","Versicherung","Steuer","Werkstatt","Sonstiges");

            element = pieDiagram.onCreateView(inflater,parent,new Bundle());

        }
        if (position == 5) {
            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
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
            List<Integer> values0 =  database.getAllExpensesPerTypeTimed(LocalDate.now().minusMonths(1).toString().replace("-", " "),LocalDate.now().toString().replace("-", " "));
            List<Integer> values1 =  database.getAllExpensesPerTypeTimed(LocalDate.now().minusMonths(2).toString().replace("-", " "),LocalDate.now().minusMonths(1).toString().replace("-", " "));
            List<Integer> values2 =  database.getAllExpensesPerTypeTimed(LocalDate.now().minusMonths(3).toString().replace("-", " "),LocalDate.now().minusMonths(2).toString().replace("-", " "));

            while (values0.size() < 5) {
                values0.add(0);
            }
            while (values1.size() < 5) {
                values1.add(0);
            }
            while (values2.size() < 5) {
                values2.add(0);
            }

            List<Float> valuesf0 = new ArrayList<>();

            for (int i:values0) {
                valuesf0.add((float) i);
            }

            List<Float> valuesf1 = new ArrayList<>();

            for (int i:values1) {
                valuesf1.add((float) i);
            }

            List<Float> valuesf2 = new ArrayList<>();

            for (int i:values2) {
                valuesf2.add((float) i);
            }


            StackedBarGraph stackedBarGraph = new StackedBarGraph("Deine Ausgaben in den letzten 3 Monaten (-> bis heute)",valuesf0,valuesf1,valuesf2,"Tanken","Versicherung","Steuer","Werkstatt","Sonstiges");
            element = stackedBarGraph.onCreateView(inflater,parent,new Bundle());
        }
        if (position == 8) {
            List<Integer> values = database.getKMInTime(LocalDate.now().minusYears(1).toString().replace("-"," "),LocalDate.now().toString().replace("-"," "));
            List<Float> valuesf = new ArrayList<>();

            for (int i:values) {
                valuesf.add((float) i);
            }
            while (valuesf.size() < 5) {
                valuesf.add(0.0f);
            }
            PieDiagram pieDiagram = new PieDiagram("Deine Fahrten im letzten Jahr (km)",valuesf.get(0),valuesf.get(1),valuesf.get(2),valuesf.get(3),valuesf.get(4),"Arbeit","Uni","Sport","Einkauf","Auto");

            element = pieDiagram.onCreateView(inflater,parent,new Bundle());
        }

        return element;
    }
}
