package com.example.fahrtenbuch.ui.rides;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fahrtenbuch.databinding.FragmentCreateRideBinding;
import com.example.fahrtenbuch.db.Database;

import java.util.Date;


public class createRideFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private FragmentCreateRideBinding binding;
    Date date = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateRideBinding.inflate(inflater, container, false);

        binding.editDateCard.setOnClickListener(this);
        binding.finishButton.setOnClickListener(this);

        date = new Date();
        String output = date.getDate() + "." + (date.getMonth()+1) + "." + (date.getYear()+1900);
        binding.editDateText.setText(output);

        output = date.getHours() + ":" + date.getMinutes();
        binding.editHourText.setText(output);

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        if (view == binding.editDateCard) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(binding.getRoot().getContext(), this, date.getHours(), date.getMinutes(), true);
            DatePickerDialog datePickerDialog = new DatePickerDialog(binding.getRoot().getContext(), this, date.getYear() + 1900, date.getMonth() + 1, date.getDate());
            timePickerDialog.show();
            datePickerDialog.show();
        } else if (view == binding.finishButton) {
            if (!binding.editKmText.getText().toString().equals("")) {
                int distanceValue = Integer.parseInt(binding.editKmText.getText().toString());
                Database db = new Database(binding.getRoot().getContext());
                db.insert(date.getTime(), distanceValue);
                getParentFragmentManager().popBackStackImmediate();
            } else {
                Toast.makeText(getActivity(), "Bitte erst gefahrene distanz eintragen!",
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
}