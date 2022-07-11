package com.example.fahrtenbuch.ui.expenses;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.fahrtenbuch.MainActivity;
import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentCreateItemBinding;
import com.example.fahrtenbuch.db.Database;

import java.util.Date;


public class CreateExpenseFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    private FragmentCreateItemBinding binding;
    Date date = null;
    int expenseType = 5;
    int intervalType = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateItemBinding.inflate(inflater, container, false);

        binding.editDateText.setOnClickListener(this);
        binding.editHourText.setOnClickListener(this);
        binding.finishButton.setOnClickListener(this);

        // Edit Text Fields
        binding.fragmentTitle.setText("Ausgabe\nbearbeiten");
        binding.editValueText.setHint("Betrag");
        binding.unitTxtView.setText("€");
        binding.saveButtonText.setText("Ausgabe Speichern");
        //set Datepicker defaults
        date = new Date();
        String output = String.format("%02d", date.getDate()) + "." + String.format("%02d", date.getMonth()) + "." + (date.getYear()+1900);
        binding.editDateText.setText(output);
        output = String.format("%02d", date.getHours()) + "." + String.format("%02d", date.getMinutes());
        binding.editHourText.setText(output);
        //set Spinner items category
        Spinner expenseTypeSpinner = binding.categorySpinner;
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.expense_categoríes, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseTypeSpinner.setAdapter(arrayAdapter);
        expenseTypeSpinner.setSelection(4);
        expenseTypeSpinner.setOnItemSelectedListener(this);

        //set Spinner items interval
        Spinner intervalSpinner = binding.intervalTypeSpinner;
        ArrayAdapter<CharSequence> arrayAdapterInterval = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.interval_categoríes, android.R.layout.simple_spinner_item);
        arrayAdapterInterval.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(arrayAdapterInterval);
        intervalSpinner.setSelection(3);
        intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = adapterView.getItemAtPosition(i).toString();
                switch(selection) {
                    case "Einmalig":
                        intervalType = 1;
                        break;
                    case "Monatlich":
                        intervalType = 2;
                        break;
                    case "Halbjährlich":
                        intervalType = 3;
                        break;
                    case "Jährlich":
                        intervalType = 4;
                        break;
                    case "Alle 2 Jahre":
                        intervalType = 5;
                        break;
                    default:
                        intervalType = 5;
                        //
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if (view == binding.editDateText) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(binding.getRoot().getContext(), this, date.getYear() + 1900, date.getMonth(), date.getDate());
            datePickerDialog.show();
        } else if (view == binding.editHourText) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(binding.getRoot().getContext(), this, date.getHours(), date.getMinutes(), true);
            timePickerDialog.show();
        } else if (view == binding.finishButton) {
            if (!binding.editValueText.getText().toString().equals("")) {
                int value = Integer.parseInt(binding.editValueText.getText().toString());
                if (value > 100000) {
                    Toast.makeText(getActivity(), "Betrag ist zu hoch!", Toast.LENGTH_LONG).show();
                    return;
                }
                Database db = MainActivity.db;
                db.insertExpense(value, date.getTime(), expenseType, intervalType);
                Navigation.findNavController(binding.getRoot()).navigateUp();
            } else {
                Toast.makeText(getActivity(), "Bitte erst Betrag eintrag!",
                        Toast.LENGTH_LONG).show();
            }

        }
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        date.setYear(year - 1900);
        date.setMonth(month);
        date.setDate(day);
        String output = String.format("%02d", day) + "." + String.format("%02d", month+1) + "." + year;
        binding.editDateText.setText(output);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        date.setHours(hour);
        date.setMinutes(minute);
        String output = String.format("%02d", hour) + "." + String.format("%02d", minute);
        binding.editHourText.setText(output);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selection = adapterView.getItemAtPosition(i).toString();
        switch(selection) {
            case "Tanken":
                binding.chooseInterval.setVisibility(View.INVISIBLE);
                expenseType = 1;
                break;
            case "Versicherung":
                expenseType = 2;
                binding.chooseInterval.setVisibility(View.VISIBLE);
                break;
            case "KFZ-Steuer":
                expenseType = 3;
                binding.chooseInterval.setVisibility(View.VISIBLE);
                break;
            case "Werkstatt":
                expenseType = 4;
                binding.chooseInterval.setVisibility(View.VISIBLE);
                break;
            default:
                binding.chooseInterval.setVisibility(View.INVISIBLE);
                expenseType = 5;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}