package com.example.fahrtenbuch.ui.settings;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.fahrtenbuch.databinding.FragmentSelectBluetoothBinding;

import java.util.Set;

public class SelectBluetoothFragment extends Fragment implements View.OnClickListener {

    private FragmentSelectBluetoothBinding binding;
    private BluetoothAdapter bluetoothAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectBluetoothBinding.inflate(inflater, container, false);

        binding.saveInput.setOnClickListener(this);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if (getActivity().checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                getActivity().requestPermissions(new String[] {Manifest.permission.BLUETOOTH_CONNECT}, 1);
            }
        }
        else {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    String deviceName = device.getName();
                    String macAddress = device.getAddress();

                    binding.names.append(deviceName + "\n");
                    binding.macAddresses.append(macAddress + "\n");
                }
            }
        }

        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onClick(View view) {
        SettingsFragment.bluetoothBeaconMacAddress = binding.macAdressInputField.getText().toString();
    }

}
