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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.fahrtenbuch.databinding.FragmentSelectBluetoothBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SelectBluetoothFragment extends Fragment {

    private FragmentSelectBluetoothBinding binding;
    private BluetoothAdapter bluetoothAdapter;

    List<String> deviceNames = new ArrayList<>();
    List<String> macAddresses = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectBluetoothBinding.inflate(inflater, container, false);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ListView listView = binding.easylist;

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
                    deviceNames.add(deviceName);
                    macAddresses.add(macAddress);
                }
            }
        }

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,deviceNames);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                SettingsFragment.bluetoothBeaconMacAddress = macAddresses.get(position);
                Toast.makeText(getContext(),"Die Ausgewählte Mac-Adresse ist: " + SettingsFragment.bluetoothBeaconMacAddress,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        View root = binding.getRoot();
        return root;
    }
}
