package com.example.fahrtenbuch.ui.rides;

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
import com.example.fahrtenbuch.databinding.FragmentEditRideBinding;
import com.example.fahrtenbuch.db.Database;
import com.example.fahrtenbuch.db.FahrtItem;

import java.util.Date;


public class EditRideFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterView.OnItemSelectedListener {
    private FragmentEditRideBinding binding;
    Date date = null;
    int rideType = 5;
    int rideId;
    Database db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditRideBinding.inflate(inflater, container, false);
        db = MainActivity.db;

        binding.editDateText.setOnClickListener(this);
        binding.editHourText.setOnClickListener(this);
        binding.finishButton.setOnClickListener(this);
        binding.deleteRide.setOnClickListener(this);


        // get FahrtItem
        rideId = EditRideFragmentArgs.fromBundle(getArguments()).getRideId();
        FahrtItem fahrtItem = db.getRide(rideId);
        rideId = fahrtItem.getRideId();
        System.out.println("id: " + fahrtItem.getRideId());
        System.out.println("datum: " + fahrtItem.getDatum());
        System.out.println("distanz: " + fahrtItem.getRideDistance());
        // System.out.println("ride ID: " + rideId);
        // Set km field
        binding.editKmText.setText(String.valueOf(fahrtItem.getRideDistance()));
        //set Datepicker defaults
        date = fahrtItem.getDatum();
        String output = String.format("%02d", date.getDate()) + "." + String.format("%02d", date.getMonth()+1) + "." + (date.getYear()+1900);
        binding.editDateText.setText(output);
        output = String.format("%02d", date.getHours()) + "." + String.format("%02d", date.getMinutes());
        binding.editHourText.setText(output);
        //set Spinner items
        Spinner rideTypeSpinner = binding.categorySpinner;
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(binding.getRoot().getContext(), R.array.ride_categoríes, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rideTypeSpinner.setAdapter(arrayAdapter);
        rideTypeSpinner.setSelection(fahrtItem.getRideType()-1);
        rideTypeSpinner.setOnItemSelectedListener(this);
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
        } else if (view == binding.deleteRide) {
            db.deleteRide(rideId);
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else if (view == binding.finishButton) {
            if (!binding.editKmText.getText().toString().equals("")) {
                int distanceValue = Integer.parseInt(binding.editKmText.getText().toString());
                if (distanceValue > 100000) {
                    Toast.makeText(getActivity(), "Distanz ist zu hoch!", Toast.LENGTH_LONG).show();
                    return;
                }
                db.updateRide(rideId, date.getTime(), distanceValue, rideType);
                Navigation.findNavController(binding.getRoot()).navigateUp();
            } else {
                Toast.makeText(getActivity(), "Bitte erst gefahrene distanz eintragen!",
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
            case "Keine Kategorie":
                rideType = 5;
                break;
            case "Arbeit":
                rideType = 1;
                break;
            case "Uni":
                rideType = 2;
                break;
            case "Sport":
                rideType = 3;
                break;
            case "Einkauf":
                rideType = 4;
                break;
            default:
                rideType = 5;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}