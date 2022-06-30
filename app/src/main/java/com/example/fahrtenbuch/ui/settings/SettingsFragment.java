package com.example.fahrtenbuch.ui.settings;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fahrtenbuch.R;
import com.example.fahrtenbuch.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private FragmentSettingsBinding binding;

    static String bluetoothBeaconMacAddress = "";
    static Location lastEndpointBluetoothBeacon = new Location("emtpy Location");

    //Context myContext = SettingsFragment.getContext();
    //PushNotificationHandler pushNotifier = new PushNotificationHandler(myContext);

    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        binding.selectBluetooth.setOnClickListener(this);
        binding.selectGps.setOnClickListener(this);

        Switch pushMessages = binding.settingsPushMessages;



        //dis-/allow App to use push-Messages
        pushMessages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    // allow App to use push-Messages
                } else {
                    // disallow App to use push-Messages
                }
            }
        });

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onClick(View view) {
        if (view == binding.selectBluetooth) {
            callSelectBluetoothFragment();
        } else if (view == binding.selectGps) {
            callSelectGpsFragment();
        }
    }

    private void callSelectGpsFragment() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container,new SelectGpsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commit();
    }

    void callSelectBluetoothFragment() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment_container,new SelectBluetoothFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commit();
    }
}
