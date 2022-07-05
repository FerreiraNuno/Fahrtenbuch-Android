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
import com.example.fahrtenbuch.db.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SelectBluetoothFragment extends Fragment {
    private FragmentSelectBluetoothBinding binding;
    private String bluetoothBeaconMacAddress;
    List<String> deviceNames = new ArrayList<>();
    List<String> macAddresses = new ArrayList<>();
    Database db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectBluetoothBinding.inflate(inflater, container, false);

        db = new Database(binding.getRoot().getContext());
        bluetoothBeaconMacAddress = db.getBluetoothDevice();
        Toast.makeText(getContext(),"Die Ausgewählte Mac-Adresse ist: " + bluetoothBeaconMacAddress,Toast.LENGTH_LONG).show();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ListView listView = binding.easylist;

        if (getActivity().checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                getActivity().requestPermissions(new String[] {Manifest.permission.BLUETOOTH_CONNECT}, 1);
            }
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String macAddress = device.getAddress();
                System.out.println(deviceName);
                if (Objects.equals(macAddress, bluetoothBeaconMacAddress)) {
                    System.out.println("here");
                    deviceName += " (ausgewählt)";
                }
                deviceNames.add(deviceName);
                macAddresses.add(macAddress);
            }
        }



        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, deviceNames);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            db.insertBluetoothDevice(macAddresses.get(position));
            bluetoothBeaconMacAddress = macAddresses.get(position);
            for (int i = 0; i < deviceNames.size(); i++) {
                String newString = deviceNames.get(i).replaceAll(" \\(ausgewählt\\)","");
                deviceNames.set(i, newString);
            }
            String newName = deviceNames.get(position) + " (ausgewählt)";
            deviceNames.set(position, newName);
            arrayAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(),"Die Ausgewählte Mac-Adresse ist: " + bluetoothBeaconMacAddress,Toast.LENGTH_LONG).show();
        });

        return binding.getRoot();
    }
}
