package com.example.fahrtenbuch.ui.rides;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentCreateRideBinding;
import com.example.fahrtenbuch.databinding.FragmentRidesBinding;

import java.time.Instant;
import java.util.Date;


public class createRideFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private FragmentCreateRideBinding binding;
    Date today;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateRideBinding.inflate(inflater, container, false);

        binding.editDateCard.setOnClickListener(this);

        today = new Date();
        String output = today.getDate() + "." + (today.getMonth()+1) + "." + (today.getYear()+1900);
        binding.editDateText.setText(output);

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if (view == binding.editDateCard) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(binding.getRoot().getContext(), this, today.getHours(), today.getMinutes(), true);
            DatePickerDialog datePickerDialog = new DatePickerDialog(binding.getRoot().getContext(), this, today.getYear()+1900, today.getMonth()+1, today.getDate());
            timePickerDialog.show();
            datePickerDialog.show();

        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String output = day + "." + month + "." + year;
        binding.editDateText.setText(output);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        String output = hour + "." + minute;
        binding.editHourText.setText(output);

    }
}