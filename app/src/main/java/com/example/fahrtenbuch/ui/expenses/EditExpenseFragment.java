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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentEditRideBinding;
import com.example.fahrtenbuch.db.Database;
import com.example.fahrtenbuch.db.ExpenseItem;

import java.util.Date;


public class EditExpenseFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    private FragmentEditRideBinding binding;
    Date date = null;
    int expenseType = 5;
    int intervalType = 5;
    int expenseId;
    Database db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditRideBinding.inflate(inflater, container, false);
        db = new Database(binding.getRoot().getContext());

        binding.editDateText.setOnClickListener(this);
        binding.editHourText.setOnClickListener(this);
        binding.finishButton.setOnClickListener(this);
        binding.deleteRide.setOnClickListener(this);


        // get FahrtItem
        //expenseId = this.getArguments().getInt("expenseId");
        expenseId = EditExpenseFragmentArgs.fromBundle(getArguments()).getExpenseId();
        ExpenseItem expenseItem = db.getExpense(expenseId);
        // Edit Text Fields
        binding.fragmentTitle.setText("Ausgabe\nbearbeiten");
        binding.deleteItemText.setText("Ausgabe\nentfernen");
        binding.editKmText.setHint("Betrag");
        binding.saveButtonText.setText("Ausgabe Speichern");
        // Set € field
        binding.editKmText.setText(String.valueOf(expenseItem.getExpenseAmmount()));
        binding.unitTxt.setText("€");
        //set Datepicker defaults
        date = expenseItem.getDatum();
        String output = String.format("%02d", date.getDate()) + "." + String.format("%02d", date.getMonth()) + "." + (date.getYear()+1900);
        binding.editDateText.setText(output);
        output = String.format("%02d", date.getHours()) + "." + String.format("%02d", date.getMinutes());
        binding.editHourText.setText(output);

        //set Spinner items category
        Spinner rideTypeSpinner = binding.categorySpinner;
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.expense_categoríes, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rideTypeSpinner.setAdapter(arrayAdapter);
        rideTypeSpinner.setSelection(expenseItem.getExpenseType()-1);
        rideTypeSpinner.setOnItemSelectedListener(this);

        //set Spinner items interval
        Spinner intervalSpinner = binding.intervalTypeSpinner;
        ArrayAdapter<CharSequence> arrayAdapterInterval = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.interval_categoríes, android.R.layout.simple_spinner_item);
        arrayAdapterInterval.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        intervalSpinner.setAdapter(arrayAdapterInterval);
        intervalSpinner.setSelection(expenseItem.getExpenseInterval()-1); //TODO this doesnt work
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
            DatePickerDialog datePickerDialog = new DatePickerDialog(binding.getRoot().getContext(), this, date.getYear() + 1900, date.getMonth() + 1, date.getDate());
            datePickerDialog.show();
        } else if (view == binding.editHourText) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(binding.getRoot().getContext(), this, date.getHours(), date.getMinutes(), true);
            timePickerDialog.show();
        } else if (view == binding.deleteRide) {
                db.deleteExpense(expenseId);
                Navigation.findNavController(binding.getRoot()).navigateUp();
        } else if (view == binding.finishButton) {
            if (!binding.editKmText.getText().toString().equals("")) {
                int value = Integer.parseInt(binding.editKmText.getText().toString());
                db.updateExpense(expenseId, value, date.getTime(), expenseType, intervalType);
                Navigation.findNavController(binding.getRoot()).navigateUp();
            } else {
                Toast.makeText(getActivity(), "Bitte erst Betrag eintragen!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        date.setYear(year - 1900);
        date.setMonth(month-1);
        date.setDate(day);
        String output = String.format("%02d", day) + "." + String.format("%02d", month) + "." + year;
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