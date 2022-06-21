package com.example.fahrtenbuch.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fahrtenbuch.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private Switch sw;

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        Switch gps_bluetooth = binding.settingsConnectGps;

        if (gps_bluetooth != null) {
            gps_bluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // disallow App to use GPS and Bluetooth
                    } else {
                        // allow App to use GPS and Bluetooth
                    }
                }
            });
        }

        Switch push_messages = binding.settingsConnectGps;

        if (push_messages != null) {
            push_messages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // disallow App to use push-Messages
                    } else {
                        // allow App to use push-Messages
                    }
                }
            });
        }

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
